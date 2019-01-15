/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.driver;

//import com.creditcloud.jpa.unit_test.constant.WebConstant;
//import com.creditcloud.jpa.unit_test.model.PtuiCheckVK;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;

/**
 *
 * @author MRB
 */
public class ExproderDriver {
    
    private static volatile FirefoxDriver instance;
    private static volatile WebDriver driver;
    
    private ExproderDriver(){
        
    }
    
    public static FirefoxDriver getInstance(){
        if(instance==null){
            synchronized(FirefoxDriver.class){
                instance = new FirefoxDriver();
            }
        }
        return instance;
    }

    public static WebDriver getChromeInstance(){
        if(driver == null){
            synchronized (ChromeDriver.class){
                try {
                    ChromeDriverService service = new ChromeDriverService.Builder()
                            .usingDriverExecutable(new File("E:\\softface\\chromedriver\\chromedriver.exe"))
                            .usingAnyFreePort()
                            .build();
                    service.start();
                    ChromeOptions options = new ChromeOptions();
                    DesiredCapabilities cap = DesiredCapabilities.chrome();
                    cap.setCapability(ChromeOptions.CAPABILITY, options);
                    driver = new RemoteWebDriver(service.getUrl(), cap);
                }catch(Exception ex){}
            }
        }
        return driver;
    }
}
