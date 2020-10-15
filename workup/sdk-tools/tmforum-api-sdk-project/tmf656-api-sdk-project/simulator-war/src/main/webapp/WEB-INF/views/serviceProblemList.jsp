<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="selectedPage" value="serviceProblemList" scope="request" />
<!-- start of serviceProblemList.jsp selectedPage=${selectedPage}-->
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
			<c:forEach var="problem" items="${problemList}">
				<tr>
					<td>${problem.id}</td>
					<td>${problem.status}</td>
					<td><small>${problem.description}</small></td>
					<td>${problem.priority}</td>
					<td><c:if test="${not empty problem.affectedService}">
							<c:forEach var="ref" items="${problem.affectedService}">
								<c:if test="${empty ref.href}">${ref.id}</c:if>
								<c:if test="${not empty ref.href}">
									<a href="${ref.href}" target="_blank">${ref.id}</a>
								</c:if>
								<br>
							</c:forEach>
						</c:if></td>
					<td><c:if test="${not empty problem.affectedResource}">
							<c:forEach var="ref" items="${problem.affectedResource}">
								<c:if test="${empty ref.href}">${ref.id}</c:if>
								<c:if test="${not empty ref.href}">
									<a href="${ref.href}" target="_blank">${ref.id}</a>
								</c:if>
								<br>
							</c:forEach>
						</c:if></td>

					<td><c:if test="${not empty problem.associatedSLAViolation}">
							<c:forEach var="ref" items="${problem.associatedSLAViolation}">
								<c:if test="${empty ref.href}">${ref.id}</c:if>
								<c:if test="${not empty ref.href}">
									<a href="${ref.href}" target="_blank">${ref.id}</a>
								</c:if>
								<br>
							</c:forEach>
						</c:if></td>
					<td><small>${problem.correlationId}</small></td>
					<td><small>
							<table>
								<tr>
									<td>raised:&nbsp;</td>
									<td>${problem.timeRaised}</td>
								</tr>
								<tr>
									<td>changed:&nbsp;</td>
									<td>${problem.timeChanged}</td>
								</tr>
								<tr>
									<td>status:&nbsp;</td>
									<td>${problem.statusChangeDate}</td>
								</tr>
								<tr>
									<td>resolved:&nbsp;</td>
									<td>${problem.resolutionDate}</td>
								</tr>
							</table>
					</small></td>
					<td><a href="${problem.href}" target="_blank">raw json</a></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

</main>


<jsp:include page="footer.jsp" />