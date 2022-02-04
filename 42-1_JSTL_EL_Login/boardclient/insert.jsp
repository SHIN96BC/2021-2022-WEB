<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	if(${flag}){
		alert("작성 완료");
	}else{
		alert("작성 실패");
	}
	location.href='boardclient.do'
</script>