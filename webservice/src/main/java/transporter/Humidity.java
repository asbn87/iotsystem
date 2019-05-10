package transporter;

public class Humidity 
{
    private int id;
    private int deviceId;
    private int timeId;
    private float humidity_pct;
    
    public Humidity(float humidity)
    {
        setHumidity_pct(humidity);
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public int getDeviceId() 
    {
        return deviceId;
    }

    public void setDeviceId(int deviceId) 
    {
        this.deviceId = deviceId;
    }

    public int getTimeId() 
    {
        return timeId;
    }

    public void setTimeId(int timeId) 
    {
        this.timeId = timeId;
    }

    public float getHumidity_pct() 
    {
        return humidity_pct;
    }

    public void setHumidity_pct(float humidity_pct) 
    {
        this.humidity_pct = humidity_pct;
    }
}
