<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layoutTag" tagdir="/WEB-INF/tags"%>
<layoutTag:layout> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Form</title>
</head>
<body>

<div class="container">
	<div class="col-xs-12" style="margin:15px auto;">
		<label style="font-size:20px;"><span class="glyphicon glyphicon-list-alt"></span>게시글 목록</label>	
		<button class="btn btn-primary btn-sm" style="float:right;" onclick="location.href='/insert'">글쓰기</button>
	</div>	
	
	<div class="col-xs-12">
		<table class="table table-hover">
			<tr>
				<th>No</th>
				<th>Subject</th>
				<th>Writer</th>
				<th>Date</th>
			</tr>
			<c:forEach var="list" items="${list}">
				 <tr onclick="location.href='/detail/${list.bno}'">
	                  <td>${list.bno}</td>
	                  <td>${list.subject}</td>
	                  <td>${list.writer}</td>
	                  <td>
	                  	<fmt:formatDate value="${list.reg_date}" pattern="yyyy.MM.dd HH:mm:ss"/>
	                  </td>
	              </tr>
			</c:forEach> 
		</table>
	</div>
</div>
</body>
</html>
</layoutTag:layout>