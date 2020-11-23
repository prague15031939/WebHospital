<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/header-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/doctor-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
    <title>WebHospital</title>
</head>
<body>

	<%@ include file="WEB-INF/templates/fmts.jsp" %>
	<%@ include file="WEB-INF/templates/header.jsp" %>

	<div class="wrapper">
		<div class="secondary-block">
			<div class="doctor-info">
				<div class="doctor-image">
					<img src="<c:out value="${doctorImage}" />">
				</div>
				<div class="doctor-info-item"><c:out value="${doctor.name}" /></div>
				<div class="doctor-info-item"><c:out value="${doctor.birthDate}" /></div>
				<div class="doctor-info-item"><c:out value="${doctorSpecialization}" /></div>
				<div class="doctor-info-item"><c:out value="${doctorEmail}" /></div>
			</div>
		</div>
		
		<div class="main-block">
			<div class="patient-list">
			
				<c:forEach var="human" items="${patientList}">
					<div class="patient">
						<div>
							<div class="patient-info-item">
								<a id="patient-link" href="patient?id=<c:out value='${human.id}' />"><c:out value="${human.name}" /></a>
							</div>
							<div class="patient-info-item"><c:out value="${human.birthDate}" /></div>
							<div class="patient-info-livingplace"><c:out value="${human.livingPlace}" /></div>
						</div>
						<c:if test="${status eq 'DOCTOR'}">
							<div class="actions">
								<div class="action-button1"><a href="doctor-prescribe?id=<c:out value='${human.id}' />"><c:out value="${hprescribe}"/></a></div>
								<div class="action-button2"><a data-discharge="<c:out value="${human.name}" />;discharge?id=<c:out value="${human.id}" />"><c:out value="${hdischarge}"/></a></div>
							</div>
						</c:if>
					</div>
				</c:forEach>
					
			</div>
	    </div>
	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
	<script type="text/javascript"><%@include file="/WEB-INF/JS/discharge.js"%></script>
	
</body>
</html>