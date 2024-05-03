package com.guide.ex.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileDTO {
    private String uuid;
    private String fileName;
    private String content;
    private Long memberId;

    private String name;
    private String phone;
    private String gender;
    private String travelType;
    private Year year;
    private String rating;

    private int postCount;
    private int commentCount;
    private int likeCount;
}
