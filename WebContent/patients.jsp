<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/header-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/patients-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
    <title>WebHospital</title>
</head>
<body>

	<%@ include file="WEB-INF/templates/header.jsp" %>

	<div class="wrapper">
		<div class="secondary-block">
			<div class="doctor-info">
				<div class="acc-info">
					<c:out value="${doctor.id}" />
					<c:out value="${doctor.name}" />
					<c:out value="${doctor.birthDate}" />
					<c:out value="${doctor.specialization}" />
				</div>
			</div>
		</div>
		
		<div class="main-block">
			<div class="patient-list">
			
				<c:forEach var="human" items="${patientList}">
					<div class="patient">
						<c:out value="${human.id}" />
						<c:out value="${human.name}" />
						<c:out value="${human.birthDate}" />
						<div>
							<a href="doctor-prescribe?id=<c:out value='${human.id}' />">prescribe</a>
							<a href="#">discharge</a>
						</div>
					</div>
				</c:forEach>
					
			</div>
	    </div>
	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
</body>
</html>