package websockets;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import com.google.gson.Gson;

public class HistoryEncoder implements Encoder.Text<History>
{
    @Override
    public String encode(History history) throws EncodeException
    {
        Gson gson = new Gson();
        String json = gson.toJson(history);
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
