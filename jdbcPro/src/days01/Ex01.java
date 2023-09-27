package days01;

public class Ex01 {

	public static void main(String[] args) {
		System.out.println("Hello World");
		
		/*
		JAVA <- JDBC 드라이버 -> DBMS
		
		JAVA + DataBase + Connectivity
		: 자바의 DB에 연결 및 CRUD 작업을 하기 위한 표준 인터페이스
		
		오라클사 + JDBC 를 구현한 클래스 = JDBC 드라이버
		11g XE ojdbc6.jar + JDK 1.6 이상
		MS사	  + JDBC 를 구현한 클래스 = JDBC 드라이버
		
		JDBC 드라이버 종류
		1) Type1 : ODBC
		2) Type2 : C, C++ 언어로 만든 라이브러리를 사용해서 DB 연동
		3) Type3 : 미들웨어 서버 사용
		4) Type4 : Thin 드라이버/ 자바로만 만들어져 있고, 가장 많이 사용
		
		[DBMS와 연결 방법]
		1. JDBC 드라이버 로딩 - Class.forName() 메소드 사용
		2. Connection 얻어오는 작업 - DriverManager 클래스 getConnection() 메소드
		3. 작업자(Statement) - CRUD 작업
			ㄴ Statement 객체
			ㄴ PreparedStatement 객체 : 성능 빠름
			ㄴ CallableStatement 객체 : 저장 프로시저, 저장 함수 사용
		4. DB 연결 종료 - Connection 객체 Close
		*/
	}

}
