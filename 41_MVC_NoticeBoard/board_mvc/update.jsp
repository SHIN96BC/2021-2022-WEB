<%@ page contentType="text/html;charset=utf-8"  import="java.util.ArrayList, mvc.domain.Board" %>


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
		<h2>Simple Board (MVC)</h2>
		&nbsp;&nbsp;&nbsp;
		<a href='../board/board.do'>글목록</a>
		<hr width='600' size='2' noshade>
		<form name='f' method='post' action='../board/board.do?b=update'>

<%
		
		ArrayList<Board> updateList = (ArrayList<Board>)request.getAttribute("updateList");
		if(updateList != null){
			if(updateList.size() != 0){
				for(Board dto: updateList){
%>
					<input type='hidden' name='seq' value='<%=dto.getSeq()%>'>
					<input type='hidden' name='writer' value="'<%=dto.getWriter()%>'">
					<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>
					<td width='30%' align='center'>글쓴이</td>
					<td align='center'><input type='text' name='aa' size='60' value='<%=dto.getWriter()%>' disabled></td>
					</tr>
					<tr>
					<td width='30%' align='center'>이메일</td>
					<td align='center'><input type='text' name='email' size='60' value='<%=dto.getEmail()%>'></td>
					</tr>
					<tr>
					<td width='30%' align='center'>글제목</td>
					<td align='center'><input type='text' name='subject' size='60' value='<%=dto.getSubject()%>'></td>
					</tr>
					<tr>
					<td width='30%' align='center'>글내용</td>
					<td align='center'><textarea name='content' rows='5' cols='53'><%=dto.getContent()%></textarea></td>
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

	<tr>
	<td colspan='2' align='center'>
	<input type='submit' value='수정'>
	</td>
	</tr>
	</table>
	</form>
	</table>