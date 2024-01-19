package com.example.odyssey.selenium.tests;


import com.example.odyssey.selenium.pages.CreateAccommodationPage;
import com.example.odyssey.selenium.pages.HomePage;
import com.example.odyssey.selenium.pages.LoggedInPage;
import com.example.odyssey.selenium.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
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
    }
}
