package websockets;

public class Realtime
{
    private Integer deviceId;
    private Float temperature;
    private Float humidity;
    private Float radiation;
    private Integer light;

    public int getDeviceId()
    {
        return deviceId;
    }
    
    public void setDeviceId(int deviceId)
    {
        this.deviceId = deviceId;
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
