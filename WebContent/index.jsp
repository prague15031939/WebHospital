<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/CSS/header-style.css"%></style>
    <style>
    	.wrapper {
    		display: flex;
  			flex-direction: row;
  			justify-content: center;	
    	}
    	.main-block {
    		margin-top: 20px;
			background-color: #F8F8FF;
			width: 600px;
			height: 600px;
			border: 1px solid #DCDCDC;
			border-radius: 5px;
			text-align: center;
    	}
    	.main-block h3 {
    		margin: 200px;
    	}
    </style>
    <style><%@include file="/WEB-INF/CSS/footer-style.css"%></style>
    <title>WebHospital</title>
</head>
<body>

	<%@ include file="WEB-INF/templates/header.jsp" %>

	<div class="wrapper">
		<div class="main-block">
			<h3>Hi girls<h3>
		</div>	
	</div>
	
	<%@ include file="WEB-INF/templates/footer.jsp" %>

</body>
</html>