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
	<form action="login" class="login-form" method="POST">
      <h1>Login</h1>

      <div class="txtb">
        <input type="text" name="username" placeholder="username">
      </div>

      <div class="txtb">
        <input type="password" name="password" placeholder="password">
      </div>

      <input type="submit" class="logbtn" value="Sign up">
    </form>

</body>
</html>