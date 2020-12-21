<%-- 
    Document   : invoice
    Created on : 1 Dec 2020, 22:02:47
    Author     : Afonso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "selectedPage" value = "invoice" scope="request"/>
<!-- start of contact.jsp selectedPage=${selectedPage}-->
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <H1>Invoices</H1>
    <div>
        <h1>Manage Users</h1>
        <p>showing ${userListSize} users: </p>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Issue Date</th>
                    <th scope="col">Paid Date</th>
                    <th scope="col">Amount</th>                    
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="invoice" items="${invoiceList}">
                    <tr>
                         <td>${invoice.id}</td>
                        <td>${invoice.issueDate}</td>
                        <td>${invoice.paidDate}</td>
                        <td>${invoice.amount}</td>    
                    </tr>
                </c:forEach>

            </tbody>
        </table>        
    </div>
    
</main>
            


<jsp:include page="footer.jsp" />
