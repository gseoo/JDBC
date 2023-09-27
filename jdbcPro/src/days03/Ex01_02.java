package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.util.DBConn;

import domain.DeptEmpSalgradeVO;
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
public class Ex01_02 {
	public static void main(String[] args) {
		String sql = 
				"SELECT grade, losal, hisal, COUNT(*) cnt"
				+ " FROM salgrade s JOIN emp e ON sal BETWEEN losal AND hisal"
				+ " GROUP BY grade, losal, hisal\r\n"
				+ " ORDER BY grade";
		String empSql = 
				"SELECT d.deptno, dname, empno, ename, sal, grade"
				+ " FROM dept d RIGHT JOIN emp e ON d.deptno = e.deptno"
				+ " JOIN salgrade s ON sal BETWEEN losal AND hisal"
				+ " WHERE grade = ?";
		
		Connection conn = null;
		ResultSet rs = null, empRs = null;
		PreparedStatement pstmt = null, empPstmt = null;;
		
		LinkedHashMap<SalgradeVO, ArrayList<DeptEmpSalgradeVO>> map = new LinkedHashMap<>();
		SalgradeVO vo = null; // key
		ArrayList<DeptEmpSalgradeVO> empList = null; // value
		DeptEmpSalgradeVO empVO = null;
		
		int deptno;
		String dname;
		int empno;
		String ename;
		double sal;
		int grade;
		
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			// pstmt sql에 ? 파라미터가 있는지 확인
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				do {
					grade = rs.getInt(1);
					vo = new SalgradeVO(grade, rs.getInt(2), rs.getInt(3), rs.getInt(4));
					
					empPstmt = conn.prepareStatement(empSql);
					empPstmt.setInt(1, grade);
					empRs = empPstmt.executeQuery();
					
					if(empRs.next()) {
						empList = new ArrayList<DeptEmpSalgradeVO>();
						do {
							deptno = empRs.getInt(1);
							dname = empRs.getString(2);
							empno = empRs.getInt(3);
							ename = empRs.getString(4);
							sal = empRs.getDouble(5);
							empVO = new DeptEmpSalgradeVO(deptno, dname, empno, ename, sal, grade);
							empList.add(empVO);
						} while(empRs.next());
					}
					
					map.put(vo, empList);
					empRs.close();
					empPstmt.close();
					
				} while (rs.next());
				printSalgrade(map);
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

	private static void printSalgrade(LinkedHashMap<SalgradeVO, ArrayList<DeptEmpSalgradeVO>> map) {
		Set<Entry<SalgradeVO, ArrayList<DeptEmpSalgradeVO>>> set = map.entrySet();
		Iterator<Entry<SalgradeVO, ArrayList<DeptEmpSalgradeVO>>> ir = set.iterator();
		while (ir.hasNext()) {
			Map.Entry<SalgradeVO, ArrayList<DeptEmpSalgradeVO>> entry = ir.next();
			SalgradeVO vo = entry.getKey();
			ArrayList<DeptEmpSalgradeVO> list = entry.getValue();
			System.out.printf("%d등급	( %d ~ %d ) - %d명\n", vo.getGrade(), vo.getLosal(), vo.getHisal(), vo.getCnt());
			
			Iterator<DeptEmpSalgradeVO> empIr = list.iterator();
			while (empIr.hasNext()) {
				DeptEmpSalgradeVO empVO = empIr.next();
				System.out.printf("\t\t%d\t%s\t%d\t%s\t%.2f\n", empVO.getDeptno(), empVO.getDname(), empVO.getEmpno(), empVO.getEname(),empVO.getSal());
			}
			
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







