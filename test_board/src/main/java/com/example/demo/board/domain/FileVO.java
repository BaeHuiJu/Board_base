package com.example.demo.board.domain;

import lombok.Data;

@Data
public class FileVO {
	
	private int fno;
	private int bno;
	private String fileName;	//저잗할 파일
	private String fileOriName; //실제 파일
	private String fileUrl;
	
	

}
