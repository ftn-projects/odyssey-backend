package com.example.odyssey.dtos.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancelledNotificationDTO extends NotificationDTO{
    //ReservationDTO reservation;
    public ReservationCancelledNotificationDTO(ReservationCancelledNotificationDTO notification){
        super(notification.getId(),notification.getTitle(),notification.getText(),notification.getReceiver());
        //this.reservation = new ReservationDTO(notification.getReservation());
    }
}
