package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class JsonManager {

    //Variables
    private String filePath;

    //Constructor
    public JsonManager(String filePath) {
        this.filePath = filePath;
    }

    //Method to Get JsonData by jsonPath
    public String getData(String jsonPath) throws IOException, ParseException {
        String[] arrofSTG = jsonPath.split("\\.");
        if (arrofSTG[0].contains("["))
        {
            String[] arrofSTG_2 = (arrofSTG[0]).split("\\[");
            String [] arrofSTG_3 = (arrofSTG_2[1]).split("]");
            int index = Integer.parseInt(arrofSTG_3[0]);

            JSONArray array = (JSONArray) readJsonFile().get(arrofSTG_2[0]);
            Object object = (array).get(index);
            if (arrofSTG.length==2) {
                object = (array).get(index);
                object = ((JSONObject) object).get(arrofSTG[1]);
            }
            if (arrofSTG.length==3) {
                object = ((JSONObject) object).get(arrofSTG[2]);
            }
            return object.toString();
        }

        else
        {
            Object object = readJsonFile().get(arrofSTG[0]);
            if (arrofSTG.length==2) {

                object = ((JSONObject) object).get(arrofSTG[1]);
            }
            else if (arrofSTG.length==3) {

                object = ((JSONObject) object).get(arrofSTG[2]);
            }
            return object.toString();
        }
    }

    //Method to Get JsonData by jsonPath
    public Object getDataAsObject(String jsonPath) throws IOException, ParseException {
        String[] arrofSTG = jsonPath.split("\\.");
        if (arrofSTG[0].contains("["))
        {
            String[] arrofSTG_2 = (arrofSTG[0]).split("\\[");
            String [] arrofSTG_3 = (arrofSTG_2[1]).split("]");
            int index = Integer.parseInt(arrofSTG_3[0]);

            JSONArray array = (JSONArray) readJsonFile().get(arrofSTG_2[0]);
            Object object = null;
            if (arrofSTG.length==2) {
                object = (array).get(index);
                object = ((JSONObject) object).get(arrofSTG[1]);
            }
            if (arrofSTG.length==3) {
                object = ((JSONObject) object).get(arrofSTG[2]);
            }
            return object;
        }

        else
        {
            Object object = readJsonFile().get(arrofSTG[0]);
            if (arrofSTG.length==2) {

                object = ((JSONObject) object).get(arrofSTG[1]);
            }
            else if (arrofSTG.length==3) {

                object = ((JSONObject) object).get(arrofSTG[2]);
            }
            return object;
        }
    }

    //Method to Set JsonData by Input
    public JsonManager setData(String key, String value) throws IOException, ParseException {
        String[] arrofSTG = key.split("\\.");
        Object object = readJsonFile();
        Object object2;
        if (arrofSTG.length==1) {
            ((JSONObject)object).put(arrofSTG[0],value);
        }
        else if (arrofSTG.length==2) {
            object2 = ((JSONObject)object).get(arrofSTG[0]);
            ((JSONObject) object2).put(arrofSTG[1], value);
        }
        else if (arrofSTG.length==3) {
            object2 = ((JSONObject)object).get(arrofSTG[0]);
            Object object3= ((JSONObject)object2).get(arrofSTG[1]);
            ((JSONObject) object3).put(arrofSTG[2], value);
        }

        createJsonFile(object,filePath);
        return this;
    }

    //Method to read JsonFile and convert it to JsonObject
    public JSONObject readJsonFile() throws IOException, ParseException {
        //pass the path of test data json file
        File filename = new File(filePath);
        //convert the json file to string
        String jsonString = FileUtils.readFileToString(filename, "UTF8");
        //parse the json string into object (Deserialization)
        Object obj = new JSONParser().parse(jsonString);
        //return the object as Json Object
        return (JSONObject) obj;
    }

    //Method to Create JsonFile from Object
    public static void createJsonFile(Object object , String jsonFilePath) throws IOException {
        FileWriter file = new FileWriter(jsonFilePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formattedJson = gson.toJson(object);
        file.write(formattedJson);
        file.close();
    }

    //Method to Combine multiple JsonObjects then write them to JsonFile as a Combined Object
    public static void setJsonFileFromMultipleJsonObjects(JSONObject[] arr,String jsonFilePath) throws IOException {
        JSONObject total = new JSONObject();
        for (int i = 0;i<arr.length;i++)
        {
            total.putAll(arr[i]);
        }

        System.out.println(total.toJSONString());
        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(total,jsonFilePath);
    }

    //Method to Convert Map to Json Object
    public static org.json.JSONObject convertMaptoJsonObject(Map map)
    {
        org.json.JSONObject object = new org.json.JSONObject(map);
        return  object;
    }

    //Method to Convert Json String to Map
    public static  Map<String, Object> convertJsonStringToMap(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map
                = mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
    return map;
    }

}


