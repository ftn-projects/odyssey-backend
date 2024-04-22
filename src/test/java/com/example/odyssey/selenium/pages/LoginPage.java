package com.example.odyssey.selenium.pages;

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
    private WebElement email;

    @FindBy(css = "input[formControlName='password']")
    private WebElement password;

    @FindBy(css = "button")
    private WebElement continueBtn;
    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitUntilOpen(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(loginTitle));
    }

    public void inputEmail(String mail){
        email.clear();
        email.sendKeys(mail);
    }

    public void inputPassword(String pass){
        password.clear();
        password.sendKeys(pass);
    }

    public void buttonClick(){
        continueBtn.click();
    }

}
