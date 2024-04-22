package com.example.odyssey.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private static String PAGE_URL="http://localhost:4200/accommodations";
    @FindBy(xpath = "//*[text() = 'Log in']")
    private WebElement login;

    public HomePage(WebDriver driver){
        this.driver=driver;
        driver.get(PAGE_URL);

        PageFactory.initElements(driver, this);
    }

    public void clickLogin(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(login)).click();
    }
}
