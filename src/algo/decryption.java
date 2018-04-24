package algo;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

public class decryption {
	public String decrypt(String txt, String skey) {
        String decryptedtext = null;
        try {
            byte[] bs = Base64.decode(skey);
            SecretKey sec = new SecretKeySpec(bs, "AES");
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, sec);
            byte[] byteCipherText = new BASE64Decoder().decodeBuffer(txt);
            aesCipher.init(Cipher.DECRYPT_MODE, sec, aesCipher.getParameters());
            byte[] byteDecryptedText = aesCipher.doFinal(byteCipherText);
            decryptedtext = new String(byteDecryptedText);
        } catch (Exception e) {
            System.out.println(e);
        }
        return decryptedtext;
    }
}
