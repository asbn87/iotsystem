package websockets;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import com.google.gson.Gson;

public class RealtimeEncoder implements Encoder.Text<Realtime>
{
    @Override
    public String encode(Realtime message) throws EncodeException
    {
        Gson gson = new Gson();
        String json = gson.toJson(message);
        return json;
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
