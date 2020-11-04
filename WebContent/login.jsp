<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/login-style.css"%></style>
    <title>WebHospital</title>
</head>

<body>
		
	<form action="sign-in" id="login-form" class="login-form" method="POST">
      <h1>Login</h1>

	  <a class="label">Username</a>
      <div class="txtb">
        <input type="text" id="username" name="username" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
      </div>

      <a class="label">Password</a>
      <div class="txtb">
        <input type="password" id="password" name="password" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
      </div>

      <button type="button" class="logbtn" onclick="checkLoginFields();">Sign in</button>
      
      <div class="bottom-text">Do not have an account? <a href="register" class="redirect-href">Sign up</a></div>
      <c:if test="${not empty error}">
      	<div class="bottom-error-text"><c:out value="${error}"></c:out></div>
      </c:if>
    </form>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
	<script type="text/javascript"><%@include file="/WEB-INF/JS/login-register.js"%></script>

</body>
</html>