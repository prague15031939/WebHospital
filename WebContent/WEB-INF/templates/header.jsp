<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	<div class="header">
		<div class="site-logo"><a class="hospital-href" href="main">WebHospital</a></div>
		<div class="navigation">
			<c:choose>
				<c:when test="${status eq 'PATIENT'}">	
					<c:if test="${accountID ne null}">	
						<a href="patient?id=<c:out value='${accountID}' />" class="navigation-link"><c:out value="${hprescriptions}"/></a>
					</c:if>
				</c:when>
				<c:when test="${(status eq 'DOCTOR') || (status eq 'NURSE')}">		
					<a href="doctor" class="navigation-link"><c:out value="${hpatients}"/></a>
				</c:when>
			</c:choose>
		</div>
		
		<c:choose>
			<c:when test="${image ne null}">
				<a class="login-href" href="login"><div class="account-icon" style="background-image: url('<c:out value="${image}"/>')"></div></a>
			</c:when>
			<c:otherwise>
				<a class="sign-caption" href="login"><c:out value="${hsignin}"/></a>
			</c:otherwise>
		</c:choose>
	</div>