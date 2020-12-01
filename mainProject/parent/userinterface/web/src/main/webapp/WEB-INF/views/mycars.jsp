<%-- 
    Document   : mycars
    Created on : 1 Dec 2020, 21:55:23
    Author     : Afonso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "selectedPage" value = "mycars" scope="request"/>
<!-- start of contact.jsp selectedPage=${selectedPage}-->
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <H1>My Cars</H1>
    
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
    
</main>


<jsp:include page="footer.jsp" />
