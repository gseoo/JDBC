package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

// INSERT

public class Ex02 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 1. dept 부서 정보 조회	days01.Ex03.java
		// 2. dept 부서 추가
		int deptno; // PK = NN + UK
		String dname;
		String loc;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("1. 부서 번호 입력 > ");
		deptno = Integer.parseInt(br.readLine());
		System.out.print("2. 부서명 입력 > ");
		dname = br.readLine();
		System.out.print("3. 지역명 입력 > ");
		loc = br.readLine();
		
		String sql = String.format("INSERT INTO dept (deptno, dname, loc) VALUES (%d, '%s', '%s')", deptno, dname, loc);
		
		Connection conn = null;
		//conn.setAutoCommit(true);
		Statement stmt = null;
		int rowCount = 0;
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			// SELECT - executeQuery
			// INSERT, UPDATE, DELETE - executeUpdate
			rowCount = stmt.executeUpdate(sql);
			
			if(rowCount == 1) System.out.println("> 부서 추가 성공!");
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
