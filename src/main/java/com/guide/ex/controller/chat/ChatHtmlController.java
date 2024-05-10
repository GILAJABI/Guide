package com.guide.ex.controller.chat;

import com.guide.ex.domain.chat.ChatMessage;
import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.repository.chat.ChatMessageRepository;
import com.guide.ex.repository.chat.ChatRoomRepository;
import com.guide.ex.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/chat")
@Controller
public class ChatHtmlController {

    @Autowired
    private MemberService memberService;

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

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
        Long memberId = (Long) session.getAttribute("member_id");
        String name = memberService.memberReadOne(memberId).getName();

        System.out.println("============================");
        System.out.println(name);
        System.out.println("============================");

        model.addAttribute("session_member_id", memberId);
        model.addAttribute("member_name", name);
    }

    @GetMapping("/chatRoom.html")
    public void chatRoom(HttpSession session, Model model){
        Long memberId = (Long) session.getAttribute("member_id");
        String name = memberService.memberReadOne(memberId).getName();

        System.out.println("============================");
        System.out.println(name);
        System.out.println("============================");

        model.addAttribute("session_member_id", memberId);
        model.addAttribute("member_name", name);
    }
}