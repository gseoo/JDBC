package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Ex05_03 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		// days02.Ex02_03.java -> pstmt로 수정
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
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
		sql = "SELECT * FROM dept WHERE deptno = ?";

		try {
			conn = DBConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			rs = pstmt.executeQuery();

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
			sql = "UPDATE dept SET dname = ?, loc = ? WHERE deptno = ?";
			
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, dname);
			pstmt2.setString(2, loc);
			pstmt2.setInt(3, deptno);
			
			rowCount = pstmt2.executeUpdate();
			if(rowCount == 1) System.out.println("> 부서 수정 완료!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				pstmt2.close();
				rs.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("END");
	}
}

