package com.example.odyssey.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccommodationPage {
    private WebDriver driver;

    @FindBy(xpath = "//div[contains(@class,'title-text') and text() = 'Soba sa 3 kreveta']")
    private WebElement title;
    @FindBy(xpath = "//span[contains(@class,'mdc-button__label') and text() = ' Edit ']")
    private WebElement editBtn;

    public AccommodationPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitUntilOpen(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(title));
    }

    public void editClick(){editBtn.click();}
}
