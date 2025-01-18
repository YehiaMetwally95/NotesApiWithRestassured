package yehiaEngine.managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import yehiaEngine.browserActions.WindowManager;
import yehiaEngine.loggers.LogHelper;

import java.util.Date;

import static yehiaEngine.managers.CookiesManager.addCookie;
import static yehiaEngine.managers.CookiesManager.deleteAllCookies;

public class SessionManager {
    private WebDriver driver;
    private String jsonFilePath;
    JsonManager json;

    public SessionManager(WebDriver driver, String jsonFilePath){
        this.driver= driver;
        this.jsonFilePath = jsonFilePath;
        json = new JsonManager(jsonFilePath);
    }

    // Get Cookies
    private JsonArray getCookiesData(){
        try {
            JsonArray cookies = new JsonArray();
            driver.manage().getCookies().stream()
                    .forEach(
                            x -> {
                                JsonObject json = new JsonObject();
                                json.addProperty("name", x.getName());
                                json.addProperty("value", x.getValue());
                                json.addProperty("path", x.getPath());
                                json.addProperty("domain", x.getDomain());
                                json.addProperty("expiry", String.valueOf(x.getExpiry()));
                                json.addProperty("isSecure", x.isSecure());
                                json.addProperty("isHttpOnly", x.isHttpOnly());
                                cookies.add(json);
                            });
            LogHelper.logInfoStep("Getting Cookies Data from Current Browser Session");
            return cookies;
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Get Cookies Data from Current Browser Session",e);
            return null;
        }
    }

    // Store Cookies Data in Json File If login success for each time
    public void storeSessionCookies(String userName) {
        try {
            JsonObject sessionObj = new JsonObject();
            sessionObj.addProperty("username", userName);
            //sessionObj.put("createdAt", LocalDateTime.now());
            sessionObj.add("cookies_data", getCookiesData());
            JsonManager.createJsonFile(sessionObj, jsonFilePath);
            LogHelper.logInfoStep("Storing Cookies Data Into Json Session Files");
        }catch (Exception e) {
            LogHelper.logErrorStep("Failed to Store Cookies Data Into Json Session Files",e);
        }
    }

    //Apply the Stored Cookies Data on Json File to the Current Session
    public void applyCookiesToCurrentSession() {
        try {
            //Delete All Cookies
            deleteAllCookies(driver);

            //Apply the Saved Cookies in the JsonFile to Current Session
            JsonArray cookiesArray = json.getDataAsJsonArray("cookies_data");
            for (int i = 0; i < cookiesArray.size(); i++) {
                JsonObject cookies = (JsonObject) cookiesArray.get(i);
                Cookie ck =
                        new Cookie.Builder(cookies.get("name").getAsString(), cookies.get("value").getAsString())
                                .path(cookies.get("path").getAsString())
                                .domain(cookies.get("domain").getAsString())
                                .expiresOn(
                                        !cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
                                .isSecure(cookies.get("isSecure").getAsBoolean())
                                .isHttpOnly(cookies.get("isHttpOnly").getAsBoolean())
                                .build();
                addCookie(driver, ck);
            }
            LogHelper.logInfoStep("Applying Cookies to Current Browser Session");
            //Refresh the Browser Page to check the Updates
            WindowManager.refreshWindow(driver);
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Apply Cookies to Current Browser Session",e);
        }
    }
}
