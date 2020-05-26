package com.example.demo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.board.mapper.BoardMapper;

@Controller
public class JspTest {
	
	/*
	 * @Resource :: 주입할 bean을 id로 지정하는 방법
	 */
	@Resource(name="com.example.demo.board.mapper.BoardMapper")
	BoardMapper mBoardMapper;
	
	@RequestMapping("/test")
	public String jspTest() throws Exception{
		
		System.out.println(mBoardMapper.boardCount());
		return "test";
	}

}
