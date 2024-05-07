//package com.guide.ex.handler;
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//
//@Component
//public class WebSocketEventListener implements ApplicationListener<SessionConnectEvent> {
//
//    @Override
//    public void onApplicationEvent(SessionConnectEvent event) {
//        String clientIp = event.getUser().getName();
//        System.out.println("Connected: " + clientIp);
//    }
//}
