<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/register-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
    <title>WebHospital</title>
</head>

<body>
		
	<form action="sign-up" id="register-form" class="register-form" method="POST" autocomplete="off">
      <h1>Register</h1>
		
		<div class="wrapper">
		
			<div class="main-fields">
			  <a class="label">Username</a>
		      <div class="txtb">
		        <input type="text" id="username" name="username" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      </div>
		      
		      <a class="label">E-mail</a>
		      <div class="txtb">
		        <input type="text" id="email" name="email" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      </div>
		
		      <a class="label">Password</a>
		      <div class="txtb">
		        <input type="password" id="password" name="password" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      </div>
		      
		      <a class="label">Status</a>
		      <select class="custom-select" id="status-select" name="status" onchange="changeFunc();">
		    	<option value="PATIENT">patient</option>
		    	<option selected value="DOCTOR">doctor</option>
		      </select>
	      	</div>
	      	
	      	<div class="secondary-fields">
	    		<a class="label">Own name</a>
		     	<div class="txtb">
		       	 	<input type="text" id="own-name" name="own-name" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      	</div>
		      	
		      	<a class="label">Birth date</a>
		      	<div class="txtb">
		        	<input type="date" id="birthdate" name="birthdate" onclick="this.parentElement.style.borderColor = '#DCDCDC';">
		      	</div>  
		      	
		      	<div id="patient-fields" style="display: none;">
		      		<a class="label">Passport number</a>
		      		<div class="txtb">
		        		<input type="text" id="passport" name="passport" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      		</div> 
		      		
		      	 	<a class="label">Living place</a>
		      		<div class="txtb">
		        		<input type="text" id="living-place" name="living-place" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      		</div> 
		      	</div>
		      	
		      	<div id="doctor-fields">
		      		<a class="label">Specialization</a>
	        		<select class="custom-select" id="specialization" name="specialization">
	    				<option selected value="THERAPIST">therapist</option>
	    				<option value="OPHTHALMOLOGIST">ophthalmologist</option>
	    				<option value="OTOLARYNGOLOGIST">otolaryngologist</option>
	    				<option value="CARDIOLOGIST">cardiologist</option>
	    				<option value="SURGEON">surgeon</option>
	      			</select>
	      			
	      	   		<a class="label">Registration key</a>
		      		<div class="txtb">
		        		<input type="password" id="regkey" name="regkey" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      		</div> 
		      	</div>
		      		
	      	</div>
      	</div>
      	
      	<div id="patient-extra" style="display: none;">
      		<a class="label">Past illnesses</a>
			<div class="txtb">
				<input type="text" id="past-illnesses" name="past-illnesses" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
			</div> 
		</div>

      	<button type="button" class="logbtn" onclick="checkFields();">Sign up</button>
      
      	<div class="bottom-text">Have an account? <a href="login" class="redirect-href">Sign in</a></div>
      	<c:if test="${not empty error}">
      		<div class="bottom-error-text"><c:out value="${error}"></c:out></div>
      	</c:if>
    </form>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
	<script type="text/javascript"><%@include file="/WEB-INF/JS/login-register.js"%></script>

</body>
</html>