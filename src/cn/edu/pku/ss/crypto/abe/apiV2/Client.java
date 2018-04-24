package cn.edu.pku.ss.crypto.abe.apiV2;

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
import cn.edu.pku.ss.crypto.abe.CPABEImplWithoutSerialize;
import cn.edu.pku.ss.crypto.abe.Ciphertext;
import cn.edu.pku.ss.crypto.abe.Parser;
import cn.edu.pku.ss.crypto.abe.Policy;
import cn.edu.pku.ss.crypto.abe.PublicKey;
import cn.edu.pku.ss.crypto.abe.SecretKey;
import cn.edu.pku.ss.crypto.abe.serialize.SerializeUtils;
import cn.edu.pku.ss.crypto.aes.AES;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Client {
	private PublicKey PK;
	private SecretKey SK;
	private String[] attrs;
	
	public Client(){}
	
	public Client(String[] attrs){
		this.attrs = attrs;
	}
	
	public String[] getAttrs(){
		return attrs;
	}
	
	public void setAttrs(String[] attrs){
		this.attrs = attrs;
	}
	
	public PublicKey getPK() {
		return PK;
	}

	public void setPK(String PKJSONString) {
		JSONObject json = JSON.parseObject(PKJSONString);
		byte[] b = json.getBytes("PK");
		this.PK = SerializeUtils.constructFromByteArray(PublicKey.class, b);
	}

	public SecretKey getSK() {
		return SK;
	}

	public void setSK(String SKJSONString) {
		JSONObject json = JSON.parseObject(SKJSONString);
		byte[] b = json.getBytes("SK");
		this.SK = SerializeUtils.constructFromByteArray(SecretKey.class, b);
	}

	public void enc(File in, String policy, String outputFileName){
		Parser parser = new Parser();
		Policy p = parser.parse(policy);
		CPABEImplWithoutSerialize.enc(in, p, this.PK, outputFileName);
	}
	
	public void dec(File in){
		String ciphertextFileName = null; 
		DataInputStream dis = null;
		try {
			ciphertextFileName = in.getCanonicalPath();
			dis = new DataInputStream(new FileInputStream(in));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Ciphertext ciphertext = SerializeUtils._unserialize(Ciphertext.class, dis);
		
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
	
	public void serializePK(File f){
		SerializeUtils.serialize(this.PK, f);
	}
	
	public void serializeSK(File f){
		SerializeUtils.serialize(this.SK, f);
	}
}
