package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reservation.ReservationRequestCreationDTO;
import com.example.odyssey.entity.notifications.ReservationAccreditedNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationAccreditedNotificationDTO extends NotificationDTO{
    ReservationRequestCreationDTO reservation;
    public ReservationAccreditedNotificationDTO(ReservationAccreditedNotification notification){
        super(notification.getId(),notification.getTitle(),notification.getText(),notification.getReceiver());
        this.reservation = new ReservationRequestCreationDTO(notification.getReservation());
    }
}
