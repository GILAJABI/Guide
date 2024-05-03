package com.guide.ex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AllPostDTO {
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

    @NotEmpty
    private int views;
    @NotEmpty
    private int likeCount;
    @NotEmpty
    private int commentCount;

    @NotEmpty
    private int postType;
    @NotEmpty
    private long grade;
    @NotEmpty
    private int expense;
    @NotEmpty
    private int numPeople;
    @NotEmpty
    private int price;
    @NotEmpty
    private boolean status;


    private List<CommentDTO> comments;

    private LocalDateTime startTravleDate;
    private LocalDateTime endTravleDate;

}
