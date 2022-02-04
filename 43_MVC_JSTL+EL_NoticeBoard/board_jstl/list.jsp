<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, mvc.domain.Board"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta charset='utf-8'>
<style>
	table, th, td {
	   border: 1px solid black;
	   border-collapse: collapse;
	}
	th, td {
	   padding: 5px;
	}
	a { text-decoration:none }
</style>
<center>
	<hr width='600' size='2' noshade>
	<h2>Simple Board JSTL + EL)</h2>
		&nbsp;&nbsp;&nbsp;
	<a href='board.do?b=input'>글쓰기</a>
		&nbsp;&nbsp;&nbsp;
	<a href='../'>인덱스</a>
	<hr width='600' size='2' noshade>
	</center>
	<table border='1' width='600' align='center' cellpadding='2'>
	<tr>
		<th align='center' width='10%'>글번호</th>
		<th align='center' width='15%'>작성자</th>
		<th align='center' width='30%'>이메일</th>
		<th align='center' width='30%'>글제목</th>
		<th align='center' width='15%'>날짜</th>
	</tr>

<c:if test="${empty list}">
	<tr>
		<td colspan="5" style="text-align:center">데이터가 하나도 없어요</td>
	</tr>
</c:if>

<c:forEach items="${list}" var="board">
	<tr>
		<td align='center'>${board.seq}</td>
		<td align='center'>${board.writer}</td>
		<td align='center'>${board.email}</td>
		<td align='center'>
		<a href='board.do?b=contentList&seq=${board.seq}'>${board.subject}</a>
		</td>
		<td align='center'>${board.rdate}</td>
	</tr>
</c:forEach>
	

</table>
<hr width='600' size='2' noshade>
</center>