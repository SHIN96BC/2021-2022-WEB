<%@ page contentType="text/html;charset=utf-8"%>
<jsp:useBean id ="boardDAO" class="sbc.mv.model.BoardDAO" scope="application"/>
<jsp:useBean id ="dto" class="sbc.mv.model.BoardDTO"/>
<jsp:setProperty name="dto" property="*"/>

<%
	boolean flag = boardDAO.insert(dto);
%>
	<script>
		if(<%=flag%>){
			alert("입력 성공(mv)");
		}else{
			alert("입력 실패(mv)");
		}
		location.href='list.jsp'
	</script>