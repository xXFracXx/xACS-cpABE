package cn.edu.pku.ss.crypto.abe.api;

import it.unisa.dia.gas.jpbc.Element;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;

import cn.edu.pku.ss.crypto.abe.CPABEImpl;
import cn.edu.pku.ss.crypto.abe.Ciphertext;
import cn.edu.pku.ss.crypto.abe.MasterKey;
import cn.edu.pku.ss.crypto.abe.Parser;
import cn.edu.pku.ss.crypto.abe.Policy;
import cn.edu.pku.ss.crypto.abe.PublicKey;
import cn.edu.pku.ss.crypto.abe.SecretKey;
import cn.edu.pku.ss.crypto.abe.serialize.SerializeUtils;
import cn.edu.pku.ss.crypto.aes.AES;

public class CPABE_old {
	public static final String Default_PKFileName = "PublicKey";
	public static final String Default_MKFileName = "MasterKey";
	public static final String Default_SKFileName = "SecretKey";
	public static final String Ciphertext_Suffix  = ".cpabe";
	public static final String Error_PK_Missing = "Must set the name of the file that holds the PublicKey!"; 
	public static final String Error_MK_Missing = "Must set the name of the file that holds the MasterKey!"; 
	public static final String Error_SK_Missing = "Must set the name of the file that holds the SecretKey!"; 
	public static final String Error_EncFile_Missing = "Must set the file to be encrypted!";
	public static final String Error_Policy_Missing = "Must set a policy for the file to be encrypted!";
	public static final String Error_Attributes_Missing = "Must set the attributes of the key to be generated!";
	public static final String Error_Ciphertext_Missing = "Must set the name of the file that to be decrypted!";
	public static final String Error_Enc_Directory = "Can not encrypt a directory!";
	
	public static void main(String[] args) {
		CPABEImpl.debug = true;
		String encFileName = "cpabe/owner/JohnImpNumb.txt";
		String ciphertextFileName = "cpabe/owner/encAesKey";
		String PKFileName = "cpabe/ca/PKFile";
		String MKFileName = "cpabe/ca/MKFile";
		String SKFileName = "cpabe/user/SKFile";
		String policy = "1 of (globalAttr)";
		String[] attrs = new String[]{"globalAttr", "Rajas"};
		//setup(PKFileName, MKFileName);
		//enc(encFileName, policy, ciphertextFileName, PKFileName);
		//keygen(attrs, PKFileName, MKFileName, SKFileName);
		dec(ciphertextFileName, PKFileName, SKFileName);
	}

	private static void err(String s){
		System.err.println(s);
	}

	private static boolean isEmptyString(String s){
		return s == null ? true : s.trim().equals("") ? true : false;
	}	

	public static void setup(String PKFileName, String MKFileName){
		PKFileName = isEmptyString(PKFileName) ? Default_PKFileName : PKFileName;
		MKFileName = isEmptyString(MKFileName) ? Default_MKFileName : MKFileName;
		CPABEImpl.setup(PKFileName, MKFileName);
	}
	
	public static void enc(String encFileName, String policy, 
			               String outputFileName, String PKFileName) throws IOException{
		if(isEmptyString(encFileName)){
			err(Error_EncFile_Missing);
			return;
		}
		File encFile = new File(encFileName);
		if(!encFile.exists()){
			err(Error_EncFile_Missing);
			return;
		}
		if(encFile.isDirectory()){
			err(Error_Enc_Directory);
			return;
		}
		try {
			outputFileName = isEmptyString(outputFileName) ? 
					encFile.getCanonicalPath() + Ciphertext_Suffix : outputFileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(isEmptyString(policy)){
			err(Error_Policy_Missing);
			return;
		}
		if(isEmptyString(PKFileName)){
			err(Error_PK_Missing);
			return;
		}
		PublicKey PK = SerializeUtils.unserialize(PublicKey.class, new File(PKFileName));
		if(PK == null){
			err(Error_PK_Missing);
			return;
		}
		Parser parser = new Parser();
		Policy p = parser.parse(policy);
		if(p == null){
			err(Error_Policy_Missing);
			return;
		}
		CPABEImpl.enc(encFile, p, PK, outputFileName);
	}
	
	public static void keygen(String[] attrs, String PKFileName, String MKFileName, String SKFileName){
		if(attrs == null || attrs.length == 0){
			err(Error_Attributes_Missing);
			return;
		}
		if(isEmptyString(PKFileName)){
			err(Error_PK_Missing);
			return;
		}
		if(isEmptyString(MKFileName)){
			err(Error_MK_Missing);
			return;
		}
		SKFileName = isEmptyString(SKFileName) ? Default_SKFileName : SKFileName;
		PublicKey PK = SerializeUtils.unserialize(PublicKey.class, new File(PKFileName));
		if(PK == null){
			err(Error_PK_Missing);
			return;
		}
		MasterKey MK = SerializeUtils.unserialize(MasterKey.class, new File(MKFileName));
		CPABEImpl.keygen(attrs, PK, MK, SKFileName);
	}
	
	public static void dec(String ciphertextFileName, String PKFileName, String SKFileName){
		if(isEmptyString(ciphertextFileName)){
			err(Error_Ciphertext_Missing);
			return;
		}
		if(isEmptyString(PKFileName)){
			err(Error_PK_Missing);
			return;
		}
		if(isEmptyString(SKFileName)){
			err(Error_SK_Missing);
			return;
		}

		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(new File(ciphertextFileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		Ciphertext ciphertext = SerializeUtils.unserialize(Ciphertext.class, new File(ciphertextFileName));
		Ciphertext ciphertext = SerializeUtils._unserialize(Ciphertext.class, dis);
		if(ciphertext == null){
			err(Error_Ciphertext_Missing);
			return;
		}
		PublicKey PK = SerializeUtils.unserialize(PublicKey.class, new File(PKFileName));
		if(PK == null){
			err(Error_PK_Missing);
			return;
		}
		SecretKey SK = SerializeUtils.unserialize(SecretKey.class, new File(SKFileName));
		if(SK == null){
			err(Error_SK_Missing);
			return;
		}
		
		String output = null;
		if(ciphertextFileName.endsWith(".cpabe")){
			int end = ciphertextFileName.indexOf(".cpabe");
			output = ciphertextFileName.substring(0, end);
		}
		else{
			output = ciphertextFileName + ".out";
		}
		File outputFile = CPABEImpl.createNewFile(output);
		OutputStream os = null;
		try {
			os =  new FileOutputStream(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Element m = CPABEImpl.dec(ciphertext, SK, PK);
		AES.crypto(Cipher.DECRYPT_MODE, dis, os, m);
	}
	
}