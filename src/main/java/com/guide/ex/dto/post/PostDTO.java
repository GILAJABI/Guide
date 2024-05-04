package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Review review;
    private Join join;
    private Carrot carrot;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Review{
        private Long postId;

        @Size(min = 3, max = 100)
        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private LocalDateTime registerDate;
        private LocalDateTime modifyDate;

        @NotNull
        private BigDecimal locationX;

        @NotNull
        private BigDecimal locationY;

        private boolean isDeleted;

        @Min(0)
        private int views;

        @Min(0)
        private int likeCount;

        @Min(0)
        private int commentCount;

        private String postType;

        @Min(1)
        private long grade;

        @Min(0)
        private int expense;

        private LocalDateTime startTravelDate;
        private LocalDateTime endTravelDate;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Join{
        private Long postId;

        @Size(min = 3, max = 100)
        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private LocalDateTime registerDate;
        private LocalDateTime modifyDate;

        @NotNull
        private BigDecimal locationX;

        @NotNull
        private BigDecimal locationY;

        private boolean isDeleted;

        @Min(0)
        private int views;

        @Min(0)
        private int likeCount;

        @Min(0)
        private int commentCount;

        private String postType;

        @Min(0)
        private int expense;

        @Min(1)
        private int numPeople;

        private LocalDateTime startTravelDate;
        private LocalDateTime endTravelDate;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Carrot{
        private Long postId;

        @Size(min = 3, max = 100)
        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private LocalDateTime registerDate;
        private LocalDateTime modifyDate;

        @NotNull
        private BigDecimal locationX;

        @NotNull
        private BigDecimal locationY;

        private boolean isDeleted;

        @Min(0)
        private int views;

        @Min(0)
        private int likeCount;

        @Min(0)
        private int commentCount;

        private String postType;

        @Min(0)
        private int price;

        private boolean status;
    }
}
