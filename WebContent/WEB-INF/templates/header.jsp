<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	<div class="header">
		<div class="site-logo"><a class="hospital-href" href="main">WebHospital</a></div>
		<div class="navigation">
			<c:choose>
				<c:when test="${status eq 'PATIENT'}">		
					<a href="prescriptions?id=3" class="navigation-link">prescriptions</a>
				</c:when>
				<c:when test="${status eq 'DOCTOR'}">		
					<a href="patients" class="navigation-link">patients</a>
				</c:when>
			</c:choose>
		</div>
		<a class="login-href" href="login"><div class="account-icon" style="background-image: url('/WebHospital/image/default-avatar.jpg')"></div></a>
	</div>