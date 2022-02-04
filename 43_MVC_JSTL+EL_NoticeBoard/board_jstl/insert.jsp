<%@ page contentType="text/html;charset=utf-8"%>

<%
	boolean flag = (Boolean)request.getAttribute("flag");
%>

	<script>
		if(<%=flag%>){
			alert("입력 성공(mv)");
		}else{
			alert("입력 실패(mv)");
		}
		location.href='board.do'
	</script>