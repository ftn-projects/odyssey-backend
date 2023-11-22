package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reservation.ResponseReservationDTO;
import com.example.odyssey.entity.notifications.ReservationCancelledNotif;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationCancelledNotifDTO extends NotificationDTO {
    private ResponseReservationDTO reservation;

    public ReservationCancelledNotifDTO(ReservationCancelledNotif notification) {
        super(notification);
        this.reservation = new ResponseReservationDTO(notification.getReservation());
    }
}
