package websockets;

import java.util.ArrayList;
import java.util.List;

public class History
{
    private List<Float> temperatures = new ArrayList<>();

    public List<Float> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Float> temperatures) {
        this.temperatures = temperatures;
    }
}
