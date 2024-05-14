package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO extends PostDTO{

    private Long postId;
    private long grade;
    private int expense;

    private LocalDateTime startTravelDate;
    private LocalDateTime endTravelDate;

    private List<ImageDTO> imageDTOs;

    public List<ImageDTO> getImageDTOs() {
        return imageDTOs;
    }

    // 이미지 리스트 설정 메서드
    public void setImageDTOs(List<ImageDTO> imageDTOs) {
        this.imageDTOs = imageDTOs;
    }
}
