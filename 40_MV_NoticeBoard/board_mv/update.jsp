<%@ page contentType="text/html;charset=utf-8"  import="java.util.ArrayList, sbc.mv.model.BoardDTO" %>
<jsp:useBean id ="boardDAO" class="sbc.mv.model.BoardDAO" scope="application"/>
<jsp:useBean id ="dto" class="sbc.mv.model.BoardDTO"/>
<jsp:setProperty name="dto" property="*"/>

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
		<h2>Simple Board (mv)</h2>
		&nbsp;&nbsp;&nbsp;
		<a href='list.jsp'>글목록</a>
		<hr width='600' size='2' noshade>
		<form name='f' method='post' action='update.jsp'>


<%
		String writerTemp = request.getParameter("writer");
		if(writerTemp != null) {
			boolean flag = boardDAO.update(dto);
			if(flag){
%>
				<script>
					if(<%=flag%>){
						alert("입력 성공(mv)");
					}else{
						alert("입력 실패(mv)");
					}
					location.href='list.jsp'
				</script>
<%
				response.sendRedirect("list.jsp");
			}else{
				response.sendRedirect("list.jsp");
			}
		}
		
		String seqStr = request.getParameter("seq");
		int seq = getSeq(request);
		if(seq == -1){
			response.sendRedirect("list.jsp");
		}else{
			ArrayList<BoardDTO> updateList = boardDAO.updateList(seq);
			if(updateList != null){
				if(updateList.size() != 0){
					for(BoardDTO bt: updateList){
%>
						<input type='hidden' name='seq' value='<%=bt.getSeq()%>'>
						<input type='hidden' name='writer' value="'<%=bt.getWriter()%>'">
						<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>
						<td width='30%' align='center'>글쓴이</td>
						<td align='center'><input type='text' name='aa' size='60' value='<%=bt.getWriter()%>' disabled></td>
						</tr>
						<tr>
						<td width='30%' align='center'>이메일</td>
						<td align='center'><input type='text' name='email' size='60' value='<%=bt.getEmail()%>'></td>
						</tr>
						<tr>
						<td width='30%' align='center'>글제목</td>
						<td align='center'><input type='text' name='subject' size='60' value='<%=bt.getSubject()%>'></td>
						</tr>
						<tr>
						<td width='30%' align='center'>글내용</td>
						<td align='center'><textarea name='content' rows='5' cols='53'><%=bt.getContent()%></textarea></td>
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
		}	

%>

	<tr>
	<td colspan='2' align='center'>
	<input type='submit' value='수정'>
	</td>
	</tr>
	</table>
	</form>
	</table>