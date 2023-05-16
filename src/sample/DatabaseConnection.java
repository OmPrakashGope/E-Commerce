package sample;

import java.sql.*;

//to communicate with database
public class DatabaseConnection {
    static String dbURL = "jdbc:mysql://localhost:3306/ecomm";//url of database of mysql containing products
    static String userName = "root";//mysql username
    static String password = "Prahlad@123456";//password of mysql
    private static Statement getStatement(){
        try{
            Connection conn = DriverManager.getConnection(dbURL,userName,password);
            return conn.createStatement();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static ResultSet getQueryTable(String query)
    {
        Statement statement = getStatement();
        try
        {
            return statement.executeQuery(query);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean insertUpdate(String query)
    {
        Statement statement = getStatement();
        try
        {
            statement.executeUpdate(query);//this is used to insert or delete the value in MySQL
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args)
    {
        String query = "SELECT * FROM PRODUCTS";
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = dbConn.getQueryTable(query);
        if(rs != null)
        {
            System.out.println("Connected to Database");
        }
    }
}


