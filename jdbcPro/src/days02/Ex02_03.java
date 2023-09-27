package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

// UPDATE
public class Ex02_03 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 부서 정보 수정
		// 1. 수정하기 전의 원래 값을 알고 있어야 함
		// 2. 부서번호는 수정 x, 부서명, 지역명만
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int rowCount = 0;
		String originalDname, originalLoc;

		System.out.print("> 수정할 부서 번호 입력 ? ");
		int deptno = Integer.parseInt(br.readLine());
		System.out.print("> 수정할 부서명 입력 ? ");
		String dname = br.readLine();
		System.out.print("> 수정할 지역명 입력 ? ");
		String loc = br.readLine();

		// 해당 부서 번호의 수정 전 부서명, 지역명 저장
		sql = String.format("SELECT * FROM dept WHERE deptno = %d", deptno);

		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				originalDname = rs.getString("dname");
				originalLoc = rs.getString("loc");
				System.out.println(originalDname);
			}
			else {
				System.out.println("부서가 존재하지 않음");
				return;
			}

			// UPDATE
			// 입력이 안 되면 원래 값을 할당
			if(dname.equals("")) dname = originalDname;
			if(loc.equals("")) loc = originalLoc;
			sql = String.format("UPDATE dept SET dname = '%s', loc = '%s' WHERE deptno = %d", dname, loc, deptno);
			
			rowCount = stmt.executeUpdate(sql);
			if(rowCount == 1) System.out.println("> 부서 수정 완료!");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("END");
	}
}

