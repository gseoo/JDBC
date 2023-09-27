package days04;

import java.sql.Connection;

import com.util.DBConn;

import days04.board.controller.BoardController;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

/**
 * @author 이경서
 * @date 2023. 9. 21. - 오후 3:41:32
 * @subject 게시판
 * @content 모델1방식 : 글쓰기, 조회, 수정, 삭제 -> 한 클래스 안에서 모든 로직 처리
 * 			모델2방식(MVC패턴) ***
 * 			: 글쓰기 컨트롤러 -> 서비스 호출 -> DAO 호출 -> DB 처리
 * 						<-			<-
 *			Model : 로직 처리 객체
 *			View : 출력 객체
 *			Controller : 모든 요청/처리 객체
 */
public class Ex01 {
	public static void main(String[] args) {

		// 1. 테이블 생성, 시퀀스 생성
		// 	  tbl_cstVSBoard, seq_tbl_cstVSBoard
		// 2. domain.BoardDTO.java
		// 3. persistence.BoardDAO.java
		// 	  persistence.BoardDAOImpl.java
		// 4. 단위테스트
		//	  days04.test.BoardDAOImplTest.java
		// 5. 서비스
		// 	  [하나의 게시글 보기]
		//		ㄴ 1) 로그 기록 dao.로그기록()
		//		ㄴ 2) 조회수 1 증가 dao.조회수증가(seq)
		//		ㄴ 3) 게시글 정보 dao.게시글정보(seq)
		// 6. 단위테스트 BoardServiceTest.java
		// 7. days04.board.controller.BoardController.java - 입력/출력(view)
		// 8. days04.Ex01.java에서 테스트
		// 9. 목록, 새글쓰기, 상세보기
		// 10. 삭제
		// 11. 수정
		// 12. 검색
		// 13. 페이징처리
		//		한 페이지에 몇 개의 게시글 출력? int numberPerPage = 10;
		//		현재 페이지 번호? int currentPage = 1;
		//		페이징 블럭 수? int numberOfPageBlock = 10;

		// BoardController.java 선언
		// 1) 현재 페이지 번호 필드	currentPage
		// 2) 페이지 당 출력 게시글 수	numberPerPage
		// 3) 페이지 블럭 수 필드
		// 4) 총 레코드 수 			BoardDAO.java - getTotalRecords()
		// 5) 총 페이지 수 			BoardDAO.java - getTotalPages()
		// 6) 쿼리 확인
		/*
		SELECT *
		FROM (
        	SELECT ROWNUM no, t.*
        	FROM ( 
                SELECT seq,  writer, email, title, readed , writedate  
                FROM tbl_cstvsboard
                ORDER BY seq DESC
        	) t 
		)  b     
		WHERE b.no BETWEEN ? AND ?;
		 */
		// 6-2) BETWEEN ? AND ? 에 대한 계산
		// 7) BoardController.목록보기()
		//			service.selectService(currentPage, numberPerPage)
		// 8) service.selectService()
		//		ArrayList<boardDTO> list = dao.select(currentPage, numberPerPage)
		
		
		
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);

		controller.boardStart();



	}
}
//CREATE SEQUENCE seq_tbl_cstVSBoard;
//CREATE TABLE tbl_cstVSBoard (
//	    seq NUMBER not null primary key,
//	    writer VARCHAR2(20) NOT NULL,
//	    pwd VARCHAR2(20) NOT NULL,
//	    email VARCHAR2(100),
//	    title VARCHAR2(200) NOT NULL,
//	    writedate DATE DEFAULT SYSDATE,
//	    readed NUMBER DEFAULT 0,
//	    tag NUMBER(1) DEFAULT 0,
//	    content CLOB
//);