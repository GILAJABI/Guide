package com.guide.ex.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@DiscriminatorValue("review")
public class Review extends Post{

    @Column(nullable = false)
    private Long grade;
    private int expense;

    @CreatedDate
    private LocalDateTime startTravelDate;

    @LastModifiedDate
    private LocalDateTime endTravelDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
