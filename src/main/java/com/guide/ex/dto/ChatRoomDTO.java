package com.guide.ex.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChatRoomDTO {

    private String room_Id;

    private int room_number;

    private LocalDateTime createdAt;

    public static ChatRoomDTO create(int room_number) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.room_number = room_number;
        chatRoomDTO.createdAt = LocalDateTime.now();
        return chatRoomDTO;
    }
}
