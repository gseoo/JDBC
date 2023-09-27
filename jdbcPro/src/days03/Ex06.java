package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

import domain.EmpVO;
import oracle.jdbc.internal.OracleTypes;

// SELECT
public class Ex06 {
	public static void main(String[] args) {
		// [복습] - 제출
		// CallableStatement를 사용해서 emp 테이블에서 CRUD 작업
		String sql = "{call up_selemp(?)}";
		Connection conn = null;
		CallableStatement cstmt = null;
		EmpVO vo = null;
		ArrayList<EmpVO> list = null;
		ResultSet rs = null;
		
		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			
			rs = (ResultSet) cstmt.getObject(1);
			
			while (rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getString("hiredate");
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
				System.out.println(vo);
			}
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

//CREATE OR REPLACE PROCEDURE up_selemp
//(pempcur OUT SYS_REFCURSOR)
//IS
//BEGIN
//    OPEN pempcur FOR SELECT * FROM emp;
//END;
