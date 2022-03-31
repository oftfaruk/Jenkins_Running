package com.app.stepdefinitions;

import io.cucumber.java.en.Given;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class MyStepdefs {
    @Given("User is on the login page")
    public void userIsOnTheLoginPage() {
       System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
//      System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox"); // Bypass OS security model
//        options.addArguments("start-maximized"); // open Browser in maximized mode
//        options.addArguments("disable-infobars"); // disabling infobars
//        options.addArguments("--disable-extensions"); // disabling extensions
//        options.addArguments("--disable-gpu"); // applicable to windows os only
//        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems

//        WebDriverManager.chromedriver().setup();
////        WebDriver driver = new ChromeDriver(options);

     //   WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--no-sandbox");
        options.setBinary("/usr/bin/google-chrome");
        options.addArguments("--disable-dev-shm-usage");
       //   WebDriverManager.chromedriver().setup();


        options.addArguments("headless");
//        WebDriver driver = new ChromeDriver();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://google.com");


    }
}
