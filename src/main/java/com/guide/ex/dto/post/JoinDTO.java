package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO extends PostDTO{

    private Long postId;

    private int expense;

    private int numPeople;

    private LocalDateTime startTravelDate;
    private LocalDateTime endTravelDate;
}
