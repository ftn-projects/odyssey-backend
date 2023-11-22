package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reservation.ResponseReservationDTO;
import com.example.odyssey.entity.notifications.ReservationRequestedNotif;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestedNotifDTO extends NotificationDTO {
    private ResponseReservationDTO reservation;

    public ReservationRequestedNotifDTO(ReservationRequestedNotif notification) {
        super(notification);
        this.reservation = new ResponseReservationDTO(notification.getReservation());
    }
}
