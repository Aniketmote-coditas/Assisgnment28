import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/hello"}, initParams = {@WebInitParam(name = "Driver", value = "com.mysql.jdbc.Driver")})
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletConfig servletConfig = getServletConfig();
		response.setContentType("text/html");

		String s = servletConfig.getInitParameter("Driver");

		PrintWriter out = response.getWriter();

		String parName, parvalue[];

		int cnnt=0;

		String ans="";

		Enumeration ee = request.getParameterNames();
		try {
			Class.forName(s);
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1122");

			Statement statement = con.createStatement();


			ResultSet rs = statement.executeQuery("select ans from answer");



			while (rs.next() && ee.hasMoreElements()){
				String ss = rs.getString(1);
				parName = (String)ee.nextElement();
				parvalue = (request.getParameterValues(parName));

				for(int i=0;i<parvalue.length;i++){
					ans = parvalue[i];
				}

				if(ans.equals(ss)){
					cnnt++;
				}

			}

			out.println("Result is: "+ cnnt);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
