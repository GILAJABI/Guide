package com.guide.ex.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CarrotDTO extends PostDTO {
    private Long postId;
    private int price;
    private boolean status;
    private List<ImageDTO> imageDTOs;

}
