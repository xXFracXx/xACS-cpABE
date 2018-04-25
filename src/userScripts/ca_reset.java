package userScripts;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBcon.DbConnection;

/**
 * Servlet implementation class ca_reset
 */
@WebServlet("/ca_reset")
public class ca_reset extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ca_reset() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {
            Connection con = DbConnection.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate("TRUNCATE TABLE reg");
            st.executeUpdate("TRUNCATE TABLE request");
            st.executeUpdate("TRUNCATE TABLE download");
            st.executeUpdate("TRUNCATE TABLE file_upload");
            st.executeUpdate("TRUNCATE TABLE acs_info");
            //st.executeUpdate("TRUNCATE TABLE aa_list");
            response.sendRedirect("ca/dashboard.jsp");
            return;
        } catch (SQLException ex) {
            Logger.getLogger(ca_reset.class.getName()).log(Level.SEVERE, null, ex);
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
