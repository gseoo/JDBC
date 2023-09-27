package days04.board.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import domain.SalgradeVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class BoardDAOImpl implements BoardDAO {

	private Connection conn = null;


	@Override
	public ArrayList<BoardDTO> select() throws SQLException {
		String sql = "SELECT seq, title, writer, email, writedate, readed "
				+ "FROM tbl_cstVSBoard "
				+ "ORDER BY seq DESC";

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<BoardDTO> list = null;
		BoardDTO vo = null;

		int seq;
		String writer;
		String email;
		String title;
		Date writedate;
		int readed;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				list = new ArrayList<>();
				do {
					seq = rs.getInt("seq");
					title = rs.getString("title");
					writer = rs.getString("writer");
					email = rs.getString("email");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					vo = BoardDTO.builder().seq(seq).title(title).writer(writer).email(email).writedate(writedate).readed(readed).build();
					list.add(vo);
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public ArrayList<BoardDTO> select(int currentPage, int numberPerPage) throws SQLException {
		int begin = (currentPage - 1) * numberPerPage + 1;
		int end = begin + numberPerPage - 1;
		String sql = "SELECT * "
				+ "FROM ("
				+ "        SELECT ROWNUM no, t.* "
				+ "        FROM ( \r\n"
				+ "                SELECT seq,  writer, email, title, readed , writedate "
				+ "                FROM tbl_cstvsboard "
				+ "                ORDER BY seq DESC "
				+ "        ) t "
				+ ") b "
				+ "WHERE b.no BETWEEN ? AND ?";

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<BoardDTO> list = null;
		BoardDTO vo = null;

		int seq;
		String writer;
		String email;
		String title;
		Date writedate;
		int readed;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, begin);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				list = new ArrayList<>();
				do {
					seq = rs.getInt("seq");
					title = rs.getString("title");
					writer = rs.getString("writer");
					email = rs.getString("email");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					vo = BoardDTO.builder().seq(seq).title(title).writer(writer).email(email).writedate(writedate).readed(readed).build();
					list.add(vo);
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@Override
	public int insert(BoardDTO dto) throws SQLException {
		int rowCount = 0;
		String sql = 
				String.format("INSERT INTO tbl_cstVSBoard(seq, writer, pwd, email, title, tag, content) "
						+ " VALUES (seq_tbl_cstVSBoard.NEXTVAL, ?, ?, ?, ?, ?, ?)");

		PreparedStatement pstmt = null;

		pstmt = this.conn.prepareStatement(sql);
		pstmt.setString(1,  dto.getWriter() );
		pstmt.setString(2,  dto.getPwd() );
		pstmt.setString(3,  dto.getEmail() );
		pstmt.setString(4,  dto.getTitle() );
		pstmt.setInt(5, dto.getTag());
		pstmt.setString(6,  dto.getContent() );
		rowCount = pstmt.executeUpdate();

		pstmt.close();

		return rowCount;
	}

	@Override
	public void increaseReaded(int seq) throws SQLException {
		String sql = "UPDATE tbl_cstvsboard SET readed = readed + 1 WHERE seq = ?";
		PreparedStatement pstmt = null;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, seq);
		int rowCount = pstmt.executeUpdate();
		pstmt.close();
	}

	@Override
	public BoardDTO viewService(int seq) throws SQLException {
		String sql = "SELECT seq, writer, email, title, readed, writedate, content FROM tbl_cstvsboard WHERE seq = ?";
		BoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, seq);
		rs = pstmt.executeQuery();
		
		if(rs.next())
			dto = BoardDTO.builder()
			.seq(seq)
			.writer(rs.getString("writer"))
			.email(rs.getString("email"))
			.title(rs.getString("title"))
			.readed(rs.getInt("readed"))
			.writedate(rs.getDate("writedate"))
			.content(rs.getString("content"))
			.build();
		
		rs.close();
		pstmt.close();
		return dto;
	}

	@Override
	public int delete(int seq) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM tbl_cstVSBoard WHERE seq = ?";
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, seq);
		rowCount = pstmt.executeUpdate();
		pstmt.close();
		
		return rowCount;
	}

	@Override
	public int update(BoardDTO dto) throws SQLException {
		int rowCount = 0;
		String sql = "UPDATE tbl_cstVSBoard SET email = ?, title = ?, content = ? WHERE seq = ?";
		PreparedStatement pstmt = this.conn.prepareStatement(sql);
		pstmt.setString(1, dto.getEmail());
		pstmt.setString(2, dto.getTitle());
		pstmt.setString(3, dto.getContent());
		pstmt.setInt(4, dto.getSeq());
		rowCount = pstmt.executeUpdate();
		pstmt.close();
		return rowCount;
	}
	
	@Override
	public ArrayList<BoardDTO> search(int searchCondition, String searchWord) throws SQLException {
		String sql = "SELECT seq, title, writer, email, writedate, readed "
				+ "FROM tbl_cstvsBoard ";
		// 검색 조건, 검색어 => WHERE 조건절 추가
		switch(searchCondition) {
		case 1: sql += "WHERE REGEXP_LIKE(title, ?, 'i') "; break; // 제목
		case 2: sql += "WHERE REGEXP_LIKE(content, ?, 'i') "; break; // 내용
		case 3: sql += "WHERE REGEXP_LIKE(writer, ?, 'i') "; break; // 작성자
		case 4: sql += "WHERE REGEXP_LIKE(title, ?, 'i') OR REGEXP_LIKE(content, ?, 'i') "; break; // 제목 + 내용
		}
		 sql += "ORDER BY seq DESC";

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<BoardDTO> list = null;
		BoardDTO vo = null;

		int seq;
		String writer;
		String email;
		String title;
		Date writedate;
		int readed;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			if(searchCondition == 4) {
				pstmt.setString(2, searchWord);
			}
				
			rs = pstmt.executeQuery();

			if(rs.next()) {
				list = new ArrayList<>();
				do {
					seq = rs.getInt("seq");
					title = rs.getString("title");
					writer = rs.getString("writer");
					email = rs.getString("email");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					vo = BoardDTO.builder().seq(seq).title(title).writer(writer).email(email).writedate(writedate).readed(readed).build();
					list.add(vo);
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public int getTotalRecord() throws SQLException {
		int totalRecords = 0;
		String sql = "SELECT COUNT(*) FROM tbl_cstvsBoard";
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) totalRecords = rs.getInt(1);
		rs.close();
		pstmt.close();
		
		return totalRecords;
	}

	@Override
	public int getTotalPages(int numberPerPage) throws SQLException {
		int totalPages = 0;
		String sql = "SELECT CEIL(COUNT(*)/?) FROM tbl_cstvsBoard";
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, numberPerPage);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) totalPages = rs.getInt(1);
		rs.close();
		pstmt.close();
		
		return totalPages;
	}

	@Override
	public ArrayList<BoardDTO> search(int currentPage, int numberPerPage, int searchCondition, String searchWord) throws SQLException {
		int begin = (currentPage - 1) * numberPerPage + 1;
		int end = begin + numberPerPage - 1;
		String sql = "SELECT * "
				+ "FROM ("
				+ "        SELECT ROWNUM no, t.* "
				+ "        FROM ( \r\n"
				+ "                SELECT seq,  writer, email, title, readed , writedate "
				+ "                FROM tbl_cstvsboard ";
		// 검색 조건 추가
		switch(searchCondition) {
		case 1: sql += "WHERE REGEXP_LIKE(title, ?, 'i') "; break; // 제목
		case 2: sql += "WHERE REGEXP_LIKE(content, ?, 'i') "; break; // 내용
		case 3: sql += "WHERE REGEXP_LIKE(writer, ?, 'i') "; break; // 작성자
		case 4: sql += "WHERE REGEXP_LIKE(title, ?, 'i') OR REGEXP_LIKE(content, ?, 'i') "; break; // 제목 + 내용
		}
		
		sql += "                ORDER BY seq DESC "
				+ "        ) t "
				+ ") b "
				+ "WHERE b.no BETWEEN ? AND ?";
		

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<BoardDTO> list = null;
		BoardDTO vo = null;

		int seq;
		String writer;
		String email;
		String title;
		Date writedate;
		int readed;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			if(searchCondition == 4) {
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, begin);
				pstmt.setInt(4, end);
			} else {
				pstmt.setInt(2, begin);
				pstmt.setInt(3, end);
			}
				
			rs = pstmt.executeQuery();

			if(rs.next()) {
				list = new ArrayList<>();
				do {
					seq = rs.getInt("seq");
					title = rs.getString("title");
					writer = rs.getString("writer");
					email = rs.getString("email");
					writedate = rs.getDate("writedate");
					readed = rs.getInt("readed");
					vo = BoardDTO.builder().seq(seq).title(title).writer(writer).email(email).writedate(writedate).readed(readed).build();
					list.add(vo);
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	
	@Override
	public int getTotalPages(int numberPerPage, int searchCondition, String searchWord) throws SQLException {
		int totalPages = 0;
		String sql = "SELECT CEIL(COUNT(*)/?) FROM tbl_cstvsBoard ";
		
		switch(searchCondition) {
		case 1: sql += "WHERE REGEXP_LIKE(title, ?, 'i') "; break; // 제목
		case 2: sql += "WHERE REGEXP_LIKE(content, ?, 'i') "; break; // 내용
		case 3: sql += "WHERE REGEXP_LIKE(writer, ?, 'i') "; break; // 작성자
		case 4: sql += "WHERE REGEXP_LIKE(title, ?, 'i') OR REGEXP_LIKE(content, ?, 'i') "; break; // 제목 + 내용
		}
		
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, numberPerPage);
		pstmt.setString(2, searchWord);
		if(searchCondition == 4) pstmt.setString(3, searchWord);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) totalPages = rs.getInt(1);
		rs.close();
		pstmt.close();
		
		return totalPages;
	}

	
}
