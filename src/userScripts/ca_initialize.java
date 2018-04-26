package userScripts;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aaScripts.KeyGen;
import aaScripts.ListGen;

import javax.servlet.annotation.WebServlet;

import cn.edu.pku.ss.crypto.abe.api.*;

/**
 * Servlet implementation class ca_initialize
 */
@WebServlet("/ca_initialize")
public class ca_initialize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ca_initialize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Connection conn = null;
        Statement stmt = null;

        int n = Integer.parseInt(request.getParameter("nAA"));
        int t = Integer.parseInt(request.getParameter("tAA"));
        int temp = Integer.parseInt(request.getParameter("gen"));
        int gen;
        switch (temp) {
            case 1:
                gen = n;
                break;
            case 2:
                gen = t;
                break;
            default:
                gen = t;
                break;
        }

        //System.out.println(n + " " + t);
        if (n > 10 || t > n || n < 1 || t < 1) {
            response.sendRedirect("ca/dashboard.jsp?#ntFail");
            return;
        } else {
            /*
             *  fix for
             *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
             *       sun.security.validator.ValidatorException:
             *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
             *               unable to find valid certification path to requested target
             */
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }

                }
            };

            SSLContext sc = null;
			try {
				sc = SSLContext.getInstance("SSL");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            /*
             * end of the fix
             */

            int count = 0; 
            String SQLurl = "jdbc:mysql://localhost:3306/xacs_db";
            String user = "xacs";
            String password = "xacspassword";
            
            // MK & PK Setup -
            
            Path path = Paths.get("D:\\cpabe");
            Files.createDirectories(path);
            
            path = Paths.get("D:\\cpabe/ca");
            Files.createDirectories(path);

            
    		String PKFileName = "D:\\cpabe/ca/PKFile";
    		String MKFileName = "D:\\cpabe/ca/MKFile";

    		String[] keypairArr = new String[2];
            keypairArr = CPABE.setup(PKFileName, MKFileName);
            
            File PKF = new File(PKFileName);
            File MKF = new File(MKFileName);
            
            System.out.println(keypairArr[0] + " " + keypairArr[1]);
            
            String pkey = keypairArr[0];
            String mkey = keypairArr[1];

            try {
                File file1=new File("D:\\cpabe/ca/PKFile");
                FileInputStream fis1=new FileInputStream(file1);
                
                File file2=new File("D:\\cpabe/ca/MKFile");
                FileInputStream fis2=new FileInputStream(file2);
                
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(SQLurl, user, password);
                String sql = "INSERT INTO acs_info (id, sysname, n, t, public_key, master_key, PKFile, MKFile) VALUES (1, 'xACS', '" + n + "', '" + t + "', '" + pkey + "', '" + mkey + "', ?, ?);";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setBinaryStream(1,fis1,(int)file1.length());
                statement.setBinaryStream(2,fis2,(int)file2.length());
                statement.executeUpdate();
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                response.sendRedirect("ca/dashboard.jsp?#acsInfoFail");
                return;
            } 
            
            try {

                if (true) { //Auto gen master AA User & Owner
                    count = 1;
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
                    stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO reg (name, pass, email, dob, gen, role, state, country, status, audd, joinDate) VALUES ('MasterUser', 'password', 'xacsmail@gmail.com', '1996-10-06', 'Male', 'User', 'Manipal', 'India', 'Yes', 'UIDmast', '2000-01-01 00:00:00')");
                    stmt.executeUpdate("INSERT INTO reg (name, pass, email, dob, gen, role, state, country, status, audd, joinDate) VALUES ('MasterAA', 'password', 'xacsmail@gmail.com', '1996-10-06', 'Male', 'AA', 'Manipal', 'India', 'Yes', 'AIDmast', '2000-01-01 00:00:00')");
                    stmt.executeUpdate("INSERT INTO reg (name, pass, email, dob, gen, role, state, country, status, audd, joinDate) VALUES ('MasterOwner', 'password', 'xacsmail@gmail.com', '1996-10-06', 'Male', 'Owner', 'Manipal', 'India', 'Yes', null, '2000-01-01 00:00:00')");
                    //stmt.executeUpdate("update aa_list set aa1 = 'AIDmast' where id = 1");
                }
                
            	KeyGen kg = new KeyGen();
            	boolean skGen = kg.aaKeyGen("UIDmast");
            	
				ListGen lg = new ListGen();
				boolean aaLGen = lg.aaListGen("AIDmast");

                URL url = new URL("https://my.api.mockaroo.com/regxacssysini.json?key=4ad46c20&__method=POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String str = "";
                while (null != (str = br.readLine())) {
                    System.out.println(str);
                    if (count < gen) {
                        count++;
                        conn = DriverManager.getConnection(SQLurl, user, password);
                        String sql = str;
                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.executeUpdate();
                        conn.close();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                response.sendRedirect("ca/dashboard.jsp?#AAIniFail");
                return;
            }

            response.sendRedirect("ca/dashboard.jsp?#iniSucc");
            return;

        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
