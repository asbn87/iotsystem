package websocket;

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

@ServerEndpoint(value = "/device/{deviceId}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class Endpoint
{
    private static final List<Endpoint> deviceEndpoints = new CopyOnWriteArrayList<>();
    private static final List<Endpoint> dashboardEndpoints = new CopyOnWriteArrayList<>();
    private Session session;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("deviceId") String deviceId)
    {
       this.session = session;
       
       if (deviceId.equals("0"))
       {
           dashboardEndpoints.add(this);
           System.out.println("Dashboard connected.");
       }
       else
       {
           deviceEndpoints.add(this);
           System.out.println("Device " + deviceId + " connected.");
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
        
        System.out.println("Disconnect.");
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
        for (Endpoint endpoint : dashboardEndpoints)
        {
            endpoint.session.getBasicRemote().sendObject(message);
        }
    }
}
