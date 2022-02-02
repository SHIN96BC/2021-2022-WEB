<%@ page contentType = 'text/html;charset=utf-8'%>
<head>
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
	
</head>
<body onload='document.f.name.focus()'>
<center>
   <h1 style='margin-top:200;'>
		정보를 입력해주세요
   </h1>
   <br/>
   <a href='login.do'>메인으로</a>
   <form name='logininput' action='login.do?m=insert' method='post' >
       <table border='1' width='500' height='200'>
	      <tr>
		     <td width='30%' colspan='2' align='center'><h2 style='margin-top:15;'>회원가입</h2></td> 
		  </tr>
		  <tr>
		     <th width='40%'>사용하실 아이디</th> 
			 <td><input name='id' align='center' size='37' align='center' minlength='5' type='email' placeholder='이메일을 입력해주세요.'></td>
		  </tr>
		  <tr>
		     <th width='40%'>사용하실 패스워드</th> 
			 <td><input name='password' size='37' align='center' type='password' minlength='8' maxlength='15' placeholder='비밀번호는 8자리 이상 입력해주세요.'></td>
		  </tr>
		  <tr>
		     <th width='40%'>사용하실 닉네임</th> 
			 <td><input name='nickname' align='center' size='37' align='center' minlength='2' maxlength='8' placeholder='사용하실 닉네임을 입력해주세요.'></td>
		  </tr>
		  <tr>
		     <th width='40%'>본인 이름</th> 
			 <td><input name='name' align='center' size='37' align='center' minlength='2' placeholder='이름을 입력해주세요.'></td>
		  </tr>
		  <tr>
		     <th width='40%'>휴대폰 번호</th> 
			 <td><input name='phonenumber' align='center' size='37' align='center' minlength='9' maxlength='11' placeholder=" - 없이 번호만 입력해주세요."></td>
		  </tr>
		  <tr>
		     <th width='40%'>주소</th> 
			 <td><input name='address' align='center' size='37' align='center' minlength='10' placeholder='상세주소까지 정확히 입력해주세요.'></td>
		  </tr>
		  <tr>
		     <td colspan='2' align='center'>
				 <input style='margin-top:5; margin-bottom:5;' type='submit' value='회원가입'/>
				 <br/>
			 </td> 
		  </tr>
	   </table>
	   <script type="text/javascript">
		   document.querySelector('form').onsubmit = function(event){
			   const msg = '유효하지 않는 전화번호입니다.';
			    // IE 브라우저에서는 당연히 var msg로 변경
			    var args = logininput.phonenumber.value;
			    var form = document.logininput;
			    if (/^[0-9]{2,3}[0-9]{3,4}[0-9]{4}/.test(args)) {
			        return true;
			    }else{
			    	alert(msg);
			    	return false;
			    }
		   }
		</script>
   </form>
</center>
</body>