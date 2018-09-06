package Database;

import java.sql.*;

public class DBOverwatch {
    Connection conn = null;
    Statement statement;

    public DBOverwatch(){
        try {
            conn = DriverManager.getConnection("jdbc:h2:./data/database;TRACE_LEVEL_FILE=3;TRACE_LEVEL_SYSTEM_OUT=3", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeSQL(String query){
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(conn.nativeSQL(query));
            System.out.println("Executed: " + conn.nativeSQL(query));
            System.out.println(resultSet);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newTable(String name){
        executeSQL("CREATE TABLE" + name);
    }

    public void closeConn(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
