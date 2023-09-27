package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

import domain.DeptVO;
import oracle.jdbc.internal.OracleTypes;

// SELECT
public class Ex05 {
	public static void main(String[] args) {
		String sql = "{call up_seldept(?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		DeptVO vo = null;
		ArrayList<DeptVO> list = null;
		ResultSet rs = null;
		
		int deptno;
		String dname, loc;
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			
			cstmt.execute();
			
			rs = (ResultSet) cstmt.getObject(1);
			
			while(rs.next()) {
				deptno = rs.getInt("deptno");
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				vo = new DeptVO(deptno, dname, loc);
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
		System.out.println("END");
	}
}

//CREATE OR REPLACE PROCEDURE up_seldept
//(
//    pdeptcur OUT SYS_REFCURSOR
//)
//IS
//BEGIN
//    OPEN pdeptcur FOR SELECT * FROM dept;
//END;