package userScripts;

import DBcon.DbConnection;
import aaScripts.sendKey;
import xacs.keyFix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		// Connection con = null;
		// Statement st = null;
		// ResultSet rs = null;
		String fileName = "";

		if (new File("cpabe/user").exists()) {
			File userFld = new File("cpabe/user");
			FileUtils.deleteDirectory(userFld);
		}
		
		Path path = Paths.get("cpabe");
		Files.createDirectories(path);

		path = Paths.get("cpabe/user");
		Files.createDirectories(path);

		path = Paths.get("cpabe/user/dl");
		Files.createDirectories(path);

		//keyFix.fixKeyLength();

		// con = DbConnection.getConnection();

		String id = request.getParameter("id");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_925e3cb61718614", "b45ac8d6e3ff55", "57d84b63");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from request where id = '" + id + "'");
			while (rs.next()) {
				fileName = rs.getString("fname");
			}
			conn.close();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		// aaSendK = false;
		if (aaSendK) {

			System.out.println("Test2");

			Scanner scanner = new Scanner(Paths.get("cpabe/user/encAesKeydec"));
			String content = scanner.useDelimiter("\\A").next();
			scanner.close();

			System.out.println(content);

			String aesKey = content.substring(0, 16);

			System.out.println(aesKey);

			File file2 = new File("cpabe/user/encFile.des");
			File file3 = new File("cpabe/user/iv.enc");
			File file4 = new File("cpabe/user/salt.enc");
			FileOutputStream fos1 = new FileOutputStream(file2);
			FileOutputStream fos2 = new FileOutputStream(file3);
			FileOutputStream fos3 = new FileOutputStream(file4);

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_925e3cb61718614", "b45ac8d6e3ff55", "57d84b63");
				byte b1[];
				byte b2[];
				byte b3[];
				Blob blob1;
				Blob blob2;
				Blob blob3;

				PreparedStatement ps = con.prepareStatement("select * from file_upload where id = " + id);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					blob1 = rs.getBlob("encFile");
					blob2 = rs.getBlob("iv");
					blob3 = rs.getBlob("salt");
					b1 = blob1.getBytes(1, (int) blob1.length());
					fos1.write(b1);
					b2 = blob2.getBytes(1, (int) blob2.length());
					fos2.write(b2);
					b3 = blob3.getBytes(1, (int) blob3.length());
					fos3.write(b3);
				}

				ps.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			fos1.close();
			fos2.close();
			fos3.close();
			
			String ogFileName = "cpabe/user/dl/" + fileName;

			try {

				String ivFileName = "cpabe/user/iv.enc";
				String saltFileName = "cpabe/user/salt.enc";
				String ciphertextFile = "cpabe/user/encFile.des";

				String password = aesKey;

				// reading the salt
				// user should have secure mechanism to transfer the
				// salt, iv and password to the recipient
				FileInputStream saltFis = new FileInputStream(saltFileName);
				byte[] salt = new byte[8];
				saltFis.read(salt);
				saltFis.close();

				// reading the iv
				FileInputStream ivFis = new FileInputStream(ivFileName);
				byte[] iv = new byte[16];
				ivFis.read(iv);
				ivFis.close();

				SecretKeyFactory factory;
				factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
				KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
				SecretKey tmp = factory.generateSecret(keySpec);
				SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

				// file decryption
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
				FileInputStream fis = new FileInputStream(ciphertextFile);
				FileOutputStream fos = new FileOutputStream(ogFileName);
				byte[] in = new byte[64];
				int read;
				while ((read = fis.read(in)) != -1) {
					byte[] output = cipher.update(in, 0, read);
					if (output != null)
						fos.write(output);
				}

				byte[] output = cipher.doFinal();
				if (output != null)
					fos.write(output);
				fis.close();
				fos.flush();
				fos.close();
				System.out.println("File Decrypted.");

			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename=" + fileName);
			
			File file = new File(ogFileName);
			FileInputStream fileIn = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();

			byte[] outputByte = new byte[4096];
			//copy binary contect to output stream
			while(fileIn.read(outputByte, 0, 4096) != -1)
			{
				out.write(outputByte, 0, 4096);
			}
			fileIn.close();
			out.flush();
			out.close();

			// byte[] bytes = new byte[8];
			// int iSK = 0;
			//
			// try {
			// Class.forName("com.mysql.jdbc.Driver");
			// Connection con =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs",
			// "xacspassword");
			//
			// File file = new File("D:\\cpabe/user/encFile");
			// FileOutputStream fos = new FileOutputStream(file);
			// byte b[];
			// Blob blob;
			//
			// System.out.println("id ------------" + id);
			//
			// PreparedStatement ps = con.prepareStatement("select * from file_upload where
			// id = " + id + "");
			// ResultSet rs = ps.executeQuery();
			// while (rs.next()) {
			// fileName = rs.getString("filename");
			// blob = rs.getBlob("encFile");
			// bytes = rs.getBytes("bArr");
			// iSK = rs.getInt("iVal");
			// b = blob.getBytes(1, (int) blob.length());
			// fos.write(b);
			// }
			//
			// ps.close();
			// fos.close();
			// con.close();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			//
			// System.out.println(bytes);
			// System.out.println(iSK);
			//
			// int[] intArray = new int[16];
			// int j = 0;
			// String s = aesKey;
			// int strLength = s.length();
			// if (strLength != 16) {
			// System.out.println("Not a valid length");
			// } else {
			// for (j = 0; j < 16; j++) {
			// if (!Character.isDigit(s.charAt(j))) {
			// System.out.println("Contains an invalid digit");
			// break;
			// }
			// intArray[j] = Integer.parseInt(String.valueOf(s.charAt(j)));
			// }
			// }
			// System.out.println(Arrays.toString(intArray));
			//
			// String ciphertextFile = "D:\\cpabe/user/encFile";
			//
			// byte[] b = new byte[16];
			// for (int i = 0; i < b.length; i++) {
			// b[i] = (byte)intArray[i];
			// }
			//
			// String cleartextAgainFile = "D:\\cpabe/user/" + fileName;
			// Cipher cipher;
			// try {
			// cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// SecretKey secKey = new SecretKeySpec(b, "AES");
			//
			// cipher.init(Cipher.DECRYPT_MODE, secKey);
			//
			// FileInputStream fis = new FileInputStream(ciphertextFile);
			// CipherInputStream cis = new CipherInputStream(fis, cipher);
			// FileOutputStream fos = new FileOutputStream(cleartextAgainFile);
			// //byte[] block = new byte[8];
			// //int i;
			// while ((iSK = cis.read(bytes)) != -1) {
			// fos.write(bytes, 0, iSK );
			// }
			// fis.close();
			// cis.close();
			// fos.close();
			// } catch (NoSuchAlgorithmException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (NoSuchPaddingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (InvalidKeyException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			//
			// String headStr = "attachment;filename=" + fileName;
			//
			// response.setContentType("application/octet-stream");
			// response.setHeader("Content-Disposition", headStr);
			//
			// File file = new File(cleartextAgainFile);
			// FileInputStream fileIn = new FileInputStream(file);
			// ServletOutputStream out = response.getOutputStream();
			//
			// byte[] outputByte = new byte[4096];
			// // copy binary contect to output stream
			// while (fileIn.read(outputByte, 0, 4096) != -1) {
			// out.write(outputByte, 0, 4096);
			// }
			// fileIn.close();
			// out.flush();
			// out.close();

			//response.sendRedirect("user/file_download.jsp#succ");
			
			System.out.println("dl done");

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
