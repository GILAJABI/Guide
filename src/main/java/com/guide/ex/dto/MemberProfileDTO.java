package com.guide.ex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileDTO extends MemberDTO{
    private String uuid;
    private String fileName;
    private String content;
    private Long memberId;

}
