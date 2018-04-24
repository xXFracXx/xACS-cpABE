package DBcon;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
             Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xacs_db", "xacs", "xacspassword");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
