

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.project.model.user.dto.User"%>
<%@page import="org.solent.com504.project.model.user.dto.UserRoles"%>
<%@page import="org.solent.com504.project.model.party.dto.Party"%>
<c:set var = "selectedPage" value = "admin" scope="request"/>
<jsp:include page="header.jsp" />
<!-- start of manageUsersParty.jsp selectedPage=${selectedPage}-->

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <h1>Manage Users Party</h1>

        <form action="./viewModifyParty" method="POST">
            <!-- partyuuid ="" creates a new party -->
            <input name="username" value="">
            <button class="btn" type="submit" >Add Party</button>
        </form> 


        <p>showing ${partyListSize} partys: </p>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">uuid</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Second Name</th>
                    <th scope="col">Party Role</th>
                    <th scope="col">Status</th>
                    <th scope="col">Users</th>
                    <th></th>
                </tr>
            </thead>


            <tbody>
                <c:forEach var="party" items="${partyList}">
                    <tr>
                        <td>${party.id}</td>
                        <td>${party.uuid}</td>
                        <td>${party.firstName}</td>
                        <td>${party.secondName}</td>
                        <td>${party.partyRole}</td>
                        <!-- party.enabled=${party.enabled}-->
                        <td><c:if test="${party.enabled}">ENABLED</c:if><c:if test="${!party.enabled}">DISABLED</c:if></td>
                        <td><c:forEach var="user" items="${party.users}"> | ${user.username} |<br></c:forEach></td>
                        </tr>
                </c:forEach>

            </tbody>
        </table>

    </div>
</main>

<jsp:include page="footer.jsp" />
