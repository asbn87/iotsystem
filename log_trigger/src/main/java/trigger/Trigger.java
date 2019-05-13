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
    
    private static void triggerDatabaseSave(String websocketHost) throws URISyntaxException, InterruptedException
    {
       Trigger trigger = new Trigger(
                new URI("ws://" + websocketHost + "/webservice/websocket/realtime/log"));
        trigger.connect();
        System.out.println("Database save request sent.");
        Thread.sleep(3000);
        trigger.close();
    }
    
    private static void triggerHistoryPush(String websocketHost) throws URISyntaxException, InterruptedException
    {
        Trigger trigger = new Trigger(new URI("ws://" + websocketHost
                                            + "/webservice/websocket/history"));
        trigger.connect();
        Thread.sleep(100);
        trigger.send("{}");
        System.out.println("Requested push history to dashboard.");
        Thread.sleep(1000);
        trigger.close();
    }
        
    public static void main(String[] args) throws URISyntaxException, InterruptedException
    {
        if (args.length != 1)
        {
            System.out.println("Please provide hostname of websocket server.");
            System.exit(-1);
        }
        
        String websocketHost = args[0];
        
        triggerDatabaseSave(websocketHost);
        triggerHistoryPush(websocketHost);
        System.exit(0);
    }
}
