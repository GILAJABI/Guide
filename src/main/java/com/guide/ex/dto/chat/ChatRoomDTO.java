package com.guide.ex.dto.chat;

import com.guide.ex.dto.chat.ChatMessageDTO;
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
}
