package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

//emp 테이블에서 한 사원의 정보를 입력받아서 추가
public class Test02 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("> 사원번호 입력 : ");
		empno = Integer.parseInt(br.readLine());
		System.out.print("> 사원 이름 입력 : ");
		ename = br.readLine();
		System.out.print("> 직업 입력 : ");
		job = br.readLine();
		System.out.print("> 관리자의 사원번호 입력 : ");
		mgr = Integer.parseInt(br.readLine());
		System.out.print("> 입사일 입력 : ");
		hiredate = br.readLine();
		System.out.print("> 월급 입력 : ");
		sal = Double.parseDouble(br.readLine());
		System.out.print("> 커미션 입력 : ");
		comm = Double.parseDouble(br.readLine());
		System.out.print("> 부서번호 입력 : ");
		deptno = Integer.parseInt(br.readLine());
		
		String sql = String.format("INSERT INTO emp VALUES(%d, '%s', '%s', %d, TO_DATE('%s', 'YY/MM/DD'), %.0f, %.0f, %d)"
				, empno, ename, job, mgr, hiredate, sal, comm, deptno);
		System.out.println(sql);
		
		Connection conn = null;
		Statement stmt = null;
		int rowCount = 0;
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rowCount = stmt.executeUpdate(sql);
			if(rowCount == 1) System.out.println("> 사원 추가 성공!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("END");
	}
}
