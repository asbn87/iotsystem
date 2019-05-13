package websockets;

import java.util.ArrayList;
import java.util.List;
import transporter.Transporter;

public class History
{
    private List<Transporter> transporters = new ArrayList<>();

    public History(List<Transporter> transporters)
    {
        this.transporters = transporters;
    }
    
    public List<Transporter> getTransporters() {
        return transporters;
    }

    public void setTransporters(List<Transporter> transporters) {
        this.transporters = transporters;
    }
}
