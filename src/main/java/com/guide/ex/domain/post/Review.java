package com.guide.ex.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
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

    public void change(int expense, Long grade, LocalDateTime startTravelDate, LocalDateTime endTravelDate) {
        this.expense = expense;
        this.grade = grade;
        this.startTravelDate = startTravelDate;
        this.endTravelDate = endTravelDate;
    }
}
