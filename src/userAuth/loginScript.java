package userAuth;

import java.sql.ResultSet;
import DBcon.DbConnection;
import java.sql.Statement;
import java.sql.Connection;
import javax.servlet.http.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginScript
 */
@WebServlet("/loginScript")
public class loginScript extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginScript() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        int status = Integer.parseInt(request.getParameter("status"));
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        HttpSession session = request.getSession();
        try {
            con = DbConnection.getConnection();
            st = con.createStatement();
            switch (status) {
                case 1:
                    try {
                        rs = st.executeQuery("select * from reg where name='" + name + "' AND pass='" + pass + "' AND role='Owner'");
                        if (rs.next()) {
                            session.setAttribute("logdIn", 1);
                            session.setAttribute("usr_status", 1);
                            session.setAttribute("usr_name", rs.getString("name"));
                            session.setAttribute("usr_role", rs.getString("role"));
                            session.setAttribute("usr_email", rs.getString("email"));
                            session.setAttribute("usr_state", rs.getString("state"));
                            session.setAttribute("usr_country", rs.getString("country"));
                            response.sendRedirect("owner/dashboard.jsp");
                            return;
                        } else {
                            response.sendRedirect("index.jsp?#fail");
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        rs = st.executeQuery("select * from reg where audd='" + name + "' AND pass='" + pass + "' AND role='User'");
                        if (rs.next()) {
                            session.setAttribute("logdIn", 1);
                            session.setAttribute("usr_status", 2);
                            session.setAttribute("usr_name", rs.getString("name"));
                            session.setAttribute("usr_id", rs.getString("audd"));
                            session.setAttribute("usr_role", rs.getString("role"));
                            session.setAttribute("usr_email", rs.getString("email"));
                            session.setAttribute("usr_state", rs.getString("state"));
                            session.setAttribute("usr_country", rs.getString("country"));

                            response.sendRedirect("user/dashboard.jsp");
                            return;
                        } else {
                            response.sendRedirect("index.jsp?#fail");
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        if (name.equalsIgnoreCase("ca") && pass.equalsIgnoreCase("ca")) {
                            session.setAttribute("logdIn", 1);
                            session.setAttribute("usr_status", 3);
                            session.setAttribute("usr_name", "CAShashank");
                            session.setAttribute("usr_role", "Certificate Authority");
                            response.sendRedirect("ca/dashboard.jsp");
                            return;
                        } else {
                            response.sendRedirect("index.jsp?#fail");
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;

                case 4:
                    try {
                        rs = st.executeQuery("select * from reg where audd='" + name + "' AND pass='" + pass + "' AND role='AA'");
                        if (rs.next()) {
                            session.setAttribute("logdIn", 1);
                            session.setAttribute("usr_status", 4);
                            session.setAttribute("usr_name", rs.getString("name"));
                            session.setAttribute("usr_id", rs.getString("audd"));
                            session.setAttribute("usr_role", "Attribute Authority");
                            session.setAttribute("usr_email", rs.getString("email"));
                            session.setAttribute("usr_state", rs.getString("state"));
                            session.setAttribute("usr_country", rs.getString("country"));

                            response.sendRedirect("aa/dashboard.jsp");
                            return;
                        } else {
                            response.sendRedirect("index.jsp?#fail");
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;

                case 5:
                    try {
                        if (name.equalsIgnoreCase("cloud") && pass.equalsIgnoreCase("cloud")) {
                            session.setAttribute("logdIn", 1);
                            session.setAttribute("usr_status", 5);
                            session.setAttribute("usr_name", "CloudShashank");
                            session.setAttribute("usr_role", "Cloud");
                            response.sendRedirect("cloud/dashboard.jsp");
                            return;
                        } else {
                            response.sendRedirect("index.jsp?#fail");
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                default:
                    response.sendRedirect("index.html");
                    return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("index.jsp?#SQLfail");
            return;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
