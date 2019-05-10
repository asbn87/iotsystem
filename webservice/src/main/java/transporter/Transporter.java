package transporter;

public class Transporter 
{
    Device device;
    Time time;
    Temperature temperature;
    Humidity humidity;
    Light light;
    Radiation radiation;
    
    public Transporter(){}

    public Device getDevice() 
    {
        return device;
    }

    public void setDevice(Device device) 
    {
        this.device = device;
    }

    public Time getTime() 
    {
        return time;
    }

    public void setTime(Time time) 
    {
        this.time = time;
    }

    public Temperature getTemperature() 
    {
        return temperature;
    }

    public void setTemperature(Temperature temperature) 
    {
        this.temperature = temperature;
    }

    public Humidity getHumidity() 
    {
        return humidity;
    }

    public void setHumidity(Humidity humidity) 
    {
        this.humidity = humidity;
    }

    public Light getLight() 
    {
        return light;
    }

    public void setLight(Light light) 
    {
        this.light = light;
    }

    public Radiation getRadiation() 
    {
        return radiation;
    }

    public void setRadiation(Radiation radiation) 
    {
        this.radiation = radiation;
    }
    
}
