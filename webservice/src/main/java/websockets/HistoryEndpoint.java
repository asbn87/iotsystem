package websockets;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/historical", decoders = HistoryDecoder.class, encoders = HistoryEncoder.class)
public class HistoryEndpoint 
{
    private static final List<HistoryEndpoint> dashboardEndpoints = new CopyOnWriteArrayList<>();
    private Session session;
    
    @OnOpen
    public void onOpen(Session session)
    {
        this.session = session;
        
        dashboardEndpoints.add(this);
        System.out.println("Dashboard connected to historical websocket.");
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
