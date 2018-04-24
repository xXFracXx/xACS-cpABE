package userScripts;

import DBcon.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class user_fileRequest
 */
@WebServlet("/user_fileRequest")
public class user_fileRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public user_fileRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String id = request.getParameter("id");
            System.out.println("ID >>--->>:" + id);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from file_upload where id = '" + id + "'");
            if (rs.next()) {

                String fname = rs.getString("filename");
                String secr = rs.getString("secret_key");

                HttpSession session = request.getSession();

                String Cname = (String) session.getAttribute("usr_name");
                String Cmail = (String) session.getAttribute("usr_email");
                String State = (String) session.getAttribute("usr_state");
                String Country = (String) session.getAttribute("usr_country");
                String Status = "No";
                System.out.println("Request is activated" + Cname + State + State + Country + Status);

                Connection con = DbConnection.getConnection();
                Statement st = con.createStatement();
                String sql = "insert into request(name, mail, state, country, fname, secretkey, status) values ('" + Cname + "','" + Cmail + "','" + State + "','" + Country + "','" + fname + "','" + secr + "','" + Status + "')";

                int x = st.executeUpdate(sql);
                con.close();
                st.close();
                if (x != 0) {
                    response.sendRedirect("user/file_request.jsp#reqSucc");
                    return;

                } else {
                    response.sendRedirect("user/file_request.jsp#reqFail");
                    return;

                }
            } else {

                System.out.println("Error");
                response.sendRedirect("user/file_request.jsp#reqFail");
                return;

            }
        } catch (Exception e) {
            System.out.println(e);
            response.sendRedirect("user/file_request.jsp#sqlFail");
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
