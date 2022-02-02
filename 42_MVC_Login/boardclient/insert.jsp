<%@ page contentType="text/html;charset=utf-8"%>

<%
	boolean flag = (Boolean)request.getAttribute("flag");
%>
	<script>
		if(<%=flag%>){
			alert("작성 완료");
		}else{
			alert("작성 실패");
		}
		location.href='boardclient.do'
	</script>