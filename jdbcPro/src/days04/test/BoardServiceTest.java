package days04.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

class BoardServiceTest {
	
	@Test
	void insertServiceTest() {
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		
		BoardDTO dto = BoardDTO.builder().writer("정창기").pwd("1234").email("jung@naver.com").title("세 번째 게시글").tag(0).content("세 번째 게시글 내용").build();
		
		int rowCount = service.insertService(dto);
		
		if(rowCount == 1) {
			System.out.println("> 게시글 쓰기 완료!!!");
			return;
		}
	
/*
	@Test
	void selectServiceTest() {
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		
		ArrayList<BoardDTO> list = service.selectService();
		
		if(list == null) {
			System.out.println("> 게시글이 존재하지 않습니다");
			return;
		}
		
		Iterator<BoardDTO> ir = list.iterator();
		while (ir.hasNext()) {
			BoardDTO dto = ir.next();
			System.out.println(dto);
		}
*/		
		
	}
}
