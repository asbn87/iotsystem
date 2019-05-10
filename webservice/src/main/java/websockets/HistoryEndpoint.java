package websockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/history", decoders = HistoryDecoder.class, encoders = HistoryEncoder.class)
public class HistoryEndpoint 
{
    private static final List<HistoryEndpoint> dashboardEndpoints = new CopyOnWriteArrayList<>();
    private Session session;
    
    @OnOpen
    public void onOpen(Session session)
    {
        this.session = session;      
        dashboardEndpoints.add(this);
        
        History history = new History();
        List<Float> temperatures = new ArrayList<>();
        temperatures.add(14.8f);
        temperatures.add(23.1f);
        history.setTemperatures(temperatures);
        
        System.out.println("Dashboard connected to historical websocket.");

            try
            {
               Thread.sleep(10);
               this.session.getBasicRemote().sendObject(history);
            }
            catch (IOException | EncodeException | InterruptedException e)
            {
                e.printStackTrace();
            }
    }

    @OnClose
    public void onClose(Session session)
    {
        dashboardEndpoints.remove(this);
        
        System.out.println("Dashboard disconnected from historical websocket.");
    }

    @OnError
    public void onError(Throwable error)
    {
        error.printStackTrace();
        System.out.println("We got an error!");
        System.out.println(error.getMessage());
    }

    @OnMessage
    public void onMessage(Session session, History history)
    {
        System.out.println("We got an message from the dashboard!");
    }  
}
