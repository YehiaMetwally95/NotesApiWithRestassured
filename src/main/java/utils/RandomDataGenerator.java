package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomDataGenerator {

    public static String generateName()
    {
      String currentTime = new SimpleDateFormat("HHmmssSSS").format(new Date());
      return "test" + currentTime;
    }

    public static String generateInteger()
    {
        return new SimpleDateFormat("HHmmssSSS").format(new Date());
    }

    public static String generateEmail()
    {
        String currentTime = new SimpleDateFormat("HHmmssSSS").format(new Date());
        return "test" + currentTime + "@gmail.com";
    }

    public static String generateStrongPassword()
    {
        String currentTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return "test" + "@%^" + currentTime;
    }





}
