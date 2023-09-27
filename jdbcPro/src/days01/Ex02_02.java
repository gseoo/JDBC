package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex02_02 {
	public static void main(String[] args) {
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:scott/tiger@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url);
			System.out.println(conn.isClosed());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
