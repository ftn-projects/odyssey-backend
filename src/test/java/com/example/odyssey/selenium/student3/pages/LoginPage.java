package com.example.odyssey.selenium.student3.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    @FindBy(className = "title")
    private WebElement loginTitle;

    @FindBy(css = "input[formControlName='email']")
    private WebElement emailInput;

    @FindBy(css = "input[formControlName='password']")
    private WebElement passwordInput;

    @FindBy(css = "button")
    private WebElement continueButton;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitUntilOpen(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(loginTitle));
    }

    public void inputEmail(String email){
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void inputPassword(String password){
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void pressContinue(){
        continueButton.click();
    }
}
