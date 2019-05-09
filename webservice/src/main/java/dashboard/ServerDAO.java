package dashboard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import transporter.Transporter;


public class ServerDAO implements DAOInterface
{
    Connection con;
    
    public ServerDAO() throws ClassNotFoundException
    {
            connectToDatabase();        
    }
    
    @Override
    public void connectToDatabase()
    {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("C:\\Git projekt\\SystemIntegrationUppg1Server\\src\\java\\Server\\settings.properties"));
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            this.con = DriverManager.getConnection(p.getProperty("connectionString"),p.getProperty("name"),p.getProperty("password"));
            } 
        catch (FileNotFoundException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        catch (IOException| SQLException | ClassNotFoundException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);        }
        }        

    @Override
    public void addDataToDatabase(Transporter transporter) {
        if(transporter.getTime() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO times (Time) VALUES (?)");
            stmt.setObject(1, transporter.getTime().getTime());} 
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if(transporter.getTemperature() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO temperatures (DeviceId, Temperature_C, TimeId) VALUES (?, ?, (SELECT Id FROM times WHERE time = ?))");
            stmt.setInt(1, transporter.getDevice().getId());
            stmt.setFloat(2, transporter.getTemperature().getTemperature_C());
            stmt.setObject(3, transporter.getTime().getTime());}
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}               
        }
        if(transporter.getHumidity() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO humiditys (DeviceId, Humidity_pct, TimeId) VALUES (?, ?, (SELECT Id FROM times WHERE time = ?))");
            stmt.setInt(1, transporter.getDevice().getId());
            stmt.setFloat(2, transporter.getHumidity().getHumidity_pct());
            stmt.setObject(3, transporter.getTime().getTime());}
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if(transporter.getLight() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO light (DeviceId, Lux, TimeId) VALUES (?, ?, (SELECT Id FROM times WHERE time = ?))");
            stmt.setInt(1, transporter.getDevice().getId());
            stmt.setFloat(2, transporter.getLight().getLux());
            stmt.setObject(3, transporter.getTime().getTime());}
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if(transporter.getRadiation() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO radiation (DeviceId, Siverts_uSv, TimeId) VALUES (?, ?, (SELECT Id FROM times WHERE time = ?))");
            stmt.setInt(1, transporter.getDevice().getId());
            stmt.setFloat(2, transporter.getRadiation().getSiverts_uSv());
            stmt.setObject(3, transporter.getTime().getTime());}
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
    }
}
