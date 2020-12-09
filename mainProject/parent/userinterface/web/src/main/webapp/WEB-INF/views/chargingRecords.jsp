<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "selectedPage" value = "chargingRecords" scope="request" />
<!-- start of home.jsp selectedPage=${selectedPage}-->
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <H1>Manage Charging Records</H1>
    <p> showing ${chargingRecordListSize} charging records: </p>
    <form action="./chargingRecords" method="get">
        <p><button class="btn" type="submit" >Search</button> ${errorMessage}</p>
        <table class="table">
            <tr>
                <td>Number Plate</td>
                <td><input type="text" name="numberPlate" value="${numberPlate}" /></td>
                <td>Entry Time</td>
                <td><input type="text" name="entryDate" value="${entryDate}" /></td>
                <td>Exit Time</td>
                <td><input type="text" name="exitDate" value="${exitDate}" /></td>
            </tr>
            <tr>
                <td>Page</td>
                <td>${page}</td>
                <td>of total pages </td>
                <td>${totalPages}</td>
                <td>total records</td>
                <td>${totalRecords}</td>
            </tr>
        </table>
    </form> 
    <BR>
    <table class="table">
        <tr>
            <th scope="col">uuid</th>
            <th scope="col">Number Plate</th>
            <th scope="col">entry time</th>
            <th scope="col">entry location</th>
            <th scope="col">exit time</th>
            <th scope="col">exit location</th>
            <th scope="col">charge</th>
        </tr>
        <c:forEach var="chargingRecord" items="${chargingRecordList}">
            <tr>
                <td>${chargingRecord.uuid}</td>
                <td>${chargingRecord.numberPlate}</td>
                <td>${chargingRecord.entryDate}</td>
                <td>${chargingRecord.entryLocation}</td>
                <td>${chargingRecord.exitDate}</td>
                <td>${chargingRecord.exitLocation}</td>
                <td>${chargingRecord.charge}</td>
            </tr>    
        </c:forEach>
    </table>

</main>

<jsp:include page="footer.jsp" />
