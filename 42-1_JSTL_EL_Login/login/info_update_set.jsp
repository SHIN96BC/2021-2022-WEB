<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<script>
		if(${flag}){
			alert("개인정보 수정완료");
		}else{
			alert("개인정보 수정실패");
		}
		location.href='login.do'
	</script>