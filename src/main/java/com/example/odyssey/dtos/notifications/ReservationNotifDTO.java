package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reservation.ResponseReservationDTO;
import com.example.odyssey.entity.notifications.ReservationNotif;

public class ReservationNotifDTO extends NotificationDTO{
    private ResponseReservationDTO reservation;
    public ReservationNotifDTO(ReservationNotif notification) {
        super(notification);
        this.reservation = new ResponseReservationDTO(notification.getReservation());
    }
}
