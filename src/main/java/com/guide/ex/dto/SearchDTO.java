package com.guide.ex.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SearchDTO {
    private Long postId;
    private String title;
    private String memberId;

    @QueryProjection
    public SearchDTO(Long postId, String title, String memberId) {
        this.postId = postId;
        this.title = title;
        this.memberId = memberId;
    }
}
