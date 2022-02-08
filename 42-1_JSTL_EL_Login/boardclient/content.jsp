<%@ page contentType="text/html;charset=utf-8" import="jstl.board.domain.Board, jstl.member.domain.Member, jstl.board.model.BoardConst"%>
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
	Board
	</h1>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='boardclient.do?b=input'>글쓰기</a>
	<hr width='600' size='2' noshade>
	<table border='1' width='600' align='center' cellpadding='3'>

<c:if test="${empty board}">
	<script>
		location.href='boardclient.do?b=list'
	</script>
</c:if>

	<tr>
	<td width='100' align='center'>글번호</td>
	<td>${board.postnumber}</td>
	</tr>
	<tr>
	<td align='center'>글쓴이</td>
	<td>${board.writernickname}</td>
	</tr>
	<tr>
	<td align='center'>이메일</td>
	<td>${board.writerid}</td>
	</tr>
	<tr>
	<td align='center'>글제목</td>
	<td>${board.postsubject}</td>
	</tr>
	<tr>
	<td align='center'>글내용</td>
	<td>${board.postcontent}</td>
	</tr>
	<tr>
	<th width='100' align='center'>날짜</th>
	<td>${board.pdate}</td>
	</tr>
	<th width='100' align='center'>조회수</th>
	<td>${board.views}</td>
	</tr>

</table>
<hr width='600' size='2' noshade>
<b>
<a id="postUpdate" style="display:none;" href='boardclient.do?b=updateList&postNumber=${board.postnumber}'>수정</a>
<a id="postDelete" style="display:none; margin-left:15;" href='boardclient.do?b=delete&postNumber=${board.postnumber}'>삭제</a>
<a style="margin-left:15;" href='boardclient.do?b=list'>목록</a>
<a style="margin-left:15;" href='boardclient.do?b=input&type=<%=BoardConst.RE%>&postNumber=${board.postnumber}'>답글</a>
</b>
<hr width='600' size='2' noshade>
</center>
	
<c:choose>
	<c:when test="${sessionScope.user.id eq board.writerid}">
		<script>
			let postUpdate = document.getElementById('postUpdate');
	        postUpdate.style.display = 'inline-block';
	        let postDelete = document.getElementById('postDelete');
        	postDelete.style.display = 'inline-block';
	    </script>
	</c:when>
	<c:when test="${sessionScope.user.authority eq 0 or sessionScope.user.authority eq 1}">
		<script>
	        let postDelete = document.getElementById('postDelete');
        	postDelete.style.display = 'inline-block';
	    </script>
	</c:when>
</c:choose>