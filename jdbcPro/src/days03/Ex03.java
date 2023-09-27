package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.internal.OracleTypes;

/**
 * @author 이경서
 * @date 2023. 9. 21. - 오전 11:30:40
 * @subject [CallableStatement] - 저장프로시저, 저장함수 처리
 * @content 회원가입
 * 			아이디 : [		] [ID 중복체크 버튼]
 * 			- 이미 사용중인 아이디입니다.
 * 			- 사용 가능한 아이디입니다.
 * 			회원테이블 (emp) - 회원ID (empno)
 */

public class Ex03 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("> 중복체크할 ID(empno) 입력 ? ");
		int pID = sc.nextInt();
		
		String sql = "{call UP_IDCHECK(?, ?)}";
		//String sql = "{call UP_IDCHECK(pid=>?, pcheck=>?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int idCheck = 0;
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			
			// IN ?
			cstmt.setInt(1, pID);
			// OUT ?
			cstmt.registerOutParameter(2, OracleTypes.INTEGER);
			
//			cstmt.execute(); // return boolean
//			cstmt.executeQuery(); // return ResultSet
			cstmt.executeQuery();
			
			idCheck = cstmt.getInt(2);
			if(idCheck == 0) System.out.println("사용 가능한 ID(empno)입니다.");
			else System.out.println("이미 사용 중인 ID(empno)입니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("END");
	}
}

//CREATE OR REPLACE PROCEDURE up_idcheck
//(
//  pid IN emp.empno%TYPE
//  , pcheck OUT NUMBER
//)
//IS
//BEGIN
//  SELECT COUNT(*) INTO pcheck
//  FROM emp
//  WHERE empno = pid;
//--EXCEPTION
//END;
