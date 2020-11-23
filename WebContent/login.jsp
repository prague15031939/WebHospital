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
		
	<%@ include file="WEB-INF/templates/fmts.jsp" %>
	
	<form action="sign-in" id="login-form" class="login-form" method="POST">
      <h1><c:out value="${hlogin}"/></h1>

	  <a class="label"><c:out value="${husername}"/></a>
      <div class="txtb">
        <input type="text" id="username" name="username" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
      </div>

      <a class="label"><c:out value="${hpassword}"/></a>
      <div class="txtb">
        <input type="password" id="password" name="password" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
      </div>

      <button type="button" class="logbtn" onclick="checkLoginFields();"><c:out value="${hsignin}"/></button>
      
      <div class="bottom-text"><c:out value="${hwithoutaccount}"/> <a href="register" class="redirect-href"><c:out value="${hsignup}"/></a></div>
      <c:if test="${error eq 1}">
      	<div class="bottom-error-text"><c:out value="${hloginerror}"></c:out></div>
      </c:if>
    </form>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
	<script type="text/javascript"><%@include file="/WEB-INF/JS/login-register.js"%></script>

</body>
</html>