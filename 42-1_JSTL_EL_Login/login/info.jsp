<%@ page contentType="text/html;charset=utf-8" import="jstl.member.domain.Member"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



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
	

<c:if test="${empty sessionScope.user}">
	<script>
		location.href='login.do';
	</script>
</c:if>

<table border='1' width='600' align='center' cellpadding='3'>
	<tr>
		<td width='100' align='center'>ID</td>
		<td>${sessionScope.user.id}</td>
		</tr>
		<tr>
		<td align='center'>닉네임</td>
		<td>${sessionScope.user.nickname}</td>
		</tr>
		<tr>
		<td align='center'>이름</td>
		<td>${sessionScope.user.name}</td>
		</tr>
		<tr>
		<td align='center'>휴대폰번호</td>
		<td>${sessionScope.user.phonenumber}</td>
		</tr>
		<tr>
		<td align='center'>주소</td>
		<td>${sessionScope.user.address}</td>
		</tr>
		<tr>
		<th width='100' align='center'>가입날짜</th>
		<td>${sessionScope.user.cdate}</td>
	</tr>
	</table>
	<hr width='600' size='2' noshade>
	<b>
	<a  href='login.do?m=infoUpdate&userId=${sessionScope.user.id}'>수정</a>
	</b>

<hr width='600' size='2' noshade>
</center>


