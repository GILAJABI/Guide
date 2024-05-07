package com.guide.ex.controller;

import com.guide.ex.dto.member.MemberProfileDTO;
import com.guide.ex.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Log4j2
public class ImgController {

    @Value("${com.guide.upload.path}")// import 시에 springframework으로 시작하는 Value
    private String uploadPath;

    @Autowired
    private MemberService memberService;
    @Autowired
    private ResourceLoader resourceLoader;


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

    @GetMapping("/photo/{fileName:.+}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(uploadPath).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        MediaType mediaType = MediaType.IMAGE_JPEG;
        if (fileName.toLowerCase().endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
