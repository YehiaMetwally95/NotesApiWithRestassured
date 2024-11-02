package yehiaEngine.assertions;

import java.util.Objects;

import static yehiaEngine.assertions.SoftAssertHelper.softAssert;
import static yehiaEngine.loggers.LogHelper.logInfoStep;

public class CustomSoftAssert {

    // Integer Soft Assertions
    public static void assertEquals(int actual, int expected) {
        logInfoStep("Soft Asserting actual result: [" + actual + "] equals expected result: [" + expected + "]");
        softAssert.assertEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertEquals(int actual, int expected, String message) {
        logInfoStep("Soft Asserting actual result: [" + actual + "] equals expected result: [" + expected + "]");
        softAssert.assertEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // String Soft Assertions
    public static void assertEquals(String actual, String expected) {
        logInfoStep("Soft Asserting actual result: [" + actual + "] equals expected result: [" + expected + "]");
        softAssert.assertEquals(actual, expected);
        if (Objects.equals(actual, expected))
            if (Objects.equals(actual, expected))
                logInfoStep("Soft Assertion Passed");
    }

    public static void assertEquals(String actual, String expected, String message) {
        logInfoStep("Soft Asserting actual result: [" + actual + "] equals expected result: [" + expected + "]");
        softAssert.assertEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Boolean Soft Assertions
    public static void assertTrue(boolean condition) {
        logInfoStep("Soft Asserting condition is True");
        softAssert.assertTrue(condition);
        if (condition)
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertTrue(boolean condition, String message) {
        logInfoStep("Soft Asserting condition is True");
        softAssert.assertTrue(condition, message);
        if (condition)
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertFalse(boolean condition) {
        logInfoStep("Soft Asserting condition is False");
        softAssert.assertFalse(condition);
        if (!condition)
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertFalse(boolean condition, String message) {
        logInfoStep("Soft Asserting condition is False");
        softAssert.assertFalse(condition, message);
        if (!condition)
            logInfoStep("Soft Assertion Passed");
    }

    // Object Soft Assertions
    public static void assertEquals(Object actual, Object expected) {
        logInfoStep("Soft Asserting actual object: [" + actual + "] equals expected object: [" + expected + "]");
        softAssert.assertEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        logInfoStep("Soft Asserting actual object: [" + actual + "] equals expected object: [" + expected + "]");
        softAssert.assertEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Null Soft Assertions
    public static void assertNull(Object object) {
        logInfoStep("Soft Asserting object is null: [" + object + "]");
        softAssert.assertNull(object);
        if (object.equals(null))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNull(Object object, String message) {
        logInfoStep("Soft Asserting object is null: [" + object + "]");
        softAssert.assertNull(object, message);
        if (object.equals(null))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotNull(Object object) {
        logInfoStep("Soft Asserting object is not null: [" + object + "]");
        softAssert.assertNotNull(object);
        if (!object.equals(null))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotNull(Object object, String message) {
        logInfoStep("Soft Asserting object is not null: [" + object + "]");
        softAssert.assertNotNull(object, message);
        if (!object.equals(null))
            logInfoStep("Soft Assertion Passed");
    }

    // Array Soft Assertions
    public static void assertEquals(Object[] actual, Object[] expected) {
        logInfoStep("Soft Asserting actual array equals expected array.");
        softAssert.assertEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertEquals(Object[] actual, Object[] expected, String message) {
        logInfoStep("Soft Asserting actual array equals expected array.");
        softAssert.assertEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Byte Soft Assertions
    public static void assertEquals(byte actual, byte expected) {
        logInfoStep("Soft Asserting actual byte: [" + actual + "] equals expected byte: [" + expected + "]");
        softAssert.assertEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertEquals(Byte actual, Byte expected, String message) {
        logInfoStep("Soft Asserting actual Byte object: [" + actual + "] equals expected Byte object: [" + expected + "]");
        softAssert.assertEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");

    }

    // Char Soft Assertions
    public static void assertEquals(char actual, char expected) {
        logInfoStep("Soft Asserting actual char: [" + actual + "] equals expected char: [" + expected + "]");
        softAssert.assertEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertEquals(Character actual, Character expected, String message) {
        logInfoStep("Soft Asserting actual Character object: [" + actual + "] equals expected Character object: [" + expected + "]");
        softAssert.assertEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Float Soft Assertions
    public static void assertEquals(float actual, float expected, float delta) {
        logInfoStep("Soft Asserting actual float: [" + actual + "] equals expected float: [" + expected + "] within delta: [" + delta + "]");
        softAssert.assertEquals(actual, expected, delta);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertEquals(float actual, float expected, float delta, String message) {
        logInfoStep("Soft Asserting actual float: [" + actual + "] equals expected float: [" + expected + "] within delta: [" + delta + "]");
        softAssert.assertEquals(actual, expected, delta, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Double Soft Assertions
    public static void assertEquals(double actual, double expected, double delta) {
        logInfoStep("Soft Asserting actual double: [" + actual + "] equals expected double: [" + expected + "] within delta: [" + delta + "]");
        softAssert.assertEquals(actual, expected, delta);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertEquals(double actual, double expected, double delta, String message) {
        logInfoStep("Soft Asserting actual double: [" + actual + "] equals expected double: [" + expected + "] within delta: [" + delta + "]");
        softAssert.assertEquals(actual, expected, delta, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Integer Not Equals Soft Assertions
    public static void assertNotEquals(int actual, int expected) {
        logInfoStep("Soft Asserting actual result: [" + actual + "] does not equal expected result: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotEquals(int actual, int expected, String message) {
        logInfoStep("Soft Asserting actual result: [" + actual + "] does not equal expected result: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Byte Not Equals Soft Assertions
    public static void assertNotEquals(byte actual, byte expected) {
        logInfoStep("Soft Asserting actual byte: [" + actual + "] does not equal expected byte: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotEquals(Byte actual, Byte expected, String message) {
        logInfoStep("Soft Asserting actual Byte object: [" + actual + "] does not equal expected Byte object: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Char Not Equals Soft Assertions
    public static void assertNotEquals(char actual, char expected) {
        logInfoStep("Soft Asserting actual char: [" + actual + "] does not equal expected char: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotEquals(Character actual, Character expected, String message) {
        logInfoStep("Soft Asserting actual Character object: [" + actual + "] does not equal expected Character object: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Float Not Equals Soft Assertions
    public static void assertNotEquals(float actual, float expected, float delta) {
        logInfoStep("Soft Asserting actual float: [" + actual + "] does not equal expected float: [" + expected + "] within delta: [" + delta + "]");
        softAssert.assertNotEquals(actual, expected, delta);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotEquals(float actual, float expected, float delta, String message) {
        logInfoStep("Soft Asserting actual float: [" + actual + "] does not equal expected float: [" + expected + "] within delta: [" + delta + "]");
        softAssert.assertNotEquals(actual, expected, delta, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Double Not Equals Soft Assertions
    public static void assertNotEquals(double actual, double expected, double delta) {
        logInfoStep("Soft Asserting actual double: [" + actual + "] does not equal expected double: [" + expected + "] within delta: [" + delta + "]");
        softAssert.assertNotEquals(actual, expected, delta);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotEquals(double actual, double expected, double delta, String message) {
        logInfoStep("Soft Asserting actual double: [" + actual + "] does not equal expected double: [" + expected + "] within delta: [" + delta + "]");
        softAssert.assertNotEquals(actual, expected, delta, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // String Not Equals Soft Assertions
    public static void assertNotEquals(String actual, String expected) {
        logInfoStep("Soft Asserting actual string: [" + actual + "] does not equal expected string: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotEquals(String actual, String expected, String message) {
        logInfoStep("Soft Asserting actual string: [" + actual + "] does not equal expected string: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    // Object Not Equals Soft Assertions
    public static void assertNotEquals(Object actual, Object expected) {
        logInfoStep("Soft Asserting actual object: [" + actual + "] does not equal expected object: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }

    public static void assertNotEquals(Object actual, Object expected, String message) {
        logInfoStep("Soft Asserting actual object: [" + actual + "] does not equal expected object: [" + expected + "]");
        softAssert.assertNotEquals(actual, expected, message);
        if (Objects.equals(actual, expected))
            logInfoStep("Soft Assertion Passed");
    }
}
