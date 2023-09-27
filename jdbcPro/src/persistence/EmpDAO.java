package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

import domain.EmpVO;

public interface EmpDAO {

	// 조회
	ArrayList<EmpVO> getSelect();
	
	// 검색
	ArrayList<EmpVO> getSelect(int searchCondition, String searchWord);
	
	// 추가
	int add(EmpVO vo);
	// 수정 - 조회
	EmpVO get(int empno);
	
	// 수정 - update
	int update(EmpVO vo);

	// 삭제
	int delete(int empno);
}
