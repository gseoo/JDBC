package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex02 {
	public static void main(String[] args) {
		/*
		1. jdbc driver 로딩
		2. Connection DriverManager.getConnection()
		3. Statement 객체를 사용해서 CRUD 작업
		4. Connection Close()
		*/
		
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		
		Connection conn = null;
		try {
			Class.forName(className); // 1. jdbc driver 로딩
			conn = DriverManager.getConnection(url, user, password); // 2. conn 객체로 받아주기
			// 3. CRUD
			System.out.println(conn.isClosed()); // 닫힘 true, 열림 false
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close(); // 4. 연결 종료
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
