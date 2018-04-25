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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import DBcon.DbConnection;
import DBcon.Ftpcon;
import algo.encryption;
import cn.edu.pku.ss.crypto.abe.PublicKey;
import cn.edu.pku.ss.crypto.abe.api.CPABE;
import cn.edu.pku.ss.crypto.abe.serialize.SerializeUtils;

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

		MultipartRequest m = new MultipartRequest(request, filepath, maxSize);
		file = m.getFile("file");
		String filename = file.getName().toLowerCase();

		String nameID = m.getParameter("nameID");
		String emailID = m.getParameter("emailID");
		String state = m.getParameter("state");
		String country = m.getParameter("country");

		String aesKey = m.getParameter("aesKey");
		
		Connection con = null;
		try {
			con = DbConnection.getConnection();
			Statement st3 = con.createStatement();
			ResultSet rt3 = st3.executeQuery("select * from file_upload where filename='" + filename + "'");
			if (rt3.next()) {
				response.sendRedirect(current + "/file_upload.jsp?#dupFail");
				return;
			} 
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		int[] intArray = new int[16];
		int j = 0;
		String s = aesKey;
		int strLength = s.length();
		if (strLength != 16) {
			System.out.println("Not a valid length");
		} else {
			for (j = 0; j < 16; j++) {
				if (!Character.isDigit(s.charAt(j))) {
					System.out.println("Contains an invalid digit");
					break;
				}
				intArray[j] = Integer.parseInt(String.valueOf(s.charAt(j)));
			}
		}
		System.out.println(Arrays.toString(intArray));

		Path path = Paths.get("D:\\cpabe");
		Files.createDirectories(path);

		path = Paths.get("D:\\cpabe/owner");
		Files.createDirectories(path);

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			// SecretKey secKey = keyGen.generateKey();
			byte[] b = new byte[16];
			for (int i = 0; i < b.length; i++) {
				b[i] = (byte) intArray[i];
			}
			System.out.println(Arrays.toString(b));
			SecretKey secKey = new SecretKeySpec(b, "AES");

			// Encrypt

			cipher.init(Cipher.ENCRYPT_MODE, secKey);

			// String cleartextFile = "README.md";
			String ciphertextFile = "D:\\cpabe/" + current + "/encFile";

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos1 = new FileOutputStream(ciphertextFile);
			CipherOutputStream cos = new CipherOutputStream(fos1, cipher);

			byte[] block = new byte[8];
			int i;
			while ((i = fis.read(block)) != -1) {
				cos.write(block, 0, i);
			}
			cos.close();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter writer = new PrintWriter("D:\\cpabe/" + current + "/aesKey", "UTF-8");
		writer.print(aesKey);
		writer.close();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");

			File file2 = new File("D:\\cpabe/" + current + "/PKFile");
			FileOutputStream fos = new FileOutputStream(file2);
			byte b1[];
			Blob blob;

			PreparedStatement ps = con.prepareStatement("select * from acs_info where id = 1");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				blob = rs.getBlob("PKFile");
				b1 = blob.getBytes(1, (int) blob.length());
				fos.write(b1);
			}

			ps.close();
			fos.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String PKFileName = "D:\\cpabe/owner/PKFile";
		PublicKey PK = SerializeUtils.unserialize(PublicKey.class, new File(PKFileName));
		
		byte[] b1 = SerializeUtils.convertToByteArray(PK);
        String pkey = Base64.encode(b1);
        System.out.println("[PK from CA] " + pkey);

		String p1 = "", p2 = " of (", p3 = "";
		int aCount = 0;

		if (!(nameID.isEmpty())) {
			aCount++;
			p3 += nameID;
			if (aCount < 4 && aCount > 1)
				p3 += ",";
		}
		if (!(emailID.isEmpty())) {
			aCount++;
			p3 += emailID;
			if (aCount < 4 && aCount > 1)
				p3 += ",";
		}
		if (!(state.isEmpty())) {
			aCount++;
			p3 += state;
			if (aCount < 4 && aCount > 1)
				p3 += ",";
		}
		if (!(country.isEmpty())) {
			aCount++;
			p3 += country;
			if (aCount < 4 && aCount > 1)
				p3 += ",";
		}

		if (aCount == 0) {
			p3 = "a";
		}

		p1 = String.valueOf(aCount);
		String policy = p1 + p2 + p3 + ")";

		System.out.println("p - " + policy);

		String encFileName = "D:\\cpabe/owner/aesKey";
		String ciphertextFileName = "D:\\cpabe/owner/encAesKey";

		CPABE.enc(encFileName, policy, ciphertextFileName, PKFileName);

		File encFile = new File(encFileName);
		File encAesKey = new File(encFileName);

		try {

			String owner = userReq.getAttribute("usr_name").toString();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String time = dateFormat.format(date);

			boolean status = new Ftpcon().upload(file);
			// status = true; //CHANGE ME FOR SURE !!!
			if (status) {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
				Statement st = con.createStatement();
				int o = st.executeUpdate("insert into file_upload (filename,encFile,owner,time,encAesKey) values ('"
						+ file.getName() + "','" + encFile + "','" + owner + "','" + time + "','" + encAesKey + "')");
				System.out.println(o);
				if (o != 0) {
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
		} catch (Exception e) {
			System.out.println(e);
			response.sendRedirect(current + "/file_upload.jsp?#sizeFail");
			return;
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
