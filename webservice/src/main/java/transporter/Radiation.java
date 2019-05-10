package transporter;

public class Radiation 
{
    int id;
    int deviceId;
    int timeId;
    float siverts_uSv;
    
    public Radiation(float uSv)
    {
        setSiverts_uSv(uSv);
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

    public float getSiverts_uSv() 
    {
        return siverts_uSv;
    }

    public void setSiverts_uSv(float siverts_uSv) 
    {
        this.siverts_uSv = siverts_uSv;
    }
    
}
