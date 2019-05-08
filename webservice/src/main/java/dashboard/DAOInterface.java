package dashboard;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import com.mysql.cj.jdbc.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DAOInterface {
    public DAOInterface() throws ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        
        try{
            connectToDatabase();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Something whent wrong");
        }
        
    }
    

    public static void connectToDatabase() throws SQLException
    {
        String username = ""; // database name mostly "root"
        String password = ""; // database password
        
        
        try(Connection con = (Connection) DriverManager.getConnection(" jdbc:MySQL://ip:port/databasename", username, password);){
            if(con != null){
                System.out.println("Connection succes!");
            }
        }
        
        catch(Exception e){
            e.printStackTrace();
            System.out.println("No Connection to database");
        }
        
    }
}

    
    

