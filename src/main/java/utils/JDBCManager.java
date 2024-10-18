package utils;

import com.mysql.cj.jdbc.Driver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.JsonManager.createJsonFile;
import static utils.PropertiesManager.getPropertiesValue;

public class JDBCManager {
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

    //Table of Multiple Rows and one Nested Key
    public static void setJsonFileFromDBForNestedArrayOfJsonObjects(String query,String jsonFilePath,String[] jsonKeys,
                                                                      String jsonMainKey) throws SQLException, IOException, ParseException {
        //Read the object1 for data retrieved from Database
        JSONObject object1 = setJsonObjectFromDBForNestedArrayOfJsonObjects(query,jsonKeys,jsonMainKey);

        //Read the Current Json File content and convert it to Object 2
        JSONObject object2 = new JsonManager(jsonFilePath).readJsonFile();

        //Combine the two Json objects into one Object then Write it to the JSON File
        object2.putAll(object1);
        createJsonFile(object2,jsonFilePath);
    }

    //Table of Multiple Rows and Multiple Nested Keys
    public static void setJsonFileFromDBForNestedJsonObjects(String query, String jsonFilePath,
                                                                         String[] jsonKeys,String[] jsonMainKeys) throws SQLException, IOException, ParseException {
        //Read the object1 for data retrieved from Database
        JSONObject object1 = setJsonObjectFromDBForNestedJsonObjects(query,jsonKeys,jsonMainKeys);

        //Read the Current Json File content and convert it to Object 2
        JSONObject object2 = new JsonManager(jsonFilePath).readJsonFile();

        //Combine the two Json objects into one Object then Write it to the JSON File
        object2.putAll(object1);
        createJsonFile(object2,jsonFilePath);
    }

    //**********************************************************************************************************************//

    //Insert New Record to Database
    public static void insertNewRecordToDatabase(String query) throws IOException, SQLException {
        //Register Driver Classs for the Database
        DriverManager.registerDriver(new Driver());
        //Create Connection with the Database
        Connection connection = DriverManager.getConnection(getPropertiesValue("dbUrl")
                ,getPropertiesValue("dbUser"),getPropertiesValue("dbPassword"));
        connection.createStatement().executeUpdate(query);
    }

    //**********************************************************************************************************************//

    /*//Table of One Rows and No Nested Key
    private static JSONObject setJsonObjectFromDBForSimpleJsonObjects(String query,
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
    private static JSONObject setJsonObjectFromDBForNestedJsonObjects(String query ,
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
    //Table of One Rows and No Nested Key
    private static void setJsonFileFromDBForSimpleJsonObjects(String query, String jsonFilePath,
                                                              String[] jsonKeys) throws SQLException, IOException {
        JSONObject object = setJsonObjectFromDBForSimpleJsonObjects(query,jsonKeys);
        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(object,jsonFilePath);
    }


    //Table of One Rows and one Nested Key
    private static void setJsonFileFromDBForNestedJsonObjects(String query, String jsonFilePath, String[] jsonKeys,
                                                              String jsonMainKey) throws SQLException, IOException, ParseException {
        JSONObject object = setJsonObjectFromDBForNestedJsonObjects(query,jsonKeys,jsonMainKey);
    }*/
}


