<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, jstl.board.domain.Board, jstl.board.model.BoardConst"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${empty sessionScope.user}">
	location.href='boardclient.do?b=index';
</c:if>
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
	<hr width='1000' size='2' noshade>
	<h2>Board</h2>
		&nbsp;&nbsp;&nbsp;
	<a href='boardclient.do?b=input&type=<%=BoardConst.MAIN%>'>글쓰기</a>
		&nbsp;&nbsp;&nbsp;
	<a href='boardclient.do?b=index'>인덱스</a>
	<hr width='1000' size='2' noshade>
	</center>
<form>
<input type="hidden" name="b" value="list" />
<select id="option" name="ps" onChange="sumbit()" style="margin-left:103;">
	<option value="5" selected>=== 페이지 선택 ===</option>
	<option value="10">페이지 10개씩 보기</option>
	<option value="15">페이지 15개씩 보기</option>
</select>
</form>
	<table border='1' width='1000' align='center' cellpadding='2'>
	<tr>
		<th align='center' width='5%'>글번호</th>
		<th align='center' width='10%'>작성자</th>
		<th align='center' width='15%'>이메일</th>
		<th align='center' width='25%'>글제목</th>
		<th align='center' width='10%'>날짜</th>
		<th align='center' width='10%'>조회수</th>
	</tr>

<c:if test="${empty list}">
	<tr>
		<td colspan="6" style="text-align:center">데이터가 하나도 없어요</td>
	</tr>
</c:if>

<!--
<c:forEach items="${list}" var="board">
	<tr>
		<td align='center'>${board.postnumber}</td>
		<td align='center'>${board.writernickname}</td>
		<td align='center'>${board.writerid}</td>
		<td>
		<a href='boardclient.do?b=content&postNumber=${board.postnumber}'>${board.postsubject}</a>
		</td>
		<td align='center'>${board.pdate}</td>
		<td align='center'>${board.views}</td>
	</tr>
</c:forEach>
-->
<% 
 	
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	long ps = (Long)request.getAttribute("ps");
	long cp = (Long)request.getAttribute("cp");
	if(ps == -1) ps = 5;
	if(cp == -1) cp = 1;
	long size = (Long)request.getAttribute("size");
	if(list != null){
		long listSize = list.size();
		if(list.size() != 0){
			for(Board board: list){
%>
				<tr>
					<td align='center'><%=board.getPostnumber() %></td>
					<td align='center'><%=board.getWriternickname() %></td>
					<td align='center'><%=board.getWriterid() %></td>
					<td>
					<a href='boardclient.do?b=content&postNumber=<%=board.getPostnumber() %>'><%=board.getPostsubject() %></a>
					</td>
					<td align='center'><%=board.getPdate() %></td>
					<td align='center'><%=board.getViews() %></td>
				</tr>
<% 
			}
		}
	}
	ArrayList<Long> cpList = (ArrayList<Long>)request.getAttribute("cpList");
	long maxCp = 1;
	long minCp = 1;
	for(long lo: cpList){
		if(lo>maxCp){
			maxCp = lo;
		}
		if(lo<minCp){
			minCp = lo;
		}
	}
%>				
	<tr>
		<td colspan="3" align="center">

			<a id="back" style="display:inline-block" href="boardclient.do?b=list&cp=<%=cp-1%>&ps=<%=ps%>">◀이전</a>
	          	|
	        <a id="page1" href="boardclient.do?b=list&cp=<%=cpList.get(0) %>&ps=<%=ps%>">1</a>
	        <a id="page2" style="display:inline-block" href="boardclient.do?b=list&cp=<%=cpList.get(1) %>>&ps=<%=ps%>">2</a>
	        <a id="page3" style="display:inline-block" href="boardclient.do?b=list&cp=<%=cpList.get(2) %>&ps=<%=ps%>"><b>3</b></a>
			<a id="page4" style="display:inline-block" href="boardclient.do?b=list&cp=<%=cpList.get(3) %>&ps=<%=ps%>">4</a>
			<a id="page5" style="display:inline-block" href="boardclient.do?b=list&cp=<%=cpList.get(4) %>&ps=<%=ps%>">5</a>
				|
	        <a id="front" style="display:inline-block" href="boardclient.do?b=list&cp=<%=cp+1%>&ps=<%=ps%>">이후▶</a>
			&nbsp;&nbsp;&nbsp;
				<%=cp%>page/${maxPage}pages
			  </td>
			  <td colspan="2" align="center">
			    총 게시물 수 : <%=size%>
		</td>
	</tr>
	<script language=javascript>
       	let back = document.getElementById('back');
       	let page1 = document.getElementById('page1');
       	let page2 = document.getElementById('page2');
       	let page3 = document.getElementById('page3');
       	let page4 = document.getElementById('page4');
       	let page5 = document.getElementById('page5');
   		let front = document.getElementById('front');
   		
		if(<%=cpList.size()%> < 2){
			back.style.display = 'none';
	   		page1.style.display = 'inline-block';
	   		page2.style.display = 'inline-block';
	   		page3.style.display = 'inline-block';
	   		page4.style.display = 'inline-block';
	   		page5.style.display = 'inline-block';
		}else if(<%=cp%> == ${maxPage} && <%=cp%> == <%=maxCp%>){
			back.style.display = 'inline-block';
	   		page1.style.display = 'inline-block';
	   		page2.style.display = 'inline-block';
	   		page3.style.display = 'inline-block';
	   		page4.style.display = 'inline-block';
	   		page5.style.display = 'inline-block';
	   		front.style.display = 'none';
		}else if(<%=cp%>==3){
			
		}else if(<%=cp%>==4){
			
		}else if(<%=cp%>==5){
			
		}
   		
    </script>
</table>
<hr width='1000' size='2' noshade>
</center>