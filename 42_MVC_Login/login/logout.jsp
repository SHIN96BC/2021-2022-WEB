<%@ page contentType="text/html;charset=utf-8" import="bcm.member.domain.Member"%>
<%
	session.invalidate();
%>

<script language=javascript>
	alert("로그아웃 되었습니다.");
	location.href='login.do';
</script>