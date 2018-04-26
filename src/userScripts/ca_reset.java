package userScripts;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.sql.Connection;
import java.sql.DriverManager;
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
		
		//FileUtils.deleteDirectory(new File("D:\\cpabe"));
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_aa", "xacs", "xacspassword");
			Statement st = con.createStatement();
			
			st.executeUpdate("TRUNCATE TABLE aalist");

			String[] aaArr = new String[]{"aa1shares", "aa2shares", "aa3shares", "aa4shares", "aa5shares", "aa6shares", "aa7shares", "aa8shares", "aa9shares", "aa10shares"};
			int n = aaArr.length;
			for(int i = 0; i < n; i++)
				st.executeUpdate("TRUNCATE TABLE " + aaArr[i]);
			
			aaArr = new String[]{"aa1reqs", "aa2reqs", "aa3reqs", "aa4reqs", "aa5reqs", "aa6reqs", "aa7reqs", "aa8reqs", "aa9reqs", "aa10reqs"};
			n = aaArr.length;
			for(int i = 0; i < n; i++)
				st.executeUpdate("TRUNCATE TABLE " + aaArr[i]);
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
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
