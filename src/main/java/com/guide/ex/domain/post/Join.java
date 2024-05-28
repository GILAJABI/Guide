package com.guide.ex.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Table(name = "postJoin")
@DiscriminatorValue("postJoin")

public class Join extends Post {

    @Column(nullable = false)
    private int numPeople;

    @Column(nullable = false)
    private int expense;

    private LocalDate startTravelDate;

    private LocalDate endTravelDate;

    public void change(String title, String content, int expense, int numPeople, LocalDate startTravelDate, LocalDate endTravelDate) {
        this.title = title;
        this.content = content;
        this.expense = expense;
        this.numPeople = numPeople;
        this.startTravelDate = startTravelDate;
        this.endTravelDate = endTravelDate;
    }
    public void changeDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
}