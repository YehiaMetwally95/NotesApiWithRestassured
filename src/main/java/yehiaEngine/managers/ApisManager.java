package yehiaEngine.managers;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import yehiaEngine.loggers.AllureReportLogger;
import yehiaEngine.loggers.LogHelper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static yehiaEngine.loggers.LogHelper.logInfoStep;

public class ApisManager {
    /**
     * *********************************  Send API Requests  ****************************************
     */
    @AllArgsConstructor
    @Getter
    public enum MethodType {
        POST("Post"),
        PUT("Put"),
        PATCH("Patch"),
        DELETE("Delete");
        final String methodType;
    }

    @AllArgsConstructor
    @Getter
    public enum ContentType {
        JSON("application/json"),
        URLENCODED("application/x-www-form-urlencoded"),
        NONE (null);
        final String contentType;
    }

    @AllArgsConstructor
    @Getter
    public enum AuthType {
        BasicAuth("BasicAuth"),
        BearerToken("BearerToken"),
        CookieAuth("CookieAuth"),
        XAuthToken("X-Auth-Token"),
        SessionID("Session-ID");

        final String authType;
    }

    @AllArgsConstructor
    @Getter
    public enum ParameterType {
        QUERY("queryParameter"),
        PATH("pathParameter"),
        NULL(null);
        final String parameterType;
    }

    public static Response MakeRequest(MethodType methodType, String endpoint, Object requestBody, ContentType requestContentType) {
        try {
            RestAssured.registerParser("text/html", Parser.JSON);
            //Upload Request and Response Into Allure Report
            RequestSpecification request = RestAssured.given().filter(AllureReportLogger.logApiRequestsToAllureReport());
            Response response = null;

            //Set Content Type of Request
            //Set The Request Body
            switch (requestContentType){
                case JSON: {
                    request = request.contentType(requestContentType.getContentType());
                    if (requestBody != null)
                        request = request.body(requestBody);
                    break;
                }
                case URLENCODED: {
                    request = request.contentType(requestContentType.getContentType()+"; charset=utf-8");
                    if (requestBody instanceof JsonObject jsonObject){
                        Map<String, Object> map = JsonManager.convertJsonStringToMap(jsonObject);
                        request = request.formParams(map);
                    }
                    else if (requestBody instanceof Map map){
                        request = request.formParams(map);
                    }
                    else {
                        Map<String, Object> map = JsonManager.convertJsonStringToMap(requestBody);
                        request = request.formParams(map);
                    }
                    break;
                }
                case NONE:{
                    request = request;
                    break;
                }
                default:{
                    break;
                }
            }

            //Set Method Type of Request
            response = switch (methodType.getMethodType()) {
                case "Post" -> request.when().post(endpoint);
                case "Put" -> request.when().put(endpoint);
                case "Patch" -> request.when().patch(endpoint);
                case "Delete" -> request.when().delete(endpoint);
                default -> response;
            };
            logInfoStep("Sending " + methodType.getMethodType() + " Request for URL [" + endpoint + "]");

            //Log Request Body
            if (requestBody == null){
                logInfoStep("Request Body : " + "Request Body = {}");
                System.out.println("*********************************************");
            }
            else if (requestBody instanceof JsonObject jsonObject)
                logRequestBody(jsonObject);
            else
                logRequestBody(requestBody);

            //Log Response Body
            logResponseBody(response);

            return response;
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Send " + methodType.getMethodType() + " Request for URL [" + endpoint + "]", e);
            return null;
        }
    }

    public static Response MakeAuthRequest(MethodType methodType, String endpoint, Object requestBody, ContentType requestContentType,
                                           AuthType authType, String token, String authUser, String authPass) {
        try {
            RestAssured.registerParser("text/html", Parser.JSON);
            //Upload Request and Response Into Allure Report
            RequestSpecification request = RestAssured.given().filter(AllureReportLogger.logApiRequestsToAllureReport());
            Response response = null;

            //Set Content Type of Request
            //Set The Request Body
            switch (requestContentType){
                case JSON: {
                    request = request.contentType(requestContentType.getContentType());
                    if (requestBody != null)
                        request = request.body(requestBody);
                    break;
                }
                case URLENCODED: {
                    request = request.contentType(requestContentType.getContentType()+"; charset=utf-8");
                    if (requestBody instanceof JsonObject jsonObject){
                        Map<String, Object> map = JsonManager.convertJsonStringToMap(jsonObject);
                        request = request.formParams(map);
                    }
                    else if (requestBody instanceof Map map){
                        request = request.formParams(map);
                    }
                    else {
                        Map<String, Object> map = JsonManager.convertJsonStringToMap(requestBody);
                        request = request.formParams(map);
                    }
                    break;
                }
                case NONE:{
                    request = request;
                    break;
                }
                default:{
                    break;
                }
            }

            //Set Authentication Type of Request
            if (authType == AuthType.BasicAuth) {
                request = request.auth().basic(authUser, authPass);
            } else if (authType == AuthType.BearerToken) {
                request = request.header("Authorization", "Bearer " + token);
            } else if (authType == AuthType.CookieAuth) {
                request = request.header("Cookie", "token="+token);
            } else if (authType == AuthType.XAuthToken) {
                request = request.header("x-auth-token", token);
            } else if (authType == AuthType.SessionID) {
                request = request.header("Session-ID", token);
            }

            //Set Method Type of Request
            response = switch (methodType.getMethodType()) {
                case "Post" -> request.when().post(endpoint);
                case "Put" -> request.when().put(endpoint);
                case "Patch" -> request.when().patch(endpoint);
                case "Delete" -> request.when().delete(endpoint);
                default -> response;
            };
            logInfoStep("Sending " + methodType.getMethodType() + "Auth Request for URL [" + endpoint + "]");

            //Log Request Body
            if (requestBody == null){
                logInfoStep("Request Body : " + "Request Body = {}");
                System.out.println("*********************************************");
            }
            else if (requestBody instanceof JsonObject jsonObject)
                logRequestBody(jsonObject);
            else
                logRequestBody(requestBody);

            //Log Response Body
            logResponseBody(response);

            return response;
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Send " + methodType.getMethodType() + "Auth Request for URL [" + endpoint + "]", e);
            return null;
        }
    }

    public static Response GetRequest(String endpoint, ParameterType parameterType, Object requestParameters) {
        try {
            //Upload Request and Response Into Allure Report
            RequestSpecification request = RestAssured.given().filter(AllureReportLogger.logApiRequestsToAllureReport());
            Response response;

            //Set Request Parameters
            switch (parameterType){
                case QUERY: {
                    if (requestParameters instanceof Map map){
                        request = request.formParams(map);
                    }
                    else {
                        Map<String, Object> map = JsonManager.convertJsonStringToMap(requestParameters);
                        request = request.formParams(map);
                    }
                    break;
                }
                case PATH: {
                    if (requestParameters instanceof Map map){
                        request = request.pathParams(map);
                    }
                    else {
                        Map<String, Object> map = JsonManager.convertJsonStringToMap(requestParameters);
                        request = request.pathParams(map);
                    }
                    break;
                }
            }
            //Perform Get Request
            response = request.when().get(endpoint);
            //Log the Request Sent Action
            logInfoStep("Sending Get Request for URL [" + endpoint + "]");

            //Log Request Body
            if (requestParameters == null){
                logInfoStep("Request Body : " + "Request Body = {}");
                System.out.println("*********************************************");
            }
            else if (requestParameters instanceof JsonObject jsonObject)
                logRequestBody(jsonObject);
            else
                logRequestBody(requestParameters);

            //Log Response Body
            logResponseBody(response);

            return response;
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Send Get Request for URL [" + endpoint + "]", e);
            return null;
        }
    }

    public static Response GetAuthRequest(String endpoint, ParameterType parameterType, Object requestParameters,
                                          AuthType authType, String token, String authUser, String authPass) {
        try {
            //Upload Request and Response Into Allure Report
            RequestSpecification request = RestAssured.given().filter(AllureReportLogger.logApiRequestsToAllureReport());
            Response response;

            //Set Request Parameters
            switch (parameterType){
                case QUERY: {
                    if (requestParameters instanceof Map map){
                        request = request.formParams(map);
                    }
                    else {
                        Map<String, Object> map = JsonManager.convertJsonStringToMap(requestParameters);
                        request = request.formParams(map);
                    }
                    break;
                }
                case PATH: {
                    if (requestParameters instanceof Map map){
                        request = request.pathParams(map);
                    }
                    else {
                        Map<String, Object> map = JsonManager.convertJsonStringToMap(requestParameters);
                        request = request.pathParams(map);
                    }
                    break;
                }
            }

            //Set Authentication Type of Request
            if (authType == AuthType.BasicAuth) {
                request = request.auth().basic(authUser, authPass);
            } else if (authType == AuthType.BearerToken) {
                request = request.header("Authorization", "Bearer " + token);
            } else if (authType == AuthType.CookieAuth) {
                request = request.header("Cookie", "token="+token);
            } else if (authType == AuthType.XAuthToken) {
                request = request.header("x-auth-token", token);
            } else if (authType == AuthType.SessionID) {
                request = request.header("Session-ID", token);
            }

            //Perform Get Request
            response = request.when().get(endpoint);
            //Log the Request Sent Action
            logInfoStep("Sending Get Request for URL [" + endpoint + "]");

            //Log Request Body
            if (requestParameters == null){
                logInfoStep("Request Body : " + "Request Body = {}");
                System.out.println("*********************************************");
            }
            else if (requestParameters instanceof JsonObject jsonObject)
                logRequestBody(jsonObject);
            else
                logRequestBody(requestParameters);

            //Log Response Body
            logResponseBody(response);

            return response;
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Send Get Request for URL [" + endpoint + "]", e);
            return null;
        }
    }

    /**
     * *********************************  Log API Request/Response Body  ****************************************
     */
    public static void logRequestBody(Object request) {
        try {
            logInfoStep("Request Body : " + request.toString());
            System.out.println("*********************************************");
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Log the Request Body", e);
        }
    }

    public static void logResponseBody(Object response) {
        try {
            logInfoStep("Response Body : " + response.toString());
            System.out.println("*********************************************\n");
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Log the Response Body", e);
        }
    }

    public static void logRequestBody(JsonObject request) {
        try {
            logInfoStep("Request Body : " + request.toString());
            System.out.println("*********************************************");
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Log the Request Body", e);
        }
    }

    public static void logResponseBody(JsonObject response) {
        try {
            logInfoStep("Response Body : " + response.toString());
            System.out.println("*********************************************\n");
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Log the Response Body", e);
        }
    }

    public static void logResponseBody(Response response) {
        try {
            logInfoStep("Response Body : " + response.getBody().asString());
            System.out.println("*********************************************\n");
        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Log the Response Body", e);
        }
    }

    /**
     * *********************************  Get Json Values from Response  ****************************************
     */
    public static int getJsonIntValueFromResponse(Response response, String jsonPath) {
        return JsonPath.read(getResponseBody(response),jsonPath);
    }

    public static double getJsonDoubleValueFromResponse(Response response, String jsonPath) {
        return JsonPath.read(getResponseBody(response),jsonPath);
    }

    public static boolean getJsonBooleanValueFromResponse(Response response, String jsonPath) {
        return JsonPath.read(getResponseBody(response),jsonPath);
    }

    public static String getJsonStringValueFromResponse(Response response, String jsonPath) {
        return JsonPath.read(getResponseBody(response),jsonPath);
    }

    public static Map<String,Object> getJsonObjectFromResponse(Response response, String jsonPath) {
        return JsonPath.read(getResponseBody(response),jsonPath);
    }

    public static List<Object> getListOfObjectsFromResponse(Response response, String jsonPath) {
        return JsonPath.read(getResponseBody(response),jsonPath);
    }

    /**
     * *********************************  Get from Response  ****************************************
     */
    public static String getResponseBody(Response response) {
        return response.getBody().asString();
    }

    public static List<Header> getResponseHeaders(Response response) {
        return response.getHeaders().asList();
    }

    public static String getResponseHeaderByName(Response response, String headerName) {
        return response.getHeader(headerName);
    }

    public static Map getResponseCookies(Response response) {
        return response.getCookies();
    }

    public static long getResponseTime(Response response, TimeUnit timeUnit) {
        return response.getTimeIn(timeUnit);
    }

    public static int getResponseCode(Response response) {
        return response.statusCode();
    }

    public static String getResponseContentType(Response response, String contentType) {
        return response.getContentType();
    }

    /**
     * *********************************  Mapping To Pojo Class  ****************************************
     */
    public static <T> T mapResponseToPojoClass (Response response,Class<T> pojoClass) {
        try{
            String responseBodyAsString = getResponseBody(response);
            JsonMapper mapper = new JsonMapper();
            logInfoStep("Mapping Response To Pojo Class ["+pojoClass.getSimpleName()+"]");
            return mapper.readValue(responseBodyAsString,pojoClass);

        }catch (Exception e){
            logInfoStep("Failed to Map Response To Pojo Class ["+pojoClass.getSimpleName()+"]");
            return null;
        }
    }

    public static <T> T mapJsonStringToPojoClass (String jsonString,Class<T> pojoClass) {
        try{
            JsonMapper mapper = new JsonMapper();
            logInfoStep("Mapping Response To Pojo Class ["+pojoClass.getSimpleName()+"]");
            return mapper.readValue(jsonString,pojoClass);

        }catch (Exception e){
            logInfoStep("Failed to Map Response To Pojo Class ["+pojoClass.getSimpleName()+"]");
            return null;
        }
    }
}
