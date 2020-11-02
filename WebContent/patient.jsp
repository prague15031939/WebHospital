<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/header-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/patient-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
    <title>WebHospital</title>
</head>
<body>

	<%@ include file="WEB-INF/templates/header.jsp" %>

	<div class="wrapper">
	
		<div class="secondary-block">
			<div class="patient-info">
				<div class="patient-image">
					<img src="<c:out value="${patientImage}" />">
				</div>
				<div class="patient-info-item"><c:out value="${patient.name}" /></div>
				<div class="patient-info-item"><c:out value="${patient.passportNumber}" /></div>
				<div class="patient-info-item"><c:out value="${patient.birthDate}" /></div>
				<div class="patient-info-item"><c:out value="${patient.livingPlace}" /></div>
				<div class="patient-info-item"><c:out value="${patientEmail}" /></div>
				<div class="patient-info-pastillnesses">past patient's illnesses: <p class="illnesses-text"><c:out value="${patient.pastIllnesses}" /><p></div>
			</div>
		</div>
	
		<div class="main-block">
		
			<div class="prescriptions-list">
			
				<c:forEach var="pres" items="${prescriptionsList}">
					<div class="prescription">
						<div>
							<div class="prescription-info-item"><c:out value="${pres.diagnosis}" /></div>
							<div class="prescription-info-item"><c:out value="${pres.getQuickDoctorInfo()}" /></div>
							<div class="prescription-timestamp-item"><c:out value="${pres.getStringTime()}" /></div>
						</div>
						<div class="actions">
							<div class="action-button1"><a href="prescription?id=<c:out value='${pres.prescriptionID}' />">view</a></div>
							<c:if test="${status eq 'DOCTOR' or status eq 'NURSE'}">
								<div class="action-button2"><a href="execute-prescription?id=<c:out value='${pres.prescriptionID}' />">execute</a></div>
							</c:if>
						</div>
					</div>
				</c:forEach>
					
			</div>
			
		</div>	
	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>

</body>
</html>