<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, sbc.mv.model.BoardDTO"%>
<jsp:useBean id="boardDAO" class="sbc.mv.model.BoardDAO" scope="application"/>

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
	<h2>Simple Board (mv))</h2>
		&nbsp;&nbsp;&nbsp;
	<a href='input.jsp'>글쓰기</a>
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


<%
	ArrayList<BoardDTO> list = boardDAO.list();
	if(list != null){
		if(list.size() != 0){
			for(BoardDTO bt: list){
%>
				<tr>
				<td align='center'><%=bt.getSeq()%></td>
				<td align='center'><%=bt.getWriter()%></td>
				<td align='center'><%=bt.getEmail()%></td>
				<td align='center'>
				<a href='content.jsp?seq=<%=bt.getSeq()%>'><%=bt.getSubject()%></a>
				</td>
				<td align='center'><%=bt.getRdate()%></td>
				</tr>
<%
			}
		}else{
%>
			<tr>
			<td colspan="5" style="text-align:center">데이터가 하나도 없어요</td>
			</tr>
<%
		}
	}
%>
	

</table>
<hr width='600' size='2' noshade>
</center>