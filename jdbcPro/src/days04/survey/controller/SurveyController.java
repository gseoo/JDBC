package days04.survey.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import days04.survey.domain.SurveyDTO;
import days04.survey.service.SurveyService;

public class SurveyController {
	private Scanner sc;
	private int selectedNumber;
	private SurveyService service;
	
	private int currentPage = 1;
	private int numberPerPage = 10;
	private int numberOfPageBlock = 10;
	
	public SurveyController() {
		this.sc = new Scanner(System.in);
	}
	
	public SurveyController(SurveyService service) {
		this();
		this.service = service;
	}
	
	public void surveyStart() {
		while(true) {
			메뉴출력();
			메뉴선택();
			메뉴처리();
		}
	}

	private void 메뉴처리() {
		switch(this.selectedNumber) {
			case 1: 설문작성(); break;
			case 2: 설문조회(); break;
			case 3: exit(); break; // 종료
		}
	}

	private void exit() {
		DBConn.close();
		System.out.println("\t\t\t  프로그램 종료!!!");
		System.exit(-1);
	}

	
	private void 설문조회() {
		System.out.print("> 현재 페이지(currentPage) 번호를 입력 ? ");
		this.currentPage = this.sc.nextInt();
		ArrayList<SurveyDTO> list = this.service.selectSurvey(currentPage, numberPerPage);
		System.out.println("▶ 설문조사");
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.printf("%s\t%-20s\t%s\t%-10s\t%s\t\t%s\t%s\t%s\n", "번호", "질문", "작성자", "시작일", "종료일", "항목수", "참여자수", "상태");
		System.out.println("--------------------------------------------------------------------------------------------");
		if(list == null) System.out.println("\t\t> 설문조사 목록 없음");
		else {
			Iterator<SurveyDTO> ir = list.iterator();
			while (ir.hasNext()) {
				SurveyDTO dto = ir.next();
				System.out.printf("%s\t%-20s\t%s\t%-10s\t%s\t%s\t%s\t%s\n"
						, dto.getSurvey_id(), dto.getTitle(), dto.getName()
						, dto.getStart_date().substring(0, 11), dto.getEnd_date().substring(0, 11), dto.getOption_cnt()
						, dto.getSurvey_allcnt(), dto.getState());
			}
		}
		System.out.println("--------------------------------------------------------------------------------------------");
		String pagingBlock = this.service.pageService(this.currentPage, this.numberPerPage, this.numberOfPageBlock);
		System.out.println(pagingBlock);
		System.out.println("--------------------------------------------------------------------------------------------");
		일시정지();
	}

	private void 설문작성() {
		System.out.print("> 질문 : ");
		String title = this.sc.next(); System.out.println();
		System.out.print("> 시작일 : ");
		String start_date = this.sc.next(); System.out.println();
		System.out.print("> 종료일 : ");
		String end_date = this.sc.next(); System.out.println();
		System.out.print("> 항목수 : ");
		int option_cnt = this.sc.nextInt(); System.out.println();
		String datas[] = new String[option_cnt];
		for(int content_id=0; content_id<option_cnt; content_id++) {
			System.out.printf("> 항목 %d. : ", content_id+1);
			datas[content_id] = this.sc.next();
		}
		
		SurveyDTO dto = null;
		int rowCount = 0;
		for(option_cnt=1; option_cnt<=datas.length; option_cnt++) {
			dto = SurveyDTO.builder().title(title)
					.start_date(start_date).end_date(end_date)
					.option_cnt(option_cnt).content_id(option_cnt).build();
			rowCount = this.service.insertService(dto);
		}

		if(rowCount == 1) System.out.println("설문 추가 완료!!!");
	}

	private void 메뉴선택() {
		System.out.printf("> 메뉴를 선택하세요 ? ");
		this.selectedNumber = this.sc.nextInt();
	}

	private void 메뉴출력() {
		String [] menus = {"설문 작성", "설문 조회", "종료"};
		System.out.println("[ 메뉴 ]");
		for (int i = 0; i < menus.length; i++) {
			System.out.printf("%d. %s\t", i+1, menus[i]);
		} // for
		System.out.println();
	}
	
	private void 일시정지() {
		System.out.println(" \t\t 계속하려면 엔터치세요.");
		try {
			System.in.read();
			System.in.skip(System.in.available()); // 13, 10
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

}
