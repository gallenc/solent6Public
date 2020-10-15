<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="selectedPage" value="about" scope="request" />
<!-- start of about.jsp selectedPage=${selectedPage}-->
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
	<H1>About</H1>

	<p>
		This experimental simulator has been contributed by the <a
			href="https://www.opennms.com/" target="_blank">OpenNMS Project</a>
		to the TMForum for testing and demonstrating core functionality of the
		TMF656 SPM interface.
	</p>
	<p>The simulator allows clients to create,delete,update(patch) and
		change the state of service problems. Clients can also register to
		receive events corresponding to updates to the created alarms.</p>
	<p>
		This simulator is based upon code generated using swagger with
		significant additional code to create a working SPM hub simulator. To
		ensure the specification is correctly implemented, this code has been partially
		generated based on the open api / swagger 1.5 spec hosted at<BR>
		<a
			href="https://github.com/tmforum-apis/TMF656_ServiceProblemManagement"
			target="_blank">https://github.com/tmforum-apis/TMF656_ServiceProblemManagement</a>
	</p>
	<p>
	Please note that all service problem ID's must be treated as integers and not strings in this implementation.
	</p>

	<h2>Current Implementation Status (4 July 2020)</h2>
	<div style="height: 300px; width: 100%; overflow: auto;">
		<table cellspacing="0" cellpadding="5" border="1">

			<tbody>
				<tr>
					<td>
						<p>Function Group</p>
					</td>
					<td>
						<p>Function</p>
					</td>
					<td>
						<p>Status</p>
					</td>
					<td>
						<p>Notes</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>Events subscription</p>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Unregister a listener</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td>
						<p>Listeners do not have Working filters</p>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Register a listener</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td>
						<p>Listeners do not have working filters</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>Notification listeners (client side)</p>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Client listener for entity
							ServiceProblemAttributeValueChangeNotification</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Client listener for entity
							ServiceProblemInformationRequiredNotification</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Client listener for entity
							ServiceProblemStateChangeNotification</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Client listener for entity ServiceProblemCreateNotification</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Client listener for generic events</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td>
						<p>Not in standard: listens for any event and not just specifc
							type</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>problemAcknowledgement</p>
					</td>
					<td></td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						<p>problemUnacknowledgement</p>
					</td>
					<td></td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						<p>problemGroup</p>
					</td>
					<td></td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						<p>problemUngroup</p>
					</td>
					<td></td>
					<td>
						<p>Not working</p>
					</td>
					<td>
						<p>does not ungroup</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>serviceProblem</p>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>List or find ServiceProblem objects (GET /serviceProblem)</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td>
						<p>no filters or criteria searchint</p>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Creates a ServiceProblem (POST /serviceProblem)</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Retrieves a ServiceProblem by ID GET /serviceProblem/{id}</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Deletes a ServiceProblem DELETE /serviceProblem/{id}</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Updates partially a ServiceProblem PATCH
							/serviceProblem/{id}</p>
					</td>
					<td>
						<p>Working</p>
					</td>
					<td>
						<p>simple patch</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>serviceProblemEventRecord</p>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>List or find ServiceProblemEventRecord objects GET
							/serviceProblemEventRecord</p>
					</td>
					<td>
						<p>not implemented</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>Retrieves a ServiceProblemEventRecord by ID GET
							/serviceProblemEventRecord/{id}</p>
					</td>
					<td>
						<p>not implemented</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						<p>default</p>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>GET /generic-hub</p>
					</td>
					<td></td>
					<td>
						<p>Test code not part of standard</p>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<p>GET /generic-listener</p>
					</td>
					<td></td>
					<td>
						<p>Test code not part of standard</p>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</main>

<jsp:include page="footer.jsp" />
