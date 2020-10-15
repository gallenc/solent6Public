<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="selectedPage" value="home" scope="request" />
<!-- start of about.jsp selectedPage=${selectedPage}-->
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">

	<a href="https://www.opennms.com/" target="_blank"><img
		src="./resources/images/OpenNMSUlf.png" alt="OpenNMS Ulf" width="200"
		height="100"></a> <a href="https://tmforum.org" target="_blank"><img
		src="./resources/images/tmForumLogo.jpg" alt="tmforum logo"
		width="200" height="100"></a>

	<h1>TMForum TMF656 Service Problem Management Interface Simulator</h1>

	<h1>Overview</h1>
	<p>
		This experimental simulator has been contributed by the <a
			href="https://www.opennms.com/" target="_blank">OpenNMS Project</a>
		to the TMForum for testing and demonstrating core functionality of the
		TMF656 SPM interface.
	</p>
	<h1>Usage</h1>

	<p>The primary purpose of this simulator is to allow test calls to
		be made by external clients to the TMF656 ReST api. The simulator will
		save all fields from posted service problems and also allow the client
		to register to receive events on changes of state. However it is also
		possible to demonstrate the capabilities of the API simply by making
		local test calls using the in-built swagger UI.</p>
	<p>The full set of features which have been or are not yet
		implemented are listed under the 'About' tab.</p>
	<p>The SwaggerIU tab allows you to make raw calls to the simulator
		ReST API and create, delete or modify service problems.</p>
	<p>The Service Problems page shows you the current service problems
		in the simulator and allows you to delete the whole service problem
		list.</p>
	<p>The simulator works with http at port 8080 or with https at port
		8443 (self signed certificate) for example:</p>

    <p>Clear: http://{hostname}:8080/tmf656-simulator-war/</p>
	<p>Secure: https://{hostname}:8443/tmf656-simulator-war/<p>
	
	<p>Replacing {hostname} with domain name of server where the simulator is hosted. e.g.</p>
	<p>Clear: <a href="http://${host}:8080${path}">http://${host}:8080${path}</a></p>
	<p>Secure: <a href="https://${host}:8443${path}">https://${host}:8443${path}</a></p>


</main>
<js p:include page="footer.jsp" />

