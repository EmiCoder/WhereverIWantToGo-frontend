package com.example.frontend.web;

import com.example.frontend.domain.ResponseCity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleShowMethod {

    public static void show(ResponseCity city) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driver.get("https://www.google.pl/maps/@52.1065387,21.3352448,13z");
        WebElement element = driver.findElement(By.xpath("//*[@id=\"searchboxinput\"]"));
        element.sendKeys(city.getCityName() + " " + city.getCountryName());
        WebElement lookForElement = driver.findElement(By.xpath("//*[@id=\"searchbox-searchbutton\"]"));
        lookForElement.click();
    }
}
