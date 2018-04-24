package userScripts;

import DBcon.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class user_fileDownload
 */
@WebServlet("/user_fileDownload")
public class user_fileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public user_fileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        con = DbConnection.getConnection();

        String sk = request.getParameter("sk");

        HttpSession session = request.getSession();

        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from file_upload where secret_key='" + sk + "'");
            if (rs.next()) {
                session.setAttribute("ssidsecretkey", rs.getString("secret_key"));
                response.sendRedirect("user/file.jsp");
                return;
            } else {
                response.sendRedirect("user/file_download.jsp#dlFail");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
