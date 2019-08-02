package com.creditcloud.jpa.unit_test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public class TestSelenium {

    @Test
    public void testSelenium() throws IOException, InterruptedException {
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("E:\\softface\\chromedriver\\chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        service.start();
        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        System.out.println(service.getUrl());
        WebDriver driver = new RemoteWebDriver(service.getUrl(),cap);
        driver.get("https://www.baidu.com");
        Thread.sleep(1000);


        WebElement kw = driver.findElement(By.id("kw"));
        WebElement su = driver.findElement(By.id("su"));
        kw.sendKeys("selenium");
        su.click();
        WebElement element = driver.findElement(By.tagName("html"));
        System.out.println(driver.manage().getCookies());
        System.out.println(element.getAttribute("innerHTML"));
        //System.out.println(element.getText());
        driver.quit();
        service.stop();
    }



}
