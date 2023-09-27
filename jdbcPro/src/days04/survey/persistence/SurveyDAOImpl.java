package days04.survey.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import days04.survey.domain.SurveyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SurveyDAOImpl implements SurveyDAO {

	private Connection conn = null;
	
	@Override
	public ArrayList<SurveyDTO> select(int currentPage, int numberPerPage) throws SQLException {
		int begin = (currentPage - 1) * numberPerPage + 1;
		int end = begin + numberPerPage - 1;
		String sql = "SELECT * "
				+ "FROM ("
				+ "        select rownum no, t.*"
				+ " 		from (\r\n"
				+ " 				select DISTINCT S.survey_id, title, name, start_date, end_date, option_cnt, survey_allcnt, state"
				+ " 				from survey s JOIN user_info u ON s.user_id = u.user_id"
				+ "             				  JOIN survey_option o  ON s.survey_id = o.survey_id )t "
				+ " 				ORDER BY survey_id DESC "
				+ ") b "
				+ "WHERE b.no BETWEEN ? AND ?";
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<SurveyDTO> list = null;
		SurveyDTO vo = null;
		
		int survey_id;
		String title;
		String name;
		String start_date;
		String end_date;
		int option_cnt;
		int survey_allcnt;
		String state;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, begin);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new ArrayList<SurveyDTO>();
				do {
					survey_id = rs.getInt("survey_id");
					title = rs.getString("title");
					name = rs.getString("name");
					start_date = rs.getString("start_date");
					end_date = rs.getString("end_date");
					option_cnt = rs.getInt("option_cnt");
					survey_allcnt = rs.getInt("survey_allcnt");
					state = rs.getString("state");
					vo = SurveyDTO.builder().survey_id(survey_id).title(title)
							.name(name).start_date(start_date).end_date(end_date)
							.option_cnt(option_cnt).survey_allcnt(survey_allcnt)
							.state(state).build();
					list.add(vo);
				} while (rs.next());
			}
		} catch (Exception e) {
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
	/*
	public int insert(SurveyDTO dto) throws SQLException {
		int rowCount = 0;
		String sql = String.format("INSERT INTO ", null);
	}
	*/

	@Override
	public int getTotalPages(int numberPerPage) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(SurveyDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
