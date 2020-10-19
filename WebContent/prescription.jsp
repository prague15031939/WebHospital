<%@ page language="java" contentType="text/html; charset=utf-8"
        	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <style><%@include file="/WEB-INF/CSS/header-style.css"%></style>
        <style><%@include file="/WEB-INF/CSS/prescription-style.css"%></style>
        <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
    <title>WebHospital</title>
</head>
<body>

	<%@ include file="WEB-INF/templates/header.jsp" %>

	<div class="wrapper">
		<div class="main-block">
			<div class="prescription">
		        <h2>Prescription #<c:out value="${prescriptionID}"/></h2>
		        
		        <div class="txtb-small">
		        	<div class="patient-info">patient: <c:out value="${patientInfo}"/></div>
		         	<div class="doctor-info">doctor: <c:out value="${doctorInfo}"/></div>
		         	<div class="timestamp"><c:out value="${timestamp}"/></div>
		        </div>
		
		        <div class="txtb-large">
		        	<a class="label">diagnosis</a>
		         	<div class="diagnosis"><c:out value="${diagnosis}"/></div>
		        </div>
				
		        <div class="proc-region">
		        	<a class="label">therapeutic procedures</a>				 
					<c:forEach var="proc" items="${procedures}">
				  		<div class="procedure-item"><c:out value="${proc}" /></div>
					</c:forEach>
					
		        </div>
		        
		        <div class="txtb-large">
		        	<a class="label">medicines</a>
		        	<c:forEach var="medicine" items="${medicines}">
		          		<div class="medicine-item"><c:out value="${medicine}"/></div>
		          	</c:forEach>
		        </div>
		        
		        <div class="oper-region">
		        	<a class="label">manipulations</a>				 
					<c:forEach var="oper" items="${manipulations}">
				  		<div class="operation-item"><c:out value="${oper}" /></div>
					</c:forEach>
				
		        </div>
		        
		   	</div>
	   	</div>
   	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
</body>
</html>