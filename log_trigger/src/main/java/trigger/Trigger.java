package trigger;

import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class Trigger extends WebSocketClient
{
    private Trigger(URI serverUri)
    {
        super(serverUri);
    }
    
    @Override
    public void onOpen(ServerHandshake handshakedata)
    {
        System.out.println("Connected to websocket.");
    }
    
    @Override
    public void onMessage(String message)
    {
        System.out.println("Received message: " + message);
    }
    
    @Override
    public void onClose(int code, String reason, boolean remote)
    {
        System.out.println("Connection closed.");
    }
    
    @Override
    public void onError(Exception e)
    {
        e.printStackTrace();
    }
        
    public static void main(String[] args) throws URISyntaxException, InterruptedException
    {
        Trigger trigger = new Trigger(
                new URI("ws://localhost:8080/webservice/websocket/realtime/log"));
        trigger.connect();
        System.out.println("Database save request sent.");
        Thread.sleep(3000);
        trigger.close();
        
        trigger = new Trigger(new URI("ws://localhost:8080/webservice/websocket/history"));
        trigger.connect();
        Thread.sleep(100);
        trigger.send("{}");
        System.out.println("Requested push history to dashboard.");
        Thread.sleep(1000);
        trigger.close();
        
        System.exit(0);
    }
}
