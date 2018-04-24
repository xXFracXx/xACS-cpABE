package userScripts;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Random;
import DBcon.Mail;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import DBcon.DbConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ca_activate
 */
@WebServlet("/ca_activate")
public class ca_activate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ca_activate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
        String url = "jdbc:mysql://localhost:3306/xacs_db";
        String user = "xacs";
        String password = "xacspassword";
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "Select *  from reg where id = '" + id + "' ";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String name = result.getString("name");
                String email = result.getString("email");
                String type = result.getString("role").toLowerCase();
                session.setAttribute("Email", email);
                Random RANDOM = new SecureRandom();
                int PASSWORD_LENGTH = 4;
                String letters = "0123456789";
                String ID = "";
                for (int i = 0; i < PASSWORD_LENGTH; i++) {
                    int index = (int) (RANDOM.nextDouble() * letters.length());
                    ID += letters.substring(index, index + 1);
                }
                String idStr = "";
                if (type.equals("aa")) {
                    idStr = "AID";
                } else if (type.equals("user")) {
                    idStr = "UID";
                } else {
                    idStr = "ErrID";
                }
                String msg = idStr + ID;

                String emailtemp = "<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>  <title></title>  <!--[if !mso]><!-- -->  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">  <!--<![endif]--><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><style type=\"text/css\">  #outlook a { padding: 0; }  .ReadMsgBody { width: 100%; }  .ExternalClass { width: 100%; }  .ExternalClass * { line-height:100%; }  body { margin: 0; padding: 0; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }  table, td { border-collapse:collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; }  img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; }  p { display: block; margin: 13px 0; }</style><!--[if !mso]><!--><style type=\"text/css\">  @media only screen and (max-width:480px) {    @-ms-viewport { width:320px; }    @viewport { width:320px; }  }</style><!--<![endif]--><!--[if mso]><xml>  <o:OfficeDocumentSettings>    <o:AllowPNG/>    <o:PixelsPerInch>96</o:PixelsPerInch>  </o:OfficeDocumentSettings></xml><![endif]--><!--[if lte mso 11]><style type=\"text/css\">  .outlook-group-fix {    width:100% !important;  }</style><![endif]--><!--[if !mso]><!-->    <link href=\"https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700\" rel=\"stylesheet\" type=\"text/css\">    <style type=\"text/css\">        @import url(https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700);    </style>  <!--<![endif]--><style type=\"text/css\">  @media only screen and (min-width:480px) {    .mj-column-per-100 { width:100%!important; }  }</style></head><body style=\"background: #FFFFFF;\">    <div class=\"mj-container\" style=\"background-color:#FFFFFF;\"><!--[if mso | IE]>      <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" align=\"center\" style=\"width:600px;\">        <tr>          <td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\">      <![endif]--><div style=\"margin:0px auto;max-width:600px;\"><table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-size:0px;width:100%;\" align=\"center\" border=\"0\"><tbody><tr><td style=\"text-align:center;vertical-align:top;direction:ltr;font-size:0px;padding:9px 0px 9px 0px;\"><!--[if mso | IE]>      <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">        <tr>          <td style=\"vertical-align:top;width:600px;\">      <![endif]--><div class=\"mj-column-per-100 outlook-group-fix\" style=\"vertical-align:top;display:inline-block;direction:ltr;font-size:13px;text-align:left;width:100%;\"><table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"word-wrap:break-word;font-size:0px;padding:0px 0px 0px 0px;\" align=\"center\"><table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;border-spacing:0px;\" align=\"center\" border=\"0\"><tbody><tr><td style=\"width:600px;\"><img alt=\"\" title=\"\" height=\"auto\" src=\"https://topolio.s3-eu-west-1.amazonaws.com/uploads/5acad6dd0a6b7/1523242931.jpg\" style=\"border:none;border-radius:0px;display:block;font-size:13px;outline:none;text-decoration:none;width:100%;height:auto;\" width=\"600\"></td></tr></tbody></table></td></tr><tr><td style=\"word-wrap:break-word;font-size:0px;padding:0px 20px 0px 20px;\" align=\"left\"><div style=\"cursor:auto;color:#000000;font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:11px;line-height:22px;text-align:left;\"><p><span style=\"font-size:14px;\">Dear " + name + ",</span></p><p><span style=\"font-size:14px;\">Welcome to xACS! Your username&#xA0;is<b>&#xA0;</b></span><span style=\"font-size:14px;\"><strong>" + msg + "</strong>.</span></p><p><span style=\"font-size:14px;\">Regards,<br>Cloud Admin - xACS</span><br>(This is system generated mail and should not be replied to)</p></div></td></tr></tbody></table></div><!--[if mso | IE]>      </td></tr></table>      <![endif]--></td></tr></tbody></table></div><!--[if mso | IE]>      </td></tr></table>      <![endif]--></div></body></html>";

                //session.setAttribute("Keyy", msg);
                Mail m = new Mail();
                m.secretMail(emailtemp, name, email, "activate");
                String j = request.getQueryString();
                System.out.println(j);
                Connection con = DbConnection.getConnection();
                Statement st = con.createStatement();
                String u_email = (String) session.getAttribute("Email");
                String key_uid = msg;
                int i = st.executeUpdate("update reg set status = 'Yes', audd='" + key_uid + "' where email = '" + u_email + "'");
                if (i != 0) {
                    response.sendRedirect("ca/auth_pending.jsp#authSucc");
                    return;
                } else {
                    response.sendRedirect("ca/auth_pending.jsp#sqlFail");
                    return;
                }
            } else {
                response.sendRedirect("ca/auth_pending.jsp#sqlFail");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ca_activate.class.getName()).log(Level.SEVERE, null, ex);
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
