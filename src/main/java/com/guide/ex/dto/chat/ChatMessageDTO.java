package com.guide.ex.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {

    private Long messageId;
    private String chatMsg;
    private Long memberId;
    private String memberName;
    private Long roomId;
    }
