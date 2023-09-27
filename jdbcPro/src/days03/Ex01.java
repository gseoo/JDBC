package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.util.DBConn;

import domain.SalgradeVO;
/*
[실행결과]
1등급   (     700~1200 ) - 2명
      20   RESEARCH   7369   SMITH   800
      30   SALES         7900   JAMES   950
2등급   (   1201~1400 ) - 2명
   30   SALES   7654   MARTIN   2650
   30   SALES   7521   WARD      1750   
3등급   (   1401~2000 ) - 2명
   30   SALES   7499   ALLEN      1900
   30   SALES   7844   TURNER   1500
4등급   (   2001~3000 ) - 4명
    10   ACCOUNTING   7782   CLARK   2450
   20   RESEARCH   7902   FORD   3000
   20   RESEARCH   7566   JONES   2975
   30   SALES   7698   BLAKE   2850
5등급   (   3001~9999 ) - 1명   
   10   ACCOUNTING   7839   KING   5000
*/      
public class Ex01 {
	public static void main(String[] args) {
		String sql = 
				"SELECT grade, losal, hisal, COUNT(*) cnt\r\n"
				+ "FROM salgrade s JOIN emp e ON sal BETWEEN losal AND hisal\r\n"
				+ "GROUP BY grade, losal, hisal\r\n"
				+ "ORDER BY grade";
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<SalgradeVO> list = null;
		SalgradeVO vo = null;
		
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			// pstmt sql에 ? 파라미터가 있는지 확인
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new ArrayList<SalgradeVO>();
				do {
					vo = new SalgradeVO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
					list.add(vo);
				} while (rs.next());
				printSalgrade(list);
			}
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

	private static void printSalgrade(ArrayList<SalgradeVO> list) {
		list.size();
		Iterator<SalgradeVO> ir = list.iterator();
		while (ir.hasNext()) {
			SalgradeVO vo = ir.next();
			System.out.printf("%d등급	( %d ~ %d ) - %d명\n", vo.getGrade(), vo.getLosal(), vo.getHisal(), vo.getCnt());
		}
	}
}


//SalgradeVO vo = new SalgradeVO();
//vo.setGrade(1);
//vo.setLosal(1000);
//vo.setHisal(2000);
//vo.setCnt(2);

//SalgradeVO vo = new SalgradeVO(1, 1000, 2000, 2);

//SalgradeVO vo = SalgradeVO.builder().grade(1).losal(1000).hisal(2000).cnt(2).build();







