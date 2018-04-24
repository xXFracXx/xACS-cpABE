package algo;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import sun.misc.BASE64Encoder;

public class encryption {
	public String encrypt(String text, SecretKey secretkey) {
        String plainData = null, cipherText = null;
        try {
            plainData = text;
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretkey);
            byte[] byteDataToEncrypt = plainData.getBytes();
            byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
            cipherText = new BASE64Encoder().encode(byteCipherText);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cipherText;
    }
}
