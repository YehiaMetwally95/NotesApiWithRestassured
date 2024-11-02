package yehiaEngine.assertions;

import org.testng.Assert;

import java.util.List;

import static yehiaEngine.loggers.LogHelper.logErrorStep;
import static yehiaEngine.loggers.LogHelper.logInfoStep;

public class CustomAssert {

    // Integer Assertions
    public static void assertEquals(int actual, int expected) {
        try {
            logInfoStep("Asserting actual result: [" + actual + "] equals expected result: [" + expected + "]");
            Assert.assertEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertEquals(int actual, int expected, String message) {
        try {
            logInfoStep("Asserting actual result: [" + actual + "] equals expected result: [" + expected + "]");
            Assert.assertEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // String Assertions
    public static void assertEquals(String actual, String expected) {
        try {
            logInfoStep("Asserting actual result: [" + actual + "] equals expected result: [" + expected + "]");
            Assert.assertEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertEquals(String actual, String expected, String message) {
        try {
            logInfoStep("Asserting actual result: [" + actual + "] equals expected result: [" + expected + "]");
            Assert.assertEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Boolean Assertions
    public static void assertTrue(boolean condition) {
        try {
            logInfoStep("Asserting condition is True");
            Assert.assertTrue(condition);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertTrue(boolean condition, String message) {
        try {
            logInfoStep("Asserting condition is True");
            Assert.assertTrue(condition, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    public static void assertFalse(boolean condition) {
        try {
            logInfoStep("Asserting condition is False");
            Assert.assertFalse(condition);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        try {
            logInfoStep("Asserting condition is False");
            Assert.assertFalse(condition, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Object Assertions
    public static void assertEquals(Object actual, Object expected) {
        try {
            logInfoStep("Asserting actual object: [" + actual + "] equals expected object: [" + expected + "]");
            Assert.assertEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            logInfoStep("Asserting actual object: [" + actual + "] equals expected object: [" + expected + "]");
            Assert.assertEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Null Assertions
    public static void assertNull(Object object) {
        try {
            logInfoStep("Asserting object is null: [" + object + "]");
            Assert.assertNull(object);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNull(Object object, String message) {
        try {
            logInfoStep("Asserting object is null: [" + object + "]");
            Assert.assertNull(object, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    public static void assertNotNull(Object object) {
        try {
            logInfoStep("Asserting object is not null: [" + object + "]");
            Assert.assertNotNull(object);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNotNull(Object object, String message) {
        try {
            logInfoStep("Asserting object is not null: [" + object + "]");
            Assert.assertNotNull(object, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // List Assertions (Example)
    public static <T> void assertListContainsObject(List<T> list, T object,String message) {
        try {
            logInfoStep("Asserting list contains object: [" + object + "]");
            Assert.assertListContainsObject(list,object,message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static <T> void assertListNotContainsObject(List<T> list, T object,String message) {
        try {
            logInfoStep("Asserting list not contain object: [" + object + "]");
            Assert.assertListNotContainsObject(list,object,message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    // Array Assertions
    public static void assertEquals(Object[] actual, Object[] expected) {
        try {
            logInfoStep("Asserting actual array equals expected array.");
            Assert.assertEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertEquals(Object[] actual, Object[] expected, String message) {
        try {
            logInfoStep("Asserting actual array equals expected array.");
            Assert.assertEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Byte Assertions
    public static void assertEquals(byte actual, byte expected) {
        try {
            logInfoStep("Asserting actual byte: [" + actual + "] equals expected byte: [" + expected + "]");
            Assert.assertEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertEquals(Byte actual, Byte expected, String message) {
        try {
            logInfoStep("Asserting actual Byte object: [" + actual + "] equals expected Byte object: [" + expected + "]");
            Assert.assertEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Char Assertions
    public static void assertEquals(char actual, char expected) {
        try {
            logInfoStep("Asserting actual char: [" + actual + "] equals expected char: [" + expected + "]");
            Assert.assertEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertEquals(Character actual, Character expected, String message) {
        try {
            logInfoStep("Asserting actual Character object: [" + actual + "] equals expected Character object: [" + expected + "]");
            Assert.assertEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Float Assertions
    public static void assertEquals(float actual, float expected, float delta) {
        try {
            logInfoStep("Asserting actual float: [" + actual + "] equals expected float: [" + expected + "] within delta: [" + delta + "]");
            Assert.assertEquals(actual, expected, delta);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertEquals(float actual, float expected, float delta, String message) {
        try {
            logInfoStep("Asserting actual float: [" + actual + "] equals expected float: [" + expected + "] within delta: [" + delta + "]");
            Assert.assertEquals(actual, expected, delta, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Double Assertions
    public static void assertEquals(double actual, double expected, double delta) {
        try {
            logInfoStep("Asserting actual double: [" + actual + "] equals expected double: [" + expected + "] within delta: [" + delta + "]");
            Assert.assertEquals(actual, expected, delta);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertEquals(double actual, double expected, double delta, String message) {
        try {
            logInfoStep("Asserting actual double: [" + actual + "] equals expected double: [" + expected + "] within delta: [" + delta + "]");
            Assert.assertEquals(actual, expected, delta, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Integer Not Equals Assertions
    public static void assertNotEquals(int actual, int expected) {
        try {
            logInfoStep("Asserting actual result: [" + actual + "] does not equal expected result: [" + expected + "]");
            Assert.assertNotEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNotEquals(int actual, int expected, String message) {
        try {
            logInfoStep("Asserting actual result: [" + actual + "] does not equal expected result: [" + expected + "]");
            Assert.assertNotEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Byte Not Equals Assertions
    public static void assertNotEquals(byte actual, byte expected) {
        try {
            logInfoStep("Asserting actual byte: [" + actual + "] does not equal expected byte: [" + expected + "]");
            Assert.assertNotEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNotEquals(Byte actual, Byte expected, String message) {
        try {
            logInfoStep("Asserting actual Byte object: [" + actual + "] does not equal expected Byte object: [" + expected + "]");
            Assert.assertNotEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Char Not Equals Assertions
    public static void assertNotEquals(char actual, char expected) {
        try {
            logInfoStep("Asserting actual char: [" + actual + "] does not equal expected char: [" + expected + "]");
            Assert.assertNotEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNotEquals(Character actual, Character expected, String message) {
        try {
            logInfoStep("Asserting actual Character object: [" + actual + "] does not equal expected Character object: [" + expected + "]");
            Assert.assertNotEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Float Not Equals Assertions
    public static void assertNotEquals(float actual, float expected, float delta) {
        try {
            logInfoStep("Asserting actual float: [" + actual + "] does not equal expected float: [" + expected + "] within delta: [" + delta + "]");
            Assert.assertNotEquals(actual, expected, delta);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNotEquals(float actual, float expected, float delta, String message) {
        try {
            logInfoStep("Asserting actual float: [" + actual + "] does not equal expected float: [" + expected + "] within delta: [" + delta + "]");
            Assert.assertNotEquals(actual, expected, delta, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Double Not Equals Assertions
    public static void assertNotEquals(double actual, double expected, double delta) {
        try {
            logInfoStep("Asserting actual double: [" + actual + "] does not equal expected double: [" + expected + "] within delta: [" + delta + "]");
            Assert.assertNotEquals(actual, expected, delta);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNotEquals(double actual, double expected, double delta, String message) {
        try {
            logInfoStep("Asserting actual double: [" + actual + "] does not equal expected double: [" + expected + "] within delta: [" + delta + "]");
            Assert.assertNotEquals(actual, expected, delta, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // String Not Equals Assertions
    public static void assertNotEquals(String actual, String expected) {
        try {
            logInfoStep("Asserting actual string: [" + actual + "] does not equal expected string: [" + expected + "]");
            Assert.assertNotEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNotEquals(String actual, String expected, String message) {
        try {
            logInfoStep("Asserting actual string: [" + actual + "] does not equal expected string: [" + expected + "]");
            Assert.assertNotEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }

    // Object Not Equals Assertions
    public static void assertNotEquals(Object actual, Object expected) {
        try {
            logInfoStep("Asserting actual object: [" + actual + "] does not equal expected object: [" + expected + "]");
            Assert.assertNotEquals(actual, expected);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed", e);
        }
    }

    public static void assertNotEquals(Object actual, Object expected, String message) {
        try {
            logInfoStep("Asserting actual object: [" + actual + "] does not equal expected object: [" + expected + "]");
            Assert.assertNotEquals(actual, expected, message);
            logInfoStep("Assertion Passed");
        } catch (AssertionError e) {
            logErrorStep("Assertion Failed: " + message, e);
        }
    }
}
