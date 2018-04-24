package userAuth;

import java.io.IOException;
import DBcon.DbConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class otpScript
 */
@WebServlet("/otpScript")
public class otpScript extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public otpScript() {
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
        ResultSet rs = null, rs2 = null;

        int Status = Integer.parseInt(request.getParameter("status"));
        String Role;
        switch (Status) {
            case 1:
                Role = "Owner";
                break;
            case 2:
                Role = "User";
                break;
            case 4:
                Role = "AA";
                break;
            default:
                Role = "other";
                break;
        }

        String dataArr[] = new String[9];
        dataArr[0] = request.getParameter("name");
        dataArr[1] = request.getParameter("pass");
        dataArr[2] = request.getParameter("email");
        dataArr[3] = request.getParameter("dob");
        dataArr[4] = request.getParameter("gen");
        dataArr[5] = Role;
        dataArr[6] = request.getParameter("state");
        dataArr[7] = request.getParameter("country");
        dataArr[8] = request.getParameter("phone");

        if (Role.equals("AA")) {
            try {
                con = DbConnection.getConnection();
				st = con.createStatement();
	            rs = st.executeQuery("select * from acs_info where id = 1");
	            rs.next();
	            int n = rs.getInt("n");
	            rs2 = st.executeQuery("select count(*) as aaCount from reg where role='AA'");
	            rs2.next();
	            int aaCount = rs2.getInt("aaCount");
	            if (aaCount >= n) {
	                response.sendRedirect("signup.jsp#aaFail");
	                return;
	            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        HttpSession session = request.getSession();
        session.setAttribute("dataArray", dataArr);

        String number = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        int len = 8;

        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = number.charAt(rndm_method.nextInt(number.length()));
        }

        String otpmsg = new String(otp);

        session.setAttribute("otp", otpmsg);
        
        boolean otpTest = false;

        if (otpTest) {

            try {
                // Construct data
                String apiKey = "apikey=" + "G7M7GCuD6sM-c1j0R7LEyZivGmHfR9RE3OsusSkbH1";
                String message = "&message=" + "xACS OTP: " + otpmsg;
                String sender = "&sender=" + "TXTLCL";
                String numbers = "&numbers=" + dataArr[8];

                // Send data
                HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                String data = apiKey + numbers + message + sender;
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                conn.getOutputStream().write(data.getBytes("UTF-8"));
                final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                }
                rd.close();

                System.out.println(stringBuffer.toString());

            } catch (Exception e) {
                System.out.println("Error SMS " + e);
                //return "Error " + e;
            }

        } else {
            System.out.println(otpmsg);
        }

        response.sendRedirect("otp.jsp");
        return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
