<%@ page language="java" contentType="text/html; charset=utf-8"
        	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <style><%@include file="/WEB-INF/CSS/header-style.css"%></style>
        <style><%@include file="/WEB-INF/CSS/prescribe-form-style.css"%></style>
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
			<div class="prescription">
		      <form action="prescribe?id=<c:out value='${patient.id}'/>" id="prescribe-form" class="prescribe-form" method="POST">
		        <h2>Create a prescription</h2>
		
		        <div class="txtb-large">
		          <textarea name="diagnosis" id="diagnosis" rows="3" cols="73" placeholder="diagnosis" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';"></textarea>
		        </div>
				
		        <div class="proc-region">
					<a class="label">therapeutic procedures</a>
					 
					<table id="procedures">
						<c:forEach var="pr" items="${procedures}">
						  	<tr>
								<c:forEach var="proc" items="${pr}">
							    	<td>
							    		<div class="proc-checkbox">
				  							<input type="checkbox" name="proc-box" value="<c:out value="${proc}" />">
				  							<label for="proc-box"><c:out value="${proc}" /></label>
										</div>
							    	</td>
								</c:forEach>
						  	</tr>
					  	</c:forEach>
					</table>
					
		        </div>
		        
		        <div class="txtb-large">
		          <textarea name="medicines" rows="7" cols="73" id="medicines" placeholder="medicines" onkeydown="this.parentElement.style.borderColor = '#DCDCDC';"></textarea>
		        </div>
		        
		        <div class="oper-region">
					<a class="label">manipulations</a>
					 
					<table id="operations">
						<c:forEach var="mn" items="${manipulations}">
						  	<tr>
								<c:forEach var="item" items="${mn}">
							    	<td>
							    		<div class="proc-checkbox">
				  							<input type="checkbox" name="manipulation-box" value="<c:out value="${item}" />">
				  							<label for="manipulation-box"><c:out value="${item}" /></label>
										</div>
							    	</td>
						    	</c:forEach>
						    </tr>
					    </c:forEach>
					</table>
					
		        </div>
		        
		        <c:choose>
		        	<c:when test="${canSubmit eq true}">
			        	<button type="button" class="createbtn" id="createbtn" onclick="submitPrescribeForm();">submit</button>
		        	</c:when>
		        	<c:otherwise>
		        		<p id="cannot-submit-text">cannot prescribe to this patient</p>
		        	</c:otherwise>
		        </c:choose>
		        
		      </form>
	    	</div>
	    </div>
	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
	<script type="text/javascript"><%@include file="/WEB-INF/JS/prescription.js"%></script>
	
</body>
</html>