package com.app.stepdefinitions;


import com.app.utilities.Driver;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Collections;
import java.util.List;


public class MyStepdefs {
    @Given("User is on the login page")
    public void userIsOnTheLoginPage() throws InterruptedException {
        //mvn clean install System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
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

//        //   WebDriverManager.chromedriver().setup();
//        WebDriverManager.chromedriver().setup();
//        WebDriver driver;
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        options.addArguments("--remote-debugging-port=9222");
//
//     //   options.setBinary("/usr/bin/google-chrome");
//        options.addArguments("--disable-dev-shm-usage");
////        //   options.addArguments("--verbose");
//////        options.addArguments("--whitelisted-ips=")
//        options.addArguments("headless");
//////        WebDriver driver = new ChromeDriver();
//        driver = new ChromeDriver(options);
//        //   WebDriverManager.firefoxdriver().setup();
////        FirefoxOptions options = new FirefoxOptions();
////        options.addArguments("headless");
////        options.addArguments("--disable-dev-shm-usage");
////        WebDriver driver = new FirefoxDriver();
////        driver = new FirefoxDriver(options);
//        driver.get("https://test.rack-eye.com/login");
        //    System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");

//        ChromeOptions options = new ChromeOptions();
//        //  opt.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");  //chrome binary location specified here
//        //   options.addArguments("start-maximized");
//        options.addArguments("--no-sandbox");
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//        options.setExperimentalOption("useAutomationExtension", false);
//        WebDriver driver = new ChromeDriver(options);
//        driver.get("https://www.google.com/");


        //Driver.get().get("https://test.rack-eye.com/login");
        System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.setBinary("/usr/bin/google-chrome");
        WebDriver driver = new ChromeDriver(options);
//        driver.get("https://google.com");
        Thread.sleep(5000);
        driver.get("https://qascript.com/run-jenkins-job-with-selenium-tests-on-aws-ec2-linux-server/");

        Thread.sleep(5000);
        System.out.println("driver.getPageSource() = " + driver.getPageSource());
        System.out.println("driver.getCurrentUrl() = " + driver.getCurrentUrl());
        System.out.println("driver.getTitle() = " + driver.getTitle());
        driver.get("https://google.com");
        System.out.println("driver.getPageSource() = " + driver.getPageSource());
        System.out.println("driver.getCurrentUrl() = " + driver.getCurrentUrl());
        System.out.println("driver.getTitle() = " + driver.getTitle());
        driver.navigate().back();
        System.out.println("driver.getCurrentUrl() = " + driver.getCurrentUrl());


        System.out.println("hello  deneme123");
    }
}
