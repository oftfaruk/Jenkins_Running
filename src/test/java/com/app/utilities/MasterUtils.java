package com.app.utilities;

import com.app.stepdefinitions.Hooks;
import org.junit.Assert;
import org.junit.ComparisonFailure;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class MasterUtils {

    private static int shortWait = Integer.parseInt(ConfigurationReader.get("shortWait"));
    private static int longWait = Integer.parseInt(ConfigurationReader.get("longWait"));

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
        log("Duration: " + between + " sn");
        return between;
    }

    public static long getElapsedTimeInSec(String actualDateOfEvent) {
        LocalDateTime start = convertStringToLocalDateTime(actualDateOfEvent);
        return getDurationBetweenTwoTimesInSeconds(start, LocalDateTime.now());
    }

    public static long getElapsedTimeInSec(LocalDateTime actualDateOfEvent) {
        return getDurationBetweenTwoTimesInSeconds(actualDateOfEvent, LocalDateTime.now());
    }

    public static void log(String data) {
        Hooks.scenario.log(data);
    }

    public static void assertTrue(boolean b) {

        for (int i = 0; i < 5; i++) {
            try {
                Assert.assertTrue(b);
                return;
            } catch (AssertionError | Exception e) {

            }
        }
        Assert.assertTrue(b);
    }

    public static void assertTrue(String message, boolean b) {

        for (int i = 0; i < 5; i++) {
            try {
                Assert.assertTrue(b);
                return;
            } catch (AssertionError | Exception e) {

            }
        }
        Assert.assertTrue(message,b);
    }

    public static void assertFalse(boolean b) {

        for (int i = 0; i < 5; i++) {
            try {
                Assert.assertFalse(b);
                return;
            } catch (AssertionError | Exception e) {

            }
        }
        Assert.assertFalse(b);
    }

    public static void assertEquals(String expected, String actual) {

        for (int i = 0; i < 5; i++) {
            try {
                Assert.assertEquals(expected, actual);
                return;
            } catch (ComparisonFailure | Exception e) {

            }
        }
        Assert.assertEquals(expected, actual);
    }

    public static void shortWait() {
        waitFor(shortWait);
    }

    public static void longWait() {
        waitFor(longWait);
    }


    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
