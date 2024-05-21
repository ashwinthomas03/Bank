import java.sql.Connection;
import java.sql.DriverManager;

class Database {
	public static Connection connection;
	
	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/Bank?serverTimezone=EST", "root", "kavunkal");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}