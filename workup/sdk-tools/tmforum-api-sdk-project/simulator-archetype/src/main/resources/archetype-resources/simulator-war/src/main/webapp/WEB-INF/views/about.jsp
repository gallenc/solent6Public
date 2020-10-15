#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="selectedPage" value="about" scope="request" />
<!-- start of about.jsp selectedPage=${symbol_dollar}{selectedPage}-->
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
	<H1>About</H1>

	<p>
		This experimental simulator has been contributed by the <a
			href="https://www.opennms.com/" target="_blank">OpenNMS Project</a>
		to the TMForum for testing and demonstrating core functionality of the
		${tmfSpecPackageName} interface.
	</p>
	<p>The simulator allows clients to create,delete,update(patch) and
		change the state of service problems. Clients can also register to
		receive events corresponding to updates to the created alarms.</p>
	<p>
		This simulator is based upon code generated using swagger with
		significant additional code to create a working ${tmfSpecPackageName} hub simulator. To
		ensure the specification is correctly implemented, this code has been partially
		generated based on the ${tmfSpecPackageName} open api / swagger 1.5 specification<BR>

	</p>
	<p>
	Please note that all service problem ID's must be treated as integers and not strings in this implementation.
	</p>

</main>

<jsp:include page="footer.jsp" />
