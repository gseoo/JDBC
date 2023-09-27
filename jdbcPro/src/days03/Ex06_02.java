package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

// INSERT
public class Ex06_02 {
	public static void main(String[] args) {
		String sql = "{call up_insemp(?, ?, ?, ?, ?, ?, ?, ?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		int empno = 1111;
		String ename = "홍길동";
		String job = "student";
		int mgr = 7521;
		String hiredate = "2023.09.22";
		double sal = 200;
		double comm = 100;
		int deptno = 30;
		int rowCount = 0;
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, empno);
			cstmt.setString(2, ename);
			cstmt.setString(3, job);
			cstmt.setInt(4, mgr);
			cstmt.setString(5, hiredate);
			cstmt.setDouble(6, sal);
			cstmt.setDouble(7, comm);
			cstmt.setInt(8, deptno);
			
			rowCount = cstmt.executeUpdate();
			if(rowCount == 1) System.out.println("사원 추가 완료!!!");
			
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


//CREATE OR REPLACE PROCEDURE up_insemp
//(
//  pempno emp.empno%TYPE  
//  , pename emp.ename%TYPE  
//  , pjob emp.job%TYPE  
//  , pmgr emp.mgr%TYPE 
//  , phiredate emp.hiredate%TYPE  
//  , psal emp.sal%TYPE 
//  , pcomm emp.comm%TYPE  
//  , pdeptno emp.deptno%TYPE 
//)
//IS
//BEGIN
//  INSERT INTO emp VALUES(pempno, pename, pjob, pmgr, phiredate, psal, pcomm, pdeptno);
//  COMMIT;
//END;