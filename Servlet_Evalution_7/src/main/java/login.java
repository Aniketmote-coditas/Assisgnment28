import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/login")
public class login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



        PreparedStatement statement;
        Statement statement1;
        String admin = "admin";
        String adminPassword="admin";
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession httpSession = request.getSession();

        boolean flag=true;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1122");

            String d = "select desp from login where email='"+email+"'";

            statement1 = connection.createStatement();
            ResultSet rs = statement1.executeQuery(d);

            while (rs.next()){
                String aa = rs.getString(1);

                if(aa.equals("admin")){
                    flag=false;
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("allDetails");
                    requestDispatcher.forward(request,response);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*if(flag==true){
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1122");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String str = "select email, password from register";

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("home");
            requestDispatcher.forward(request,response);
        }*/

        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1122");

            String str = "select email, password from register";

            statement1 = connection.createStatement();

            ResultSet rs = statement1.executeQuery(str);

            while(rs.next()){
                String a = rs.getString(1);
                String b = rs.getString(2);
                if(a.equals(email) && b.equals(password)){
                    String q = "insert into login values(?,?,?)";
                    statement = connection.prepareStatement(q);
                    statement.setString(1,a);
                    statement.setString(2,b);
                    statement.setString(3,"Student");
                    statement.executeUpdate();

                    httpSession.setAttribute("email",a);
                    response.sendRedirect("home");
                    /*RequestDispatcher requestDispatcher = request.getRequestDispatcher("home");
                    requestDispatcher.forward(request,response);*/

                }
            }


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
