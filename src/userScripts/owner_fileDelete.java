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
        	
        	Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_925e3cb61718614", "b45ac8d6e3ff55", "57d84b63");
			Statement st = con.createStatement();
			
			st.executeUpdate("TRUNCATE TABLE aalist");
			
			con = DbConnection.getConnection();
            st = con.createStatement();
            String fname = "";
            ResultSet rs = st.executeQuery("SELECT filename FROM file_upload WHERE id = " + id + " and owner = \"" + owner + "\"");
            while(rs.next()) {
            	fname = rs.getString("filename");
            }
        	
            con = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_925e3cb61718614", "b45ac8d6e3ff55", "57d84b63");
			st = con.createStatement();
        	String[] aaArr = new String[]{"aa1reqs", "aa2reqs", "aa3reqs", "aa4reqs", "aa5reqs", "aa6reqs", "aa7reqs", "aa8reqs", "aa9reqs", "aa10reqs"};
			int n = aaArr.length;
			for(int i = 0; i < n; i++)
				st.executeUpdate("DELETE FROM " + aaArr[i] + " WHERE fname = \"" + fname + "\"");
        	
			con = DbConnection.getConnection();
            st = con.createStatement();
            st.executeUpdate("DELETE FROM request WHERE fname = \"" + fname + "\"");
			
            con = DbConnection.getConnection();
            st = con.createStatement();
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
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
