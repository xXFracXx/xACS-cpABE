package userScripts;

import DBcon.DbConnection;
import aaScripts.policyTest;
import aaScripts.sendKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class user_fileDownload
 */
@WebServlet("/user_fileDownload")
public class user_fileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public user_fileDownload() {
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

		// Connection con = null;
		// Statement st = null;
		// ResultSet rs = null;
		// String fileName = "";

		if (new File("D:\\cpabe/user").exists()) {
			File userFld = new File("D:\\cpabe/user");
			FileUtils.deleteDirectory(userFld);
		}

		// con = DbConnection.getConnection();

		String id = request.getParameter("id");

		// try {
		// Class.forName("com.mysql.jdbc.Driver");
		// Connection conn =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs",
		// "xacspassword");
		// Statement stmt = conn.createStatement();
		// rs = stmt.executeQuery("select * from request where id = '" + id + "'");
		// while (rs.next()) {
		// fileName = rs.getString("fname");
		// }
		// } catch (ClassNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		HttpSession session = request.getSession();

		sendKey sendK = new sendKey();
		boolean aaSendK = false;
		try {
			aaSendK = sendK.aaSendKey(id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			aaSendK = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			aaSendK = false;
		} catch (java.lang.NullPointerException npe) {
			aaSendK = false;
		}

		System.out.println("Test");

		//aaSendK = false;
		if (aaSendK) {

			System.out.println("Test2");

			Scanner scanner = new Scanner(Paths.get("D:\\cpabe/user/encAesKeydec"));
			String content = scanner.useDelimiter("\\A").next();
			scanner.close();

			System.out.println(content);

			String aesKey = content.substring(0, 16);

			System.out.println(aesKey);

			// byte[] b = new byte[16];
			// for (int i = 0; i < b.length; i++) {
			// b[i] = (byte) i;
			// }
			// System.out.println(Arrays.toString(b));
			// SecretKey secKey = new SecretKeySpec(b, "AES");
			//
			// String cleartextAgainFile = fileName;
			//
			// cipher.init(Cipher.DECRYPT_MODE, secKey);
			//
			// fis = new FileInputStream(ciphertextFile);
			// CipherInputStream cis = new CipherInputStream(fis, cipher);
			// fos = new FileOutputStream(cleartextAgainFile);
			//
			// while ((i = cis.read(block)) != -1) {
			// fos.write(block, 0, i);
			// }
			// fos.close();
		} else {
			response.sendRedirect("user/file_download.jsp#dlFail");
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
