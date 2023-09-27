package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

// DELETE
public class Ex06_04 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		int empno = 1111;
		int rowCount = 0;
		
		String sql = "{call up_delemp(?)}";
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, empno);
			rowCount = cstmt.executeUpdate();
			
			if(rowCount > 0) System.out.println("사원 삭제 완료!!!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

//CREATE OR REPLACE PROCEDURE up_delemp
//( pempno emp.empno%TYPE)
//IS
//BEGIN  
//  DELETE FROM emp WHERE empno = pempno;
//END;
