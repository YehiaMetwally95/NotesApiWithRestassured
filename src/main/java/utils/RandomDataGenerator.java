package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomDataGenerator {

    public static String generateName()
    {
      String currentTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
      return "Test" + currentTime;
    }

    public static String generateInteger()
    {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public static String generateEmail()
    {
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return "Test" + currentTime + "@gmail.com";
    }

    public static String generateStrongPassword()
    {
        String currentTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return "Test" + "@%^" + currentTime;
    }





}
