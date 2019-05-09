package websockets;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import com.google.gson.Gson;

public class HistoryDecoder implements Decoder.Text<History> 
{
    @Override
    public History decode(String s) throws DecodeException
    {
        Gson gson = new Gson();
        History history = gson.fromJson(s, History.class);
        return history;
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
