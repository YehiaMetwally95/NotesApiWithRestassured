package yehiaEngine.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import yehiaEngine.loggers.AllureReportLogger;
import yehiaEngine.loggers.LogHelper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static yehiaEngine.loggers.LogHelper.logInfoStep;
import static yehiaEngine.managers.JsonManager.convertMapToJsonObject;

public class ApisManager {

    public static Response MakeRequest(String requestType,String endpoint,Object requestBody,String contentType) throws JsonProcessingException {
        try {
            RestAssured.registerParser("text/html", Parser.JSON);
            RequestSpecification request = RestAssured.given().filter(AllureReportLogger.logApiRequestsToAllureReport());
            Response response = null;

            if (contentType != null) {
                if (contentType.equalsIgnoreCase("application/json")) {
                    request = request.contentType(contentType).body(requestBody);
                } else if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded")) {
                    //convert requestObject to Map
                    Map<String, Object> map = JsonManager.convertJsonStringToMap(requestBody);
                    request = request.contentType(contentType + "; charset=utf-8").formParams(map);
                }
            }

            switch (requestType) {
                case "Post":
                    response = request.when().post(endpoint);
                    break;
                case "Put":
                    response = request.when().put(endpoint);
                    break;
                case "Patch":
                    response = request.when().patch(endpoint);
                    break;
                case "Delete":
                    response = request.when().delete(endpoint);
                    break;
            }

            logInfoStep("Sending "+requestType+" Request for URL ["+endpoint+"]");

            //Convert Request body and Response body to Objects
            Object responseBody = JsonParser.parseString(getResponseBody(response)).getAsJsonObject();
            String requestString = new JsonMapper().writeValueAsString(requestBody);
            requestBody = JsonParser.parseString(requestString).getAsJsonObject();

            //Log Request Body and Response Body
            logRequestBody(requestBody);
            logResponseBody(responseBody);
            return response;
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Send "+requestType+" Request for URL ["+endpoint+"]",e);
            return null;
        }
    }

    public static Response MakeAuthRequest(String requestType,String endpoint,Object requestBody,String contentType,
                                           String authType,String authUser,String authPass, String token) throws JsonProcessingException {
       try{
        RestAssured.registerParser("text/html", Parser.JSON);
        RequestSpecification request = RestAssured.given().filter(AllureReportLogger.logApiRequestsToAllureReport());
        Response response = null;

        if(contentType != null)
        {
            if (contentType.equalsIgnoreCase("application/json"))
            {
                request = request.contentType(contentType).body(requestBody);
            }

            else if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded"))
            {
                //convert requestObject to Map
                Map<String, Object> map = JsonManager.convertJsonStringToMap(requestBody);
                request = request.contentType(contentType + "; charset=utf-8").formParams(map);
            }
        }

        if(authType.equalsIgnoreCase("BasicAuth"))
        {
            request = request.auth().basic(authUser,authPass);
        }

        else if(authType.equalsIgnoreCase("BearerToken"))
        {
            request = request.header("Authorization","Bearer "+token);
        }

        else if(authType.equalsIgnoreCase("CookieAuth"))
        {
            request = request.header("Cookie","token="+token);
        }

        else if(authType.equalsIgnoreCase("X-Auth-Token"))
        {
            request = request.header("x-auth-token",token);
        }

        switch (requestType)
        {
            case "Post":
                response = request.when().post(endpoint);
                break;
            case "Put":
                response = request.when().put(endpoint);
                break;

            case "Patch":
                response = request.when().patch(endpoint);
                break;

            case "Delete":
                response = request.when().delete(endpoint);
                break;
        }
        logInfoStep("Sending Auth "+requestType+" Request for URL ["+endpoint+"]");
           //Convert Request body and Response body to Objects
           Object responseBody = JsonParser.parseString(getResponseBody(response)).getAsJsonObject();
           String requestString = new JsonMapper().writeValueAsString(requestBody);
           requestBody = JsonParser.parseString(requestString).getAsJsonObject();

           //Log Request Body and Response Body
           logRequestBody(requestBody);
           logResponseBody(responseBody);
        return response;
    }catch (Exception e)
       {
        LogHelper.logErrorStep("Failed to Send Auth "+requestType+" Request for URL ["+endpoint+"]",e);
        return null;
       }
    }

    public static Response GetRequest(String endpoint,Map queryParameters)
    {
        try{
        RequestSpecification request = RestAssured.given().filter(AllureReportLogger.logApiRequestsToAllureReport());
        Response response = null;

        if (queryParameters != null)
        {request = request.queryParams(queryParameters);}

        response = request.when().get(endpoint);

        logInfoStep("Sending Get Request for URL ["+endpoint+"]");
            //Convert Request body to Objects
            Object responseBody = JsonParser.parseString(getResponseBody(response)).getAsJsonObject();
            //Log Request Body
            logResponseBody(responseBody);

            if (queryParameters != null)
            {
                //Convert Response body to Object
                Object requestBody = convertMapToJsonObject(queryParameters);
                //Log Response Body
                logRequestBody(requestBody);
            }
        return response;
     } catch (Exception e)
        {
         LogHelper.logErrorStep("Failed to Send Get Request for URL ["+endpoint+"]",e);
         return null;
        }
    }

    public static Response GetAuthRequest(String endpoint,Map queryParameters,
                                          String authType,String authUser,String authPass, String token)
    {
        try{
        RequestSpecification request = RestAssured.given().filter(AllureReportLogger.logApiRequestsToAllureReport());
        Response response = null;

        if (queryParameters != null)
        {request = request.queryParams(queryParameters);}

        if(authType.equalsIgnoreCase("BasicAuth"))
        {
            request = request.auth().basic(authUser,authPass);
        }

        else if(authType.equalsIgnoreCase("BearerToken"))
        {
            request = request.header("Authorization","Bearer "+token);
        }

        else if(authType.equalsIgnoreCase("CookieAuth"))
        {
            request = request.header("Cookie","token="+token);
        }
        else if(authType.equalsIgnoreCase("X-Auth-Token"))
        {
            request = request.header("x-auth-token",token);
        }

        response = request.when().get(endpoint);
        logInfoStep("Sending Auth Get Request for URL ["+endpoint+"]");

            //Convert Request body to Objects
            Object responseBody = JsonParser.parseString(getResponseBody(response)).getAsJsonObject();
            //Log Request Body
            logResponseBody(responseBody);

            if (queryParameters != null)
            {
                //Convert Response body to Object
                Object requestBody = convertMapToJsonObject(queryParameters);
                //Log Response Body
                logRequestBody(requestBody);
            }
        return response;
    } catch (Exception e)
        {
        LogHelper.logErrorStep("Failed to Send Auth Get Request for URL ["+endpoint+"]",e);
        return null;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    public static int getJsonIntValueFromResponse(Response response, String jsonPath)
    {
        return response.jsonPath().getInt(jsonPath);
    }

    public static String getJsonStringValuefromResponse(Response response, String jsonPath)
    {
        return response.jsonPath().getString(jsonPath);
    }

    public static JsonObject getJsonObjectfromResponse(Response response, String jsonPath)
    {
        return response.jsonPath().getJsonObject(jsonPath);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    public static void logRequestBody(Object request)
    {
        try {
            logInfoStep("Request Body : " + request.toString());
            System.out.println("*********************************************");
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Log the Request Body",e);
        }
    }

    public static void logResponseBody(Object response)
    {
        try {
            logInfoStep("Response Body : " + response.toString());
            System.out.println("*********************************************\n");
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Log the Response Body",e);
        }
    }

    public static String getResponseBody(Response response)
    {
       return response.getBody().asPrettyString();
    }

    public static List<Header> getResponseHeaders(Response response)
    {
        return response.getHeaders().asList();
    }

    public static String getResponseHeaderByName(Response response, String headerName)
    {
        return response.getHeader(headerName);
    }

    public static Map getResponseCookies(Response response)
    {
        return response.getCookies();
    }

    public static long getResponseTime(Response response, TimeUnit timeUnit)
    {
        return response.getTimeIn(timeUnit);
    }

    public static void verifyResponseCode(Response response, int code)
    {
        response.then().statusCode(code);
    }

    public static void verifyResponseContentType(Response response,String contentType)
    {
        response.then().contentType(contentType);
    }

}
