package userScripts;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import java.sql.Statement;
import java.sql.Connection;
import DBcon.DbConnection;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class owner_fileDelete
 */
@WebServlet("/owner_fileDelete")
public class owner_fileDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public owner_fileDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int id = Integer.parseInt(request.getQueryString());
        HttpSession user = request.getSession(true);
        String owner = user.getAttribute("usr_name").toString();
        try {
            Connection con = DbConnection.getConnection();
            Statement st = con.createStatement();
            int i = st.executeUpdate("DELETE FROM file_upload WHERE id = " + id + " and owner = \"" + owner + "\"");
            if (i != 0) {
                response.sendRedirect("owner/file_details.jsp#delSucc");
                return;
            } else {
                response.sendRedirect("owner/file_details.jsp#delFail");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(owner_fileDelete.class.getName()).log(Level.SEVERE, null, ex);
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
