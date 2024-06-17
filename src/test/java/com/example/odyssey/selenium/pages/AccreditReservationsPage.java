package com.example.odyssey.selenium.pages;

import com.example.odyssey.entity.reservations.Reservation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class AccreditReservationsPage {
    private final WebDriver driver;
    private final WebDriverWait driverWait;

    @FindBy(className = "title")
    private WebElement pagetitle;
    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    public AccreditReservationsPage(WebDriver driver) {
        this.driver = driver;
        this.driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void waitUntilLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(pagetitle));
        ExpectedCondition<Boolean> textNotEmpty = driver -> {
            assert driver != null;
            var element = driver.findElement(By.xpath("//tbody/tr[1]/td[1]"));
            return element != null && !element.getText().trim().isEmpty();
        };
        driverWait.until(textNotEmpty);
    }

    public HashMap<Long, Reservation.Status> getReservationWithStatuses() {
        var reservations = new HashMap<Long, Reservation.Status>();

        for (var row : rows)
            reservations.put(Long.parseLong(getIdField(row).getText()),
                    parseStatus(getStatusField(row).getText()));

        return reservations;
    }

    public void declineReservation(long id) {
        var row = rows.stream()
                .filter(r -> Long.parseLong(getIdField(r).getText()) == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reservation with id " + id + " not found"));

        var declineBtn = getDeclineButton(row);
        declineBtn.click();
        driverWait.until(ExpectedConditions.invisibilityOf(declineBtn));
    }

    public void acceptReservation(long id) {
        var row = rows.stream()
                .filter(r -> Long.parseLong(getIdField(r).getText()) == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reservation with id " + id + " not found"));

        var acceptBtn = getAcceptButton(row);
        acceptBtn.click();
        driverWait.until(ExpectedConditions.invisibilityOf(acceptBtn));
    }

    private static Reservation.Status parseStatus(String status) {
        return switch (status) {
            case "Requested" -> Reservation.Status.REQUESTED;
            case "Accepted" -> Reservation.Status.ACCEPTED;
            case "Declined" -> Reservation.Status.DECLINED;
            case "Cancelled request" -> Reservation.Status.CANCELLED_REQUEST;
            case "Cancelled reservation" -> Reservation.Status.CANCELLED_RESERVATION;
            default -> throw new IllegalArgumentException("Unexpected value: " + status);
        };
    }

    private WebElement getIdField(WebElement row) {
        return row.findElement(By.className("mat-column-id"));
    }

    private WebElement getStatusField(WebElement row) {
        return row.findElement(By.className("mat-column-status"));
    }

    private WebElement getAcceptButton(WebElement row) {
        return row.findElement(By.className("accept-button"));
    }

    private WebElement getDeclineButton(WebElement row) {
        return row.findElement(By.className("decline-button"));
    }
}