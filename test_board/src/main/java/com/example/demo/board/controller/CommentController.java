package com.example.demo.board.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.board.domain.CommentVO;
import com.example.demo.board.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	/* @Resource :: Bean이름을 이용해서 주입할 객체를 검색한다. */
	@Resource(name="com.example.demo.board.service.CommentService")
	CommentService mCommentService;
	
	@RequestMapping("/list") //댓글 리스트
	/* @ResponseBody :: */
	@ResponseBody
	private List<CommentVO> mCommentServiceList(Model model, int bno) throws Exception {
		
		//model.addAttribute("comment", mCommentService.commentListService(bno));
		return mCommentService.commentListService(bno);
		//return "list";
	}
	
	@RequestMapping("/insert") //댓글 작성
	@ResponseBody
	private int mCommentServiceInsert(@RequestParam int bno, @RequestParam String content) throws Exception {
		
		CommentVO comment = new CommentVO();
		comment.setBno(bno);
		comment.setContent(content);
		comment.setWriter("Bae Hui Ju");
		
		return mCommentService.commentInsertService(comment);
	}
	
	@RequestMapping("/update") //댓글 수정
	@ResponseBody
	/* @RequestParam :: */
	private int mCommentServiceUpdateProc(@RequestParam int cno, @RequestParam String content) throws Exception {
		
		CommentVO comment = new CommentVO();
		comment.setCno(cno);
		comment.setContent(content);
		
		return mCommentService.commentUpdateService(comment);
	}
	
	/*  @PathVariable :: url경로에 변수를 넣어준다. */
	@RequestMapping("/delete/{cno}")
	@ResponseBody
	private int mCommentServiceDelete(@PathVariable int cno) throws Exception {
		
		return mCommentService.commentDeleteService(cno);
	}
	
	
}
