package com.example.odyssey.selenium.student3.tests;

import com.example.odyssey.selenium.student3.pages.GuestHomePage;
import com.example.odyssey.selenium.student3.pages.HomePage;
import com.example.odyssey.selenium.student3.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SearchAndFilterAccommodationTest extends TestBase {
    @Test
    public void searchAndFilter() {
        HomePage loggedOffhomePage = new HomePage(driver);
        loggedOffhomePage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitUntilOpen();
        loginPage.inputEmail("milos@gmail.com");
        loginPage.inputPassword("milos");
        loginPage.pressContinue();

        GuestHomePage homepage = new GuestHomePage(driver);
        homepage.inputAddress("Beograd");
        homepage.inputGuests("3");
        homepage.openDatePicker();
        homepage.clickNextMonth();
        homepage.clickDayButton("July 5, 2024");
        homepage.clickDayButton("July 10, 2024");
        homepage.clickSearchButton();
        homepage.countAccommodationCards(3);
        List<String> accommodationNames = Arrays.asList("Soba sa bračnim ležajem", "Soba sa 3 ležaja", "Veliki apartman");
        boolean allNamesFound = homepage.checkAccommodationNames(accommodationNames);
        assertTrue(allNamesFound, "Not all accommodation names found");



        homepage.clickFilterButton();
        homepage.waitUntilFilterDialogOpen();

        homepage.selectAmenity(" Beach access ");
        homepage.selectAmenity(" King bed ");
        homepage.clickApplyFilters();
        homepage.clickSearchButton();
        homepage.countAccommodationCards(2);
        List<String> accommodationNamesTwo = Arrays.asList("Soba sa bračnim ležajem", "Veliki apartman");
        boolean allNamesFoundTwo = homepage.checkAccommodationNames(accommodationNamesTwo);
        assertTrue(allNamesFoundTwo, "Not all accommodation names found");


        homepage.clickFilterButton();
        homepage.waitUntilFilterDialogOpen();

        homepage.selectAmenity(" Beach access ");
        homepage.selectAmenity(" King bed ");
        homepage.openTypesDropdown();
        homepage.selectDropdownOption("house");
        homepage.clickApplyFilters();
        homepage.clickSearchButton();
        homepage.checkIfNoResultsFound();
    }
}
