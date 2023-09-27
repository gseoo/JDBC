package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

public class Ex02 {
	public static void main(String[] args) {
		/*
		[자바 트랜잭션 처리]
		- 논리적인 작업 단위
		- 전부 완료(commit) or 전부 롤백(rollback)
		예) 계좌 이체
		A 통장의 돈 인출 UPDATE : dept 50 부서 추가 O
		B 통장에 돈 입금 UPDATE : dept 50 부서 추가 X
		*/
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		
		String sql = "INSERT INTO dept VALUES (?, ?, ?)";
		
		conn = DBConn.getConnection();
		
		try {
			conn.setAutoCommit(false); // 자동으로 커밋 되는 걸 막음
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC1");
			pstmt.setString(3, "SEOUL");
			rowCount = pstmt.executeUpdate();
			if(rowCount == 1) System.out.println("~ 1번 부서 추가 성공 ~");
			
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC2");
			pstmt.setString(3, "SEOUL");
			rowCount = pstmt.executeUpdate();
			if(rowCount == 1) System.out.println("~ 2번 부서 추가 성공 ~");
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		DBConn.close();
	}
}
