package aaScripts;

import cn.edu.pku.ss.crypto.abe.SecretKey;
import cn.edu.pku.ss.crypto.abe.api.*;
import cn.edu.pku.ss.crypto.abe.serialize.SerializeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.*;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class sendKey {
	public boolean aaSendKey(String id) throws SQLException, ClassNotFoundException, IOException {

		String[] subSArr = null;
		int count = 0;

		String subShare = "";
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from request where id = '" + id + "'");
		String fileName = "";
		while (rs.next()) {
			fileName = rs.getString("fname");
			int aaCount = Integer.parseInt(rs.getString("aCount"));
			subSArr = new String[aaCount];
			try {if(!(rs.getString("aa1Share").isEmpty())) subSArr[count++] = rs.getString("aa1Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa2Share").isEmpty())) subSArr[count++] = rs.getString("aa2Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa3Share").isEmpty())) subSArr[count++] = rs.getString("aa3Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa4Share").isEmpty())) subSArr[count++] = rs.getString("aa4Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa5Share").isEmpty())) subSArr[count++] = rs.getString("aa5Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa6Share").isEmpty())) subSArr[count++] = rs.getString("aa6Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa7Share").isEmpty())) subSArr[count++] = rs.getString("aa7Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa8Share").isEmpty())) subSArr[count++] = rs.getString("aa8Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa9Share").isEmpty())) subSArr[count++] = rs.getString("aa9Share");} catch (NullPointerException npe) {}
			try {if(!(rs.getString("aa10Share").isEmpty())) subSArr[count++] = rs.getString("aa10Share");} catch (NullPointerException npe) {}
		}
		conn.close();

		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select * from acs_info where id = 1");
		rs.next();
		int n = rs.getInt("n");
		int t = rs.getInt("t");
		conn.close();

		System.out.println("count ---- " + count);
		
		Gson gson2 = new GsonBuilder().create();
		String jsonArray = gson2.toJson(subSArr);
		
		String jsonFormattedString = jsonArray.replaceAll("\\\\", "");
		String jsonFormattedString2 = jsonFormattedString.replaceAll("\"\\{", "\\{");
		String jsonFormattedString3 = jsonFormattedString2.replaceAll("\\}\"", "\\}");

		//System.out.println(jsonFormattedString3);

		Gson gson = new Gson();
		Type type = new TypeToken<List<Share>>() {
		}.getType();
		List<Share> share = gson.fromJson(jsonFormattedString3, type);
		
		SecretKey mSK = new SecretKey();

		// takes 2 shares, recovers secret
		List<Share> someShares = new ArrayList<Share>();
		for(int i = 0; i < count; i++) { 
			someShares.add(share.get(i));
		}
		ShareCombiner combiner = new ShareCombiner(someShares);
		mSK = SerializeUtils.constructFromByteArray(SecretKey.class, combiner.combine());
		
		//System.out.println(SKstr);
		
        Path path = Paths.get("D:\\cpabe");
        Files.createDirectories(path);
        
        path = Paths.get("D:\\cpabe/user");
        Files.createDirectories(path);

		String SKFileName = "D:\\cpabe/user/SKFile";
		File SKFile = new File(SKFileName);
		SerializeUtils.serialize(mSK, SKFile);

		/*try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
            
            File file=new File("D:\\cpabe/user/encAesKey");
            FileOutputStream fos=new FileOutputStream(file);
            byte b[];
            Blob blob;
            
            PreparedStatement ps=con.prepareStatement("select encAesKey from file_upload where filename = '" + fileName + "'"); 
            rs=ps.executeQuery();
            while(rs.next()){
                blob=rs.getBlob("encAesKey");
                b=blob.getBytes(1,(int)blob.length());
                fos.write(b);
            }
            
            ps.close();
            fos.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }*/
		
		String selectSQL = "select * from file_upload where filename = '" + fileName + "'";
        rs = null;
        FileOutputStream fos1 = null;
        conn = null;
        PreparedStatement pstmt = null;
 
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
//            pstmt = conn.prepareStatement(selectSQL);
//            rs = pstmt.executeQuery();
// 
//            // write binary stream into file
//            File file = new File("D:\\cpabe/user/encAesKey");
//            fos1 = new FileOutputStream(file);
// 
//            System.out.println("Writing BLOB to file " + file.getAbsolutePath());
//            while (rs.next()) {
//                InputStream input = rs.getBinaryStream("encAesKey");
//                byte[] buffer = new byte[1024];
//                while (input.read(buffer) > 0) {
//                    fos1.write(buffer);
//                }
//            }
//            fos1.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
// 
//                if (conn != null) {
//                    conn.close();
//                }
//                if (fos1 != null) {
//                    fos1.close();
//                }
// 
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
        
        File file=new File("D:\\cpabe/user/PKFile");
        FileOutputStream fos=new FileOutputStream(file);
		
		try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
            byte b[];
            Blob blob;
            
            PreparedStatement ps=con.prepareStatement("select PKFile from acs_info where id = 1"); 
            rs=ps.executeQuery();
            while(rs.next()){
                blob=rs.getBlob("PKFile");
                b=blob.getBytes(1,(int)blob.length());
                fos.write(b);
            }
            
            ps.close();
            //fos.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    	fos.close();
		

        File file2=new File("D:\\cpabe/user/encAesKey");
        FileOutputStream fos2=new FileOutputStream(file2);
    	
		try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
            
            byte b[];
            Blob blob;
            
            System.out.println("id ------------" + id);
            
            PreparedStatement ps=con.prepareStatement("select encAesKey from file_upload where id = " + id + ""); 
            rs=ps.executeQuery();
            while(rs.next()){
                blob=rs.getBlob("encAesKey");
                b=blob.getBytes(1,(int)blob.length());
                fos2.write(b);
            }
            
            ps.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
		
        fos2.close();
		
		String ciphertextFileName = "D:\\cpabe/user/encAesKey";
		String PKFileName = "D:\\cpabe/user/PKFile";
		return CPABE.dec(ciphertextFileName, PKFileName, SKFileName);
	}
}
