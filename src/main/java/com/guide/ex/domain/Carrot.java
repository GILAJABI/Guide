package com.guide.ex.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@DiscriminatorValue("carrot")
public class Carrot extends Post{

    @Column(nullable = false)
    private Long grade;
    private int expense;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private final boolean status = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}