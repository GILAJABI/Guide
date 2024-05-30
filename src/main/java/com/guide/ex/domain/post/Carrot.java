package com.guide.ex.domain.post;

import com.guide.ex.dto.post.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carrot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrot extends Post{

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private final boolean status = false;

    public void change(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
    public void changeDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
}