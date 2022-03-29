package com.app.stepdefinitions;

import com.app.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.SQLException;

public class MyStepdefs {
    @Given("User is on the login page")
    public void userIsOnTheLoginPage() throws SQLException {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        System.setProperty("webdriver.chrome.args", "--disable-logging");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        // Bypass OS security model
        options.addArguments("--disable-dev-shm-usage");
        // System.setProperty("webdriver.chrome.driver", "http://18.203.176.202:8080");
        //options.setBinary("http://18.203.176.202:8080");
        options.addArguments("--remote-debugging-port=9222");


        ChromeDriver driver = new ChromeDriver(options);
        driver.get(ConfigurationReader.get("url"));


    }
}
