<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/login-style.css"%></style>
    <title>WebHospital</title>
</head>

<body>
		
	<form action="sign-in" class="login-form" method="POST">
      <h1>Login</h1>

	  <a class="label">Username</a>
      <div class="txtb">
        <input type="text" name="username">
      </div>

      <a class="label">Password</a>
      <div class="txtb">
        <input type="password" name="password">
      </div>

      <input type="submit" class="logbtn" value="Sign in">
      
      <div class="bottom-text">Do not have an account? <a href="#" class="redirect-href">Sign up</a></div>
      <div class="bottom-error-text"></div>
    </form>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>

</body>
</html>