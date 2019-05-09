package server;

public class Message
{
    private Float temperature;
    private Float humidity;
    private Float raditation;
    private Integer light;

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
        return raditation;
    }

    public void setRaditation(Float raditation) {
        this.raditation = raditation;
    }

    public Integer getLight() {
        return light;
    }

    public void setLight(Integer light) {
        this.light = light;
    }
}
