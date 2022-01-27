<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, sbc.mv.model.BoardDTO"%>
<jsp:useBean id="boardDAO" class="sbc.mv.model.BoardDAO" scope="application"/>


<%! 
	private int getSeq(HttpServletRequest request){
		int seq = -1;
		String seqStr = request.getParameter("seq");
		if(seqStr != null){
			seqStr = seqStr.trim();
			if(seqStr.length() != 0){
				try{
					seq = Integer.parseInt(seqStr);
					return seq;
				}catch(NumberFormatException nfe){
				}
			}
		}
		return seq;
	}
%>

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
	Simple Board (mv)
	</h1>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='input.jsp'>글쓰기</a>
	<hr width='600' size='2' noshade>
	<table border='1' width='600' align='center' cellpadding='3'>


<%
	int seq = getSeq(request);
	if(seq == -1){
		response.sendRedirect("list.jsp");
	}else{
		ArrayList<BoardDTO> contentList = boardDAO.content(seq);
		if(contentList != null){
			if(contentList.size() != 0){
				for(BoardDTO bt: contentList){
%>
					<tr>
					<td width='100' align='center'>글번호</td>
					<td><%=seq%></td>
					</tr>
					<tr>
					<td align='center'>글쓴이</td>
					<td><%=bt.getWriter()%></td>
					</tr>
					<tr>
					<td align='center'>이메일</td>
					<td><%=bt.getEmail()%></td>
					</tr>
					<tr>
					<td align='center'>글제목</td>
					<td><%=bt.getSubject()%></td>
					</tr>
					<tr>
					<td align='center'>글내용</td>
					<td><%=bt.getContent()%></td>
					</tr>
					<tr>
					<th width='100' align='center'>날짜</th>
					<td><%=bt.getRdate()%></td>
					</tr>
<%
				}
			}
		}
	}
			
%>
		

	
<%
	
%>

</table>
<hr width='600' size='2' noshade>
<b>
<a  href='update.jsp?seq=<%=seq%>'>수정</a>
 | 
<a href='delete.jsp?seq=<%=seq%>'>삭제</a>
 | 
<a href='list.jsp'>목록</a>
</b>
<hr width='600' size='2' noshade>
</center>