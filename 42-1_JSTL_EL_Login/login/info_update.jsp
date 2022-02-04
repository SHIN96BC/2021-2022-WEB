<%@ page contentType="text/html;charset=utf-8"  import="java.util.ArrayList, jstl.member.domain.Member" %>
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
		<a href='login.do'>메인으로</a>
		<hr width='600' size='2' noshade>
		<form name='f' method='post' action='login.do?m=infoUpdateSet'>
<c:if test="${empty sessionScope.user}">
	<tr>
		<td colspan="5" style="text-align:center">데이터가 하나도 없어요</td>
	</tr>
</c:if>
		<input type='hidden' name='infoId' value='${sessionScope.user.id}'>
		<table border='1' width='600' align='center' cellpadding='3' cellspacing='1'><tr>
		<tr>
		<td width='30%' align='center'>ID</td>
		<td align='center'><input type='text' name='ii' size='60' value='${sessionScope.user.id}' disabled></td>
		</tr>
		<tr>
		<td width='30%' align='center'>PASSWORD</td>
		<td align='center'><input type='text' name='infoPassword' size='60' value='${sessionScope.user.password}'></td>
		</tr>
		<tr>
		<td width='30%' align='center'>닉네임</td>
		<td align='center'><input type='text' name='infoNickName' size='60' value='${sessionScope.user.nickname}'></td>
		</tr>
		<tr>
		<td width='30%' align='center'>이름</td>
		<td align='center'><input type='text' name='in' size='60' value='${sessionScope.user.name}' disabled></td>
		</tr>
		<tr>
		<td width='30%' align='center'>휴대폰번호</td>
		<td align='center'><input type='text' name='infoPhoneNumber' size='60' value='${sessionScope.user.phonenumber}'></td>
		</tr>
		<tr>
		<td width='30%' align='center'>주소</td>
		<td align='center'><input type='text' name='infoAddress' size='60' value='${sessionScope.user.address}'></td>
		</tr>
		<tr>
		<td width='30%' align='center'>가입날짜</td>
		<td align='center'><input type='text' name='infoCdate' size='60' value='${sessionScope.user.cdate}'></td>
		</tr>
	<tr>
	<td colspan='2' align='center'>
	<input type='submit' value='수정'>
	</td>
	</tr>
	</table>
	</form>
	</table>