<%@ page contentType = 'text/html;charset=utf-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<body onload='document.f.name.focus()'>
<center>
   <h1 style='margin-top:200;'>
		로그인정보 입력 
   </h1>
   <br/>
   <a href='login.do'>메인으로</a>
   <form name='f' action='' method='post'>
       <table border='1' width='300' height='200'>
	      <tr>
		     <td width='30%' colspan='2' align='center'><h2>로그인</h2></td> 
		  </tr>
		  <tr>
		     <th width='30%'>ID</th> 
			 <td><input name='id' align='center' size='20' align='center'></td>
		  </tr>
		  <tr>
		     <th width='30%'>PWD</th> 
			 <td><input name='password' size='20' align='center' type='password'></td>
		  </tr>
		  <tr>
		     <td colspan='2' align='center'>
			     <input style='margin-top:5; margin-bottom:5;' type='submit' value='로그인' onclick="javascript: form.action='login.do?m=login';"/>
				 <input style='margin-top:5; margin-bottom:5;' type='submit' value='회원가입' onclick="javascript: form.action='login.do?m=signin';"/>
				 <br/>
				 <a href='' style='font-size:x-small;'>ID / PWD 변경</a>
			 </td> 
		  </tr>
	   </table>
   </form>
</center>
</body>