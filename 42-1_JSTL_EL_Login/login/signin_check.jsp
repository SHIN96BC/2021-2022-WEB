<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<script>
		switch(${message}){
			case 0: 
				alert("반드시 모든값을 입력해주세요.");
				location.href='login.do?m=signin';
				break;
			case 1: 
				alert("유효하지않은 아이디입니다.");
				location.href='login.do?m=signin';
				break;
			case 2: 
				alert("닉네임이 이미 사용되고 있습니다.");
				location.href='login.do?m=signin';
				break;
			case 3:
				alert("회원가입이 완료되었습니다.");
				location.href='login.do?m=loginmenu';
				break;
			default: 
				location.href='login.do?m=signin';
				break;
		}
	</script>
