package websockets;

public class Realtime
{
    private String mac;
    private String description;
    private String dateTime;
    private Float temperature;
    private Float humidity;
    private Float radiation;
    private Integer light;

    public String getMac()
    {
        return mac;
    }
    
    public void setMac(String mac)
    {
        this.mac = mac;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }
    
    
    
    public float getTemperature()
    {
        return temperature;
    }

    public void setTemperature(Float temperature)
    {
        this.temperature = temperature;
    }

    public Float getHumidity()
    {
        return humidity;
    }

    public void setHumidity(Float humidity)
    {
        this.humidity = humidity;
    }

    public Float getRaditation() {
        return radiation;
    }

    public void setRaditation(Float radiation) {
        this.radiation = radiation;
    }

    public Integer getLight() {
        return light;
    }

    public void setLight(Integer light) {
        this.light = light;
    }
}
