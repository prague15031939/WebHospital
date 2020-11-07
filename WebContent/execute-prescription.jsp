<%@ page language="java" contentType="text/html; charset=utf-8"
        	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <style><%@include file="/WEB-INF/CSS/header-style.css"%></style>
        <style><%@include file="/WEB-INF/CSS/prescription-style.css"%></style>
        <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
        <script type="text/javascript"><%@include file="/WEB-INF/JS/tooltip.js"%></script>
    <title>WebHospital</title>
</head>
<body>
	<%@ include file="WEB-INF/templates/header.jsp" %>
	<div class="wrapper">
		<div class="main-block">
			<form action="execute?id=<c:out value="${prescriptionID}"/>" class="prescription" method="POST">
		        <h2>Execute prescription #<c:out value="${prescriptionID}"/></h2>
		        
		        <div class="txtb-small">
		        	<div class="patient-info">patient: <c:out value="${patientInfo}"/></div>
		         	<div class="doctor-info">doctor: <c:out value="${doctorInfo}"/></div>
		         	<div class="timestamp"><c:out value="${timestamp}"/></div>
		        </div>
		
		        <div class="txtb-large">
		        	<div class="label">diagnosis</div>
		         	<div class="diagnosis"><c:out value="${diagnosis}"/></div>
				
		        </div>
		        <div class="proc-region">
		        	<div class="label-c">therapeutic procedures</div>				 
					<c:forEach var="proc" items="${procedures}" varStatus="varstatus">
						<div class="procedure-item">
						
							<c:choose>
								<c:when test="${fn:substring(proc, 0, 1) eq '@'}">
									<input type="checkbox" name="proc-box" value="<c:out value="${proc}" />" checked disabled>
									<a data-tooltip="<c:out value="${fn:substringAfter(proc, '!?')}" />">
										<c:out value="${fn:substringBefore(fn:substringAfter(proc, '@'), '!?')}" />
									</a>
								</c:when>
								<c:otherwise>
									
									<c:choose>
										<c:when test="${(procAccessors[varstatus.index] eq true) || (status eq 'NURSE')}">
											<input type="checkbox" name="proc-box" value="<c:out value="${proc}" />">
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="proc-box" value="<c:out value="${proc}" />" disabled>
										</c:otherwise>
									</c:choose>										
									
									<label for="proc-box"><c:out value="${proc}" /></label>
								</c:otherwise>
							</c:choose>
							
				  		</div>
					</c:forEach>
					
		        </div>
		        
		        <div class="txtb-large">
		        	<div class="label-c">medicines</div>
		        	<c:forEach var="medicine" items="${medicines}">
		          		<div class="medicine-item">
		          		
		          			<c:choose>
								<c:when test="${fn:substring(medicine, 0, 1) eq '@'}">
									<input type="checkbox" name="medicine-box" value="<c:out value="${medicine}" />" checked disabled>
									<a data-tooltip="<c:out value="${fn:substringAfter(medicine, '!?')}" />">
										<c:out value="${fn:substringBefore(fn:substringAfter(medicine, '@'), '!?')}" />
									</a>
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="medicine-box" value="<c:out value="${medicine}" />">
									<label for="medicine-box"><c:out value="${medicine}" /></label>
								</c:otherwise>
							</c:choose>
							
		          		</div>
		          	</c:forEach>
		        </div>
		        
		        <div class="oper-region">
		        	<div class="label-c">manipulations</div>				 
					<c:forEach var="oper" items="${manipulations}" varStatus="varstatus">
				  		<div class="operation-item">
				  			
				  			<c:choose>
								<c:when test="${fn:substring(oper, 0, 1) eq '@'}">
									<input type="checkbox" name="manipulation-box" value="<c:out value="${oper}" />" checked disabled>
									<a data-tooltip="<c:out value="${fn:substringAfter(oper, '!?')}" />">
										<c:out value="${fn:substringBefore(fn:substringAfter(oper, '@'), '!?')}" />
									</a>
								</c:when>
								<c:otherwise>
								
									<c:choose>
										<c:when test="${(operAccessors[varstatus.index] eq true) && (status eq 'DOCTOR')}">
											<input type="checkbox" name="manipulation-box" value="<c:out value="${oper}" />">
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="manipulation-box" value="<c:out value="${oper}" />" disabled>
										</c:otherwise>
									</c:choose>	

									<label for="manipulation-box"><c:out value="${oper}" /></label>
								</c:otherwise>
							</c:choose>
				  			
				  		</div>
					</c:forEach>
		        </div>
		        
		        <input type="submit" class="executebtn" value="execute">
		        
		   	</form>
	   	</div>
   	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
</body>
</html>