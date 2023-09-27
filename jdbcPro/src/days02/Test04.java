package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.util.DBConn;

// emp 테이블에서 사원의 정보를 수정
// 수정할 사원번호, 기타 사원 정보를 입력받아서
public class Test04 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int rowCount = 0;

		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		System.out.print("> 수정할 사원 번호 입력 : ");
		empno = Integer.parseInt(br.readLine());
		System.out.print("> 수정할 사원 이름 입력 : ");
		ename = br.readLine();
		System.out.print("> 수정할 직업 입력 : ");
		job = br.readLine();
		System.out.print("> 수정할 관리자 입력 : ");
		mgr = Integer.parseInt(br.readLine());
		System.out.print("> 수정할 입사일자 입력 : ");
		hiredate = br.readLine();
		System.out.print("> 수정할 월급 입력 : ");
		sal = Double.parseDouble(br.readLine());
		System.out.print("> 수정할 커미션 입력 : ");
		comm = Double.parseDouble(br.readLine());
		System.out.print("> 수정할 부서 번호 입력 : ");
		deptno = Integer.parseInt(br.readLine());

		String originalEname, originalJob, originalHiredate;
		int originalMgr, originalDeptno;
		Double originalSal, originalComm;
		String sql = String.format("SELECT * FROM emp WHERE empno = %d", empno);

		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				originalEname = rs.getString("ename");
				originalJob = rs.getString("job");;
				originalMgr = rs.getInt("mgr");;
				originalHiredate = rs.getString("hiredate");;
				originalSal = rs.getDouble("sal");
				originalComm = rs.getDouble("comm");
				originalDeptno = rs.getInt("deptno");
			}
			else {
				System.out.println("사원이 존재하지 않음");
				return;
			}
			
			if(ename.equals("")) ename = originalEname;
			if(job.equals("")) job = originalJob;
			if(Objects.isNull(mgr)) mgr = originalMgr;
			if(hiredate.equals("")) hiredate = originalHiredate;
			if(Objects.isNull(sal)) sal = originalSal;
			if(Objects.isNull(comm)) comm = originalComm;
			if(Objects.isNull(deptno)) deptno = originalDeptno;
			
			
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
}
