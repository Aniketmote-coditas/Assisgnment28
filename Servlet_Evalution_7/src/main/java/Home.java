import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/home")
public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession httpSession = request.getSession();
        String s = (String)httpSession.getAttribute("email");

        if(httpSession.getAttribute("email")!=null) {
            out.println("Welcome " + s);
            out.println(" <a href=\"logout\">logout</a>");
            out.println("<a href=\"profile\">Profile</a>");
        }else {
            response.sendRedirect(request.getContextPath()+"/login.html");
        }

    }
}
