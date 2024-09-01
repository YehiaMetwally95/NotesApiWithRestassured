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

    //Method to get JsonFile and convert it to JsonObject
    private JSONObject getJsonFile() throws IOException, ParseException {
        //pass the path of test data json file
        File filename = new File(filePath);
        //convert the json file to string
        String jsonString = FileUtils.readFileToString(filename, "UTF8");
        //parse the json string into object (Deserialization)
        Object obj = new JSONParser().parse(jsonString);
        //return the object as Json Object
        return (JSONObject) obj;
    }

    //Method to Get JsonData by Input
    public String getTestData(String key) throws IOException, ParseException {
        String[] arrofSTG = key.split("\\.");
        Object object = getJsonFile().get(arrofSTG[0]);
        if (arrofSTG.length==2) {
            object = ((JSONObject) object).get(arrofSTG[1]);
        }
        if (arrofSTG.length==3) {
            object = ((JSONObject) object).get(arrofSTG[2]);
        }
        return (String) object;
    }

    //Method to Get JsonData by Input
    public String getTestDataInsideArray(String key) throws IOException, ParseException {
        String[] arrofSTG = key.split("\\.");
        String[] arrofSTG_2 = (arrofSTG[0]).split("\\[");
        String [] arrofSTG_3 = (arrofSTG_2[1]).split("]");
        int index = Integer.parseInt(arrofSTG_3[0]);

        JSONArray array = (JSONArray) getJsonFile().get(arrofSTG_2[0]);
        Object object = null;
        if (arrofSTG.length==2) {
            object = (array).get(index);
            object = ((JSONObject) object).get(arrofSTG[1]);
        }
        if (arrofSTG.length==3) {
            object = ((JSONObject) object).get(arrofSTG[2]);
        }
        return (String) object;
    }

    //Method to Set JsonData by Input
    public void setTestData(String key, String value) throws IOException, ParseException {
        String[] arrofSTG = key.split("\\.");
        Object object = getJsonFile();
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formattedJson = gson.toJson(object);
        createJsonFile(formattedJson,filePath);
    }

    public static void createJsonFile(Object object , String jsonFilePath) throws IOException {
        FileWriter file = new FileWriter(jsonFilePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formattedJson = gson.toJson(object);
        file.write(formattedJson);
        file.close();
    }

    public static org.json.JSONObject convertMaptoJsonObject(Map map)
    {
        org.json.JSONObject object = new org.json.JSONObject(map);
        return  object;
    }

    public static  Map<String, Object> convertJsonStringToMap(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map
                = mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
    return map;
    }

}


