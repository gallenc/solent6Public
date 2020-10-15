#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%-- 
    Document   : header
    Created on : Jan 4, 2020, 11:19:01 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${symbol_dollar}{pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!-- start of header.jsp -->
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="../../favicon.ico">
        <!--<link rel="canonical" href="https://getbootstrap.com/docs/3.3/examples/navbar/">-->

        <title>${tmfSpecPackageName} Simulator</title>

        <!-- Bootstrap core CSS -->
        <link href="./resources/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="./resources/css/navbar.css" rel="stylesheet">

    </head>

    <body>

        <header>

            <!-- Static navbar -->
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="${symbol_pound}navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="${symbol_pound}">${tmfSpecFullName} API Simulator</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">

                            <!-- this jstl should work but problems with multiple if statements -->
                            <!-- selected page = ${symbol_dollar}{selectedPage} home ${symbol_dollar}{'home'.equals(selectedPage) } about ${symbol_dollar}{'about'.equals(selectedPage) } contact ${symbol_dollar}{'contact'.equals(selectedPage) }-->
                            <!--<li <c:if test="${symbol_dollar}{'home'.equals(selectedPage) }"> class="active"  </c:if> ><a href="${symbol_dollar}{contextPath}/home">Home</a></li>--> 

                            <!-- this raw java code works !! -->
                            <li <% if ("home".equals(request.getAttribute("selectedPage"))) {%> class="active"  <% } %> ><a href="${symbol_dollar}{contextPath}/home">Home</a></li> 
                            <li <% if ("serviceProblemList".equals(request.getAttribute("selectedPage"))) {%>  class="active"  <% }%> ><a href="${symbol_dollar}{contextPath}/serviceProblemList">Service Problems</a></li>                          
                            <li <% if ("swaggerUI".equals(request.getAttribute("selectedPage"))) {%>  class="active"  <% }%> ><a href="${symbol_dollar}{contextPath}/swaggerUI">Swagger Test Page</a></li>                          
                            <li <% if ("about".equals(request.getAttribute("selectedPage"))) {%>  class="active"  <% } %> ><a href="${symbol_dollar}{contextPath}/about">About</a></li> 
                            <li <% if ("contact".equals(request.getAttribute("selectedPage"))) {%>  class="active"  <% }%> ><a href="${symbol_dollar}{contextPath}/contact">Contact</a></li>                          

                        </ul>
                        <ul class="nav navbar-nav navbar-right">

                        </ul>
                    </div><!--/.nav-collapse -->
                </div><!--/.container-fluid -->
            </nav>
        </header>
        <!-- end of header.jsp -->