package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO extends PostDTO{

    private Long postId;

    private int expense;

    private int numPeople;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTravelDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTravelDate;

    private List<ImageDTO> imageDTOs;

    public List<ImageDTO> getImageDTOs() {
        return imageDTOs;
    }

    // 이미지 리스트 설정 메서드
    public void setImageDTOs(List<ImageDTO> imageDTOs) {
        this.imageDTOs = imageDTOs;
    }
}
