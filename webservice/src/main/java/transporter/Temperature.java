package transporter;

public class Temperature 
{
    int id;
    int deviceId;
    int timeId;
    float temperature_C;

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

    public float getTemperature_C() 
    {
        return temperature_C;
    }

    public void setTemperature_C(float temperature_C) 
    {
        this.temperature_C = temperature_C;
    }
    
}
