package com.example.odyssey.controllers;

import com.example.odyssey.dtos.notifications.NotificationDTO;
import com.example.odyssey.dtos.reservation.ReservationDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.reservations.Reservation;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/notifications")
public class NotificationController {
//    @Autowired
//    private NotificationService service;
//
//    @Autowired
//    public NotificationController(NotificationService service) {
//        this.service = service;
//    }

    private final List<Notification> data;

    public static List<Notification> generateData() {
        return new ArrayList<>() {{
            add(new Notification());
            add(new Notification());
            add(new Notification());
            add(new Notification());
            add(new Notification());
        }};
    }

    public NotificationController() {
        data = generateData();
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAll() {
        List<Notification> notifications = data;

//        notifications = service.getAll();

        return new ResponseEntity<>(mapToDTO(notifications), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> findById(@PathVariable Long id) {
        Notification notification = data.get(0);

//        notification = service.findById(id);

        return new ResponseEntity<>(new NotificationDTO(notification), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<NotificationDTO>> findByUserId(@PathVariable Long id) {
        List<Notification> notifications = data.subList(2, 4);

//        notifications = service.findByUserId(id);

        return new ResponseEntity<>(mapToDTO(notifications), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> readNotification(@PathVariable Long id) {
        Notification notification = data.get(0);

//        notification = service.readNotification(id);

        if (notification != null) {
            return new ResponseEntity<>(new NotificationDTO(notification), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private static List<NotificationDTO> mapToDTO(List<Notification> notifications) {
        return notifications.stream().map(NotificationDTO::new).toList();
    }
}
