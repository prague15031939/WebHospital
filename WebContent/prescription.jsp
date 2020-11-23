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
				
				<c:if test="${fn:length(procedures) > 0 and procedures[0] ne ''}">
			        <div class="proc-region">
			        	<a class="label">therapeutic procedures</a>				 
						<c:forEach var="proc" items="${procedures}">
							<div class="procedure-item">
							
								<c:choose>
									<c:when test="${fn:substring(proc, 0, 1) eq '@'}">
										<input type="checkbox" checked disabled>
										<a data-tooltip="<c:out value="${fn:substringAfter(proc, '!?')}" />">
											<c:out value="${fn:substringBefore(fn:substringAfter(proc, '@'), '!?')}" />
										</a>
									</c:when>
									<c:otherwise>
										<c:out value="${proc}" />
									</c:otherwise>
								</c:choose>
						
					  		</div>
						</c:forEach>
						
			        </div>
		        </c:if>
		        
		        <c:if test="${fn:length(medicines) > 0 and medicines[0] ne ''}">
			        <div class="txtb-large">
			        	<a class="label">medicines</a>
						<c:forEach var="medicine" items="${medicines}">
							<div class="medicine-item">
							
								<c:choose>
									<c:when test="${fn:substring(medicine, 0, 1) eq '@'}">
										<input type="checkbox" checked disabled>
										<a data-tooltip="<c:out value="${fn:substringAfter(medicine, '!?')}" />">
											<c:out value="${fn:substringBefore(fn:substringAfter(medicine, '@'), '!?')}" />
										</a>
									</c:when>
									<c:otherwise>
										<c:out value="${medicine}" />
									</c:otherwise>
								</c:choose>
						
					  		</div>
						</c:forEach>
			        </div>
		        </c:if>
		        
		        <c:if test="${fn:length(manipulations) > 0 and manipulations[0] ne ''}">
			        <div class="oper-region">
			        	<a class="label">manipulations</a>				 
						<c:forEach var="oper" items="${manipulations}">
							<div class="operation-item">
							
								<c:choose>
									<c:when test="${fn:substring(oper, 0, 1) eq '@'}">
										<input type="checkbox" checked disabled>
										<a data-tooltip="<c:out value="${fn:substringAfter(oper, '!?')}" />">
											<c:out value="${fn:substringBefore(fn:substringAfter(oper, '@'), '!?')}" />
										</a>
									</c:when>
									<c:otherwise>
										<c:out value="${oper}" />
									</c:otherwise>
								</c:choose>
						
					  		</div>
						</c:forEach>
			        </div>
		        </c:if>
		        
		   	</div>
	   	</div>
   	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
</body>
</html>