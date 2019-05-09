package websockets;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/realtime/{deviceId}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class RealtimeEndpoint
{
    private static final List<RealtimeEndpoint> deviceEndpoints = new CopyOnWriteArrayList<>();
    private static final List<RealtimeEndpoint> dashboardEndpoints = new CopyOnWriteArrayList<>();
    private Session session;
    private String deviceId;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("deviceId") String deviceId)
    {
       this.session = session;
       this.deviceId = deviceId;
       
       if (deviceId.equals("dashboard"))
       {
           dashboardEndpoints.add(this);
           System.out.println("Dashboard connected to realtime websocket.");
       }
       else
       {
           deviceEndpoints.add(this);
           System.out.println("Device " + deviceId + " connected to realtime websocket.");
       }
    }

    @OnClose
    public void onClose(Session session)
    {
        if (deviceEndpoints.contains(this))
        {
            deviceEndpoints.remove(this);
        }
        else if (dashboardEndpoints.contains(this))
        {
            dashboardEndpoints.remove(this);
        }
        
        System.out.println("Device " + deviceId 
                + " disconnected from realtime websocket.");
    }

    @OnError
    public void onError(Throwable error)
    {
        error.printStackTrace();
        System.out.println("We got an error!");
        System.out.println(error.getMessage());
    }

    @OnMessage
    public void onMessage(Session session, Message message)
    {
        System.out.println("We got an message!");
        try
        {
            broadcast(message);
            System.out.println("Broadcasted message.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (EncodeException e)
        {
            e.printStackTrace();
        }
    }
    
    private static void broadcast(Message message) throws IOException, EncodeException
    {
        for (RealtimeEndpoint endpoint : dashboardEndpoints)
        {
            endpoint.session.getBasicRemote().sendObject(message);
        }
    }
}
