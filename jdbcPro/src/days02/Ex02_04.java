package days02;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

// DELETE
public class Ex02_04 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		Statement stmt = null;

		String deptno;
		int rowCount = 0;

		System.out.print("> 삭제할 부서 번호를 입력 ? ");
		deptno = sc.next();

		String sql = "DELETE FROM dept WHERE deptno IN (" + deptno + ")";
		System.out.println(sql);

		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rowCount = stmt.executeUpdate(sql);
			if(rowCount > 0) {
				System.out.println("부서 삭제 완료!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("END");
	}
}
