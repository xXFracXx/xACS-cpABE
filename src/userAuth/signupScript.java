package userAuth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBcon.DbConnection;
import java.sql.Statement;
import java.sql.Connection;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class signupScript
 */
@WebServlet("/signupScript")
public class signupScript extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signupScript() {
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

        String otp = request.getParameter("otp");

        HttpSession session = request.getSession();

        String OGotp = (String) session.getAttribute("otp");

        if (OGotp.equals(otp)) {
            String[] dataArr = (String[]) session.getAttribute("dataArray");

            String name = dataArr[0];
            String pass = dataArr[1];
            String Eamil = dataArr[2];
            String dob = dataArr[3];
            String Gender = dataArr[4];
            String Role = dataArr[5];
            String State = dataArr[6];
            String Country = dataArr[7];
            String phone = dataArr[8];

            session.invalidate();

            try {
                con = DbConnection.getConnection();
                st = con.createStatement();
                int i = st.executeUpdate("insert into reg(name, pass, email, dob, gen, role, state, country, status, phone) values ('" + name + "','" + pass + "','" + Eamil + "','" + dob + "','" + Gender + "','" + Role + "','" + State + "','" + Country + "', 'No', '" + phone + "')");
                if (i != 0) {
                    response.sendRedirect("index.jsp#signupSucc");
                    return;
                } else {
                    response.sendRedirect("signup.jsp#signupFail");
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                response.sendRedirect("signup.jsp#SQLfail");
                return;
            }
        } else {
            response.sendRedirect("signup.jsp#otpFail");
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
