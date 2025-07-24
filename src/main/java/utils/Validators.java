package utils;

import org.testng.Assert;

public class Validators {

    // Assert equality with custom message
    public static void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    // Assert equality without message
    public static void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }

    // Assert a boolean condition is true
    public static void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    // Assert a boolean condition is true without message
    public static void assertTrue(boolean condition) {
        Assert.assertTrue(condition);
    }

    // Assert a boolean condition is false
    public static void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    // Assert a boolean condition is false without message
    public static void assertFalse(boolean condition) {
        Assert.assertFalse(condition);
    }

    // Assert not null
    public static void assertNotNull(Object object, String message) {
        Assert.assertNotNull(object, message);
    }

    public static void assertNotNull(Object object) {
        Assert.assertNotNull(object);
    }

    // Assert null
    public static void assertNull(Object object, String message) {
        Assert.assertNull(object, message);
    }

    public static void assertNull(Object object) {
        Assert.assertNull(object);
    }

}
