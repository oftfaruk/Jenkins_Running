package com.app.stepdefinitions;

import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.SQLException;
import java.util.Collections;

public class MyStepdefs {
    @Given("User is on the login page")
    public void userIsOnTheLoginPage() throws SQLException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
//        options.setBinary("/usr/bin/google-chrome");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-extensions"); // disabling extensions
//        options.addArguments("--disable-setuid-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--headless");
//        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
//        options.setExperimentalOption("useAutomationExtension", false);
//
//        WebDriverManager.chromedriver().setup();
//
//
//       // options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");  //chrome binary location specified here
//        options.addArguments("start-maximized");
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//        options.setExperimentalOption("useAutomationExtension", false);
//        WebDriver driver = new ChromeDriver(options);
//        driver.get("https://www.google.com/");


//

//        options.addArguments("--headless");
//        System.setProperty("webdriver.chrome.args", "--disable-logging");
//        System.setProperty("webdriver.chrome.silentOutput", "true");
//        options.setExperimentalOption("useAutomationExtension", false);
//        options.addArguments("--disable-gpu"); // applicable to windows os only
//        options.addArguments("start-maximized"); // open Browser in maximized mode
//        options.addArguments("disable-infobars"); // disabling infobars
//
//        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//        // Bypass OS security model
//        options.addArguments("--disable-dev-shm-usage");
//        // System.setProperty("webdriver.chrome.driver", "http://18.203.176.202:8080");
//        //options.setBinary("http://18.203.176.202:8080");
//        options.addArguments("--remote-debugging-port=9222");
//        String chromeDriverPath = "./usr/bin/chromedriver";
//        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//
//
//        ChromeDriver driver1 = new ChromeDriver(options);


    }
}
