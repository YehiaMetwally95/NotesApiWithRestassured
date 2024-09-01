package utils;

import com.mysql.cj.jdbc.Driver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.JsonManager.createJsonFile;
import static utils.PropertiesManager.getPropertiesValue;

public class JDBC {

    //Table of One Rows and No Nested Key
    public static JSONObject setJsonObjectFromDBForSimpleJsonObjects(String query,
                                                             String[] jsonKeys) throws SQLException, IOException {
        //Register Driver Classs for the Database
        DriverManager.registerDriver(new Driver());
        //Create Connection with the Database
        Connection connection = DriverManager.getConnection(getPropertiesValue("dbUrl")
                ,getPropertiesValue("dbUser"),getPropertiesValue("dbPassword"));        //Execute the Select Query on the Database and retrieve the query results in Result Set
        ResultSet rs = connection.createStatement().executeQuery(query);

        //Create JSON Objects and JSON Array that represent the Json File Format
        JSONObject record = null;
        //Outer Looping on each record/row on Database table
        while((rs.next())) {
            //Inner Looping on each column in a specific row of Database table
            record = new JSONObject();
            for (int i = 0 ; i <rs.getMetaData().getColumnCount(); i++)
            {
                //Fill the child Json Object with the column value of a specific row
                record.put(jsonKeys[i],
                        rs.getString(rs.getMetaData().getColumnName(i+1)));
            }
            //Fill the  Json Array of each object hat represent a certain row on Table
        }
        return record ;
    }


    //Table of One Rows and one Nested Key
    public static JSONObject setJsonObjectFromDBForNestedJsonObjects(String query ,
                                                             String[] jsonKeys, String jsonMainKey) throws SQLException, IOException {
        //Register Driver Classs for the Database
        DriverManager.registerDriver(new Driver());
        //Create Connection with the Database
        Connection connection = DriverManager.getConnection(getPropertiesValue("dbUrl")
                ,getPropertiesValue("dbUser"),getPropertiesValue("dbPassword"));        //Execute the Select Query on the Database and retrieve the query results in Result Set
        ResultSet rs = connection.createStatement().executeQuery(query);

        //Create JSON Objects that represent the Json File Format
        JSONObject record = null;
        JSONObject mainKey = new JSONObject();
        //Outer Looping on each record/row on Database table
        while((rs.next())) {
            //Inner Looping on each column in a specific row of Database table
            record = new JSONObject();
            for (int i = 0 ; i <rs.getMetaData().getColumnCount(); i++)
            {
                //Fill the child Json Object with the column value of a specific row
                record.put(jsonKeys[i],
                        rs.getString(rs.getMetaData().getColumnName(i+1)));
            }
        }
        //assign the Json Array as value of the Main key
        mainKey.put(jsonMainKey,record);

        return mainKey;
    }


    //Table of Multiple Rows and one Nested Key
    public static JSONObject setJsonObjectFromDBForNestedArrayOfJsonObjects(String query ,
                                                                     String[] jsonKeys, String jsonMainKey) throws SQLException, IOException {
        //Register Driver Classs for the Database
        DriverManager.registerDriver(new Driver());
        //Create Connection with the Database
        Connection connection = DriverManager.getConnection(getPropertiesValue("dbUrl")
                ,getPropertiesValue("dbUser"),getPropertiesValue("dbPassword"));        //Execute the Select Query on the Database and retrieve the query results in Result Set
        ResultSet rs = connection.createStatement().executeQuery(query);

        //Create JSON Objects and JSON Array that represent the Json File Format
        JSONArray array = new JSONArray();
        JSONObject record;
        JSONObject mainKey = new JSONObject();
        //Outer Looping on each record/row on Database table
        while((rs.next())) {
            //Inner Looping on each column in a specific row of Database table
            record = new JSONObject();
            for (int i = 0 ; i <rs.getMetaData().getColumnCount(); i++)
            {
                //Fill the child Json Object with the column value of a specific row
                record.put(jsonKeys[i],
                        rs.getString(rs.getMetaData().getColumnName(i+1)));
            }
            //Fill the  Json Array of each object hat represent a certain row on Table
            array.add(record);
        }
        //assign the Json Array as value of the Main key
        mainKey.put(jsonMainKey,array);

        return mainKey;
    }


    //Table of Multiple Rows and Multiple Nested Keys
    public static JSONObject setJsonObjectFromDBForNestedJsonObjects(String query ,
                                                             String[] jsonKeys,String[] jsonMainKeys) throws SQLException, IOException {
        //Register Driver Classs for the Database
        DriverManager.registerDriver(new Driver());
        //Create Connection with the Database
        Connection connection = DriverManager.getConnection(getPropertiesValue("dbUrl")
                ,getPropertiesValue("dbUser"),getPropertiesValue("dbPassword"));        //Execute the Select Query on the Database and retrieve the query results in Result Set
        ResultSet rs = connection.createStatement().executeQuery(query);

        //Create Parent & Child JSON Objects that represent the Json File Format
        JSONObject mainRecord = new JSONObject();
        JSONObject record;

        //Outer Looping on each record/row on Database table
        while((rs.next())) {
            //Inner Looping on each column in a specific row of Database table
            record = new JSONObject();
            for (int i = 0 ; i <rs.getMetaData().getColumnCount(); i++)
            {
                //Fill the child Json Object with the column value of a specific row
                record.put(jsonKeys[i],
                        rs.getString(rs.getMetaData().getColumnName(i+1)));
            }
            //Fill the Parent Json Object with main keys and the corresponding child object of each key
            mainRecord.put(jsonMainKeys[ rs.getRow()-1 ], record);
        }
        return mainRecord;
    }

    //**********************************************************************************************************************//
    //Table of One Rows and No Nested Key
    public static void setJsonFileFromDBForSimpleJsonObjects(String query, String jsonFilePath,
                                                                     String[] jsonKeys) throws SQLException, IOException {
        JSONObject object = setJsonObjectFromDBForSimpleJsonObjects(query,jsonKeys);
        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(object,jsonFilePath);
    }


    //Table of One Rows and one Nested Key
    public static void setJsonFileFromDBForNestedJsonObjects(String query, String jsonFilePath, String[] jsonKeys,
                                                             String jsonMainKey) throws SQLException, IOException {
        JSONObject object = setJsonObjectFromDBForNestedJsonObjects(query,jsonKeys,jsonMainKey);
        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(object,jsonFilePath);
    }


    //Table of Multiple Rows and one Nested Key
    public static void setJsonFileFromDBForNestedArrayOfJsonObjects(String query,String jsonFilePath,String[] jsonKeys,
                                                                      String jsonMainKey) throws SQLException, IOException {
        JSONObject object = setJsonObjectFromDBForNestedArrayOfJsonObjects(query,jsonKeys,jsonMainKey);
        //Convert the Format of Parent JSON Array to a pretty format
        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(object,jsonFilePath);
    }


    //Table of Multiple Rows and Multiple Nested Keys
    public static void setJsonFileFromDBForNestedJsonObjects(String query, String jsonFilePath,
                                                                         String[] jsonKeys,String[] jsonMainKeys) throws SQLException, IOException {
        JSONObject object = setJsonObjectFromDBForNestedJsonObjects(query,jsonKeys,jsonMainKeys);
        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(object,jsonFilePath);
    }


    public static void setJsonFileFromMultipleJsonObjects(JSONObject[] arr,String jsonFilePath) throws IOException {
        JSONObject total = new JSONObject();
        for (int i = 0;i<arr.length;i++)
        {
            total.putAll(arr[i]);
        }

        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(total,jsonFilePath);


    }

}


