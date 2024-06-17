package com.example.odyssey.selenium.student3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GuestHomePage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@formcontrolname = 'address']")
    private WebElement addressInput;

    @FindBy(xpath = "//*[@formcontrolname = 'guests']")
    private WebElement guestsInput;

    @FindBy(xpath = "//*[@Aria-label = 'Open calendar']")
    private WebElement openDatePickerButton;

    @FindBy(xpath = "//*[@Aria-label = 'Next month']")
    private WebElement nextMonthButton;

    @FindBy(className = "search-button")
    private WebElement searchButton;

    @FindBy(className = "filter-button")
    private WebElement filterButton;

    @FindBy(className = "filter-header")
    private WebElement filterHeader;

    @FindBy(xpath = "//span[text()='Apply filters']")
    private WebElement applyFiltersButton;

    @FindBy(xpath = "//mat-label[text()='Select accommodation type']")
    private WebElement accommodationTypeDropdown;

    @FindBy(className = "no-results")
    private WebElement NoResultsText;

    public GuestHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void inputAddress(String address){
        addressInput.clear();
        addressInput.sendKeys(address);
    }

    public void inputGuests(String guests){
        guestsInput.clear();
        guestsInput.sendKeys(guests);
    }

    public void openDatePicker() {
        openDatePickerButton.click();
    }

    public void clickNextMonth() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(nextMonthButton)).click();
    }

    public void clickDayButton(String day) {
        WebElement dayButton = driver.findElement(By.xpath("//*[@aria-label = '" + day + "']"));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(dayButton)).click();
    }

    public void clickSearchButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickFilterButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(filterButton)).click();
    }

    public void waitUntilFilterDialogOpen(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(filterHeader));
    }

    public void selectAmenity(String amenityInput) {
        String checkboxPath = "//label[text()='" + amenityInput + "']";

        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(checkboxPath));

        actions.moveToElement(element).click().build().perform();
    }


    public void clickApplyFilters(){
        Actions actions = new Actions(driver);
        actions.moveToElement(applyFiltersButton).click().build().perform();
    }

    public void openTypesDropdown(){
        Actions actions = new Actions(driver);
        actions.moveToElement(accommodationTypeDropdown).click().build().perform();
    }

    public void selectDropdownOption(String option){
        WebElement optionElement = driver.findElement(By.xpath("//mat-option[@value='" + option + "']"));
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(optionElement)).click();
    }

    public void checkIfNoResultsFound(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(NoResultsText));
    }

    public int countAccommodationCards(int numberOfCards) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement listSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-section")));

        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                List<WebElement> accommodationCards = listSection.findElements(By.tagName("app-accommodation-card"));
                return accommodationCards.size() == numberOfCards;
            }
        });

        List<WebElement> accommodationCards = listSection.findElements(By.tagName("app-accommodation-card"));
        return accommodationCards.size();
    }



    public boolean checkAccommodationNames(List<String> accommodationNames) {
        WebElement listSection = driver.findElement(By.className("list-section"));

        List<WebElement> accommodationCards = listSection.findElements(By.tagName("app-accommodation-card"));

        Set<String> foundNames = new HashSet<>();

        for (WebElement card : accommodationCards) {
            WebElement titleTextElement = card.findElement(By.className("title_text"));
            String titleText = titleTextElement.getText().trim();

            if (accommodationNames.contains(titleText)) {
                foundNames.add(titleText);
            }
        }

        return foundNames.containsAll(accommodationNames);
    }
}
