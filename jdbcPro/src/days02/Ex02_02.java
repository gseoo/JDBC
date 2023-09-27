package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;

// SELECT
public class Ex02_02 {
	public static void main(String[] args) {
		// 부서 검색 -> 부서 정보 수정 + 삭제
		// 1:deptno, 2:dname, 3:loc 검색
		Scanner sc = new Scanner(System.in);
		int searchCondition;
		String searchWord;
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		int deptno;
		String dname;
		String loc;
		
		DeptVO vo = null;
		ArrayList<DeptVO > list = null;
		
		System.out.print("> 검색 조건을 입력 ? ");
		searchCondition = sc.nextInt();
		System.out.print("> 검색어를 입력 ? ");
		searchWord = sc.next();
		
		// sql문은 StringBuffer, StringBuilder 쓰는 게 더 좋음
		String sql = "SELECT * FROM dept WHERE ";
		if(searchCondition == 1) { // 부서번호
			sql += String.format("deptno IN (%s)", searchWord);
		} else if(searchCondition == 2) { // 부서명
			sql += String.format("REGEXP_LIKE (dname, '%s', 'i')", searchWord);
		} else if(searchCondition == 3) { // 지역명
			sql += String.format("REGEXP_LIKE (loc, '%s', 'i')", searchWord);
		}
		
		System.out.println(sql);
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				list = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					if(searchCondition == 2) {
						searchWord = searchWord.toUpperCase();
						dname = dname.replaceAll(searchWord, "[" + searchWord + "]");
					}
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
		System.out.printf(" > 검색 결과 : %d개 \n", list.size());
		
		Iterator<DeptVO> ir = list.iterator();
		while (ir.hasNext()) {
			DeptVO vo = ir.next();
			System.out.println(vo);
		}
	}
}
