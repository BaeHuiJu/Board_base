package com.example.demo.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.domain.BoardVO;
import com.example.demo.board.domain.FileVO;
import com.example.demo.board.service.BoardService;

@Controller
public class BoardController {

	@Resource(name="com.example.demo.board.service.BoardService")
	BoardService mBoardService;
	
	@Value("${file.upload.directory}")
	String uploadFileDir;
	
	@RequestMapping("/list")	//view의 경로 지정
	private String boardList(Model model) throws Exception{
		
		model.addAttribute("list", mBoardService.boardListService()); //(변수이름,변수에 넣을 데이터값)

		return "list";
	}
	/*
	 * @PathVariable :: url경로에 변수를 넣어준다.
	 */
	@RequestMapping("/detail/{bno}")
	private String boardDetail(@PathVariable int bno, Model model) throws Exception {
		
		model.addAttribute("detail", mBoardService.boardDetailService(bno));
		model.addAttribute("files", mBoardService.fileDetailService(bno));
		
		return "detail";
	}
	
	@RequestMapping("/insert")
	private String boardInsertForm() {
		
		return "insert";
	}
	
	/*
	 * HttpServletRequest :: 객체가 소멸하기 까지 상태정보를 유지하고자 할때, 한번의 요청으로 실행된 페이지끼리 정보를 공유하고자 할때 사용된다.
	 * MultipartFile :: 업로드한 파일을 표현할 때 사용되는 interface
	 * @RequestPart :: multipart 요청의 경우 웹요청 파라미터와 맵핑가능한 어노테이션
	 */
	@RequestMapping("/insertProc")
	private String boardInsertProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception {
		
		BoardVO board = new BoardVO();
		FileVO file = new FileVO();
		 
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		board.setWriter(request.getParameter("writer"));
		
		if(files.isEmpty()) { //업로드 할 파일이 없을 시
			mBoardService.boardInsertService(board); //게시글 insert
		} else {
		/* 
		 * getOriginalFileName :: 업로드한 파일의 실제이름을 구한다
		 * FilenameUtils.getExtension :: 확장자를 구해준다.
		 */
		String sourceFileName = files.getOriginalFilename(); 
		String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
		File destinationFile;
		String destinationFileName;
		//String fileUrl = "D:/study_huiju/test_board/src/main/webapp/WEB-INF/uploadFiles/";
		
		do {
			//RandomStringUtils.randomAlphanumeric :: 대소문자, 숫자를 32개 만큼 랜덤으로 생성
			destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
			destinationFile = new File(uploadFileDir + destinationFileName);
		} while (destinationFile.exists());
		
		// getParentFile :: 현재 파일, 디렉토리의 부모를 파일 객체로 리턴한다.
		// mkdirs() :: 존재하지 않는 부모 폴더까지 포함하여 해당 경로에 폴더를 만든다.
		// transferTo :: 요펑 시점의 임시파일을 로컬 파일 시스템에 영구적으로 복사 , 단 한번만 실행되며 두번쨰 실행부터는 성공을 보장할 수 없다.  
		destinationFile.getParentFile().mkdirs();
		files.transferTo(destinationFile);
		
		mBoardService.boardInsertService(board);
		
		file.setBno(board.getBno());
		file.setFileName(destinationFileName);
		file.setFileOriName(sourceFileName);
		file.setFileUrl(uploadFileDir);
		
		mBoardService.fileInsertService(file); //file insert
		} 
		//redirect :: 다른페이지로 이동하라고 명령
		return "redirect:/list";
	}
	
	@RequestMapping("/update/{bno}") 
	private String boardUpdateForm(@PathVariable int bno, Model model) throws Exception {
		
		model.addAttribute("detail", mBoardService.boardDetailService(bno));
		
		return "update";
	}
	
	@RequestMapping("/updateProc")
	private String boardUpdateProc(HttpServletRequest request) throws Exception {
		
		BoardVO board = new BoardVO();
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		board.setBno(Integer.parseInt(request.getParameter("bno")));
		
		mBoardService.boardUpdateService(board);
		
		return "redirect:/detail/" + request.getParameter("bno");
	}
	
	@RequestMapping("/delete/{bno}")
	private String boardDelete(@PathVariable int bno) throws Exception {
		
		mBoardService.boardDeleteService(bno);
		
		return "redirect:/list";
	}
	
	@RequestMapping("/fileDown/{bno}")
	private void fileDown(@PathVariable int bno, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		FileVO fileVO = mBoardService.fileDetailService(bno);
		
		try {
				
			String fileUrl = fileVO.getFileUrl(); //파일 업로드 된 경로
			fileUrl += "/";
			String savePath = fileUrl;
			String fileName = fileVO.getFileName();
			
			String oriFileName = fileVO.getFileOriName(); //실제 내보낼  파일명
			InputStream in = null;  // 바이트 단위로 데이터를 읽는다. 
			OutputStream os = null; // 외부로 데이터를 전송한다.
			File file = null;
			boolean skip = false;
			String client = "";
			
			//파일을 읽어 스트림에 담기
			try {
				//File(File parent, String child) :: parent객체 폴더의 child라는 파일에 대한 File 객체를 생성한다.
				file = new File(savePath, fileName); // 파일 업로드 경로의 파일이름에 대한 File 객체를 생성
				in = new FileInputStream(file); // FileInputStream :: File 객체가 가리키는 파일을 바이트 스트림으로 읽기 위한 FileInputStream 객체를 생성
			} catch (FileNotFoundException fe) {
				skip = true;
			}
			
			client = request.getHeader("User-Agent"); //브라우저 알아내는 용도 ex)IE, Chrome, FireFox....
			
			//파일 다운로드 헤어 지정
			response.reset();
			//MIME type(type/sybtype) :: 알려진 서브타입이 없는 이진 문서에 대해서는 application/octet-stream이 쓰임 :: application = 모든 종류의 이진 데이터를 나타냅니다
			response.setContentType("application/octet-stream"); 
			response.setHeader("Contnet-Descrption", "JSP Generated Data"); //Contnet-Descrption의 값을 "JSP Generated Data"로 지정한다.
			
			if(!skip) {
				//IE
				/*
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(oriFileName, "UTR-8").replaceAll("\\+", "\\ ") + "\"");
				//IE 11이상
				} else if (client.indexOf("Trident") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(oriFileName, "UTR-8").replaceAll("\\+", "\\ ") + "\"");
				}
				
				*/
				if (client.indexOf("MSIE") != -1 || client.indexOf("Trident") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				} else {
					//한글 파일명 처리
					response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}
				response.setHeader("Content-Length", "" + file.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int) file.length()];
				int leng = 0;
				while ((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			} else {
				response.setContentType("text/html);charset=UTF-8");
				System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
			}
			in.close();
			os.close();
		} catch (Exception e) {
			System.out.println("ERROR : " + e.getMessage());
		}
	}
}
