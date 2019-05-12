package dashboard;

import java.util.List;
import transporter.Transporter;

interface DAOInterface
{
    public void connectToDatabase();
    public void addDataToDatabase(Transporter transporter);
    public List<Transporter> retrieveLatest6HTemperature();
    public List<Transporter> retrieveLatest6HHumidity();
    public List<Transporter> retrieveLatest6HLight();
    public List<Transporter> retrieveLatest6HRadiation();
}