package server;

import dashboard.RealtimeData;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receiver implements Runnable
{
    private DatagramSocket socket = null;
    private final RealtimeData realtimeData;
    private byte[] data = new byte[256];
    
    public Receiver(int port, RealtimeData realtimeData)
    {
        this.realtimeData = realtimeData;
        
        try
        {
          socket = new DatagramSocket(port);  
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        while (!Thread.interrupted())
        {
            DatagramPacket packet = new DatagramPacket(data, data.length);
            
            try
            {
               socket.receive(packet);
               String message = new String(packet.getData(), 0, packet.getLength());
               realtimeData.setValue(message);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
