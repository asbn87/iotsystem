package dashboard;

import java.sql.Connection;
import java.sql.DriverManager;


public class ServerDAO implements DAOInterface
{
    public ServerDAO() throws ClassNotFoundException
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
    
    @Override
    public void connectToDatabase()
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
