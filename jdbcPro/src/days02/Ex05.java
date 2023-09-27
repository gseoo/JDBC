package days02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.util.DBConn;

import domain.DeptVO;

public class Ex05 {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();

		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM dept";
		ResultSet rs = null;

		int deptno = 0;
		String dname = null;
		String loc = null;
		DeptVO vo = null;
		ArrayList<DeptVO> list = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
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
				pstmt.close();
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
