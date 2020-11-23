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
		
	<%@ include file="WEB-INF/templates/fmts.jsp" %>
	
	<form action="sign-up" id="register-form" class="register-form" method="POST" autocomplete="off">
      <h1><c:out value="${hregister}"/></h1>
		
		<div class="wrapper">
		
			<div class="main-fields">
			  <a class="label"><c:out value="${husername}"/></a>
		      <div class="txtb">
		        <input type="text" id="username" name="username" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      </div>
		      
		      <a class="label"><c:out value="${hemail}"/></a>
		      <div class="txtb">
		        <input type="text" id="email" name="email" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      </div>
		
		      <a class="label"><c:out value="${hpassword}"/></a>
		      <div class="txtb">
		        <input type="password" id="password" name="password" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      </div>
		      
		      <a class="label"><c:out value="${hstatus}"/></a>
		      <select class="custom-select" id="status-select" name="status" onchange="changeFunc();">
		    	<option value="PATIENT"><c:out value="${hpat}"/></option>
		    	<option selected value="DOCTOR"><c:out value="${hdoc}"/></option>
		    	<option value="NURSE"><c:out value="${hnurse}"/></option>
		      </select>
	      	</div>
	      	
	      	<div class="secondary-fields">
	    		<a class="label"><c:out value="${hownname}"/></a>
		     	<div class="txtb">
		       	 	<input type="text" id="own-name" name="own-name" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      	</div>
		      	
		      	<a class="label"><c:out value="${hbirthdate}"/></a>
		      	<div class="txtb">
		        	<input type="date" id="birthdate" name="birthdate" onclick="this.parentElement.style.borderColor = '#DCDCDC';">
		      	</div>  
		      	
		      	<div id="patient-fields" style="display: none;">
		      		<a class="label"><c:out value="${hpassport}"/></a>
		      		<div class="txtb">
		        		<input type="text" id="passport" name="passport" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      		</div> 
		      		
		      	 	<a class="label"><c:out value="${hliving}"/></a>
		      		<div class="txtb">
		        		<input type="text" id="living-place" name="living-place" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      		</div> 
		      	</div>
		      	
		      	<div id="doctor-fields">
		      		<a class="label"><c:out value="${hspec}"/></a>
	        		<select class="custom-select" id="specialization" name="specialization" onchange="changeSpec();">
	    				<option selected value="THERAPIST"><c:out value="${hth}"/></option>
	    				<option value="OPHTHALMOLOGIST"><c:out value="${hoph}"/></option>
	    				<option value="OTOLARYNGOLOGIST"><c:out value="${hoto}"/></option>
	    				<option value="CARDIOLOGIST"><c:out value="${hcar}"/></option>
	    				<option value="SURGEON"><c:out value="${hsurg}"/></option>
	    				<option value="NURSE"><c:out value="${hnurse}"/></option>
	      			</select>
	      			
	      	   		<a class="label"><c:out value="${hregkey}"/></a>
		      		<div class="txtb">
		        		<input type="password" id="regkey" name="regkey" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
		      		</div> 
		      	</div>
		      		
	      	</div>
      	</div>
      	
      	<div id="patient-extra" style="display: none;">
      		<a class="label"><c:out value="${hpilln}"/></a>
			<div class="txtb">
				<input type="text" id="past-illnesses" name="past-illnesses" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';">
			</div> 
		</div>

      	<button type="button" class="logbtn" onclick="checkFields();"><c:out value="${hsignup}"/></button>
      
      	<div class="bottom-text"><c:out value="${hwithaccount}"/> <a href="login" class="redirect-href"><c:out value="${hsignin}"/></a></div>
      	<c:choose>
      		<c:when test="${error eq 1}">
      			<div class="bottom-error-text"><c:out value="${hregerror1}"></c:out></div>
      		</c:when>
      		<c:when test="${error eq 2}">
      			<div class="bottom-error-text"><c:out value="${hregerror2}"></c:out></div>
      		</c:when>
      	</c:choose>
    </form>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
	<script type="text/javascript"><%@include file="/WEB-INF/JS/login-register.js"%></script>

</body>
</html>