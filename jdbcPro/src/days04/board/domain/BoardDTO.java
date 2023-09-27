package days04.board.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BoardDTO {
	private int seq;
	private String writer;
	private String pwd;
	private String email;
	private String title;
	private Date writedate;
	private int readed;
	private int tag;
	private String content;
}