package com.guide.ex.service;

import com.guide.ex.dto.chat.ChatRoomDTO;

public interface ChatService {
    Long createChatRoom(ChatRoomDTO chatRoomDTO);
}
