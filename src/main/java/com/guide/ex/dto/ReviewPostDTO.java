package com.guide.ex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPostDTO {
    private Long post_id;

    @Size(min = 3, max = 100)
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    private LocalDateTime register_date;
    private LocalDateTime modify_date;

    @NotEmpty
    private BigDecimal locationX;
    @NotEmpty
    private BigDecimal locationY;
    @NotEmpty
    private boolean isDeleted;

    private int views;
    private int likeCount;
    private int commentCount;

    @NotEmpty
    private int postType;
    @NotEmpty
    private long grade;
    @NotEmpty
    private int expense;

    private LocalDateTime startTravleDate;
    private LocalDateTime endTravleDate;

}
