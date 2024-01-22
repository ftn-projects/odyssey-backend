package com.example.odyssey.selenium.tests;


import com.example.odyssey.selenium.pages.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CreateAndEditAccommodationTest extends TestBase{

    @Test
    public void createAndEdit(){
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitUntilOpen();
        loginPage.inputEmail("petar@gmail.com");
        loginPage.inputPassword("petar");
        loginPage.buttonClick();

        LoggedInPage loggedInPage = new LoggedInPage(driver);
        loggedInPage.waitUntilOpen();
        loggedInPage.clickCreate();

        CreateAccommodationPage createAccommodationPage = new CreateAccommodationPage(driver);
        createAccommodationPage.waitUntilOpen();
        createAccommodationPage.inputTitle("Naslov");
        createAccommodationPage.selectAmenity();

        createAccommodationPage.inputPrice("0");
        createAccommodationPage.inputDue("5");

        createAccommodationPage.inputDescription("Opis");
        createAccommodationPage.inputStreet("Armina Lajningena 32");
        createAccommodationPage.inputCity("Titel");

        createAccommodationPage.addSlotClick();
        createAccommodationPage.clickSnackBar("Please select starting date of the slot.");

        createAccommodationPage.clickCalendar();
        createAccommodationPage.selectDateRange();

        createAccommodationPage.inputSlotPrice("-200");
        createAccommodationPage.addSlotClick();
        createAccommodationPage.clickSnackBar("Price cannot be negative.");

        createAccommodationPage.inputSlotPrice("0");
        createAccommodationPage.addSlotClick();
        createAccommodationPage.clickSnackBar("Price cannot be negative.");

        createAccommodationPage.inputSlotPrice("200");
        createAccommodationPage.addSlotClick();
        createAccommodationPage.checkIfAdded();
        createAccommodationPage.removeSlot();

        createAccommodationPage.clickCalendar();
        createAccommodationPage.selectDateRange();
        createAccommodationPage.inputSlotPrice("200");
        createAccommodationPage.addSlotClick();
        createAccommodationPage.checkIfAdded();

        createAccommodationPage.clickCalendar();
        createAccommodationPage.selectDateRange();
        createAccommodationPage.inputSlotPrice("300");
        createAccommodationPage.addSlotClick();
        createAccommodationPage.checkIfAdded();
        createAccommodationPage.checkNewPrice();

        createAccommodationPage.clickCalendar();
        createAccommodationPage.selectOverlapDateRange();
        createAccommodationPage.inputSlotPrice("100");
        createAccommodationPage.addSlotClick();
        createAccommodationPage.checkOverlap();

        createAccommodationPage.removeSlot();
        createAccommodationPage.removeSlot();

        createAccommodationPage.clickCalendar();
        createAccommodationPage.selectDateRange();
        createAccommodationPage.inputSlotPrice("300");
        createAccommodationPage.addSlotClick();
        createAccommodationPage.checkIfAdded();
        createAccommodationPage.checkNewPrice();

        createAccommodationPage.clickCalendar();
        createAccommodationPage.selectOverlapDateRange();
        createAccommodationPage.inputSlotPrice("300");
        createAccommodationPage.addSlotClick();
        createAccommodationPage.checkUnion();

        createAccommodationPage.createClick();
        createAccommodationPage.clickSnackBar("Error: Default price must be positive.");

        createAccommodationPage.inputPrice("-200");
        createAccommodationPage.createClick();
        createAccommodationPage.clickSnackBar("Error: Default price must be positive.");

        createAccommodationPage.inputPrice("200");
        createAccommodationPage.inputDue("0");
        createAccommodationPage.createClick();
        createAccommodationPage.clickSnackBar("Error: Cancellation due must be positive.");

        createAccommodationPage.inputDue("-5");
        createAccommodationPage.createClick();
        createAccommodationPage.clickSnackBar("Error: Cancellation due must be positive.");

        createAccommodationPage.inputDue("5");
        createAccommodationPage.createClick();
        createAccommodationPage.clickSnackBar("Accommodation request has been successfully created.");

        createAccommodationPage.clickAccommodations();

        MyAccommodationsPage myAccommodationsPage = new MyAccommodationsPage(driver);
        myAccommodationsPage.waitUntilOpen();
        myAccommodationsPage.imageClick();

        AccommodationPage accommodationPage = new AccommodationPage(driver);
        accommodationPage.waitUntilOpen();
        accommodationPage.editClick();

        EditAccommodationPage editAccommodationPage = new EditAccommodationPage(driver);
        editAccommodationPage.waitUntilOpen();

        editAccommodationPage.removeSlot();
        editAccommodationPage.removeSlot();
        editAccommodationPage.removeSlot();

        editAccommodationPage.clickCalendar();
        editAccommodationPage.selectDateRange();

        editAccommodationPage.inputSlotPrice("-200");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.clickSnackBar("Price cannot be negative.");

        editAccommodationPage.inputSlotPrice("0");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.clickSnackBar("Price cannot be negative.");

        editAccommodationPage.inputSlotPrice("200");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.checkIfAdded();
        editAccommodationPage.removeSlot();

        editAccommodationPage.clickCalendar();
        editAccommodationPage.selectDateRange();
        editAccommodationPage.inputSlotPrice("200");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.checkIfAdded();

        editAccommodationPage.clickCalendar();
        editAccommodationPage.selectDateRange();
        editAccommodationPage.inputSlotPrice("300");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.checkIfAdded();
        editAccommodationPage.checkNewPrice();

        editAccommodationPage.clickCalendar();
        editAccommodationPage.selectOverlapDateRange();
        editAccommodationPage.inputSlotPrice("100");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.checkOverlap();

        editAccommodationPage.removeSlot();
        editAccommodationPage.removeSlot();

        editAccommodationPage.clickCalendar();
        editAccommodationPage.selectOverlapWithReservation();
        editAccommodationPage.inputSlotPrice("100");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.checkIfReservationOverlapAdded();
        editAccommodationPage.editClick();
        editAccommodationPage.clickSnackBar("Availability slot cannot be edited due to reservations made in that period.");

        editAccommodationPage.removeSlot();

        editAccommodationPage.clickCalendar();
        editAccommodationPage.selectDateRange();
        editAccommodationPage.inputSlotPrice("300");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.checkIfAdded();
        editAccommodationPage.checkNewPrice();

        editAccommodationPage.clickCalendar();
        editAccommodationPage.selectOverlapDateRange();
        editAccommodationPage.inputSlotPrice("300");
        editAccommodationPage.addSlotClick();
        editAccommodationPage.checkUnion();

        editAccommodationPage.inputPrice("0");
        editAccommodationPage.editClick();
        editAccommodationPage.clickSnackBar("Error: Default price must be positive.");

        editAccommodationPage.inputPrice("-200");
        editAccommodationPage.editClick();
        editAccommodationPage.clickSnackBar("Error: Default price must be positive.");

        editAccommodationPage.inputPrice("200");
        editAccommodationPage.inputDue("0");
        editAccommodationPage.editClick();
        editAccommodationPage.clickSnackBar("Error: Cancellation due must be positive.");

        editAccommodationPage.inputDue("-5");
        editAccommodationPage.editClick();
        editAccommodationPage.clickSnackBar("Error: Cancellation due must be positive.");

        editAccommodationPage.inputDue("5");
        editAccommodationPage.editClick();
        editAccommodationPage.clickSnackBar("Accommodation request has been successfully created.");

    }
}
