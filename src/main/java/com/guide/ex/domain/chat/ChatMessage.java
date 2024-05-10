package com.guide.ex.domain.chat;

import com.guide.ex.domain.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @NotNull
    @Column(name = "chat_mesg", length = 200)
    private String chatMsg;

    @NotNull
    @Column(name = "member_name", length = 200)
    private String memberName;

    @NotNull
    @CreatedDate
    @Column(name = "regist_date")
    private LocalDateTime registDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    public void change(String newMessage, String s) {
        this.chatMsg = newMessage;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", insertable=false, updatable=false)
    private ChatRoom chatroom;
}