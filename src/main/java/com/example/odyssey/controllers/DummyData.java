package com.example.odyssey.controllers;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.notifications.AccommodationReviewedNotif;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.notifications.ReservationNotif;
import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class DummyData {
    private static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static List<User> getUsers() {
        return new ArrayList<>() {{
            add(new Guest(1L, User.Role.GUEST, User.AccountStatus.ACTIVE,
                    "Slavica",
                    "Cukteras",
                    "slavica@gmail.com",
                    "123456",
                    new Address("Ulica", 16, "Beograd", "Srbija"),
                    "0622345678",
                    "/",
                    new User.Settings(),
                    new HashSet<>()));
            add(new Host(2L, User.Role.HOST, User.AccountStatus.ACTIVE,
                    "Milan",
                    "Stankovic",
                    "milan@gmail.com",
                    "123456",
                    new Address("Ulica", 15, "Beograd", "Srbija"),
                    "0622345678",
                    "/",
                    new User.Settings(),
                    "About.",
                    new HashSet<>()));
            add(new Guest(3L, User.Role.GUEST, User.AccountStatus.DEACTIVATED,
                    "Guest1",
                    "Guest1",
                    "guest1@gmail.com",
                    "123456",
                    new Address("Ulica", 14, "Beograd", "Srbija"),
                    "0674823748",
                    "/",
                    new User.Settings(),
                    new HashSet<>()));
            add(new Guest(4L, User.Role.GUEST, User.AccountStatus.BLOCKED,
                    "Guest1",
                    "Guest1",
                    "guest1@gmail.com",
                    "123456",
                    new Address("Ulica", 13, "Beograd", "Srbija"),
                    "0674823748",
                    "/",
                    new User.Settings(),
                    new HashSet<>()));
            add(new User(5L, User.Role.ADMIN, User.AccountStatus.ACTIVE,
                    "Admin",
                    "Admin",
                    "admin@gmail.com",
                    "999999",
                    new Address("Ulica", 12, "Beograd", "Srbija"),
                    "0674627381",
                    "/",
                    new User.Settings()));
        }};
    }

    public static List<Accommodation> getAccommodations() {
        List<User> users = getUsers();
        return new ArrayList<>() {{
            add(new Accommodation(1L, "Smestaj1", "opis", Accommodation.Type.APARTMENT,
                    new Address("Ulica", 20, "Beograd", "Srbija"),
                    Accommodation.PricingType.PER_PERSON,
                    2000.0,
                    false,
                    Duration.ofDays(5),
                    new HashSet<>(), new HashSet<>(),
                    1, 2,
                    new HashSet<>(),
                    (Host) users.get(1)));
            add(new Accommodation(2L, "Smestaj2", "opis", Accommodation.Type.APARTMENT,
                    new Address("Ulica", 20, "Beograd", "Srbija"),
                    Accommodation.PricingType.PER_PERSON,
                    1300.0,
                    true,
                    Duration.ofDays(2),
                    new HashSet<>(), new HashSet<>(),
                    1, 1,
                    new HashSet<>(),
                    (Host) users.get(1)));
            add(new Accommodation(3L, "Smestaj3", "opis", Accommodation.Type.HOUSE,
                    new Address("Ulica", 23, "Beograd", "Srbija"),
                    Accommodation.PricingType.PER_ACCOMMODATION,
                    4200.0,
                    false,
                    Duration.ofDays(60),
                    new HashSet<>(), new HashSet<>(),
                    2, 5,
                    new HashSet<>(),
                    (Host) users.get(1)));
        }};
    }

    public static List<Reservation> getReservations() {
        List<Accommodation> accommodations = getAccommodations();
        List<User> users = getUsers();

        return new ArrayList<>() {{
            add(new Reservation(1L, 12000.0, 2, Reservation.Status.REQUESTED, now(), now(),
                    new TimeSlot(now().plusDays(4), now().plusDays(8)), accommodations.get(0), (Guest) users.get(0)));
            add(new Reservation(2L, 10000.0, 1, Reservation.Status.ACCEPTED, now(), now(),
                    new TimeSlot(now().plusDays(10), now().plusDays(13)), accommodations.get(1), (Guest) users.get(3)));
            add(new Reservation(3L, 9000.0, 1, Reservation.Status.DECLINED, now(), now(),
                    new TimeSlot(now().plusDays(13), now().plusDays(15)), accommodations.get(2), (Guest) users.get(0)));
            add(new Reservation(4L, 15000.0, 4, Reservation.Status.ACCEPTED, now(), now(),
                    new TimeSlot(now().plusDays(14), now().plusDays(20)), accommodations.get(0), (Guest) users.get(3)));
        }};
    }

    public static List<Notification> getNotifications() {
        List<AccommodationReview> reviews = getAccommodationReviews();
        List<Reservation> reservations = getReservations();
        List<User> users = getUsers();
        return new ArrayList<>() {{
            add(new Notification(1L, "Account blocked", "You have been blocked.", users.get(2)));

            ReservationNotif n1 = new ReservationNotif(reservations.get(0), users.get(1));
            n1.setId(2L);
            add(n1);

            AccommodationReviewedNotif n2 = new AccommodationReviewedNotif(reviews.get(0), users.get(1));
            n2.setId(3L);
            add(n2);
        }};
    }

    public static List<HostReview> getHostReviews() {
        List<User> users = getUsers();
        return new ArrayList<>() {{
            add(new HostReview(1L, 5.0, "comment", Review.Status.ACCEPTED,
                    now(), (Guest) users.get(0), (Host) users.get(1)));
            add(new HostReview(2L, 1.0, "comment", Review.Status.REQUESTED,
                    now(), (Guest) users.get(3), (Host) users.get(1)));
        }};
    }

    public static List<AccommodationReview> getAccommodationReviews() {
        List<Accommodation> accommodations = getAccommodations();
        List<User> users = getUsers();
        return new ArrayList<>() {{
            add(new AccommodationReview(1L, 3.5, "comment", Review.Status.DECLINED,
                    now(), (Guest) users.get(0), accommodations.get(0)));
            add(new AccommodationReview(2L, 4.0, "comment", Review.Status.ACCEPTED,
                    now(), (Guest) users.get(3), accommodations.get(0)));
        }};
    }

    public static List<ReviewReport> getReviewReports() {
        List<AccommodationReview> reviews = getAccommodationReviews();
        List<User> users = getUsers();

        return new ArrayList<>() {{
            add(new ReviewReport(1L, "description", now(), users.get(0), reviews.get(0)));
            add(new ReviewReport(2L, "description", now(), users.get(3), reviews.get(1)));
        }};
    }

    public static List<UserReport> getUserReports() {
        List<User> users = getUsers();

        return new ArrayList<>() {{
            add(new UserReport(1L, "description", now(), users.get(0), users.get(1)));
            add(new UserReport(2L, "description", now(), users.get(3), users.get(1)));
        }};
    }
}
