package aaScripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ListGen {
	public boolean aaListGen(String aid) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_925e3cb61718614", "b45ac8d6e3ff55", "57d84b63");
			Statement st = con.createStatement();
			st.executeUpdate("INSERT INTO aalist (AID) values ('" + aid + "')");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return true;
	}
}
