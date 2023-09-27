package days04.survey.service;

import java.sql.SQLException;
import java.util.ArrayList;

import days04.survey.domain.SurveyDTO;
import days04.survey.persistence.SurveyDAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SurveyService {
	
	private SurveyDAO dao = null;

	public ArrayList<SurveyDTO> selectSurvey(int currentPage, int numberPerPage) {
		ArrayList<SurveyDTO> list = null;
		System.out.println("> 설문조사 목록 조회 로그 기록");
		try {
			list = this.dao.select(currentPage, numberPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String pageService(int currentPage, int numberPerPage, int numberOfPageBlock) {
		String pagingBlock = "\t\t\t";
		int totalPages;
		try {
			totalPages = this.dao.getTotalPages(numberPerPage);
			int start = (currentPage -1) /numberOfPageBlock * numberOfPageBlock +1 ;
			int end= start + numberOfPageBlock -1;         
			end =   end > totalPages ? totalPages : end;

			if( start != 1 ) pagingBlock += " < ";          
			for (int j = start; j <= end; j++) {
				pagingBlock += String.format(currentPage == j ? "[%d] " : "%d " , j);
			}         
			if( end != totalPages ) pagingBlock += " > ";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagingBlock;
	}

	public int insertService(SurveyDTO dto) {
		int rowCount = 0;
		System.out.println("> 설문조사 등록 로그 기록 작업");
		try {
			rowCount = this.dao.insert(dto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}
	

}
