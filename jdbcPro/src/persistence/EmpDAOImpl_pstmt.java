package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

import domain.EmpVO;

public class EmpDAOImpl_pstmt implements EmpDAO {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// Setter DI
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	// Constructor DI
	public EmpDAOImpl_pstmt(Connection conn) {
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
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();

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
				pstmt.close();
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
			sql += "REGEXP_LIKE(ename, ?, 'i')";
		} else if(searchCondition == 2) {
			sql += "deptno = ?";
		} else if(searchCondition == 3) {
			sql += "REGEXP_LIKE(job, ?, 'i')";
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
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			if(searchCondition == 1) pstmt.setString(1, searchWord);
			else if(searchCondition == 2) pstmt.setInt(1, Integer.parseInt(searchWord));
			else if(searchCondition == 3) pstmt.setString(1, searchWord);
			
			rs = pstmt.executeQuery();

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
				pstmt.close();
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
		String sql = "INSERT INTO emp ( empno, ename, job, mgr, hiredate, sal, comm, deptno ) "
						+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql); 
			rowCount = pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		return rowCount;
	}

	// 수정 - 조회
	public EmpVO get(int empno) {
		String sql = "SELECT * FROM emp WHERE empno = ?";

		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		EmpVO vo = null;

		try {
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(sql);

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
				pstmt.close();
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
		String sql = "UPDATE emp "
						+ "SET ename = '%s', job = '%s', mgr = %d, sal = %f, comm = %f, deptno = %d "
						+ "WHERE empno = %d";

		try {
			pstmt = conn.prepareStatement(sql); 
			rowCount = pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		return rowCount;
	}


	// 삭제
	public int delete(int empno) {
		int rowCount = 0;
		String sql = "DELETE FROM emp WHERE empno = ?";
		try {
			pstmt = conn.prepareStatement(sql); 
			rowCount = pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		return rowCount;
	}

}
