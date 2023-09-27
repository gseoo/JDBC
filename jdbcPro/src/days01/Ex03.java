package days01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.util.DBConn;

import domain.DeptVO;

public class Ex03 {
	public static void main(String[] args) {
		// Scott.dept 테이블에서 CRUD 작업
		// domain.DeptVO : 하나의 부서 정보를 저장할 객체
		
		// dept 테이블 조회
		Connection conn = DBConn.getConnection();
		
		// 3. 작업자(Statement) + SELECT
		Statement stmt = null;
		String sql = "SELECT * FROM dept";
		ResultSet rs = null;
		
		int deptno = 0;
		String dname = null;
		String loc = null;
		DeptVO vo = null;
		ArrayList<DeptVO> list = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// rs로부터 읽어올 다음 행이 있니?
			//boolean rs.next() true : 다음 읽어올 레코드로 이동시키는 기능까지 함
			// 해당 레코드의 부서번호, 부서명, 지역명 읽기
			//System.out.println(rs.next()); // true
			//int deptno = rs.getInt(1);
			
			if(rs.next()) {
				list = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					vo = new DeptVO(deptno, dname, loc);
					list.add(vo);
				} while(rs.next());
			}
			
			printDeptList(list);
			
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

	private static void printDeptList(ArrayList<DeptVO> list) {
		if(list == null) {
			System.out.println("> 부서가 존재하지 않음");
			return;
		}
		Iterator<DeptVO> ir = list.iterator();
		while (ir.hasNext()) {
			DeptVO vo = ir.next();
			System.out.println(vo);
		}
	}
}
