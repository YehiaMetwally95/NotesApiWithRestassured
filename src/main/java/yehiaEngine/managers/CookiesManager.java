package yehiaEngine.managers;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class CookiesManager {

    public static Cookie buildCookie(String name , String value , String url)
    {
        Cookie cookie = new Cookie.Builder(name , value)
                .domain(url)
                .build();
    return cookie;
    }

    public static void addCookie(WebDriver driver , Cookie cookie)
    {
        driver.manage().addCookie(cookie);
    }

    public static void deleteCookie(WebDriver driver ,Cookie cookie)
    {
        driver.manage().deleteCookie(cookie);
    }

    public static Cookie getCookieByName(WebDriver driver , String cookieName)
    {
        return driver.manage().getCookieNamed(cookieName);
    }

    public static void deleteAllCookies(WebDriver driver)
    {
        driver.manage().deleteAllCookies();
    }

    public static int getCookiesNumber(WebDriver driver)
    {
        return driver.manage().getCookies().size();
    }

}
