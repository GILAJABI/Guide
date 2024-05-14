package com.guide.ex.dto.chat;

import com.guide.ex.domain.member.Member;
import com.guide.ex.dto.chat.ChatMessageDTO;
import com.guide.ex.dto.member.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    private Long senderId;
    private Long receiverId;

    private MemberDTO sender;
    private MemberDTO receiver;
}
