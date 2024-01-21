package com.example.odyssey.services;

import com.example.odyssey.entity.notifications.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notificationChange(Long userId) {
        messagingTemplate.convertAndSend("/topic/notificationChange", userId);
    }

    public void newNotification(Long userId, Long notificationId) {
        Map<String, Long> map = new HashMap<>();
        map.put("userId", userId);
        map.put("notificationId", notificationId);
        messagingTemplate.convertAndSend("/topic/newNotification", map);
    }
}
