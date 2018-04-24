import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class ExampleCrypt {

    public static void main(String[] args) throws Exception {

	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//	SecretKey secKey = keyGen.generateKey();
	byte[] b = new byte[16];
	for(int i=0; i<b.length; i++){
		b[i] = (byte)i;
	}
	SecretKey secKey = new SecretKeySpec(b, "AES");

	// Encrypt

	cipher.init(Cipher.ENCRYPT_MODE, secKey);

	String cleartextFile = "README.md";
	String ciphertextFile = "ciphertextSymm.txt";

	FileInputStream fis = new FileInputStream(cleartextFile);
	FileOutputStream fos = new FileOutputStream(ciphertextFile);
	CipherOutputStream cos = new CipherOutputStream(fos, cipher);

	byte[] block = new byte[8];
	int i;
	while ((i = fis.read(block)) != -1) {
	    cos.write(block, 0, i);
	}
	cos.close();

	// Decrypt

	String cleartextAgainFile = "cleartextAgainSymm.txt";

	cipher.init(Cipher.DECRYPT_MODE, secKey);

	fis = new FileInputStream(ciphertextFile);
	CipherInputStream cis = new CipherInputStream(fis, cipher);
	fos = new FileOutputStream(cleartextAgainFile);

	while ((i = cis.read(block)) != -1) {
	    fos.write(block, 0, i);
	}
	fos.close();
    }
}