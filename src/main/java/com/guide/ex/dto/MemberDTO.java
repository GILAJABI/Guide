package com.guide.ex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.Year;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long memberId;

    @NotEmpty
    private String uid;
    @NotEmpty
    private String pwd;

    private String salt;


    @NotEmpty
    private String name;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String gender;


    @NotEmpty
    private String travelType;


    private Year year;
    private String rating;


    //마이페이지 왼쪽 하단 사용
    private int postCount;
    private int commentCount;
    private int likeCount;

}
