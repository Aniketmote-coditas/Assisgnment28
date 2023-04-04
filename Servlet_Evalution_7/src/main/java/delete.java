import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/delet")
public class delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        String str = request.getParameter("email");
        PreparedStatement statement1;
        PreparedStatement statement2;
        Statement statement;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1122");

            String d = "DELETE FROM register WHERE email='"+str+"'";
            String str1 = "select * from register where email='"+str+"'";

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(str1);

            /*String ans = "insert into resentdelete values('"+rs.getString(1)+
                    "','"+rs.getString(2)+
                    "','"+rs.getString(3)+
                    "','"+rs.getString(4)+
                    "','"+rs.getString(5)+"'"*/
            String ans = "insert into resentdelete values(?,?,?,?,?)";


            statement2 = connection.prepareStatement(ans);
            while (rs.next()) {
                statement2.setString(1, rs.getString(1));
                statement2.setString(2, rs.getString(2));
                statement2.setString(3, rs.getString(3));
                statement2.setString(4, rs.getString(4));
                statement2.setString(5, rs.getString(5));
            }

            statement2.executeUpdate();

            statement1 =  connection.prepareStatement(d);
            statement1.executeUpdate();
            out.println("Succesfully Delete");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("allDetails");
            requestDispatcher.include(request,response);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
