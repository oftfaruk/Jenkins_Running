package com.app.utilities;

import com.app.stepdefinitions.Hooks;
import io.cucumber.java.Scenario;
import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrowserUtils extends MasterUtils {

    private static WebDriverWait webDriverWait = new WebDriverWait(Driver.get(), Integer.parseInt(ConfigurationReader.get("explicitWait")));

    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitFor(int miliSec, TimeUnit t) {
        try {
            Thread.sleep(miliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebDriverWait wait(int seconds) {
        return new WebDriverWait(Driver.get(), seconds);
    }

    public static Object executeScript(String script) {

        return ((JavascriptExecutor) Driver.get()).executeScript(script);

    }

    public static boolean wait(WebElement webElement) {

        WebDriverWait wait = wait(Integer.parseInt(ConfigurationReader.get("explicitWait")));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            return true;
        } catch (NoSuchElementException n) {
            Assert.fail("The element does not exist on the current page");
        } catch (TimeoutException e) {
            Assert.fail("I was unable to find the element  within the wait time");
        }
        return false;
    }

    public static void waitForElemetToBeClickable(String elementCSSLocator, String elementName) {
        try {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(elementCSSLocator)));
        } catch (NoSuchElementException n) {
            Assert.fail("The element " + elementName + " does not exist on the current page");
        } catch (TimeoutException t) {
            Assert.fail("I was unable to find the element " + elementName + " within the wait time");
        }
    }

    public static boolean waitForElementsToBeVisible(List<WebElement> elements) {

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(elements));
        return elements.size() >= 1;
    }

    public static void click(WebElement element) {
        waitForElementToBeClickable(element);
        try {
            wait(element);
            scrolltoElement(element);
            element.click();
        } catch (ElementClickInterceptedException c) {
            clickWithJS(element);
        } catch (StaleElementReferenceException s) {
//            shortWait();
            scrolltoElement(element);
            element.click();
        }
    }

    /**
     * ! use this method only for the web elements starts with "//button" and "//a" tags
     * Be careful! -- if there is another web element having same name with the button, it may click on it..
     *
     * @param elementName: Name of the button
     */
    public static void clickByText(String elementName) {
        WebElement element = getWebElementByText(elementName);
        click(element);
    }

    public static void click(By by) {
        waitForElementToBeClickable(by);
        if (isElementPresent(by)) {
            click(getElement(by));
        }
    }

    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].click();", element);
    }

    public static void clickWithJS(By by) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].click();", Driver.get().findElement(by));
    }

    public static void scrollDown() {
        executeScript("window.scrollTo(0, 200)");
    }

    public static void scrollUp() {
        executeScript("window.scrollTo(200, 0)");
    }

    public static void scrolltoElement(WebElement element) {
        try {
            ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (StaleElementReferenceException ignored) {

        }
    }

    public static void scrolltoElement(By by) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", Driver.get().findElement(by));
    }

    public static void scrollToBottom() {
        executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static String getText(WebElement element, String elementname) {
        String VisibleText = "";
        try {
            wait(element);
            VisibleText = element.getText();
        } catch (Exception e) {
            scrolltoElement(element);
            VisibleText = element.getText();
        }
        return VisibleText;
    }

    public static String getText(WebElement element) {
        String VisibleText = "";
        try {
            wait(element);
            VisibleText = element.getText();
        } catch (Exception e) {
            scrolltoElement(element);
            VisibleText = element.getText();
        }
        return VisibleText;
    }

    public static String getAttribute(WebElement element, String attributeName, String elementname) {
        wait(element);
        return element.getAttribute(attributeName);
    }

    public static void elementSelectedByText(List<WebElement> elements, String text, String elementname) {
        if (text.equals(""))
            return;

        if (elements.stream().allMatch(e -> e.isEnabled()) && elements.stream().allMatch(e -> e.isDisplayed())) {
            WebElement element = elements.stream().filter(e -> e.getText().equals(text)).findFirst().get();
            element.click();
            System.out.println("Element ( " + elementname + " )  is Selected");
            //
        } else {
            Assert.fail("Element ( " + elementname + " ) Not present/enabled during wait time. Unable to select element by its text");
        }
    }

    public static void elementSelectFilterByText(String locator, String text, String elementname) {

        if (text.equals(""))
            return;

        int length = 0, temp = 0;
        boolean condition = false;
        List<WebElement> elements = getElementsBycssSelector(locator);
        temp = length = elements.size();
        do {
            if (elements.stream().findAny().equals(text.trim())) {
                elements.stream().filter(x -> x.equals(text.trim())).findFirst().get().click();
            } else {
                scrolltoElement(getElementsBycssSelector(locator).get(length - 1));

                elements = getElementsBycssSelector(locator);
                length = elements.size();
            }
            if (length == temp) {
                condition = true;
                //steplog.AddStepLog("Given input is not on dropdown list");
                Assert.fail("Given input is not on dropdown list");
            } else {
                temp = length;
            }
        } while (!condition);
    }

    public static WebElement

    getWebElementByText(String visibleText) {
        String locator = "//*[.='" + visibleText + "']";
        return getElementByXPath(locator);
    }

    public static WebElement getElement(By locator) {

        WebElement element = null;
        int tryCount = 0;
        int[] trials = {0, 0, 2};
        while (element == null) {
            try {
                element = Driver.get().findElement(locator);
            } catch (Exception e) {
                if (tryCount == 3) {
                    throw e;
                }
                BrowserUtils.shortWait();
                try {
                    Thread.sleep(trials[tryCount]);
                } catch (InterruptedException ignored) {
                }

                tryCount++;
            }
        }
        return element;
    }

    public static WebElement getElementById(String id) {
        By locator = By.id(id);
        return getElement(locator);
    }

    public static WebElement getElementByClassName(String clasname) {
        By locator = By.className(clasname);
        return getElement(locator);
    }

    public static WebElement getElementByName(String name) {
        By locator = By.name(name);
        return getElement(locator);
    }

    public static WebElement getElementBycssSelector(String cssSelector) {
        By locator = By.cssSelector(cssSelector);
        return getElement(locator);
    }

    public static WebElement getElementByLinktext(String linktext) {
        By locator = By.linkText(linktext);
        return getElement(locator);
    }

    public static WebElement getElementByXPath(String xPath) {
        By locator = By.xpath(xPath);
        isElementPresent(locator);
        return getElement(locator);

    }

    public static List<WebElement> getElements(By locator) {
        List<WebElement> element = null;
        int tryCount = 0;
        int[] seconds = {0, 0, 2};

        while (element == null) {
            try {
                element = Driver.get().findElements(locator);
            } catch (Exception e) {
                if (tryCount == 3) {
                    throw e;
                }

                try {
                    Thread.sleep(seconds[tryCount]);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                tryCount++;
            }
        }
        //Console.WriteLine(element.ToString() + "Now Retrieved");
        return element;
    }

    public static List<WebElement> getElementsById(String id) {
        By locator = By.id(id);
        return getElements(locator);
    }

    public static List<WebElement> getElementsByClassName(String className) {
        By locator = By.className(className);
        return getElements(locator);
    }

    public static void doubleClick(WebElement webElement) {
        Actions actions = new Actions(Driver.get());
        actions.moveToElement(webElement).doubleClick();
    }

    public static void clearByDoubleClick(WebElement webElement) {
        Actions actions = new Actions(Driver.get());
        actions.moveToElement(webElement).doubleClick();

        webElement.sendKeys(Keys.BACK_SPACE);
        webElement.clear();
    }

    public static String getTextofTheAlert() {
        return Driver.get().switchTo().alert().getText();
    }

    public static void waitImplicitly(int milisec) {
        Driver.get().manage().timeouts().implicitlyWait(milisec, TimeUnit.MILLISECONDS);
    }

    public static void waitForReadyState() {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Integer.parseInt(ConfigurationReader.get("explicitWait")));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static void shortImplicit() {
        long sec = (Long.parseLong(ConfigurationReader.get("shortImlicit")));
        Driver.get().manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);

    }

    public static void longImplicit() {

        long sec = (Long.parseLong(ConfigurationReader.get("longImplicit").toString()));
        Driver.get().manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
    }

    public static void shortWait() {
        waitFor(Integer.parseInt(ConfigurationReader.get("shortWait")));
    }

    public static void longWait() {
        waitFor(Integer.parseInt(ConfigurationReader.get("longWait")));
    }

    public static void refresh() {
        Driver.get().navigate().refresh();
    }

    public static void getDefaultUrl() {
        Driver.get().get(ConfigurationReader.get("url"));
        new WebDriverWait(Driver.get(), 10).until(ExpectedConditions.titleIs("A-SAFE RackEye - " + ConfigurationReader.get("env")));
    }

    public static void navigateToLink(String link) {
        Driver.get().get(link);
    }

    public static String getCurrentUrl() {
        return Driver.get().getCurrentUrl();
    }

    public static String getTextById(String id) {
        WebElement element = getElementById(id);
        return getText(element);
    }

    public static void moveToElement(By by) {
        getActions().moveToElement(Driver.get().findElement(by));
    }

    public static Actions getActions() {
        return new Actions(Driver.get());
    }


    public static boolean isElementPresent(String text) {
        return isElementPresent(By.xpath("//*[.='" + text + "']"));
    }

    public static boolean isElementPresent(By by) {
        for (int i = 0; i < 8; i++) {
            if (getElements(by).size() > 0) {
                return true;
            }
            shortWait();
        }
        return false;
    }

    public static boolean isElementAbsent(By by) {
        shortWait();
        int size = getElement_s(by).size();
        return size == 0;
    }

    public static boolean isElementAbsent(String text) {
        shortWait();
        return getElement_s(By.xpath("//*[@text='" + text + "']")).size() == 0;
    }

    public static boolean isElementAbsent(By by, int scrollCount) {

        for (int i = 0; i < scrollCount; i++) {

            for (int j = 0; j < 5; j++) {
                try {
                    Assert.assertTrue(getElement_s(by).size() == 0);
                    break;
                } catch (AssertionError a) {
                    shortWait();

                }
            }

        }
        return getElement_s(by).size() == 0;
    }

    public static void verifyElementAbsent(By by) {
        for (int i = 0; i < 5; i++) {
            try {
                Assert.assertEquals(0, getElement_s(by).size());
                return;
            } catch (ComparisonFailure c) {
                shortWait();
            }
        }
        Assert.assertEquals(0, getElement_s(by).size());
    }

    public static List<WebElement> getElement_s(By by) {
        List elements = Driver.get().findElements(by);
        return elements;
    }

    public List<WebElement> getElementsByName(String name) {
        By locator = By.name(name);
        return getElements(locator);
    }

    public static List<WebElement> getElementsBycssSelector(String cssSelector) {
        By locator = By.cssSelector(cssSelector);
        return getElements(locator);
    }

    public static List<WebElement> getElementsByXPath(String xPath) {
        By locator = By.xpath(xPath);
        return getElements(locator);
    }


    public static List<String> elementSelectFilterByText_GetElement(String locator, String elementname) {
        int length = 0, temp = 0;
        boolean condition = false;
        List<String> AllItems = new ArrayList<>();
        List<WebElement> elements;
        elements = getElementsBycssSelector(locator);

        temp = length = elements.size();
        do {
            scrolltoElement(getElementsBycssSelector(locator).get(length - 1));
            elements = getElementsBycssSelector(locator);
            length = elements.size();
            if (length == temp) {
                condition = true;

                for (WebElement element : elements) {
                    AllItems.add(element.getText());
                }
            } else
                temp = length;

        } while (!condition);
        return AllItems;
    }

    public static void elementSelectedByIndex(List<WebElement> elements, int index, String elementname) {
        if (elements.size() == 0 || elements.size() < index)
            Assert.fail("Element (" + elementname + " ) not present/enabled during wait time. Unable to select element");
        else
            BrowserUtils.click(elements.get(index));
    }

    public static void sendKeys(WebElement element, String text, String elementname) {
        if (text.equals(""))
            return;

        try {
            wait(element);
            element.clear();
            element.sendKeys(text);
            log("'" + text + "'" + " is Entered in " + elementname);
        } catch (StaleElementReferenceException s) {
            WebElement Ele = element;
            sendKeys(Ele, text, elementname);
        }
    }

    public static void sendKeys(WebElement element, String text) {
        if (text.equals(""))
            return;

        try {
            wait(element);
            element.clear();
            element.sendKeys(text);
            System.out.println("'" + text + "'" + " is Entered in ");
        } catch (StaleElementReferenceException s) {
            WebElement Ele = element;
            sendKeys(Ele, text);
        }
    }

    public static void sendKeysByID(String id, String text) {
        WebElement element;
        try {
            element = getElementById(id);
        } catch (NoSuchElementException n) {
            shortWait();
            shortWait();
            shortWait();
            shortWait();
            element = getElementById(id);
        }
        if (text.equals("") || text.equals("N/A")) {
            return;
        } else {
            try {
                sendKeys(element, text, id);
            } catch (StaleElementReferenceException s) {
                sendKeysByJSE(element, text);
            }
        }
    }

    public static void sendKeysByJSE(WebElement element, String text) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.get();
        jse.executeScript("arguments[0].value='" + text + "';", element);
    }

    public static void sendKeysByIDAndHitTheEnter(String id, String text) {
        if (text.equals("") || text.equals("N/A"))
            return;

        WebElement element = getElementById(id);
        try {
            wait(element);
            element.clear();
            element.sendKeys(text);
            element.sendKeys(Keys.ENTER);
            System.out.println("'" + text + "'" + " is Entered in " + id);
        } catch (StaleElementReferenceException s) {
            WebElement we = element;
            sendKeys(we, text, id);
            we.sendKeys(Keys.ENTER);
        }
    }

    public static void clearText(WebElement element, String elementname) {
        wait(element);
        element.clear();
        log("Cleared Text on the Element ( " + elementname + " ) ");
    }

    public static void clearText(WebElement element) {
        wait(element);
        element.clear();
    }

    public static void inputData(String id, String value) {
        shortImplicit();
        try {
            sendKeysByID(id, value);
        } catch (NoSuchElementException n) {
            selectDD(id, value);
        }
        longImplicit();
    }

    /**
     * @param dropDownID: the ID attribute of the dropdown
     * @param value:      the visible text of the dropdown
     */
    public static void selectDD(String dropDownID, String value) {
        if (!value.equals("N/A")) {
            WebElement dd = getElementById(dropDownID);
            waitForElementToBeClickable(dd);
            selectDropDownByText(dd, value, dropDownID);
        }
    }

    public static void selectDropDownByText(WebElement element, String text, String elementname) {
        wait(element);
        Select elementToBeSelected = new Select(element);
        try {
            elementToBeSelected.selectByVisibleText(text);
        } catch (NoSuchElementException e) {
            BrowserUtils.shortWait();
            elementToBeSelected.selectByVisibleText(text);
        }
        log("'" + text + "'" + " is selected from dropdown ( " + elementname + " )");
    }

    /**
     * @param dropDownID: the ID attribute of the dropdown
     */
    public static String getSelectedValueDD(String dropDownID) {
        WebElement dd = getElementById(dropDownID);
        Select select = new Select(dd);
        return select.getFirstSelectedOption().getText();
    }

    public static void addScreenshot(String filename, Scenario scenario) {

        final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "screenshot"); // not sure id it will work :)
    }

    public static void selectDropDownByValue(WebElement element, String value, String elementname, Scenario
            scenario) {
        wait(element);
        Select elementToBeSelected = new Select(element);
        elementToBeSelected.selectByValue(value);
        String text = elementToBeSelected.getFirstSelectedOption().getText();
        System.out.println("'" + text + "'" + " is selected from dropdown .( " + elementname + " )");
    }

    public static void selectDropDownByIndex(WebElement element, int index, String elementname) {
        wait(element);
        Select elementToBeSelected = new Select(element);
        elementToBeSelected.selectByIndex(index);
        String text = elementToBeSelected.getFirstSelectedOption().getText().toLowerCase();
        System.out.println("'" + text + "'" + " is selected from dropdown . ( " + elementname + " )");
    }

    public static String getSelectedOption(WebElement element, String elementname) {
        wait(element);
        Select elementSelected = new Select(element);
        return elementSelected.getFirstSelectedOption().getText();
    }


    public static void tabAcross(WebElement element, String elementname) {
        wait(element);
        element.sendKeys(Keys.TAB);
    }

    public static void staleWait() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForLoadingIconToDisappear() {
        try {
            WebElement Progressbar = Driver.get().findElement(By.cssSelector("[role='progressbar']"));
            while (Progressbar.isDisplayed()) {
                Driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            }
        } catch (Exception e) {
        }
    }

    public static void waitForElementToBeClickable(WebElement element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementToBeClickableAndClick(WebElement element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public static void waitForElementToBeClickable(By by) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(Driver.get().findElement(by)));
    }

    public static void waitForElementToBeVisible(WebElement element) {
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementToBeVisible(By by) {
        webDriverWait.until(ExpectedConditions.visibilityOf(Driver.get().findElement(by)));
    }

    public static void log(String data) {
        Hooks.scenario.log(data);
    }

    public static void logScreenShot(String scenarioName) {

        final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
        Hooks.scenario.attach(screenshot, "image/png", scenarioName);
    }

    public static List<String> getTextsOfWebElementsAndSort(List<WebElement> listOfWE) {
        waitForReadyState();
        List<String> textList = new ArrayList<>();
        for (WebElement webElement : listOfWE) {
            textList.add(webElement.getText());
        }
        Collections.sort(textList);

        return textList;
    }

    /**
     * @param id:    the id attribute of the web element
     * @param value: the value to be sent
     */

    public void sendKeysToInputBoxByID(String id, String value) {
        waitForReadyState();
        WebElement element = getElementByXPath("//*[@id='" + id + "']");
        scrolltoElement(element);
        clearText(element, "web element");
        if (!(value == null) || !value.equals("")) {
            sendKeys(element, value, id);
        }
    }

    /**
     * @param id:    the placeholder attribute of the web element
     * @param value: the value to be sent
     */
    public void sendKeysToInputBoxAndHitToEnter(String id, String value) {
        waitForReadyState();
        WebElement element = getElementByXPath("//*[@id='" + id + "']");
        scrolltoElement(element);
        clearText(element, "web element");
        if (!(value == null) || !value.equals("")) {
            sendKeys(element, value, id);
            element.sendKeys(Keys.ENTER);
        }
    }

    public static String getTextInputById(String id) {
        return getElementById(id).getAttribute("value");
    }

    public static List<String> getTextsOfWebElements(List<WebElement> listOfWE) {
        waitForReadyState();
        List<String> textList = new ArrayList<>();
        for (WebElement webElement : listOfWE) {
            textList.add(webElement.getText());
        }
        return textList;
    }

    public static void switchIframeById(int id) {
        List<WebElement> iframeElements = Driver.get().findElements(By.tagName("iframe"));
        Driver.get().switchTo().frame(id);
    }

    public static String timeStamp() {
        String ldt = LocalDateTime.now().toString();
        return ldt.substring(0, ldt.indexOf('.')).replace("-", "").replace(":", "");
    }

    public static LocalDateTime convertStringToLocalDateTime(String actualDateOfEvent) {
        String[] date_hour = actualDateOfEvent.split(" ");

        String[] date = date_hour[0].split("/");
        int day = Integer.parseInt(date[0]);
        int mount = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        String[] hour_minute = date_hour[1].split(":");
        int hour = Integer.parseInt(hour_minute[0]);
        int minute = Integer.parseInt(hour_minute[1]);

        return LocalDateTime.of(year, mount, day, hour, minute);
    }

    public static long getDurationBetweenTwoTimesInSeconds(LocalDateTime start, LocalDateTime end) {
        long between = ChronoUnit.SECONDS.between(start, end);
        log("Duration: " + between);
        return between;
    }

}




