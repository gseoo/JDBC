package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.EmpVO;
import persistence.EmpDAO;
import persistence.EmpDAOImpl;
import persistence.EmpDAOImpl_pstmt;

public class Ex04 {
	public static void main(String[] args) throws NumberFormatException, IOException {

		ArrayList<EmpVO> list = null;
		Connection conn = DBConn.getConnection();
		EmpDAO dao = new EmpDAOImpl_pstmt(conn);
		
		/*
		// 1. 조회
		list = dao.getSelect();
		printEmpList(list);
		*/
		
		// 2. 검색
		/*
		Scanner sc = new Scanner(System.in);		
		System.out.print("> 검색 조건 입력(1-사원명, 2-부서번호, 3-job) : ");
		int searchCondition = sc.nextInt();
		System.out.print("> 검색어 입력 : ");
		String searchWord = sc.next();

		list = dao.getSelect(searchCondition, searchWord);
		printEmpList(list);
		 */

		
		// 3. 추가
		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate; 
		double sal;
		double comm;
		int deptno;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("1. empno 입력 ? ");      
		empno = Integer.parseInt( br.readLine() );      
		System.out.print("2. ename 입력 ? ");
		ename = br.readLine() ;      
		System.out.print("3. job 입력 ? ");
		job = br.readLine() ;
		System.out.print("4. mgr 입력 ? ");
		mgr = Integer.parseInt( br.readLine() );
		System.out.print("5. hiredate 입력 ? ");
		hiredate = br.readLine() ;
		System.out.print("6. sal 입력 ? ");
		sal = Double.parseDouble(br.readLine()) ;
		System.out.print("7. comm 입력 ? ");
		comm = Double.parseDouble(br.readLine()) ;
		System.out.print("8. deptno 입력 ? ");
		deptno = Integer.parseInt( br.readLine() );

		EmpVO vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
		
		int rowCount = dao.add(vo);
		if(rowCount == 1) System.out.println("사원 추가 완료!!!");
		
	
		
		// 4. 수정
		/*
		int empno;
		String ename;
		String job;
		int mgr;
		double sal;
		double comm;
		int deptno;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		EmpVO vo = new EmpVO();
		
		System.out.print("> 수정할 사원 번호 입력 : ");
		empno = Integer.parseInt(br.readLine());
		
		System.out.print("> 수정할 사원 이름 입력 : ");
		ename = br.readLine();
		if(ename.equals("")) ename = vo.getEname();
		
		System.out.print("> 수정할 직업 입력 : ");
		job = br.readLine();
		if(job.equals("")) job = vo.getJob();
		
		System.out.print("> 수정할 관리자 입력 : ");
		try {
			mgr = Integer.parseInt(br.readLine());
		} catch (Exception e) {
			mgr = vo.getMgr();
		}
		
		System.out.print("> 수정할 월급 입력 : ");
		try {
			sal = Double.parseDouble(br.readLine());
		} catch (Exception e) {
			sal = vo.getSal();
		}
		
		System.out.print("> 수정할 커미션 입력 : ");
		try {
			comm = Double.parseDouble(br.readLine());
		} catch (Exception e) {
			comm = vo.getComm();
		}
		
		System.out.print("> 수정할 부서 번호 입력 : ");
		try {
			deptno = Integer.parseInt(br.readLine());  
		} catch (Exception e) {
			deptno = vo.getDeptno();
		}
		
		// 입력 x -> 예전의 컬럼값
		
		vo = new EmpVO(empno, ename, job, mgr, vo.getHiredate(), sal, comm, deptno);
		
		int rowCount = dao.update(vo);
		if(rowCount == 1) System.out.println("사원 수정 완료!!!");
		*/
		
		/*
		// 5. 삭제
		Scanner sc = new Scanner(System.in);
		System.out.print("> 삭제할 사원 번호 입력 ? ");
		int empno = sc.nextInt();
		int rowCount = dao.delete(empno);
		if(rowCount == 1) System.out.println("사원 삭제 완료!!!");
		*/
		
		DBConn.close();
		System.out.println("program exit");
	}

	private static void printEmpList(ArrayList<EmpVO> list) {
		if(list == null) {
			int count = list.size();
			System.out.println("> 사원 정보가 없음");
			return;
		}
		System.out.printf(" > 검색 결과 : %d개 \n", list.size());
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.println(vo);
		}
	}
}
