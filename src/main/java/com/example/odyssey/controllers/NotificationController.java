package com.example.odyssey.controllers;

import com.example.odyssey.dtos.notifications.NotificationDTO;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.mappers.NotificationDTOMapper;
import com.example.odyssey.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Notification> notifications = service.getAll();
        return new ResponseEntity<>(mapToDTO(notifications), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Notification notification = service.findById(id);
        return new ResponseEntity<>(NotificationDTOMapper.fromNotificationToDTO(notification), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findByUserId(
            @PathVariable Long id,
            @RequestParam(required = false) List<Notification.Type> types,
            @RequestParam(required = false) Boolean read
    ) {
        List<Notification> notifications = service.findAllByFilter(id, types, read);
        return new ResponseEntity<>(mapToDTO(notifications), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = NotificationDTOMapper.fromDTOtoNotification(notificationDTO);
        notification = service.save(notification);
        return new ResponseEntity<>(NotificationDTOMapper.fromNotificationToDTO(notification), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(value = "/{id}/{read}")
    public ResponseEntity<?> updateRead(@PathVariable Long id, @PathVariable Boolean read) {
        Notification notification = service.updateRead(id, read);
        return new ResponseEntity<>(NotificationDTOMapper.fromNotificationToDTO(notification), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static List<?> mapToDTO(List<Notification> notifications) {
        return notifications.stream().map(NotificationDTO::new).toList();
    }
}
