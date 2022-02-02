<%@ page contentType="text/html;charset=utf-8" import="bcm.member.domain.Member"%>



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
	내 정보
	</h1>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='login.do'>메인으로 돌아가기</a>
	<hr width='600' size='2' noshade>
	<table border='1' width='600' align='center' cellpadding='3'>


<%		
		Member member = (Member)session.getAttribute("user");
		if(member !=  null){
%>
			<tr>
			<td width='100' align='center'>ID</td>
			<td><%=member.getId()%></td>
			</tr>
			<tr>
			<td align='center'>닉네임</td>
			<td><%=member.getNickName()%></td>
			</tr>
			<tr>
			<td align='center'>이름</td>
			<td><%=member.getName()%></td>
			</tr>
			<tr>
			<td align='center'>휴대폰번호</td>
			<td><%=member.getPhoneNumber()%></td>
			</tr>
			<tr>
			<td align='center'>주소</td>
			<td><%=member.getAddress()%></td>
			</tr>
			<tr>
			<th width='100' align='center'>가입날짜</th>
			<td><%=member.getCdate()%></td>
			</tr>
			
<%			
		}else{
			response.sendRedirect("login.do");
		}
%>

</table>
<hr width='600' size='2' noshade>
<b>
<a  href=''>수정</a>
</b>
<hr width='600' size='2' noshade>
</center>