<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/register-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
    <script type="text/javascript"><%@include file="/WEB-INF/JS/change-status.js"%></script>
    <title>WebHospital</title>
</head>

<body>
		
	<form action="sign-up" class="register-form" method="POST">
      <h1>Register</h1>
		
		<div class="wrapper">
		
			<div class="main-fields">
			  <a class="label">Username</a>
		      <div class="txtb">
		        <input type="text" name="username">
		      </div>
		      
		      <a class="label">E-mail</a>
		      <div class="txtb">
		        <input type="text" name="email">
		      </div>
		
		      <a class="label">Password</a>
		      <div class="txtb">
		        <input type="password" name="password">
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
		       	 	<input type="text" name="own-name">
		      	</div>
		      	
		      	<a class="label">Birth date</a>
		      	<div class="txtb">
		        	<input type="date" name="birthdate">
		      	</div>  
		      	
		      	<div id="patient-fields" style="display: none;">
		      		<a class="label">Passport number</a>
		      		<div class="txtb">
		        		<input type="text" name="passport">
		      		</div> 
		      		
		      	 	<a class="label">Living place</a>
		      		<div class="txtb">
		        		<input type="text" name="living-place">
		      		</div> 
		      		
		      		<a class="label">Past illnesses</a>
		      		<div class="txtb">
		        		<input type="text" name="past-illnesses">
		      		</div> 
		      	</div>
		      	
		      	<div id="doctor-fields">
		      		<a class="label">Specialization</a>
	        		<select class="custom-select" name="specialization">
	    				<option selected value="THERAPIST">therapist</option>
	    				<option value="OPHTHALMOLOGIST">ophthalmologist</option>
	    				<option value="OTOLARYNGOLOGIST">otolaryngologist</option>
	    				<option value="CARDIOLOGIST">cardiologist</option>
	    				<option value="SURGEON">surgeon</option>
	      			</select>
	      			
	      	   		<a class="label">Registration key</a>
		      		<div class="txtb">
		        		<input type="password" name="regkey">
		      		</div> 
		      	</div>
		      		
	      	</div>
      	</div>

      <input type="submit" class="logbtn" value="Sign up">
      
      <div class="bottom-text">Have an account? <a href="login" class="redirect-href">Sign in</a></div>
    </form>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>

</body>
</html>