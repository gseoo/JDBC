package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

import domain.EmpVO;

public class EmpDAOImpl implements EmpDAO {

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	// Setter DI
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	// Constructor DI
	public EmpDAOImpl(Connection conn) {
		this.conn = conn;
	}

	// 조회
	public ArrayList<EmpVO> getSelect() {
		ArrayList<EmpVO> list = null;
		String sql = "SELECT * FROM emp";

		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		EmpVO vo = null;

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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	// 검색
	public ArrayList<EmpVO> getSelect(int searchCondition, String searchWord) {
		ArrayList<EmpVO> list = null;
		String sql = "SELECT * FROM emp WHERE ";
		if(searchCondition == 1) {
			sql += String.format("REGEXP_LIKE(ename, '%s', 'i')", searchWord);
		} else if(searchCondition == 2) {
			sql += String.format("deptno IN(%s)", searchWord);
		} else if(searchCondition == 3) {
			sql += String.format("REGEXP_LIKE(job, '%s', 'i')", searchWord);
		}

		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		EmpVO vo = null;

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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("END");
		return list;
	}
	// 추가
	public int add(EmpVO vo) {
		int rowCount = 0;
		String sql = 
				String.format("INSERT INTO emp ( empno, ename, job, mgr, hiredate, sal, comm, deptno ) "
						+ " VALUES ( %d, '%s', '%s', %d, '%s', %f, %f, %d)"
						, vo.getEmpno(), vo.getEname(), vo.getJob(), vo.getMgr(), vo.getHiredate(), vo.getSal(), vo.getComm(), vo.getDeptno() );

		try {
			stmt = conn.createStatement(); 
			rowCount = stmt.executeUpdate(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		return rowCount;
	}

	// 수정 - 조회
	public EmpVO get(int empno) {
		String sql = "SELECT * FROM emp WHERE empno = " + empno;

		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		EmpVO vo = null;

		try {
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getString("hiredate");
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}
	// 수정 - update
	public int update(EmpVO vo) {
		int rowCount = 0;
		String sql = 
				String.format("UPDATE emp "
						+ "SET ename = '%s', job = '%s', mgr = %d, sal = %f, comm = %f, deptno = %d "
						+ "WHERE empno = %d"
						, vo.getEname(), vo.getJob(), vo.getMgr(), vo.getSal(), vo.getComm(), vo.getDeptno(), vo.getEmpno());

		try {
			stmt = conn.createStatement(); 
			rowCount = stmt.executeUpdate(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		return rowCount;
	}


	// 삭제
	public int delete(int empno) {
		int rowCount = 0;
		String sql = "DELETE FROM emp WHERE empno = " + empno;
		try {
			stmt = conn.createStatement(); 
			rowCount = stmt.executeUpdate(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		return rowCount;
	}

}
