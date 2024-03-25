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
    @FindBy(xpath = "//span[contains(@class,'mdc-button__label') and text() = 'Submit ']")
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
    @FindBy(css="[aria-label='January 24, 2024']")
    private WebElement january24;
    @FindBy(xpath="//td[text()=' 1/24/2024 ']")
    private WebElement january24text;
    @FindBy(css="[aria-label='January 25, 2024']")
    private WebElement january25;
    @FindBy(xpath="//td[text()=' 1/25/2024 ']")
    private WebElement january25text;
    @FindBy(css="[aria-label='January 26, 2024']")
    private WebElement january26;
    @FindBy(xpath="//td[text()=' 1/26/2024 ']")
    private WebElement january26text;
    @FindBy(xpath="//td[text()=' 1/27/2024 ']")
    private WebElement january27text;
    @FindBy(css="[aria-label='January 30, 2024']")
    private WebElement january30;
    @FindBy(xpath="//td[text()=' 1/30/2024 ']")
    private WebElement january30text;
    @FindBy(xpath="//td[text()=' 300 ']")
    private WebElement slotNewPrice;
    @FindBy(xpath="//td[text()=' 100 ']")
    private WebElement slotOverlapPrice;
    @FindBy(name = "price")
    private WebElement slotPrice;
    @FindBy(className = "add-slot-button")
    private WebElement addSlotBtn;
    @FindBy(xpath = "//td/button[1]")
    private WebElement removeSlotBtn;
    @FindBy(xpath = "//a[text()='My accommodations']")
    private WebElement myAccommodations;
    @FindBy(className = "mat-mdc-snack-bar-action")
    private WebElement snackBtn;
    @FindBy(className = "mat-mdc-snack-bar-label")
    private WebElement snackText;
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

    public void selectOverlapDateRange(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january24));
        january24.click();
        january26.click();
    }

    public void inputSlotPrice(String price){
        slotPrice.clear();
        slotPrice.sendKeys(price);
    }

    public void addSlotClick(){ addSlotBtn.click(); }

    public void checkIfAdded(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january25text));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january30text));
    }

    public void checkOverlap(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january24text));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january26text));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(slotOverlapPrice));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january27text));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january30text));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(slotNewPrice));
    }

    public void checkUnion(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january24text));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(january30text));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(slotNewPrice));
    }

    public void checkNewPrice(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(slotNewPrice));
    }
    public void createClick(){ createBtn.click();}

    public void clickSnackBar(String text){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(snackBtn));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(snackText,text));
        snackBtn.click();
    }

    public void clickAccommodations() {myAccommodations.click();}
    public void removeSlot(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(removeSlotBtn));
        removeSlotBtn.click();
    }

}
