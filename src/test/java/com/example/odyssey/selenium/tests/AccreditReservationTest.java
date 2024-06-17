package com.example.odyssey.selenium.tests;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.selenium.pages.AccreditReservationsPage;
import com.example.odyssey.selenium.pages.HomePage;
import com.example.odyssey.selenium.pages.LoggedInPage;
import com.example.odyssey.selenium.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccreditReservationTest extends TestBase {
    @BeforeEach
    public void setup() {
        new HomePage(driver).clickLogin();

        var loginPage = new LoginPage(driver);
        loginPage.waitUntilOpen();

        loginPage.inputEmail("petar@gmail.com");
        loginPage.inputPassword("petar");
        loginPage.buttonClick();

        var loggedInPage = new LoggedInPage(driver);
        loggedInPage.waitUntilOpen();
        loggedInPage.navigateToReservations();
    }

    @Test
    @DirtiesContext
    public void testAcceptNonOverlappingReservation() {
        var reservationsPage = new AccreditReservationsPage(driver);
        reservationsPage.waitUntilLoaded();

        var reservationId = 4L;
        var expected = Map.of(
                1L, Reservation.Status.ACCEPTED,
                2L, Reservation.Status.DECLINED,
                3L, Reservation.Status.REQUESTED,
                4L, Reservation.Status.ACCEPTED,
                5L, Reservation.Status.REQUESTED
        );
        reservationsPage.acceptReservation(reservationId);

        var actual = reservationsPage.getReservationWithStatuses();
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    public void testAcceptOverlappingReservation() {
        var reservationsPage = new AccreditReservationsPage(driver);
        reservationsPage.waitUntilLoaded();

        var reservationId = 5L;
        var expected = Map.of(
                1L, Reservation.Status.ACCEPTED,
                2L, Reservation.Status.DECLINED,
                3L, Reservation.Status.DECLINED,
                4L, Reservation.Status.REQUESTED,
                5L, Reservation.Status.ACCEPTED
        );
        reservationsPage.acceptReservation(reservationId);

        var actual = reservationsPage.getReservationWithStatuses();
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    public void testDeclineRequestedReservation() {
        var reservationsPage = new AccreditReservationsPage(driver);
        reservationsPage.waitUntilLoaded();

        var reservationId = 3L;
        var expected = Map.of(
                1L, Reservation.Status.ACCEPTED,
                2L, Reservation.Status.DECLINED,
                3L, Reservation.Status.DECLINED,
                4L, Reservation.Status.REQUESTED,
                5L, Reservation.Status.REQUESTED
        );
        reservationsPage.declineReservation(reservationId);

        var actual = reservationsPage.getReservationWithStatuses();
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    public void testDeclineAcceptedReservation() {
        var reservationsPage = new AccreditReservationsPage(driver);
        reservationsPage.waitUntilLoaded();

        var reservationId = 1L;
        var expected = Map.of(
                1L, Reservation.Status.DECLINED,
                2L, Reservation.Status.DECLINED,
                3L, Reservation.Status.REQUESTED,
                4L, Reservation.Status.REQUESTED,
                5L, Reservation.Status.REQUESTED
        );
        reservationsPage.declineReservation(reservationId);

        var actual = reservationsPage.getReservationWithStatuses();
        assertEquals(expected, actual);
    }
}
