<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "ex" uri = "WEB-INF/tld/custom.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/header-style.css"%></style>
	<style><%@include file="WEB-INF/CSS/main-style.css"%></style>
    <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
    <title>WebHospital</title>
</head>
<body>

	<%@ include file="WEB-INF/templates/fmts.jsp" %>	
	<%@ include file="WEB-INF/templates/header.jsp" %>
	
	<div class="wrapper">
	
		<div class="secondary-block">
			<div class="inner-secondary">
				<p><c:out value="${htitle1}"/><br/><c:out value="${htitle2}"/><br/>WebHospital</p>
			</div>
		</div>
		
		<div class="main-block">
			<div class="inner-main">
			
				<ex:switch checkboxName="langChoise" jsOnclick="switchLang();"><c:out value='${lang}'/></ex:switch>
					
			</div>
	    </div>
	    
	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>
	
	<script type="text/javascript"><%@include file="/WEB-INF/JS/switch-lang.js"%></script>

</body>
</html>