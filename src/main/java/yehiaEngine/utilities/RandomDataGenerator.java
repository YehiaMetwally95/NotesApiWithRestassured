package yehiaEngine.utilities;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomDataGenerator {

    public static String generateUniqueName()
    {
      String currentTime = new SimpleDateFormat("mmssSSS").format(new Date());
      return "test" + currentTime;
    }

    public static String generateUniqueInteger()
    {
        return new SimpleDateFormat("mmssSSS").format(new Date());
    }

    public static String generateUniqueEmail()
    {
        String currentTime = new SimpleDateFormat("mmssSSS").format(new Date());
        return "test" + currentTime + "@gmail.com";
    }

    public static String generateStrongPassword()
    {
        String currentTime = new SimpleDateFormat("mmssSSS").format(new Date());
        return "test" + "@%^" + currentTime;
    }

    public static String generateName()
    {
       return new Faker().name().firstName().replace("'", "''");
    }

    public static String generateCompany()
    {
        return new Faker().company().name().replace("'", "''");
    }

    public static String generateAddress()
    {
        return new Faker().address().fullAddress().replace("'", "''");
    }

    public static String generateCity()
    {
        return new Faker().address().city().replace("'", "''");
    }

    public static String generateZipCode()
    {
        return new Faker().address().zipCode();
    }

    public static String generateDescription()
    {
        return new Faker().lorem().sentence();
    }

    public static String generatePreviousDate(String format)
    {
        Date pastDate = new Faker().date().past(50, TimeUnit.DAYS);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(pastDate);
    }

    public static String generateFutureDate(String format)
    {
        Date futureDate = new Faker().date().future(50, TimeUnit.DAYS);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(futureDate);
    }

    public static <T> T generateItemFromList(List<T> list)
    {
        int randomIndex = new Random().nextInt(list.size());
        return list.get(randomIndex);
    }


    public static String generateNumericalString (int min,int max)
    {
        int randomNumber = new Random().nextInt(max-min)+min;
        return String.valueOf(randomNumber);
    }
}
