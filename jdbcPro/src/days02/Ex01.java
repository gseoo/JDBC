package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.EmpVO;

public class Ex01 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("> 부서 번호를 입력하세요 : ");
		int eno = sc.nextInt();
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		String sql = "SELECT * FROM emp WHERE deptno = " + eno;
		ResultSet rs = null;

		int empno = 0;
		String ename = null;
		String job = null;
		int mgr = 0;
		String hiredate = null;
		double sal = 0;
		double comm = 0;
		int deptno = 0;
		EmpVO vo = null;
		ArrayList<EmpVO> list = null;
		
		try {
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				list = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hiredate = rs.getString("hiredate");
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");
					deptno = rs.getInt("deptno");
					vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
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

	private static void printDeptList(ArrayList<EmpVO> list) {
		if(list == null) {
			System.out.println("> 사원 정보가 없음");
			return;
		}
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.println(vo);
		}
	}
}

