package com.guide.ex.domain.post;

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
    private int price;

    @Column(nullable = false)
    private final boolean status = false;
}