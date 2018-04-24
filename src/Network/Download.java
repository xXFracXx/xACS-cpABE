package Network;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import DBcon.DbConnection;
import algo.decryption;

/**
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String fileid = request.getQueryString();
            String fname = null, fkey = null;
            InputStream is = null;
            Connection con = DbConnection.getConnection();
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            //HttpSession user = request.getSession();
            HttpSession session = request.getSession(true);
            String uname = (String) session.getAttribute("usr_name");
            ResultSet rt = st.executeQuery("select * from file_upload where id='" + fileid + "'");
            if (rt.next()) {
                fname = rt.getString("filename");
                fkey = rt.getString("secret_key");
                is = (InputStream) rt.getAsciiStream("content");
            } else {
                out.println("error while retreiving data");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String temp = null;
            StringBuffer sb = new StringBuffer();
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            String content = new decryption().decrypt(sb.toString(), fkey);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fname + "\"");
            out.write(content);
            Calendar cal = Calendar.getInstance();
            DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String time = dateformat.format(cal.getTime());
            System.out.println("download time:" + time);
            st1.executeUpdate("insert into download (filename,username,time)values('" + fname + "','" + uname + "','" + time + "')");
        } catch (Exception e) {
            //out.println(e);
        } finally {
            out.close();
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
