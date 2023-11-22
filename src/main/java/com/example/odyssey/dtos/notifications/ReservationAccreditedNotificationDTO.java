package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reservation.ResponseReservationDTO;
import com.example.odyssey.entity.notifications.ReservationAccreditedNotif;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationAccreditedNotificationDTO extends NotificationDTO {
    private ResponseReservationDTO reservation;

    public ReservationAccreditedNotificationDTO(ReservationAccreditedNotif notification) {
        super(notification);
        this.reservation = new ResponseReservationDTO(notification.getReservation());
    }
}
