<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, bcm.board.domain.Board"%>



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
	<a href='../board/board.do?b=input'>글쓰기</a>
	<hr width='600' size='2' noshade>
	<table border='1' width='600' align='center' cellpadding='3'>


<%		
		Board dto = null;
		ArrayList<Board> contentList = (ArrayList<Board>)request.getAttribute("contentList");
		if(contentList != null){
			if(contentList.size() != 0){
				for(Board dt: contentList){
					dto = dt;
%>
					<tr>
					<td width='100' align='center'>글번호</td>
					<td><%=dto.getPostNumber()%></td>
					</tr>
					<tr>
					<td align='center'>글쓴이</td>
					<td><%=dto.getWriterNickName()%></td>
					</tr>
					<tr>
					<td align='center'>이메일</td>
					<td><%=dto.getWriterId()%></td>
					</tr>
					<tr>
					<td align='center'>글제목</td>
					<td><%=dto.getPostSubject()%></td>
					</tr>
					<tr>
					<td align='center'>글내용</td>
					<td><%=dto.getPostContent()%></td>
					</tr>
					<tr>
					<th width='100' align='center'>날짜</th>
					<td><%=dto.getPdate()%></td>
					</tr>
					
<%			
				}
			}
		}
%>

</table>
<hr width='600' size='2' noshade>
<b>
<a  href='../board/board.do?b=updateList&seq='>수정</a>
 | 
<a href='../board/board.do?b=delete&seq='>삭제</a>
 | 
<a href='../board/board.do'>목록</a>
</b>
<hr width='600' size='2' noshade>
</center>