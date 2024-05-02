package com.guide.ex.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatRoomDTO {

    private String roomId;
    private String name;

    public static ChatRoomDTO create(String name) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.roomId = UUID.randomUUID().toString();
        return chatRoomDTO;
    }
}
