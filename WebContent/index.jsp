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
	
		<div class="footer">
			<div class="admin-info">
				<a target="_blank" rel="noopener noreferrer" href="https://vk.com/prague15031939" class="admin-link"><i class="fab fa-vk"></i></a>
				<a target="_blank" rel="noopener noreferrer" href="https://www.youtube.com/channel/UC3YnNKk6I3EZ6OK6ZM3C56g" class="admin-link"><i class="fab fa-youtube"></i></a>
				<a target="_blank" rel="noopener noreferrer" href="https://www.instagram.com/vienna12031938/" class="admin-link"><i class="fab fa-instagram"></i></a>
				<a target="_blank" rel="noopener noreferrer" href="https://github.com/prague15031939" class="admin-link"><i class="fab fa-github"></i></a>	
			</div>
			<div class="hospital-logo">
				&copy; 2020, prague15031939
			</div>
		</div>
		
		<script src="https://kit.fontawesome.com/5d6364b770.js" crossorigin="anonymous"></script>

</body>
</html>