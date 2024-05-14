package com.guide.ex.controller.chat;

import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.dto.chat.ChatRoomDTO;
import com.guide.ex.repository.chat.ChatMessageRepository;
import com.guide.ex.repository.chat.ChatRoomRepository;
import com.guide.ex.service.ChatService;
import com.guide.ex.service.ChatServiceImpl;
import com.guide.ex.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/chat")
@Controller
public class ChatHtmlController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ChatService chatService;


//    @MessageMapping("/chat/message")
//    public void sendMessage(ChatMessage message) {
//        ChatRoom room = chatRoomRepository.findById(message.getChatRoom().getRoomId()).orElse(null);
//        if (room != null) {
//            chatMessageRepository.save(message);
//            messagingTemplate.convertAndSend("/sub/chat/room/" + room.getRoomId(), message);
//        }
//    }

    @GetMapping("/chatList")
    public void getRoomById(HttpSession session, Model model) {
        List<ChatRoomDTO> rooms = chatService.memberChatRooms((long) session.getAttribute("member_id"));
        model.addAttribute("rooms", rooms);
    }

    @GetMapping("/chatRoom/{roomId}")
    public String chatRoom(@PathVariable("roomId") Long roomId, HttpSession session, Model model) {
        Long memberId = (Long) session.getAttribute("member_id");
        String name = memberService.memberReadOne(memberId).getName();

        // roomId를 사용하여 추가적인 채팅방 정보를 가져올 수 있습니다.
        // 예: ChatRoom chatRoom = chatService.findChatRoomById(roomId);

        System.out.println("============================");
        System.out.println(name);
        System.out.println("============================");


        model.addAttribute("member_name", name);
        model.addAttribute("room_id", roomId); // 채팅방 ID를 모델에 추가

        return "/chat/chatRoom.html"; // chatRoom.html 페이지 반환
    }
}