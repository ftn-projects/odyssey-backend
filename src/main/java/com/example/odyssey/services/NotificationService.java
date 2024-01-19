package com.example.odyssey.services;

import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.notifications.ReservationNotif;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.exceptions.notifications.NotificationNotFoundException;
import com.example.odyssey.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Notification create(Notification notification) {
        notification.setDate(LocalDateTime.now());
        notification.setRead(false);
        return save(notification);
    }

    public Notification updateRead(Long id, Boolean read) {
        Notification notification = findById(id);
        notification.setRead(read);
        return save(notification);
    }

    public Notification save(Notification notification) {
        Notification n = notificationRepository.save(notification);
        webSocketService.notificationChange(n.getReceiver().getId());
        return n;
    }

    public void delete(Long id) {
        Long receiverId = findById(id).getReceiver().getId();
        notificationRepository.deleteById(id);
        webSocketService.notificationChange(receiverId);
    }

    public void notifyRequested(Reservation reservation) {
        String accommodation = reservation.getAccommodation().getTitle();

        User receiver = reservation.getAccommodation().getHost();
        if (receiver.getSettings().getReservationRequested())
            create(new ReservationNotif(reservation, receiver, Notification.Type.RESERVATION_REQUESTED,
                    "Reservation requested for your accommodation " + accommodation));

        receiver = reservation.getGuest();
        if (receiver.getSettings().getReservationRequested())
            create(new ReservationNotif(reservation, receiver, Notification.Type.RESERVATION_REQUESTED,
                    "You made a reservation request for " + accommodation));
    }

    public void notifyAccepted(Reservation reservation) {
        String accommodation = reservation.getAccommodation().getTitle();
        User receiver = reservation.getGuest();
        if (receiver.getSettings().getReservationAccepted())
            create(new ReservationNotif(reservation, receiver, Notification.Type.RESERVATION_ACCEPTED,
                    "Your reservation request for " + accommodation + " has been accepted"));
    }

    public void notifyDeclined(Reservation reservation) {
        String accommodation = reservation.getAccommodation().getTitle();
        User receiver = reservation.getGuest();
        if (reservation.getGuest().getSettings().getReservationDeclined())
            create(new ReservationNotif(reservation, receiver, Notification.Type.RESERVATION_DECLINED,
                    "Your reservation request for " + accommodation + " has been declined"));
    }

    public void notifyCancelled(Reservation reservation) {
        String accommodation = reservation.getAccommodation().getTitle();
        User receiver = reservation.getAccommodation().getHost();
        if (receiver.getSettings().getReservationDeclined())
            create(new ReservationNotif(reservation, receiver, Notification.Type.RESERVATION_CANCELLED,
                    "Reservation for your accommodation " + accommodation + " has been cancelled"));
    }
}
