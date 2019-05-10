package transporter;

public class Device 
{
    int id;
    String mac;
    String description;
    
    public Device(String mac, String description)
    {
        setMac(mac);
        setDescription(description);
    }

    public String getMac() 
    {
        return mac;
    }

    public void setMac(String mac) 
    {
        this.mac = mac;
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }
}
