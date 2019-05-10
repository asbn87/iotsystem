package dashboard;

import java.util.List;
import transporter.Transporter;

interface DAOInterface
{
    public void connectToDatabase();
    public void addDataToDatabase(Transporter transporter);
    public List<Transporter> retrieveLatest6HDataFromDatabase();
}