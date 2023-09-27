package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

//				1		2		3
// emp 테이블에서 사원명 or 부서명 or job으로 검색된 사원 정보 조회
//			  LIKE     10     LIKE 사용
public class Test03 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int searchCondition;
		String searchWord;
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;
		
		EmpVO vo = null;
		ArrayList<EmpVO> list = null;
		
		System.out.print("> 검색 조건 입력(1-사원명, 2-부서명, 3-job) : ");
		searchCondition = sc.nextInt();
		System.out.print("> 검색어 입력 : ");
		searchWord = sc.next();
		
		String sql = "SELECT * FROM emp WHERE ";
		if(searchCondition == 1) {
			sql += String.format("REGEXP_LIKE (ename, '%s', 'i')", searchWord);
		} else if(searchCondition == 2) {
			sql += String.format("deptno IN (%s)", searchWord);
		} else if(searchCondition == 3) {
			sql += String.format("REGEXP_LIKE (job, '%s', 'i')", searchWord);
		}
		
		System.out.println(sql);
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				list = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");;
					mgr = rs.getInt("mgr");;
					hiredate = rs.getString("hiredate");;
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
			System.out.println("> 사원이 존재하지 않음");
			return;
		}
		System.out.printf(" > 검색 결과 : %d개 \n", list.size());
		
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.println(vo);
		}
	}
}
