package com.example.odyssey.dtos.notifications;

import com.example.odyssey.entity.reservations.Reservation;

import java.util.List;

public class NotifListDTO {
        private List<NotificationDTO> notifications;
        private List<AccommodationReviewedNotifDTO> accommodationNotifications;

        private List<HostReviewedNotifDTO> hostNotifications;

        private List<ReservationNotifDTO> reservationNotifications;

        public NotifListDTO(List<NotificationDTO> notifications, List<AccommodationReviewedNotifDTO> accommodationNotifications, List<HostReviewedNotifDTO> hostNotifications, List<ReservationNotifDTO> reservationNotifications) {
                this.notifications = notifications;
                this.accommodationNotifications = accommodationNotifications;
                this.hostNotifications = hostNotifications;
                this.reservationNotifications = reservationNotifications;
        }
}
