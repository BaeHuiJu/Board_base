package com.example.demo.board.domain;

import java.sql.Date;
import lombok.Data;

@Data 
public class BoardVO {
	
	private int bno;
	private String subject;
	private String content;
	private String writer;
	private Date reg_date;

}
