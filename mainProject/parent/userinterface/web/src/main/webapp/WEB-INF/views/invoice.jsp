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
</main>


<jsp:include page="footer.jsp" />
