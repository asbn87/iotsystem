package dashboard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import transporter.Device;
import transporter.Humidity;
import transporter.Light;
import transporter.Radiation;
import transporter.Temperature;
import transporter.Time;
import transporter.Transporter;


public class ServerDAO implements DAOInterface
{
    Connection con;
    
    public ServerDAO()
    {
                
    }
    
    @Override
    public void connectToDatabase()
    {
        try {
            Properties p = new Properties();
            //p.load(ServerDAO.class.getResourceAsStream("database.properties"));
            p.load(new FileInputStream("C:\\Users\\emil\\Documents\\iotsystem\\webservice\\src\\main\\java\\dashboard\\database.properties"));
            
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connectionString: " + p.getProperty("connectionString"));
            
            this.con = DriverManager.getConnection(p.getProperty("connectionString"),p.getProperty("name"),p.getProperty("password"));
            } 
        catch (FileNotFoundException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        catch (IOException| SQLException | ClassNotFoundException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
    }        

    @Override
    public void addDataToDatabase(Transporter transporter) 
    {
        if(transporter.getDevice() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("call select_or_insert(?, ?);");
            stmt.setString(1, transporter.getDevice().getMac());
            stmt.setString(2, transporter.getDevice().getDescription());
            int u = stmt.executeUpdate();} 
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if(transporter.getTime() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("call add_time(?)");
            stmt.setObject(1, transporter.getTime().getTime());
            int u = stmt.executeUpdate();} 
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if(transporter.getTemperature() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO temperatures (DeviceId, Temperature_C, TimeId) VALUES ((SELECT Id FROM devices WHERE Mac = ?), ?, (SELECT Id FROM times WHERE time = ?))");
            stmt.setString(1, transporter.getDevice().getMac());
            stmt.setFloat(2, transporter.getTemperature().getTemperature_C());
            stmt.setObject(3, transporter.getTime().getTime());
            int u = stmt.executeUpdate();}
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}               
        }
        if(transporter.getHumidity() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO humiditys (DeviceId, Humidity_pct, TimeId) VALUES ((SELECT Id FROM devices WHERE Mac = ?), ?, (SELECT Id FROM times WHERE time = ?))");
            stmt.setString(1, transporter.getDevice().getMac());
            stmt.setFloat(2, transporter.getHumidity().getHumidity_pct());
            stmt.setObject(3, transporter.getTime().getTime());
            int u = stmt.executeUpdate();}
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if(transporter.getLight() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO light (DeviceId, Lux, TimeId) VALUES ((SELECT Id FROM devices WHERE Mac = ?), ?, (SELECT Id FROM times WHERE time = ?))");
            stmt.setString(1, transporter.getDevice().getMac());
            stmt.setInt(2, transporter.getLight().getLux());
            stmt.setObject(3, transporter.getTime().getTime());
            int u = stmt.executeUpdate();}
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
        if(transporter.getRadiation() != null)
        {
            try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO radiation (DeviceId, Siverts_uSv, TimeId) VALUES ((SELECT Id FROM devices WHERE Mac = ?), ?, (SELECT Id FROM times WHERE time = ?))");
            stmt.setString(1, transporter.getDevice().getMac());
            stmt.setFloat(2, transporter.getRadiation().getSiverts_uSv());
            stmt.setObject(3, transporter.getTime().getTime());
            int u = stmt.executeUpdate();}
            catch (SQLException ex) {Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        }
    } 

    @Override
    public List<Transporter> retrieveLatest6HData() 
    {
        List<Transporter> transporterList = new ArrayList<>();
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT times.Time, devices.Mac, devices.Description, temperatures.Temperature_C FROM times JOIN temperatures ON times.Id = temperatures.TimeId JOIN devices ON devices.Id = temperatures.DeviceId WHERE times.Time <= current_timestamp() AND times.Time >= DATE_SUB(NOW(), INTERVAL '360:0' MINUTE_SECOND);");
            while(rs.next())
            {
                Transporter tr = new Transporter();
                tr.setDevice(new Device(rs.getString("devices.Mac"), rs.getString("devices.Description")));
                tr.setTime(new Time(rs.getTimestamp("times.Time").toLocalDateTime()));
                tr.setTemperature(new Temperature(rs.getFloat("temperatures.Temperature_C")));
                transporterList.add(tr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT times.Time, devices.Mac, devices.Description, humiditys.Humidity_pct FROM times JOIN humiditys ON times.Id = humiditys.TimeId JOIN devices ON devices.Id = humiditys.DeviceId WHERE times.Time <= current_timestamp() AND times.Time >= DATE_SUB(NOW(), INTERVAL '360:0' MINUTE_SECOND);");
            while(rs.next())
            {
                Transporter tr = new Transporter();
                tr.setDevice(new Device(rs.getString("devices.Mac"), rs.getString("devices.Description")));
                tr.setTime(new Time(rs.getTimestamp("times.Time").toLocalDateTime()));
                tr.setHumidity(new Humidity(rs.getFloat("humiditys.Humidity_pct")));
                transporterList.add(tr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT times.Time, devices.Mac, devices.Description, light.Lux FROM times JOIN light ON times.Id = light.TimeId JOIN devices ON devices.Id = light.DeviceId WHERE times.Time <= current_timestamp() AND times.Time >= DATE_SUB(NOW(), INTERVAL '360:0' MINUTE_SECOND);");
            while(rs.next())
            {
                Transporter tr = new Transporter();
                tr.setDevice(new Device(rs.getString("devices.Mac"), rs.getString("devices.Description")));
                tr.setTime(new Time(rs.getTimestamp("times.Time").toLocalDateTime()));
                tr.setLight(new Light(rs.getInt("light.Lux")));
                transporterList.add(tr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT times.Time, devices.Mac, devices.Description, radiation.Siverts_uSv FROM times JOIN radiation ON times.Id = radiation.TimeId JOIN devices ON devices.Id = tradiation.DeviceId WHERE times.Time <= current_timestamp() AND times.Time >= DATE_SUB(NOW(), INTERVAL '360:0' MINUTE_SECOND);");
            while(rs.next())
            {
                Transporter tr = new Transporter();
                tr.setDevice(new Device(rs.getString("devices.Mac"), rs.getString("devices.Description")));
                tr.setTime(new Time(rs.getTimestamp("times.Time").toLocalDateTime()));
                tr.setRadiation(new Radiation(rs.getFloat("radiation.Siverts_uSv")));
                transporterList.add(tr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ServerDAO.class.getName()).log(Level.SEVERE, null, ex);}
        return transporterList;
    }    
}
