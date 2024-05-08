package com.guide.ex.domain.post;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Entity
@Getter
@NoArgsConstructor
public class PostImage {
    @Id
    private Long postId;

    @Column(name="uuid", length = 500)
    private String uuid;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "ord", length = 50)
    @Builder.Default
    private int ord = 0;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}