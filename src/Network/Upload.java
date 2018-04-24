package Network;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpSession;
import DBcon.DbConnection;
import DBcon.Ftpcon;
import algo.encryption;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
    File file;
    final String filepath = "D:/ACSUploads/";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
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
        HttpSession user = request.getSession(true);
        String current = user.getAttribute("usr_role").toString().toLowerCase();
        int maxSize = 5242880;
        try {
            
            MultipartRequest m = new MultipartRequest(request, filepath, maxSize);
            String pkey = m.getParameter("public");
            File file = m.getFile("file");
            String filename = file.getName().toLowerCase();
            
            Connection con = DbConnection.getConnection();
            Statement st3 = con.createStatement();
            ResultSet rt3 = st3.executeQuery("select * from file_upload where filename='" + filename + "'");
            if (rt3.next()) {
                response.sendRedirect(current + "/file_upload.jsp?#dupFail");
                return;
            } else {
                
                BufferedReader br = new BufferedReader(new FileReader(filepath + filename));
                StringBuffer sb = new StringBuffer();
                String temp = null;
                
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
                
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                SecretKey secretKey = keyGen.generateKey();
                System.out.println("secret key:" + secretKey);
                
                encryption e = new encryption();
                String CipherText = e.encrypt(sb.toString(), secretKey);
                FileWriter fw = new FileWriter(file);
                fw.write(CipherText);
                fw.close();
                
                byte[] b = secretKey.getEncoded();
                String skey = Base64.encode(b);
                System.out.println("converted secretkey to string:" + skey);
                
                String owner = user.getAttribute("usr_name").toString();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String time = dateFormat.format(date);
                
                boolean status = new Ftpcon().upload(file);
                //status = true; //CHANGE ME FOR SURE !!!
                if (status) {
                    Statement st = con.createStatement();
                    int i = st.executeUpdate("insert into file_upload(filename,content,owner,time,secret_key,public_key)values('" + file.getName() + "','" + CipherText + "','" + owner + "','" + time + "','" + skey + "','" + pkey + "')");
                    System.out.println(i);
                    if (i != 0) {
                        response.sendRedirect(current + "/file_upload.jsp?#uploadSucc");
                        return;
                    } else {
                        response.sendRedirect(current + "/file_upload.jsp?#uploadFail");
                        return;
                    }
                } else {
                    response.sendRedirect(current + "/file_upload.jsp?#ftpFail");
                    return;
                }
            }
        } catch (Exception e) {
            out.println(e);
            response.sendRedirect(current + "/file_upload.jsp?#sizeFail");
            return;
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
