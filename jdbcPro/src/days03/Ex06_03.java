package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

// UPDATE
public class Ex06_03 {
	public static void main(String[] args) {
		String sql = "{call up_updemp(?, ?, ?, ?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		int empno;
		String job = "estudent";
		double sal = 9999;
		double comm = 999;
		int rowCount = 0;
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, 1111);
			cstmt.setString(2, job);
			cstmt.setDouble(3, sal);
			cstmt.setDouble(4, comm);
			
			rowCount = cstmt.executeUpdate();
			if(rowCount == 1) System.out.println("사원 수정 완료");

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

//CREATE OR REPLACE PROCEDURE up_updemp
//(
//  pempno emp.empno%TYPE  
//  , pename emp.ename%TYPE  DEFAULT NULL
//  , pjob emp.job%TYPE  DEFAULT NULL
//  , pmgr emp.mgr%TYPE DEFAULT NULL
//  , phiredate emp.hiredate%TYPE  DEFAULT NULL
//  , psal emp.sal%TYPE DEFAULT NULL
//  , pcomm emp.comm%TYPE  DEFAULT NULL
//  , pdeptno emp.deptno%TYPE DEFAULT NULL
//)
//IS
//BEGIN
//  UPDATE emp 
//  SET  ename = NVL(pename, ename), job = NVL(pjob, job)
//      , mgr = NVL(pmgr, mgr), hiredate = NVL(phiredate, hiredate), sal = NVL(psal, sal)
//      , comm = NVL(pcomm, comm), deptno = NVL(pdeptno, deptno)
//  WHERE empno = pempno;
//  COMMIT;
//END;