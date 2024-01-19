package com.example.odyssey.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateAccommodationPage {
    private WebDriver driver;

    @FindBy(xpath = "//span[text()='Create']")
    private WebElement pagetitle;
    @FindBy(xpath = "//button[text()='Create']")
    private WebElement createBtn;
    @FindBy(name = "title")
    private WebElement title;

    public CreateAccommodationPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitUntilOpen(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(pagetitle));
    }

    public void inputTitle(String titleText){
        title.clear();
        title.sendKeys(titleText);
    }

}
