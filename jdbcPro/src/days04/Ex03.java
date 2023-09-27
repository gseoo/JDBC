package days04;

import java.sql.Connection;

import com.util.DBConn;

import days04.survey.controller.SurveyController;
import days04.survey.persistence.SurveyDAO;
import days04.survey.persistence.SurveyDAOImpl;
import days04.survey.service.SurveyService;

public class Ex03 {
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		SurveyDAO dao = new SurveyDAOImpl(conn);
		SurveyService service = new SurveyService(dao);
		SurveyController controller = new SurveyController(service);
		controller.surveyStart();
		
	}
}
