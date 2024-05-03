package com.guide.ex.controller.test;

import com.guide.ex.dto.MemberProfileDTO;
import com.guide.ex.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Log4j2
public class TestRestController {

    @Value("${com.guide.upload.path}")// import 시에 springframework으로 시작하는 Value
    private String uploadPath;

    @Autowired
    private MemberService memberService;


    @ApiOperation(value = "파일 업로드", notes = "단일 파일을 업로드합니다.")
    @PostMapping("/upload")
    public MemberProfileDTO uploadFile(@RequestParam("file") MultipartFile file,
                                       @RequestParam("content") String content,
                                       @RequestParam("memberId") Long memberId) {

        if (file.isEmpty()) {
            throw new RuntimeException("업로드된 파일이 비어 있습니다.");
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);

            boolean isImage = Files.probeContentType(savePath).startsWith("image");

            MemberProfileDTO memberProfileDTO = new MemberProfileDTO();
            memberProfileDTO.setUuid(uuid);
            memberProfileDTO.setFileName(originalName);
            memberProfileDTO.setContent(content);
            memberProfileDTO.setMemberId(memberId);
            memberService.fileUpload(memberProfileDTO);

            return memberProfileDTO;

        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }
    }



}
