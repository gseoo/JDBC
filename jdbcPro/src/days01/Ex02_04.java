package days01;

import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

public class Ex02_04 {
	public static void main(String[] args) throws SQLException {
		String url = "jdbc:oracle:thin:@192.168.10.167:1521:xe";
		String user = "scott";
		String password = "tiger";
		
		Connection conn = DBConn.getConnection(url, user, password);
		
	}
}
