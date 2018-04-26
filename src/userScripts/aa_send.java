package userScripts;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import DBcon.DbConnection;
import DBcon.Mail;
import aaScripts.ListGen;
import aaScripts.sendKey;

import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class aa_send
 */
@WebServlet("/aa_send")
public class aa_send extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public aa_send() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("usr_id");

		try {
			Connection con = DbConnection.getConnection();
			Statement st = con.createStatement();
			String id = request.getParameter("id");

			String aaNum = "";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_aa", "xacs", "xacspassword");
				st = con.createStatement();
				rs = st.executeQuery("select * from aalist where AID = '" + user_id + "'");
				while (rs.next()) {
					aaNum = rs.getString("aaNum");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String aaRTable = "aa" + aaNum + "reqs";
			String aaSTable = "aa" + aaNum + "shares";

			try {

				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from request where id = '" + id + "'");
				if (rs.next()) {
					String name = rs.getString("name");
					String email = rs.getString("mail");
					String fname = rs.getString("fname");
					int aCount = rs.getInt("aCount");

					int currCount = aCount + 1;

					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
					stmt = conn.createStatement();
					rs = stmt.executeQuery("select * from acs_info where id = 1");
					rs.next();
					int n = rs.getInt("n");
					int t = rs.getInt("t");
					conn.close();

					String[] aaArr = new String[] { "aa1Share", "aa2Share", "aa3Share", "aa4Share", "aa5Share",
							"aa6Share", "aa7Share", "aa8Share", "aa9Share", "aa10Share" };

					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_aa", "xacs", "xacspassword");
					st = con.createStatement();
					int i = st.executeUpdate("update " + aaRTable + " set status = 'Yes' where id = '" + id + "'");
					if (i == 0) {
						response.sendRedirect("aa/user_requests.jsp?#aaXShareFail");
						return;
					}
					con.close();

					String uid = "";
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
					stmt = conn.createStatement();
					rs = stmt.executeQuery("select audd from reg where name = '" + name + "'");
					while (rs.next()) {
						uid = rs.getString("audd");
					}
					conn.close();

					String subShare = "";
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_aa", "xacs", "xacspassword");
					stmt = conn.createStatement();
					rs = stmt.executeQuery("select subShare from " + aaSTable + " where UID = '" + uid + "'");
					while (rs.next()) {
						subShare = rs.getString("subShare");
					}
					conn.close();

					con = DbConnection.getConnection();
					st = con.createStatement();
					i = st.executeUpdate("update request set aCount= " + currCount + " where id = '" + id + "'");
					if (i == 0) {
						response.sendRedirect("aa/user_requests.jsp?#countFail");
						return;
					}
					con.close();

					con = DbConnection.getConnection();
					st = con.createStatement();
					i = st.executeUpdate("update request set " + aaArr[Integer.parseInt(aaNum) - 1] + " = '" + subShare
							+ "' where id = '" + id + "'");
					if (i == 0) {
						response.sendRedirect("aa/user_requests.jsp?#aaListFail");
						return;
					}
					con.close();

					if (currCount >= t) {

						System.out.println("yus");

						// sendKey sendK = new sendKey();
						// boolean aaSendK = sendK.aaSendKey(id);
						// call aa class

						String emailtemp = "<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>  <title></title>  <!--[if !mso]><!-- -->  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">  <!--<![endif]--><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><style type=\"text/css\">  #outlook a { padding: 0; }  .ReadMsgBody { width: 100%; }  .ExternalClass { width: 100%; }  .ExternalClass * { line-height:100%; }  body { margin: 0; padding: 0; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }  table, td { border-collapse:collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; }  img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; }  p { display: block; margin: 13px 0; }</style><!--[if !mso]><!--><style type=\"text/css\">  @media only screen and (max-width:480px) {    @-ms-viewport { width:320px; }    @viewport { width:320px; }  }</style><!--<![endif]--><!--[if mso]><xml>  <o:OfficeDocumentSettings>    <o:AllowPNG/>    <o:PixelsPerInch>96</o:PixelsPerInch>  </o:OfficeDocumentSettings></xml><![endif]--><!--[if lte mso 11]><style type=\"text/css\">  .outlook-group-fix {    width:100% !important;  }</style><![endif]--><!--[if !mso]><!-->    <link href=\"https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700\" rel=\"stylesheet\" type=\"text/css\">    <style type=\"text/css\">        @import url(https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700);    </style>  <!--<![endif]--><style type=\"text/css\">  @media only screen and (min-width:480px) {    .mj-column-per-100 { width:100%!important; }  }</style></head><body style=\"background: #FFFFFF;\">    <div class=\"mj-container\" style=\"background-color:#FFFFFF;\"><!--[if mso | IE]>      <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" align=\"center\" style=\"width:600px;\">        <tr>          <td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">      <![endif]--><div style=\"margin:0px auto;max-width:600px;\"><table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-size:0px;width:100%;\" align=\"center\" border=\"0\"><tbody><tr><td style=\"text-align:center;vertical-align:top;direction:ltr;font-size:0px;padding:9px 0px 9px 0px;\"><!--[if mso | IE]>      <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">        <tr>          <td style=\"vertical-align:top;width:600px;\">      <![endif]--><div class=\"mj-column-per-100 outlook-group-fix\" style=\"vertical-align:top;display:inline-block;direction:ltr;font-size:13px;text-align:left;width:100%;\"><table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"word-wrap:break-word;font-size:0px;padding:0px 0px 0px 0px;\" align=\"center\"><table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;border-spacing:0px;\" align=\"center\" border=\"0\"><tbody><tr><td style=\"width:600px;\"><img alt=\"\" title=\"\" height=\"auto\" src=\"https://topolio.s3-eu-west-1.amazonaws.com/uploads/5acad6dd0a6b7/1523242931.jpg\" style=\"border:none;border-radius:0px;display:block;font-size:13px;outline:none;text-decoration:none;width:100%;height:auto;\" width=\"600\"></td></tr></tbody></table></td></tr><tr><td style=\"word-wrap:break-word;font-size:0px;padding:0px 20px 0px 20px;\" align=\"left\"><div style=\"cursor:auto;color:#000000;font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:11px;line-height:22px;text-align:left;\"><p><span style=\"font-size:14px;\">Dear "
								+ name + ",</span></p><p><span style=\"font-size:14px;\">File request for <u>" + fname
								+ "</u> has been&#xA0;approved! </span><br><span style=\"font-size:14px;\">Visit xACS to download!</span></p><p><span style=\"font-size:14px;\">Regards,<br>Cloud Admin - xACS</span><br>(This is system generated mail and should not be replied to)</p></div></td></tr></tbody></table></div><!--[if mso | IE]>      </td></tr></table>      <![endif]--></td></tr></tbody></table></div><!--[if mso | IE]>      </td></tr></table>      <![endif]--></div></body></html>";

						Mail mail = new Mail();
						mail.secretMail(emailtemp, name, email, "sk");
						con = DbConnection.getConnection();
						st = con.createStatement();
						i = st.executeUpdate("update request set status= 'Yes' where id = '" + id + "'");
						con.close();
						st.close();
						if (i != 0) {
							response.sendRedirect("aa/user_requests.jsp?#sendSucc");
							return;
						} else {
							response.sendRedirect("aa/user_requests.jsp?#sendFail");
							return;
						}

					}
				} else {
					System.out.println("Error");
					response.sendRedirect("aa/user_requests.jsp?#sendFail");
					return;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("aa/user_requests.jsp?#sqlFail");
			return;

		}

		response.sendRedirect("aa/user_requests.jsp?#countSucc");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
