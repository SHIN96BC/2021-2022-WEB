<%@ page contentType = 'text/html;charset=utf-8'%>

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
		정보를 입력해주세요
   </h1>
   <br/>
   <a href='../'>메인으로</a>
   <form name='f' action='' method='post'>
       <table border='1' width='300' height='200'>
	      <tr>
		     <td width='30%' colspan='2' align='center'><h2 style='margin-top:15;'>회원가입</h2></td> 
		  </tr>
		  <tr>
		     <th width='30%'>사용하실 아이디(이메일)</th> 
			 <td><input name='id' align='center' size='20' align='center' type='email'></td>
		  </tr>
		  <tr>
		     <th width='30%'>사용하실 패스워드</th> 
			 <td><input name='password' size='20' align='center' type='password'></td>
		  </tr>
		  <tr>
		     <th width='30%'>사용하실 닉네임</th> 
			 <td><input name='nickname' align='center' size='20' align='center'></td>
		  </tr>
		  <tr>
		     <th width='30%'>본인 이름</th> 
			 <td><input name='name' align='center' size='20' align='center'></td>
		  </tr>
		  <tr>
		     <th width='30%'>핸드폰 번호</th> 
			 <td><input name='phonenumber' align='center' size='20' align='center'></td>
		  </tr>
		  <tr>
		     <th width='30%'>주소</th> 
			 <td><input name='address' align='center' size='20' align='center'></td>
		  </tr>
		  <tr>
		     <td colspan='2' align='center'>
				 <input style='margin-top:5; margin-bottom:5;' type='submit' value='회원가입' onclick='javascript: form.action=/arrd';"/>
				 <br/>
			 </td> 
		  </tr>
	   </table>
   </form>
</center>
</body>