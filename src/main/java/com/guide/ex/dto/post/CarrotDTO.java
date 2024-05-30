package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CarrotDTO extends PostDTO {
    private Long postId;
    private int price;
    private boolean status;
    private List<ImageDTO> imageDTOs;

    public List<ImageDTO> getImageDTOs() {
        return imageDTOs;
    }

    // 이미지 리스트 설정 메서드
    public void setImageDTOs(List<ImageDTO> imageDTOs) {
        this.imageDTOs = imageDTOs;
    }
}
