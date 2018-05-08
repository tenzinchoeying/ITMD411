package Lab02;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	
	//Code Database URL
	static final String DB_URL = "jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false";
	static final String USER = "db411";
	static final String PASS = "411";
	
	static final String DB_URL1 = "jdbc:mysql://127.0.0.1/411labs?autoReconnect=true&useSSL=false";
	static final String USER1 = "root";
	static final String PASS1 = "";
	
	public Connection connection;
	
	public DBConnect() {
		try {
			this.connection = this.connect();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection connect() throws SQLException{
		if (this.connection == null) {
			this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
		}
		return this.connection;
	}

}
