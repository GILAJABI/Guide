package com.guide.ex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;   // 시작 페이지

    @Builder.Default
    private int size = 6;  // 1개 페이지의 갯수

    private String searchValue;

    public Pageable getPageable(String...props) {
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }
//    private String link;        // 다른 경로로 움직일 때 원래 페이지의 정보(4번 페이지 -> 수정 -> 4번 페이지)

//    public String getLink() {
//        if(link == null) {
//            StringBuilder builder = new StringBuilder();
//
//            builder.append("page=" + this.page); // '='를 사용하여 매개변수와 값 사이에 구분자 추가
//            builder.append("&size=" + this.size); // '&'를 사용하여 여러 매개변수를 구분
//
//            if(type != null && type.length() > 0){// 공백 제거 및 비어 있는 경우 처리
//                builder.append("&type=" + type); // '&'를 사용하여 여러 매개변수를 구분
//            }
//            if(keyword != null) {
//                try {
//                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8")); // URL 인코딩 적용
//                } catch (UnsupportedEncodingException e) {
//                    // 예외 처리 필요
//                }
//            }
//            link = builder.toString();
//        }
//        return link;
//    }
}
