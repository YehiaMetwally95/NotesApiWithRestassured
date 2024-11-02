package yehiaEngine.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.FileUtils;
import yehiaEngine.loggers.LogHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonManager {

    //Variables
    private String filePath;

    //Constructor
    public JsonManager(String filePath) {
        this.filePath = filePath;
    }

    //Method to Get JsonData as String using JsonPath Expression
    public String getData(String jsonPath) throws IOException {
        try {
            String data;
        Object result = JsonPath.parse(new File(filePath)).read(jsonPath);
        if (result.toString().contains("{"))
            data = JsonPath.parse(result).jsonString();
        else
            data = result.toString();

        LogHelper.logInfoStep("Getting Test Data ["+data+"] by Json Path ["+jsonPath+"]");
        return data;

        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Read Test Data by Json Path ["+jsonPath+"]",e);
            return null;
        }
    }

    //Method to Get JsonData as Object using JsonPath Expression
    public Object getDataAsJson(String jsonPath) throws IOException {
       try {
           Object data;
           Object result = JsonPath.parse(new File(filePath)).read(jsonPath);
           if (result.toString().contains("{"))
               data = JsonPath.parse(result).json();
           else
               data = result;
           LogHelper.logInfoStep("Getting Test Data ["+data+"] by Json Path ["+jsonPath+"]");
           return data;
       }catch (Exception e){
           LogHelper.logErrorStep("Failed to Read Test Data by Json Path ["+jsonPath+"]",e);
           return null;
       }
    }

    //Method to Get JsonData as JsonArray using JsonPath Expression
    public JsonArray getDataAsJsonArray(String jsonPath) throws IOException {
        try {
            List<Object> list = JsonPath.parse(new File(filePath)).read(jsonPath);
            JsonArray data;
            data = JsonParser.parseString(list.toString()).getAsJsonArray();
            LogHelper.logInfoStep("Getting Test Data ["+data+"] by Json Path ["+jsonPath+"]");
            return data;
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Get Test Data by Json Path ["+jsonPath+"]",e);
            return null;
        }
    }

    //Method to Set Key expressed in JsonPath with new value
    public JsonManager setData(String jsonPath, String value) throws IOException {
        try {
            // Read the Json File and convert it to Json String
            String jsonString = readJsonFile(filePath).toString();
            // Parse the Json String and set the key with new value through jsonPath Expression
            jsonString = JsonPath.parse(jsonString).set(jsonPath, value).jsonString();
            // Convert the Updated Json String into Json Object
            JsonObject obj = JsonParser.parseString(jsonString).getAsJsonObject();
            LogHelper.logInfoStep("Update the Json Path ["+jsonPath+"] with new Test Data ["+value+"]");
            // Set the Json File with the new Json Object
            createJsonFile(obj, filePath);
        }catch (Exception e){
            LogHelper.logInfoStep("Failed to Update the Json Path ["+jsonPath+"] with new Test Data ["+value+"]");
        }
        return this;
    }

    //Method to read JsonFile and convert it to JsonObject
    public static JsonObject readJsonFile(String filePath) throws IOException {
        try {
        //pass the path of test data json file
        File filename = new File(filePath);
        //convert the json file to string
        String jsonString = FileUtils.readFileToString(filename, "UTF8");
        //parse the json string into Json Object (Deserialization)
            LogHelper.logInfoStep("Reading the Json File By filePath ["+filePath+"]");
            return JsonParser.parseString(jsonString).getAsJsonObject();
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Read the Json File By filePath ["+filePath+"]",e);
            return null;
        }
    }

    //Method to Create JsonFile from Object
    public static void createJsonFile(Object obj , String filePath) throws IOException {
        try{
            FileWriter file = new FileWriter(filePath);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String formattedJson = gson.toJson(obj);
            file.write(formattedJson);
            file.close();
            LogHelper.logInfoStep("Writing Into Json File By filePath ["+filePath+"]");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Write Data Into Json File By filePath ["+filePath+"]",e);
        }
    }

    //Method to Combine multiple JsonObjects then write them to JsonFile as a Combined Object
    public static void createJsonFileFromMultipleJsonObjects(JsonObject[] arr, String jsonFilePath) throws IOException {
        Map<String,Object> total = new HashMap<>();
        for (int i = 0;i<arr.length;i++)
        {
            total.putAll(arr[i].asMap());
        }
        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(total,jsonFilePath);
    }

    //Method to Convert Map to Json Object
    public static JsonObject convertMapToJsonObject(Map map) throws JsonProcessingException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        return gson.fromJson(jsonString,JsonObject.class);
    }

    //Method to Convert Object to Map
    public static  Map<String, Object> convertJsonStringToMap(Object object) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        Map<String, Object> map =
                gson.fromJson(jsonString, new TypeToken<Map<String, Object>>() {}.getType());
        return map;
    }
}


