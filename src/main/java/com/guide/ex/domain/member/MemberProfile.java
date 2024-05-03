package com.guide.ex.domain.member;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_profile_id;

    @Column(name="uuid", length = 500)
    private String uuid;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(length = 500)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
