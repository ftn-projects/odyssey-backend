package com.example.odyssey.selenium.student3.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver webDriver;

    private static String PAGE_URL="http://localhost:4200/accommodations";
    @FindBy(xpath = "//*[text() = 'Log in']")
    private WebElement loginButton;

    public HomePage(WebDriver webDriver){
        this.webDriver=webDriver;
        webDriver.get(PAGE_URL);

        PageFactory.initElements(webDriver, this);
    }
    public void clickLogin(){
        (new WebDriverWait(webDriver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
