package aaScripts;

import java.io.*;
import java.sql.*;
import cn.edu.pku.ss.crypto.abe.api.*;

public class KeyGen {
	
	public boolean aaKeyGen(String uid) {
		
		try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");

            File dir = new File("cpabe");
        	dir.mkdirs();
            
            dir = new File("cpabe/aa");
        	dir.mkdirs();
            
            File file = new File("cpabe/aa/PKFile");
            FileOutputStream fos=new FileOutputStream(file);
            byte b[];
            Blob blob;
            
            PreparedStatement ps=con.prepareStatement("select PKFile from acs_info WHERE id = 1"); 
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                blob=rs.getBlob("PKFile");
                b=blob.getBytes(1,(int)blob.length());
                fos.write(b);
            }
            
            ps.close();
            fos.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }

		//String[] keypairArr = new String[2];
        //keypairArr = CPABE.setup(PKFileName, MKFileName);
		
		return true;
	}

}
