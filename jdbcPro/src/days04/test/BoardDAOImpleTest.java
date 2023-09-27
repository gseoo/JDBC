package days04.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAOImpl;

class BoardDAOImpleTest {
/*
	@Test
	void selecttest() {
		Connection conn = DBConn.getConnection();
		BoardDAOImpl dao = new BoardDAOImpl(conn);
		
		try {
			ArrayList<BoardDTO> list = dao.select();
			if(list == null) {
				System.out.println("> 게시글이 존재하지 않습니다");
				return;
			}
			
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.println(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close();
		}
	}
*/
	/*
	@Test
	void inserttest() {
		Connection conn = DBConn.getConnection();
		BoardDAOImpl dao = new BoardDAOImpl(conn);
		
		try {
			BoardDTO dto = BoardDTO.builder().writer("서영학").pwd("1234").email("se@naver.com").title("두 번째 게시글").tag(0).content("두 번째 게시글 내용").build();
			int rowCount = dao.insert(dto);
			
			if(rowCount == 1) {
				System.out.println("> 게시글 쓰기 완료!!!");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close();
		}
	}
	*/
	
	/*
	@Test
	void ttest() {
		Connection conn = DBConn.getConnection();
		BoardDAOImpl dao = new BoardDAOImpl(conn);
		
		try {
			int totalRecords = dao.getTotalRecord();
			System.out.println(totalRecords);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close();
		}
	}
	*/
	
	@Test
	void totalPagestest() {
		Connection conn = DBConn.getConnection();
		BoardDAOImpl dao = new BoardDAOImpl(conn);
		
		try {
			int totalPages = dao.getTotalPages(10);
			System.out.println(totalPages);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close();
		}
	}
}
