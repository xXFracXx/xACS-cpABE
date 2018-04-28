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

import com.google.gson.Gson;

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
			Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_925e3cb61718614", "b45ac8d6e3ff55", "57d84b63");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from file_upload where id = '" + id + "'");
            if (rs.next()) {

                String fname = rs.getString("filename");
                HttpSession session = request.getSession();

                String Cname = (String) session.getAttribute("usr_name");
                String Cmail = (String) session.getAttribute("usr_email");
                String State = (String) session.getAttribute("usr_state");
                String Country = (String) session.getAttribute("usr_country");
                String Status = "No";
                System.out.println("Request is activated" + Cname + State + State + Country + Status);
                
                int colCount = 0;
                
                Connection con = DbConnection.getConnection();
                Statement st = con.createStatement();
                String sql = "SELECT COUNT(*) as colCount FROM request";
                ResultSet rs1 = st.executeQuery(sql);
                while(rs1.next()) {
                	System.out.println("here ----- " + rs1.getString("colCount"));
                	colCount = rs1.getInt("colCount");
                }
                con.close();
                st.close();
                
                colCount = colCount + 1;

                con = DbConnection.getConnection();
                st = con.createStatement();
                sql = "insert into request (name, mail, state, country, fname, status) values ('" + Cname + "','" + Cmail + "','" + State + "','" + Country + "','" + fname + "','" + Status + "')";

                int x = st.executeUpdate(sql);
                con.close();
                st.close();
                
                int n = 0, t = 0;
        		try {
    				Class.forName("com.mysql.jdbc.Driver");
        			con = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_925e3cb61718614", "b45ac8d6e3ff55", "57d84b63");
        			st = con.createStatement();
        			rs = st.executeQuery("select * from acs_info where id = 1");
        			while (rs.next()) {
        				n = Integer.parseInt(rs.getString("n"));
        				t = Integer.parseInt(rs.getString("t"));
        			}
        		} catch (Exception ex) {
        			ex.printStackTrace();
        		}
        		
        		String[] aaArr = new String[]{"aa1reqs", "aa2reqs", "aa3reqs", "aa4reqs", "aa5reqs", "aa6reqs", "aa7reqs", "aa8reqs", "aa9reqs", "aa10reqs"};
        		int ck = 0;
        		for(int i = 0; i < n; i++) {
        			try {
        				Class.forName("com.mysql.jdbc.Driver");
        				con = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_925e3cb61718614", "b45ac8d6e3ff55", "57d84b63");
        				st = con.createStatement();
                        sql = "insert into " + aaArr[i] + " (id, name, mail, state, country, fname, status) values ('" + colCount + "','" + Cname + "','" + Cmail + "','" + State + "','" + Country + "','" + fname + "','" + Status + "')";
        				ck = st.executeUpdate(sql);
        			} catch (Exception ex) {
        				ex.printStackTrace();
        			}
        		}
                
                if (ck != 0) {
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
