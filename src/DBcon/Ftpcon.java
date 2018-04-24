package DBcon;

import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.net.ftp.FTPClient;

public class Ftpcon {
	FTPClient client = new FTPClient();
    FileInputStream fis = null;
    boolean status;
    public boolean upload(File file) {
        try {
            client.connect("ftp.drivehq.com");
            client.login("xACS", "xacsmail1232!");
            client.enterLocalPassiveMode();
            fis = new FileInputStream(file);
            status = client.storeFile(" /xACS-Files/" + file.getName(), fis);
            client.logout();
            fis.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (status) {
            System.out.println("success");
            return true;
        } else {
            System.out.println("failed");
            return false;
        }
    }
}
