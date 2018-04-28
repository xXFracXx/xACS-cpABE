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
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import DBcon.DbConnection;
import DBcon.Ftpcon;
import algo.encryption;
import cn.edu.pku.ss.crypto.abe.PublicKey;
import cn.edu.pku.ss.crypto.abe.api.CPABE;
import cn.edu.pku.ss.crypto.abe.serialize.SerializeUtils;
import xacs.keyFix;

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
		
		if (new File("cpabe/owner").exists()) {
			File userFld = new File("cpabe/owner");
			FileUtils.deleteDirectory(userFld);
		}

		PrintWriter out = response.getWriter();
		HttpSession userReq = request.getSession(true);
		String current = userReq.getAttribute("usr_role").toString().toLowerCase();
		int maxSize = 5242880;

		MultipartRequest m = new MultipartRequest(request, filepath, maxSize);
		file = m.getFile("file");
		//file = new File("C:\\Users/Shashank Pincha/Desktop/Test Data/John/JohnImpNumb.txt");
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
//
//		int[] intArray = new int[16];
//		int j = 0;
//		String s = aesKey;
//		int strLength = s.length();
//		if (strLength != 16) {
//			System.out.println("Not a valid length");
//		} else {
//			for (j = 0; j < 16; j++) {
//				if (!Character.isDigit(s.charAt(j))) {
//					System.out.println("Contains an invalid digit");
//					break;
//				}
//				intArray[j] = Integer.parseInt(String.valueOf(s.charAt(j)));
//			}
//		}
//		System.out.println(Arrays.toString(intArray));

		Path path = Paths.get("cpabe");
		Files.createDirectories(path);

		path = Paths.get("cpabe/owner");
		Files.createDirectories(path);
		

		
		
		
		
		
		
//		byte[] block = new byte[8];
//		int iSK = 0;
//
//		try {
//			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//			// SecretKey secKey = keyGen.generateKey();
//			byte[] b = new byte[16];
//			for (int i = 0; i < b.length; i++) {
//				b[i] = (byte) intArray[i];
//			}
//			System.out.println(Arrays.toString(b));
//			SecretKey secKey = new SecretKeySpec(b, "AES");
//
//			// Encrypt
//
//			cipher.init(Cipher.ENCRYPT_MODE, secKey);
//
//			// String cleartextFile = "README.md";
//			String ciphertextFile = "D:\\cpabe/" + current + "/encFile";
//
//			FileInputStream fis = new FileInputStream(file);
//			FileOutputStream fos1 = new FileOutputStream(ciphertextFile);
//			CipherOutputStream cos = new CipherOutputStream(fos1, cipher);
//
//			while ((iSK = fis.read(block)) != -1) {
//				cos.write(block, 0, iSK);
//			}
//			cos.close();
//		} catch (NoSuchAlgorithmException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (NoSuchPaddingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (InvalidKeyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String ivFileName = "cpabe/" + current + "/iv.enc";
		String saltFileName = "cpabe/" + current + "/salt.enc";
		String ciphertextFile = "cpabe/" + current + "/encFile.des";
		
		try {
		
			//keyFix.fixKeyLength();
		
		// file to be encrypted
				FileInputStream inFile = new FileInputStream(file);

				// encrypted file
				FileOutputStream outFile = new FileOutputStream(ciphertextFile);

				// password to encrypt the file
				String password = aesKey;

				// password, iv and salt should be transferred to the other end
				// in a secure manner

				// salt is used for encoding
				// writing it to a file
				// salt should be transferred to the recipient securely
				// for decryption
				byte[] salt = new byte[8];
				SecureRandom secureRandom = new SecureRandom();
				secureRandom.nextBytes(salt);
				FileOutputStream saltOutFile = new FileOutputStream(saltFileName);
				saltOutFile.write(salt);
				saltOutFile.close();

				SecretKeyFactory factory = SecretKeyFactory
						.getInstance("PBKDF2WithHmacSHA1");
				KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
						128);
				SecretKey secretKey = factory.generateSecret(keySpec);
				SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

				//
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, secret);
				AlgorithmParameters params = cipher.getParameters();

				// iv adds randomness to the text and just makes the mechanism more
				// secure
				// used while initializing the cipher
				// file to store the iv
				FileOutputStream ivOutFile = new FileOutputStream(ivFileName);
				byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
				ivOutFile.write(iv);
				ivOutFile.close();

				//file encryption
				byte[] input = new byte[64];
				int bytesRead;

				while ((bytesRead = inFile.read(input)) != -1) {
					byte[] output = cipher.update(input, 0, bytesRead);
					if (output != null)
						outFile.write(output);
				}

				byte[] output = cipher.doFinal();
				if (output != null)
					outFile.write(output);

				inFile.close();
				outFile.flush();
				outFile.close();

				System.out.println("File Encrypted.");
				
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		
		
		PrintWriter writer = new PrintWriter("cpabe/" + current + "/aesKey", "UTF-8");
		writer.print(aesKey);
		writer.close();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");

			File file2 = new File("cpabe/" + current + "/PKFile");
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

		String policy = "";

		String[] attrArr = new String[5];

		attrArr[0] = "globalAttr";
		attrArr[1] = nameID;
		attrArr[2] = emailID;
		attrArr[3] = state;
		attrArr[4] = country;

		String midP = "";
		int aCount = 0;
		for (int i = 0; i < 5; i++) {
			if (!(attrArr[i].isEmpty())) {
				aCount++;
				midP += attrArr[i];
				if (!(attrArr[i + 1].isEmpty()))
					midP += ",";
			}
		}
		
		policy = String.valueOf(aCount) + " of (" + midP + ")";

		System.out.println("p - " + policy);

		String encFileName = "cpabe/owner/aesKey";
		String ciphertextFileName = "cpabe/owner/encAesKey";
		String PKFileName = "cpabe/owner/PKFile";

		CPABE.enc(encFileName, policy, ciphertextFileName, PKFileName);
		

		String ciphertextFileNEW = "cpabe/" + current + "/encFile.des";

		File encFile = new File(ciphertextFileNEW);
		FileInputStream fis1 = new FileInputStream(encFile);

		File encAesKey = new File(ciphertextFileName);
		FileInputStream fis2 = new FileInputStream(encAesKey);
		
		File ivFile = new File(ivFileName);
		FileInputStream fis3 = new FileInputStream(ivFile);
		
		File saltFile = new File(saltFileName);
		FileInputStream fis4 = new FileInputStream(saltFile);

		try {

			String owner = userReq.getAttribute("usr_name").toString();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String time = dateFormat.format(date);

			boolean status = new Ftpcon().upload(file);
			// status = true; //CHANGE ME FOR SURE !!!
			if (status) {	

				String SQLurl = "jdbc:mysql://localhost:3306/xacs_db";
				String user = "xacs";
				String password2 = "xacspassword";
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(SQLurl, user, password2);
				String sql = "insert into file_upload (filename, encFile, owner, time, encAesKey, iv, salt) values ('"
						+ file.getName() + "', ?,'" + owner + "','" + time + "', ?, ?, ?)";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setBinaryStream(1, fis1, (int) encFile.length());
				statement.setBinaryStream(2, fis2, (int) encAesKey.length());
				statement.setBinaryStream(3, fis3, (int) ivFile.length());
				statement.setBinaryStream(4, fis4, (int) saltFile.length());
				int o = statement.executeUpdate();
				conn.close();
				
				fis1.close();
				fis2.close();
				fis3.close();
				fis4.close();

				// con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db",
				// "xacs", "xacspassword");
				// Statement st = con.createStatement();
				// int o = st.executeUpdate("insert into file_upload
				// (filename,encFile,owner,time,encAesKey) values ('" + file.getName() + "','" +
				// encFile + "','" + owner + "','" + time + "','" + encAesKey + "')");

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
