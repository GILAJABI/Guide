package com.guide.ex.domain;

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
@Table(name = "postJoin")
public class Join extends Post {

    @Column(nullable = false)
    private int numPeople;

    @Column(nullable = false)
    private int expense;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime startTravelDate;

    @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime endTravelDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}