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
<c:set var="selectedPage" value="serviceProblemList" scope="request" />
<!-- start of serviceProblemList.jsp selectedPage=${symbol_dollar}{selectedPage}-->
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
	<form action="./serviceProblemList" method="post">
		<input type="hidden" name="action" value="deleteAll">
		<button class="btn" type="submit">clear all problems</button>
	</form>
	<H1>Current Service Problems</H1>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">id</th>
				<th scope="col">status</th>
				<th scope="col">description</th>
				<th scope="col">priority</th>
				<th scope="col">affected service</th>
				<th scope="col">affected resource</th>
				<th scope="col">affected SLA violation</th>
				<th scope="col">correlationId</th>
				<th scope="col">time of change</th>
				<th scope="col">href</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="problem" items="${symbol_dollar}{problemList}">
				<tr>
					<td>${symbol_dollar}{problem.id}</td>
					<td>${symbol_dollar}{problem.status}</td>
					<td><small>${symbol_dollar}{problem.description}</small></td>
					<td>${symbol_dollar}{problem.priority}</td>
					<td><c:if test="${symbol_dollar}{not empty problem.affectedService}">
							<c:forEach var="ref" items="${symbol_dollar}{problem.affectedService}">
								<c:if test="${symbol_dollar}{empty ref.href}">${symbol_dollar}{ref.id}</c:if>
								<c:if test="${symbol_dollar}{not empty ref.href}">
									<a href="${symbol_dollar}{ref.href}" target="_blank">${symbol_dollar}{ref.id}</a>
								</c:if>
								<br>
							</c:forEach>
						</c:if></td>
					<td><c:if test="${symbol_dollar}{not empty problem.affectedResource}">
							<c:forEach var="ref" items="${symbol_dollar}{problem.affectedResource}">
								<c:if test="${symbol_dollar}{empty ref.href}">${symbol_dollar}{ref.id}</c:if>
								<c:if test="${symbol_dollar}{not empty ref.href}">
									<a href="${symbol_dollar}{ref.href}" target="_blank">${symbol_dollar}{ref.id}</a>
								</c:if>
								<br>
							</c:forEach>
						</c:if></td>

					<td><c:if test="${symbol_dollar}{not empty problem.associatedSLAViolation}">
							<c:forEach var="ref" items="${symbol_dollar}{problem.associatedSLAViolation}">
								<c:if test="${symbol_dollar}{empty ref.href}">${symbol_dollar}{ref.id}</c:if>
								<c:if test="${symbol_dollar}{not empty ref.href}">
									<a href="${symbol_dollar}{ref.href}" target="_blank">${symbol_dollar}{ref.id}</a>
								</c:if>
								<br>
							</c:forEach>
						</c:if></td>
					<td><small>${symbol_dollar}{problem.correlationId}</small></td>
					<td><small>
							<table>
								<tr>
									<td>raised:&nbsp;</td>
									<td>${symbol_dollar}{problem.timeRaised}</td>
								</tr>
								<tr>
									<td>changed:&nbsp;</td>
									<td>${symbol_dollar}{problem.timeChanged}</td>
								</tr>
								<tr>
									<td>status:&nbsp;</td>
									<td>${symbol_dollar}{problem.statusChangeDate}</td>
								</tr>
								<tr>
									<td>resolved:&nbsp;</td>
									<td>${symbol_dollar}{problem.resolutionDate}</td>
								</tr>
							</table>
					</small></td>
					<td><a href="${symbol_dollar}{problem.href}" target="_blank">raw json</a></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

</main>


<jsp:include page="footer.jsp" />