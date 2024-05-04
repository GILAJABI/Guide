package com.guide.ex.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 100,nullable = false)
    String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime modifyDate;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal locationX;
    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal locationY;

    @Column(nullable = false)
    private final boolean isDeleted = false;

    @Builder.Default
    private int views = 0;

    @Builder.Default
    private int commentCount = 0;

    private String postType;

    @Builder.Default
    private int likeCount = 1;


    @Column(nullable = true)
    private String memberId;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Long getId() {
        return this.postId;
    }
}