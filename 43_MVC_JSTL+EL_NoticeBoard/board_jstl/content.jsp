<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, mvc.domain.Board"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<meta charset='utf-8'>
	<style>
	table, th, td {
	border: 1px solid black
	border-collapse: collapse;
	}
	th, td {
	padding: 5px;
	}
	a { text-decoration:none }
	</style>
	<center>
	<hr width='600' size='2' noshade>
	<h1>
	Simple Board (MVC)
	</h1>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='board.do?b=input'>글쓰기</a>
	<hr width='600' size='2' noshade>
	<table border='1' width='600' align='center' cellpadding='3'>

<c:if test="${empty contentList}">
	<tr>
		<td colspan="5" style="text-align:center">데이터가 하나도 없어요</td>
	</tr>
</c:if>

<c:forEach items="${contentList}" var="board">
	<tr>
		<td width='100' align='center'>글번호</td>
		<td>${board.seq}</td>
		</tr>
		<tr>
		<td align='center'>글쓴이</td>
		<td>${board.writer}</td>
		</tr>
		<tr>
		<td align='center'>이메일</td>
		<td>${board.email}</td>
		</tr>
		<tr>
		<td align='center'>글제목</td>
		<td>${board.subject}</td>
		</tr>
		<tr>
		<td align='center'>글내용</td>
		<td>${board.content}</td>
		</tr>
		<tr>
		<th width='100' align='center'>날짜</th>
		<td>${board.rdate}</td>
	</tr>

	</table>
	<hr width='600' size='2' noshade>
	<b>
	<a  href='board.do?b=updateList&seq=${board.seq}'>수정</a>
	 | 
	<a href='board.do?b=delete&seq=${board.seq}'>삭제</a>
	 | 
	<a href='board.do'>목록</a>
	</b>
	<hr width='600' size='2' noshade>
</c:forEach>
</center>