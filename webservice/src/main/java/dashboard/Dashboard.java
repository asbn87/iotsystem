package dashboard;

import server.Receiver;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dashboard extends HttpServlet {
    
    private Receiver receiver;
    private final RealtimeData realtimeData = new RealtimeData();
    
    @Override
    public void init() throws ServletException
    {
        receiver = new Receiver(55555, realtimeData);
        receiver.run();
    }
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter output = response.getWriter();
        
        output.println("<HTML><HEAD></HEAD><BODY>Hello world!<br>");
        output.print("Value: ");
        output.println(realtimeData.getValue());
        output.println("</BODY></HTML>");
    }
}
