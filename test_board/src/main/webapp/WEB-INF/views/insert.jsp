<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="layoutTag" tagdir="/WEB-INF/tags"%>
<layoutTag:layout> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert Form</title>
</head>
<body>

<div class="container">
	<form action="/insertProc" method="post" enctype="multipart/form-data">
		<div class="form-group">
			<label for="subject">제목</label>
			<input type="text" class="form-control" id="subject" name="subject" placeholder="제목을 입력해주세요.">
		</div>
		<div class="form-group">
			<label for="writer">작성자</label>
			<input type="text" class="form-control" id="writer" name="writer" placeholder="제목을 입력해주세요.">
		</div>
		<div class="form-group">
			<label for="content">내용</label>
			<textarea class="form-control" id="content" name="content" rows="15"></textarea>
		</div>
			
		<div class="form-group">
			<label for="file">파일 업로드</label>
			<input type="file" id="file" name="files">
		</div>
		
		<button type="submit" class="btn btn-primary btn-sm"  style="float:right;">작성</button>
		
	</form>
</div>
</body>
</html>
</layoutTag:layout>