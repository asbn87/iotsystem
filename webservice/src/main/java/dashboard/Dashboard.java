package dashboard;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dashboard extends HttpServlet {
    
    @Override
    public void init() throws ServletException
    {
        
    }
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter output = response.getWriter();
        
        output.println("<HTML><HEAD></HEAD><BODY>Hello world!</BODY></HTML>");
    }
}
