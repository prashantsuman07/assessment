<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>  
    <%String msg=(String)request.getAttribute("msg"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 
<fieldset>
<legend>EMPLOYEE Register</legend>
<form method='POST' action='./register'>

		<table border="1" align='center'>
			<tr>
				<td>username:</td>
				<td><input type='text' name='uname' required></td>
			</tr>
			<tr>
				<td>new pswd:</td>
				<td><input type='password' name='pswd' required></td>
			</tr>
			<tr>
				<td>age</td>
				<td><input type='number' name='age' required></td>
			</tr>
			<tr>
				<td>gender</td>
				<td><input type='text' name='gender' required></td>
			</tr>
			<tr>
				
				<td colspan="3" align="center">
				<input type=submit value='register'></td>
			</tr>
		</table>
</form>
</fieldset>	
</body>
</html>