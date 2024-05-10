package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO extends PostDTO{

    private Long postId;
    private long grade;
    private int expense;

    private LocalDateTime startTravelDate;
    private LocalDateTime endTravelDate;

}
