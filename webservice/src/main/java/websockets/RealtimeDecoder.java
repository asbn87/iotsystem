package websockets;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import com.google.gson.Gson;

public class RealtimeDecoder implements Decoder.Text<Realtime> 
{
    @Override
    public Realtime decode(String s) throws DecodeException
    {
        Gson gson = new Gson();
        Realtime message = gson.fromJson(s, Realtime.class);
        return message;
    }

    @Override
    public boolean willDecode(String s)
    {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig)
    {
        // Custom initialization logic
    }

    @Override
    public void destroy()
    {
        // Close resources
    }
}
