<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value='<%=request.getSession().getAttribute("lang")%>'/>
<fmt:setBundle basename="resources.lang" var="loc"/>

<fmt:message bundle="${loc}" key="lang.common.submit" var="hsubmit"/>

<fmt:message bundle="${loc}" key="lang.mainpage.title1" var="htitle1"/>
<fmt:message bundle="${loc}" key="lang.mainpage.title2" var="htitle2"/>

<fmt:message bundle="${loc}" key="lang.header.patients" var="hpatients"/>
<fmt:message bundle="${loc}" key="lang.header.prescriptions" var="hprescriptions"/>

<fmt:message bundle="${loc}" key="lang.doctor.prescribe" var="hprescribe"/>
<fmt:message bundle="${loc}" key="lang.doctor.discharge" var="hdischarge"/>
<fmt:message bundle="${loc}" key="lang.doctor.finaldiagnosis" var="hfinaldiagnosis"/>

<fmt:message bundle="${loc}" key="lang.doctor.view" var="hview"/>
<fmt:message bundle="${loc}" key="lang.doctor.execute" var="hexecute"/>
<fmt:message bundle="${loc}" key="lang.doctor.pastill" var="hpastill"/>

<fmt:message bundle="${loc}" key="lang.prescription.create" var="hcreateprescription"/>
<fmt:message bundle="${loc}" key="lang.prescription.execute" var="hexecuteprescription"/>
<fmt:message bundle="${loc}" key="lang.prescription.prescription" var="hprescription"/>
<fmt:message bundle="${loc}" key="lang.prescription.patient" var="hpatient"/>
<fmt:message bundle="${loc}" key="lang.prescription.doctor" var="hdoctor"/>
<fmt:message bundle="${loc}" key="lang.prescription.diagnosis" var="hdiagnosis"/>
<fmt:message bundle="${loc}" key="lang.prescription.procedures" var="hprocedures"/>
<fmt:message bundle="${loc}" key="lang.prescription.medicines" var="hmedicines"/>
<fmt:message bundle="${loc}" key="lang.prescription.manipulations" var="hmanipulations"/>
<fmt:message bundle="${loc}" key="lang.prescription.cannot" var="hcannot"/>

<fmt:message bundle="${loc}" key="lang.user.login" var="hlogin"/>
<fmt:message bundle="${loc}" key="lang.user.signin" var="hsignin"/>
<fmt:message bundle="${loc}" key="lang.user.register" var="hregister"/>
<fmt:message bundle="${loc}" key="lang.user.signup" var="hsignup"/>
<fmt:message bundle="${loc}" key="lang.user.username" var="husername"/>
<fmt:message bundle="${loc}" key="lang.user.password" var="hpassword"/>
<fmt:message bundle="${loc}" key="lang.user.withoutaccount" var="hwithoutaccount"/>
<fmt:message bundle="${loc}" key="lang.user.withaccount" var="hwithaccount"/>
<fmt:message bundle="${loc}" key="lang.user.loginerror" var="hloginerror"/>

<fmt:message bundle="${loc}" key="lang.user.email" var="hemail"/>
<fmt:message bundle="${loc}" key="lang.user.status" var="hstatus"/>
<fmt:message bundle="${loc}" key="lang.user.patient" var="hpat"/>
<fmt:message bundle="${loc}" key="lang.user.doctor" var="hdoc"/>
<fmt:message bundle="${loc}" key="lang.user.nurse" var="hnurse"/>
<fmt:message bundle="${loc}" key="lang.user.ownname" var="hownname"/>
<fmt:message bundle="${loc}" key="lang.user.birthdate" var="hbirthdate"/>
<fmt:message bundle="${loc}" key="lang.user.spec" var="hspec"/>
<fmt:message bundle="${loc}" key="lang.user.regkey" var="hregkey"/>
<fmt:message bundle="${loc}" key="lang.user.passport" var="hpassport"/>
<fmt:message bundle="${loc}" key="lang.user.living" var="hliving"/>
<fmt:message bundle="${loc}" key="lang.user.pilln" var="hpilln"/>

<fmt:message bundle="${loc}" key="lang.user.regerror1" var="hregerror1"/>
<fmt:message bundle="${loc}" key="lang.user.regerror2" var="hregerror2"/>

<fmt:message bundle="${loc}" key="lang.spec.th" var="hth"/>
<fmt:message bundle="${loc}" key="lang.spec.oph" var="hoph"/>
<fmt:message bundle="${loc}" key="lang.spec.oto" var="hoto"/>
<fmt:message bundle="${loc}" key="lang.spec.car" var="hcar"/>
<fmt:message bundle="${loc}" key="lang.spec.surg" var="hsurg"/>





