<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>
<%@page import="org.solent.com504.project.model.service.ServiceFacade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "selectedPage" value = "home" scope="request" />
<!-- start of home.jsp selectedPage=${selectedPage}-->
<jsp:include page="header.jsp" />


<!-- Begin page content -->
<main role="main" class="container">
    <H1>Home</H1> <br>

    <div>
        <h1>Car List</h1>
        <p>showing cars </p>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Model</th>
                    <th scope="col">Number Plate</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="car" items="${carList}">
                    <tr>
                        <td>${car.id}</td>
                        <td>${car.model}</td>
                        <td>${car.numberPlate}</td>
                        
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
    <br>
    <h1>Modify Cars</h1>
    <tbody>
        <tr>
            <td>Model</td>
            <td><input type="text" name="firstName" value="${user.firstName}" /></td>
        </tr>
        <tr>
            <td>Number Plate</td>
            <td><input type="text" name="secondName" value="${user.secondName}" /></td>
        </tr>
    </tbody>
    <p>
    <form action="./viewModifyUser" method="get">
        <input type="hidden" name="username" value="${user.username}">
        <button class="btn" type="submit" >Add Car</button>
    </form> 
</main>

<jsp:include page="footer.jsp" />
