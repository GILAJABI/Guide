package com.guide.ex.domain.member;

import com.guide.ex.domain.BaseEntity;
import com.guide.ex.util.YearAttributeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.Year;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 256, nullable = false)
    private String pwd;

    @Column(length = 256, nullable = false)
    private String salt;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(nullable = false)
    @Convert(converter = YearAttributeConverter.class)
    private Year year;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(length = 100, nullable = false)
    private String name;

    // 자동으로 기본값을 설정하기 위해 rating 필드 수정
    @Column(length = 20, nullable = false, columnDefinition = "varchar(20) default '동메달'")
    private String rating;

    @Column(length = 500, nullable = false)
    private boolean isBan;

    @Column(columnDefinition = "int default 0", name = "post_count")
    private int postCount;

    @Column(columnDefinition = "int default 0", name = "comment_count")
    private int commentCount;

    @Column(columnDefinition = "int default 0", name = "like_count")
    private int likeCount;

    // 새로운 회원이 추가될 때 rating 필드의 기본값 설정
    @PrePersist
    protected void onCreate() {
        this.rating = "동메달";
    }
}
