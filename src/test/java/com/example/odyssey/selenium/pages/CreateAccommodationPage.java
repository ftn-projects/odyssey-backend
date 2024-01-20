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
    @FindBy(xpath = "//span[contains(@class,'mdc-button__label') and text() = 'Create ']")
    private WebElement createBtn;
    @FindBy(name = "title")
    private WebElement title;
    @FindBy(xpath = "//p[text()='TV']")
    private WebElement amenity;
    @FindBy(name = "defaultPrice")
    private WebElement defaultPrice;
    @FindBy(name = "cancellationDue")
    private WebElement cancellationDue;
    @FindBy(name = "about")
    private WebElement description;
    @FindBy(name = "street")
    private WebElement street;
    @FindBy(name = "city")
    private WebElement city;
    @FindBy(css="[aria-label='Open calendar']")
    private WebElement calender;
    @FindBy(className = "mat-calendar-next-button")
    private WebElement calendarNextBtn;
    @FindBy(css="[aria-label='January 25, 2024']")
    private WebElement january25;
    @FindBy(css="[aria-label='January 30, 2024']")
    private WebElement january30;
    @FindBy(name = "price")
    private WebElement slotPrice;
    @FindBy(className = "add-slot-button")
    private WebElement addSlotBtn;
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

    public void selectAmenity(){
        amenity.click();
    }

    public void inputPrice(String price){
        defaultPrice.clear();
        defaultPrice.sendKeys(price);
    }

    public void inputDue(String due){
        cancellationDue.clear();
        cancellationDue.sendKeys(due);
    }

    public void inputDescription(String about){
        description.clear();
        description.sendKeys(about);
    }

    public void inputStreet(String streetName){
        street.clear();
        street.sendKeys(streetName);
    }

    public void inputCity(String cityName){
        city.clear();
        city.sendKeys(cityName);
    }

    public void clickCalendar(){calender.click();}
    public void selectDateRange(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january25));
        january25.click();
        january30.click();
    }

    public void inputSlotPrice(String price){
        slotPrice.clear();
        slotPrice.sendKeys(price);
    }

    public void addSlotClick(){ addSlotBtn.click(); }
    public void createClick(){ createBtn.click();}

}
