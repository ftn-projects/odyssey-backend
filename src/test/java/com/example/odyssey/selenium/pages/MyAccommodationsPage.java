package com.example.odyssey.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccommodationsPage {
    private WebDriver driver;
    @FindBy(xpath = "//h1[text()='My accommodations']")
    private WebElement title;
    @FindBy(xpath = "//div[@class='list-section']//img[1]")
    private WebElement img;

    public MyAccommodationsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitUntilOpen(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(title));
    }

    public void imageClick(){
        img.click();
    }
}
