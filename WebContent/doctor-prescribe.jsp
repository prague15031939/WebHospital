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
				${patient.id}: ${patient.name}: ${patient.birthDate}	
			</div>
		</div>
		
		<div class="main-block">
	      <form action="prescribe?id=<c:out value='${patient.id}'/>" class="prescribe-form" method="POST">
	        <h2>Create a prescription</h2>
	
	        <div class="txtb-large">
	          <textarea name="diagnosis" rows="3" cols="73" placeholder="diagnosis"></textarea>
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
	          <textarea name="medicines" rows="7" cols="73" placeholder="medicines"></textarea>
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
	        
	        <input type="submit" class="createbtn" value="submit">
	      </form>
	    </div>
	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
</body>
</html>