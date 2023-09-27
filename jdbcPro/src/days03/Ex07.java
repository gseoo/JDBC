package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.SalgradeVO;

/**
 * @author 이경서
 * @date 2023. 9. 21. - 오후 2:40:55
 * @subject 리플렉션 (reflection)
 * @content   ㄴ 반사, 상, 반영
 * 			  ㄴ JDBC 리플렉션 : 결과물(rs)에 대한 정보를 추출해서 사용할 수 있는 기술
 */
public class Ex07 {
	public static void main(String[] args) {
		String sql = "SELECT table_name "
				+ " FROM tabs";
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<String> tnlist = null;
		String tableName = null;
		
		conn = DBConn.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				tnlist = new ArrayList<>();
				do {
					tableName = rs.getString(1);
					tnlist.add(tableName);
				} while (rs.next());
				printSalgrade(tnlist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// [2]
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.print("> 테이블명 입력 ? ");
		tableName = sc.next();
		
		sql = String.format("SELECT * FROM %s", tableName);
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			//System.out.println("> 컬럼 수 : " + rsmd.getColumnCount());
			int columnCount = rsmd.getColumnCount();
//			for(int i=1; i<=columnCount; i++) {
//				String columnName = rsmd.getColumnName(i);
//				int columnType = rsmd.getColumnType(i);
//				String columnTypeName = rsmd.getColumnTypeName(i);
//				int p = rsmd.getPrecision(i);
//				int s = rsmd.getScale(i);
//				System.out.println(columnName + "/" + columnType + "/" + columnTypeName + "(" + p + "," + s + ")");
//			}
			
			System.out.println("-".repeat(7 * columnCount));
			for(int i=1; i<=columnCount; i++) {
				System.out.printf("%s\t", rsmd.getColumnName(i));
			}
			System.out.println();
			System.out.println("-".repeat(7 * columnCount));
			
			if(rs.next()) {
				do {
					for(int i=1; i<=columnCount; i++) {
						int columnType = rsmd.getColumnType(i);
						int s = rsmd.getScale(i);
						if(columnType == 2 && s == 0) { // NUMBER - 정수
							System.out.printf("%d / ", rs.getInt(i));
						} else if(columnType == 2 && s != 0) { // NUMBER - 실수
							System.out.printf("%.2f / ", rs.getDouble(i));
						} else if(columnType == 12) { // VARCHAR2 문자열
							System.out.printf("%s / ", rs.getString(i));
						} else if(columnType == 93) { // DATE 날짜
							System.out.printf("%tF / ", rs.getDate(i));
						}
					}
					System.out.println();
				} while (rs.next());
			} else System.out.println("레코드가 존재하지 않음");
			
			System.out.println("-".repeat(7 * columnCount));
			
			/*
			if(rs.next()) {
				do {
					
				} while (condition);
			}
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
		System.out.println("END");
	}

	private static void printSalgrade(ArrayList<String> tnlist) {
		System.out.println("[Scott의 테이블 목록]");
		Iterator<String> ir = tnlist.iterator();
		int count = 1;
		while (ir.hasNext()) {
			String tableName = ir.next();
			System.out.printf("%d. %s  ", count, tableName);
			if(count % 5 == 0) System.out.println();
			count++;
		}
	}
}
