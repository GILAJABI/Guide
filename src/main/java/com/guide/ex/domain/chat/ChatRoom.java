package com.guide.ex.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
//    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @NotNull
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "room_number")
    private Long roomNumber;

    public void change(String s, String s1) {

    }

//    @JsonCreator
//    public static ChatRoom fromJson(@JsonProperty("chatRoom") Long roomId) {
//        // 여기서는 단순 예시로 roomId만을 사용하여 ChatRoom 객체를 생성합니다.
//        // 실제 사용 시에는 roomId로 ChatRoom 객체를 데이터베이스에서 찾거나,
//        // 적절한 처리를 해야 할 수 있습니다.
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.roomId = roomId; // private으로 선언된 경우 적절히 수정 필요
//        return chatRoom;
//    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
}
