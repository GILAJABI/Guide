package com.guide.ex.service;

import com.guide.ex.domain.post.Carrot;
import com.guide.ex.domain.post.Join;
import com.guide.ex.domain.post.Post;
import com.guide.ex.domain.post.Review;
import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @Override
    public void register(String postType, PostDTO postDTO) {
        Post post;
        switch (postType) {
            case "Review":
                post = modelMapper.map(postDTO.getReview(), Review.class);
                postRepository.save(post);
                break;
            case "Carrot":
                post = modelMapper.map(postDTO.getCarrot(), Carrot.class);
                postRepository.save(post);
                break;
            case "Join":
                post = modelMapper.map(postDTO.getJoin(), Join.class);
                postRepository.save(post);
                break;
            default:
                log.error("Unknown post type {}", postType);
                return;
        }
        postRepository.save(post);
    }


    @Override
    public void remove(String postType, Long postId) {
        // postId로 게시물을 찾는다.
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (!optionalPost.isPresent()) {
            log.error("게시물을 찾을 수 없습니다. ID: {}", postId);
            return;
        }

        Post post = optionalPost.get();

        // 게시물 유형을 확인하고 일치하는 경우에만 삭제를 진행한다.
        switch (postType) {
            case "Review":
                if (post instanceof Review) {
                    postRepository.delete(post);
                } else {
                    log.error("게시물 유형이 일치하지 않습니다. 요청 유형: Review, 실제 유형: {}", post.getClass().getSimpleName());
                }
                break;
            case "Carrot":
                if (post instanceof Carrot) {
                    postRepository.delete(post);
                } else {
                    log.error("게시물 유형이 일치하지 않습니다. 요청 유형: Carrot, 실제 유형: {}", post.getClass().getSimpleName());
                }
                break;
            case "Join":
                if (post instanceof Join) {
                    postRepository.delete(post);
                } else {
                    log.error("게시물 유형이 일치하지 않습니다. 요청 유형: Join, 실제 유형: {}", post.getClass().getSimpleName());
                }
                break;
            default:
                log.error("알 수 없는 게시물 유형: {}", postType);
        }
    }

}
