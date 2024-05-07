package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarrotDTO {

    private Long postId;
    private int price;
    private boolean status;
}
