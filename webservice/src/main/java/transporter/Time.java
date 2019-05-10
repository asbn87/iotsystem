package transporter;

import java.time.LocalDateTime;

public class Time 
{
    int id;
    LocalDateTime time;
    
    public Time(LocalDateTime time)
    {
        setTime(time);
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public LocalDateTime getTime() 
    {
        return time;
    }

    public void setTime(LocalDateTime time) 
    {
        this.time = time;
    }    
}
