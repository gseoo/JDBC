package com.util;

public class ConvertLowerColumnName {
	public static void main(String[] args) {
		String descEmp = 
				"SURVEY_NO  NOT NULL NUMBER(3)      \r\n"
				+ "USER_NO    NOT NULL NUMBER(7)      \r\n"
				+ "REGDATE    NOT NULL DATE           \r\n"
				+ "START_DATE NOT NULL DATE           \r\n"
				+ "END_DATE   NOT NULL DATE           \r\n"
				+ "QUESTION   NOT NULL NVARCHAR2(200) \r\n"
				+ "CATE_COUNT NOT NULL NUMBER(2)  ";
		String lowerDescEmp = descEmp.toLowerCase();
		String regex = "\\s+.+\\s+";
		lowerDescEmp = lowerDescEmp.replaceAll(regex, ";\r\n");
		System.out.println(lowerDescEmp);
	}
}
