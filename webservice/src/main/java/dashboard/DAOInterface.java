package dashboard;

import transporter.Transporter;

interface DAOInterface
{
    public void connectToDatabase();
    public void addDataToDatabase(Transporter transporter);
}