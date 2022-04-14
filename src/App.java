
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class App {
    private static String USERNAME = "*";
    private static String PASSWORD = "*";
    private static String URL = "jdbc:postgresql://localhost/";
    public static void main(String[] args){
        Connection connection = null;
        Properties prop = new Properties();   
        try{
            Class.forName("org.postgresql.Driver");
            prop.setProperty("port", "5432");
            prop.setProperty("database", "testdb");
            prop.setProperty("user", USERNAME);
            prop.setProperty("password", PASSWORD);
            connection = DriverManager.getConnection(URL, prop);
            System.out.println("Opened Successfully!");

            // createTables(connection);
            DataClass edan = new DataClass("edan", "YOLO", 5);
            DataClass omi = new DataClass("omi", "YOLO", 4);
            insertData(connection, edan);
            insertData(connection, omi);
            alterData(connection, edan);
            alterData(connection, omi);
        }catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
            System.exit(0);
        }
        
    }

    public static void createTables(Connection connection){
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "CREATE TABLE userTable " + "(NAME TEXT, SERVERNAME TEXT, RESPONSES SMALLINT DEFAULT 0)";
            
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void insertData(Connection connection, DataClass dc){
        PreparedStatement statement;
        try{
            String sql = "INSERT INTO userTable(name, servername) "+"VALUES(?,?)";            
            statement = connection.prepareStatement(sql);
            statement.setString(1, dc.name);
            statement.setString(2, dc.serverName);
            statement.executeUpdate();
            statement.close();
            System.out.println("Data inserted Successfully!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void alterData(Connection connection, DataClass data){
        PreparedStatement statement;
        try{
            String sql = "UPDATE usertable SET responses=? WHERE name=? AND servername=? ;";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, data.responses);
            statement.setString(2, data.name);
            statement.setString(3, data.serverName);
            statement.executeUpdate();
            statement.close();
            System.out.println("Data changed!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteData(Connection connection, DataClass data){
        PreparedStatement statement;
        try{
            String sql = "DELETE FROM usertable WHERE name=? AND servername=?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, data.name);
            statement.setString(2, data.serverName);
            statement.executeUpdate();
            statement.close();
            System.out.println("Data deleted!");
        }catch(Exception e){e.printStackTrace();}
    }
}

class DataClass{
    public String name;
    public String serverName;
    public int responses;
    public DataClass(String name, String serverName, int responses){
        this.name = name;
        this.serverName = serverName;
        this.responses = responses;
    }
}
