package days04.survey.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SurveyDTO {
	private int no;
	private int survey_id;
	private String title;
	private String name;
	private String start_date;
	private String end_date;
	private int option_cnt;
	private int survey_allcnt;
	private String state;
	
	private String user_id;
	private int content_id;
	private String option_content;
}
