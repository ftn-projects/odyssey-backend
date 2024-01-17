package com.example.odyssey.services;

import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.exceptions.notifications.NotificationNotFoundException;
import com.example.odyssey.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private WebSocketService webSocketService;

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException(id));
    }

    public List<Notification> findAllByFilter(Long id, List<Notification.Type> types, Boolean read) {
        return notificationRepository.findAllByFilter(id, types, read);
    }

    public Notification update(Notification notification) {
        Notification n = notificationRepository.save(notification);
        webSocketService.notifyChange(n.getReceiver().getId());
        return n;
    }

    public void setRead(Long id, Boolean read) {
        Notification notification = findById(id);
        notification.setRead(read);
        update(notification);
    }

    public void delete(Long id) {
        Long receiverId = findById(id).getReceiver().getId();
        notificationRepository.deleteById(id);
        webSocketService.notifyChange(receiverId);
    }

    public void create(Notification n) {
        n.setDate(LocalDateTime.now());
        n.setRead(false);
        notificationRepository.save(n);
        webSocketService.notifyChange(n.getReceiver().getId());
    }
}
