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

import cn.edu.pku.ss.crypto.abe.api.*;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();
		HttpSession userReq = request.getSession(true);
		String current = userReq.getAttribute("usr_role").toString().toLowerCase();
		int maxSize = 5242880;

		String pkey = userReq.getAttribute("pkey").toString();
		String mkey = userReq.getAttribute("mkey").toString();

		try {

			MultipartRequest m = new MultipartRequest(request, filepath, maxSize);
			//String pkey = m.getParameter("public"); //OLD METHOD pKey
			File file = m.getFile("file");
			String filename = file.getName().toLowerCase();

			String nameID = m.getParameter("nameID");
			String emailID = m.getParameter("emailID");
			String state = m.getParameter("state");
			String country = m.getParameter("country");
			String authLvl = m.getParameter("authLvl");

			int pCount = 0;
			String[] pArr = new String[5];

			if (!(nameID.isEmpty())) {
				pArr[pCount] = nameID;
				pCount++;
			}
			if (!(emailID.isEmpty())) {
				pArr[pCount] = emailID;
				pCount++;
			}
			if (!(state.isEmpty())) {
				pArr[pCount] = state;
				pCount++;
			}
			if (!(country.isEmpty())) {
				pArr[pCount] = country;
				pCount++;
			}
			if (!(authLvl.isEmpty())) {
				pArr[pCount] = authLvl;
				pCount++;
			}

			String policy = "";
			policy = Integer.toString(pCount) + " of (";
			for (int i = 0; i < pCount; i++) {
				policy += pArr[i];
				if (!(i == pCount - 1)) {
					policy += ",";
				}
			}
			policy += ")";
			System.out.println(policy);

			Connection con = DbConnection.getConnection();
			Statement st3 = con.createStatement();
			ResultSet rt3 = st3.executeQuery("select * from file_upload where filename='" + filename + "'");
			if (rt3.next()) {
				response.sendRedirect(current + "/file_upload.jsp?#dupFail");
				return;
			} else {

				/*
				 * BufferedReader br = new BufferedReader(new FileReader(filepath + filename));
				 * StringBuffer sb = new StringBuffer(); String temp = null;
				 * 
				 * while ((temp = br.readLine()) != null) { sb.append(temp); }
				 * 
				 * KeyGenerator keyGen = KeyGenerator.getInstance("AES"); keyGen.init(128);
				 * SecretKey secretKey = keyGen.generateKey(); System.out.println("secret key:"
				 * + secretKey);
				 * 
				 * encryption e = new encryption(); String CipherText = e.encrypt(sb.toString(),
				 * secretKey); FileWriter fw = new FileWriter(file); fw.write(CipherText);
				 * fw.close();
				 * 
				 * byte[] b = secretKey.getEncoded(); String skey = Base64.encode(b);
				 * System.out.println("converted secretkey to string:" + skey);
				 */

				File ciphertextFile;
				HttpSession session = request.getSession();
				String _userName = session.getAttribute("usr_name").toString();
				_userName = _userName.replaceAll("\\s+", "");
				String ciphertextFileName = "cpabe/" + _userName + "/" + file.getName() + ".cpabe";
				String PKFileName = "cpabe/" + _userName + "/PKFile";
				ciphertextFile = CPABE.enc(file, policy, ciphertextFileName, PKFileName);
				
				String MKFileName = "cpabe/" + _userName + "/MKFile";
				File pkFile = new File(PKFileName);
				File mkFile = new File(MKFileName);

				String owner = userReq.getAttribute("usr_name").toString();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				String time = dateFormat.format(date);

				boolean status = new Ftpcon().upload(ciphertextFile);
				// status = true; //CHANGE ME FOR SURE !!!
				if (status) {
					Statement st = con.createStatement();
					int i = st.executeUpdate(
							"insert into file_upload(filename,content,owner,time,master_key,public_key,PKFile,MKFile)values('"
									+ file.getName() + "','" + ciphertextFile + "','" + owner + "','" + time + "','"
									+ mkey + "','" + pkey + "','" + pkFile + "','" + mkFile + "')");
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
			//response.sendRedirect(current + "/file_upload.jsp?#sizeFail");
			//return;
		} finally {
			out.close();
		}
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
