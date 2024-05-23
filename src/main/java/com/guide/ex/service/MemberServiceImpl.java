package com.guide.ex.service;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.member.MemberProfile;
import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;
import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.repository.member.MemberProfileRepository;
import com.guide.ex.repository.member.MemberRepository;
import com.guide.ex.repository.post.PostRepository;
import com.guide.ex.repository.search.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    @Value("${com.guide.upload.path}")
    private String uploadPath;

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final CommentRepository commentRepository;

    // 회원 등록 작업(회원가입)
    @Override
    public void signUp(MemberDTO memberDto) {
        String salt = generateSalt();
        String hashedPassword = hashPassword(memberDto.getPwd(), salt);

        memberDto.setSalt(salt);
        memberDto.setPwd(hashedPassword);

        Member member = modelMapper.map(memberDto, Member.class);
        memberRepository.save(member);
    }

    @Override
    public boolean login(String uid, String pwd) {
        Member member = memberRepository.findByUid(uid);

        if (member != null && !member.isBan()) {
            String hashedPassword = hashPassword(pwd, member.getSalt());
            if (hashedPassword.equals(member.getPwd())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Long setLoginSession(String uid) {
        Member member = memberRepository.findByUid(uid);
        return member.getMemberId();
    }

    @Override
    public boolean isIdAlreadyExists(String uid) {
        Member member = memberRepository.findByUid(uid);
        if (member != null)
            return true;
        return false;
    }

    @Override
    public void fileUpload(MemberProfileDTO dto, MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("업로드된 파일이 비어 있습니다.");
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }

        dto.setUuid(uuid);
        dto.setFileName(originalName);

        MemberProfile memberProfile = modelMapper.map(dto, MemberProfile.class);
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("해당하는 회원을 찾을 수 없습니다."));

        memberProfile.setMember(member);
        memberProfileRepository.save(memberProfile);
    }

    private String generateSalt() {
        return UUID.randomUUID().toString();
    }

    private String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
    }

    @Override
    public MemberDTO memberReadOne(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 회원을 찾을 수 없습니다."));

        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        MemberProfile memberProfile = memberProfileRepository.findByMember(member).orElse(null);

        if (memberProfile != null) {
            memberDTO.setProfileInfo(modelMapper.map(memberProfile, MemberProfileDTO.class));
        } else {
            memberDTO.setProfileInfo(null);
        }

        List<Post> memberPosts = postRepository.findTop5ByMemberOrderByPostIdDesc(member);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : memberPosts) {
            postDTOs.add(modelMapper.map(post, PostDTO.class));
        }

        memberDTO.setPosts(postDTOs);
        return memberDTO;
    }


    @Override
    public void profileRegister(MemberProfileDTO memberProfileDTO) {
        Member member = memberRepository.findById(memberProfileDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberProfileDTO.getMemberId()));

        MemberProfile memberProfile = modelMapper.map(memberProfileDTO, MemberProfile.class);
        memberProfile.setMember(member); // Member 설정
        memberProfileRepository.save(memberProfile);
    }

    @Override
    public List<MemberDTO> findProfileMember() {
        List<Member> members = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for (Member member : members) {
            memberProfileRepository.findByMember(member).ifPresent(memberProfile -> {
                MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
                memberDTO.setProfileInfo(modelMapper.map(memberProfile, MemberProfileDTO.class));
                memberDTOList.add(memberDTO);
            });
        }
        return memberDTOList;
    }

    @Override
    public boolean setProfileSession(Long member_id) {
        MemberDTO member = memberReadOne(member_id);
        if (member.getProfileInfo() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void updateCommentCount(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.orElseThrow(() -> new NoSuchElementException("해당하는 회원을 찾을 수 없습니다.")); // 조회된 Member가 없을 경우 예외 발생
        int memberCommentCount = commentRepository.countByMember(member);
        member.setCommentCount(memberCommentCount);

        memberRepository.save(member);
    }

    @Override
    public void updateBoardCount(HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.orElseThrow(() -> new NoSuchElementException("해당하는 회원을 찾을 수 없습니다.")); // 조회된 Member가 없을 경우 예외 발생
        int memberPostCount = postRepository.countByMember(member);
        member.setPostCount(memberPostCount);

        memberRepository.save(member);
    }

}
