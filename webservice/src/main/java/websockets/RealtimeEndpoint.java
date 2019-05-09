package websockets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/realtime/{deviceId}", decoders = RealtimeDecoder.class, encoders = RealtimeEncoder.class)
public class RealtimeEndpoint
{
    private static final List<RealtimeEndpoint> deviceEndpoints = new CopyOnWriteArrayList<>();
    private static final List<RealtimeEndpoint> dashboardEndpoints = new CopyOnWriteArrayList<>();
    private static final Map<Integer, Realtime> realtimeMeasurements = new ConcurrentHashMap<>();
    private Session session;
    private String deviceId;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("deviceId") String deviceId)
    {
       this.session = session;
       this.deviceId = deviceId;
       
        switch (deviceId) {
            case "dashboard":
                dashboardEndpoints.add(this);
                System.out.println("Dashboard connected to realtime websocket.");
                break;
                
            case "log":
                dbSave();
                break;
                
            default:
                deviceEndpoints.add(this);
                System.out.println("Device " + deviceId + " connected to realtime websocket.");
                break;
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
    }

    @OnMessage
    public void onMessage(Session session, Realtime message)
    {
        realtimeMeasurements.put(Integer.parseInt(deviceId), message);
        
        try
        {
            broadcast(message);
            System.out.println("Broadcasted message.");
        }
        catch (IOException | EncodeException e)
        {
            e.printStackTrace();
        }
    }
    
    private static void broadcast(Realtime message) throws IOException, EncodeException
    {
        for (RealtimeEndpoint endpoint : dashboardEndpoints)
        {
            endpoint.session.getBasicRemote().sendObject(message);
        }
    }
    
    private static void dbSave()
    {
        System.out.println("All realtime measurements stored in memory: ");
        
        for (Map.Entry<Integer, Realtime> entry : realtimeMeasurements.entrySet())
        {
            int deviceId = entry.getKey();
            Float temperature = entry.getValue().getTemperature();
            Float humidity = entry.getValue().getHumidity();
            Float radiation = entry.getValue().getRaditation();
            Integer light = entry.getValue().getLight();
            
            System.out.println("\nDevice ID: " + deviceId);
            System.out.println("Temperature: " + temperature);
            System.out.println("Humidity: " + humidity);
            System.out.println("Radiation: " + radiation);
            System.out.println("Light: " + light);
        }
    }
}