package days04.survey.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import days04.survey.domain.SurveyDTO;

public interface SurveyDAO {

	ArrayList<SurveyDTO> select(int currentPage, int numberPerPage) throws SQLException;

	int getTotalPages(int numberPerPage) throws SQLException;

	int insert(SurveyDTO dto) throws SQLException;
	
}
