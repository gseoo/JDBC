package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

import domain.DeptVO;

// UPDATE
public class Ex05_03 {
	public static void main(String[] args) {
		String sql = "{call up_upddept(?, ?, ?)}";

		Connection conn = null;
		CallableStatement cstmt = null;
		DeptVO vo = null;

		int deptno;
		String dname = "EQC", loc = "ESEOUL";
		int rowCount = 0;

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, 50);
			cstmt.setString(2, dname);
			cstmt.setString(3, loc);

			rowCount = cstmt.executeUpdate();
			
			if(rowCount == 1) System.out.println("부서 수정 완료");

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

//CREATE OR REPLACE PROCEDURE up_upddept
//(
//    pdeptno dept.deptno%TYPE
//    , pdname dept.dname%TYPE DEFAULT NULL
//    , ploc dept.loc%TYPE DEFAULT NULL
//)
//IS
//    odname dept.dname%TYPE;
//    oloc dept.loc%TYPE;
//BEGIN
//    /*
//    SELECT dname, loc INTO odname, oloc FROM dept WHERE deptno = pdeptno;
//    IF  pdname IS NULL THEN
//        pdname := opdname;
//    END IF;
//    
//    IF  ploc IS NULL THEN
//        ploc := oloc;
//    END IF;
//    
//    UPDATE dept SET dname = pdname, loc = ploc WHERE deptno = pdeptno;
//    */
//    UPDATE dept SET dname = NVL(pdname, dname), loc = NVL(ploc, loc) WHERE deptno = pdeptno;
//    COMMIT;
//END;