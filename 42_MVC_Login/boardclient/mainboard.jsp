<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, bcm.board.domain.Board"%>


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
	<h2>Simple Board (MVC))</h2>
		&nbsp;&nbsp;&nbsp;
	<a href='boardclient.do?b=input'>글쓰기</a>
		&nbsp;&nbsp;&nbsp;
	<a href='boardclient.do?b=index'>인덱스</a>
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
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	if(list != null){
		if(list.size() != 0){
			for(Board dto: list){
%>
				<tr>
					<td align='center'><%=dto.getPostNumber()%></td>
					<td align='center'><%=dto.getWriterNickName()%></td>
					<td align='center'><%=dto.getWriterId()%></td>
					<td align='center'>
					<a href='boardclient.do?b=contentList&postNumber=<%=dto.getPostSubject()%>'><%=dto.getPostSubject()%></a>
					</td>
					<td align='center'><%=dto.getPdate()%></td>
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
	}else{
%>
		<tr>
			<td colspan="5" style="text-align:center">데이터가 하나도 없어요</td>
		</tr>
<%
	}
%>
	

</table>
<hr width='600' size='2' noshade>
</center>