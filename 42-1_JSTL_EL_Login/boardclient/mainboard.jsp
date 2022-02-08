<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, jstl.board.domain.Board, jstl.board.model.BoardConst"%>
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
	<h2>Board</h2>
		&nbsp;&nbsp;&nbsp;
	<a href='boardclient.do?b=input&type=<%=BoardConst.MAIN%>'>글쓰기</a>
		&nbsp;&nbsp;&nbsp;
	<a href='boardclient.do?b=index'>인덱스</a>
	<hr width='800' size='2' noshade>
	</center>
	<table border='1' width='800' align='center' cellpadding='2'>
	<tr>
		<th align='center' width='10%'>글번호</th>
		<th align='center' width='10%'>작성자</th>
		<th align='center' width='20%'>이메일</th>
		<th align='center' width='25%'>글제목</th>
		<th align='center' width='15%'>날짜</th>
		<th align='center' width='10%'>조회수</th>
	</tr>

<c:if test="${empty list}">
	<tr>
		<td colspan="5" style="text-align:center">데이터가 하나도 없어요</td>
	</tr>
</c:if>

<c:forEach items="${list}" var="board">
	<tr>
		<td align='center'>${board.postnumber}</td>
		<td align='center'>${board.writernickname}</td>
		<td align='center'>${board.writerid}</td>
		<td align='center'>
		<a href='boardclient.do?b=content&postNumber=${board.postnumber}'>${board.postsubject}</a>
		</td>
		<td align='center'>${board.pdate}</td>
		<td align='center'>${board.views}</td>
	</tr>
</c:forEach>

</table>
<hr width='800' size='2' noshade>
</center>