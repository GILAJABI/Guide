package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private Long imageId;
    private String fileName;
    private int ord;
    private String uuid;
    private Long postId;
}
