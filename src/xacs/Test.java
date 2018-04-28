package xacs;

import cn.edu.pku.ss.crypto.abe.api.*;
import cn.edu.pku.ss.crypto.abe.serialize.SerializeUtils;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsecretsharing.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Test() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		System.out.println("start ----");
		
		
		
		try {
		
		// file to be encrypted
				FileInputStream inFile = new FileInputStream("plainfile.txt");

				// encrypted file
				FileOutputStream outFile = new FileOutputStream("encryptedfile.des");

				// password to encrypt the file
				String password = "javapapers";

				// password, iv and salt should be transferred to the other end
				// in a secure manner

				// salt is used for encoding
				// writing it to a file
				// salt should be transferred to the recipient securely
				// for decryption
				byte[] salt = new byte[8];
				SecureRandom secureRandom = new SecureRandom();
				secureRandom.nextBytes(salt);
				FileOutputStream saltOutFile = new FileOutputStream("salt.enc");
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
				FileOutputStream ivOutFile = new FileOutputStream("iv.enc");
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
		
		try {
			String password = "javapapers";

			// reading the salt
			// user should have secure mechanism to transfer the
			// salt, iv and password to the recipient
			FileInputStream saltFis = new FileInputStream("salt.enc");
			byte[] salt = new byte[8];
			saltFis.read(salt);
			saltFis.close();

			// reading the iv
			FileInputStream ivFis = new FileInputStream("iv.enc");
			byte[] iv = new byte[16];
			ivFis.read(iv);
			ivFis.close();

			SecretKeyFactory factory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
					128);
			SecretKey tmp = factory.generateSecret(keySpec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

			// file decryption
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
			FileInputStream fis = new FileInputStream("encryptedfile.des");
			FileOutputStream fos = new FileOutputStream("plainfile_decrypted.txt");
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
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
//		String[] args = new String[0]; 
//		//CPABE.main(args);
//		
//		int n = 10, t = 2;
//		
//		// build 10 shares, of which 2 are required to recover the secret
//		SecureRandom random = new SecureRandom();
//		ShareBuilder builder = new ShareBuilder("I am Batman. Seriously.".getBytes(), t, 512, random);
//		List<Share> shares = builder.build(n);		
//		
//		
//		
//		// create a new Gson instance
//		 Gson gson = new Gson();
//		 // convert your list to json
//		 String jsonCartList = gson.toJson(shares);
//		 // print your generated json
//		 System.out.println("jsonCartList: " + jsonCartList);
//		 
//		 
//		 Type type = new TypeToken<List<Share>>(){}.getType();
//		 List<Share> prodList = gson.fromJson(jsonCartList, type);
//
//		 // print your List<Product>
//		 System.out.println("prodList: " + prodList);
//		 
//		 
//
//		// takes 2 shares, recovers secret
//		List<Share> someShares = new ArrayList<Share>();
//		someShares.add(prodList.get(2));
//		someShares.add(prodList.get(7));
//		ShareCombiner combiner = new ShareCombiner(someShares);
//		System.out.println(new String(combiner.combine()));
//
//		// omg I'm batman
		
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
