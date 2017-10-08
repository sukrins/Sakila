<%-- 
    Document   : customer
    Created on : Jan 24, 2016, 12:50:22 PM
    Author     : sas691
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<h1>Sakila Customer!</h1>

<c:if test="${not empty requestScope[customer]}">
    <h2>${requestScope.customer.firstName} ${requestScope.customer.lastName}</h2>
</c:if>

<c:if test="${not empty requestScope.violations}">
    <h2>Violations were found in my controller! (and passed back in the request scope)</h2>
    <ul>
        <c:forEach items="${requestScope.violations}" var="violation">
            <li>
                <c:out value="${violation.propertyPath}"/>: ${violation.message}
            </li>
        </c:forEach>
    </ul>
</c:if>

<c:if test="${not empty requestScope.messages}">
    <h2></h2>
    <ul>
        <c:forEach items="${requestScope.messages}" var="message">
            <li>
                <c:out value="${message.key}"/>: ${message.value}
            </li>
        </c:forEach>
    </ul>
</c:if>
<table class="table table-hover">
    <thead>
        <tr>
            <th>Customer ID</th>
            <th>Customer First Name</th>
            <th>Customer Last Name</th>
            <th>Customer Email</th>       
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${requestScope.customers}" var="customer">
            <tr>
                <td>${customer.id}</td>
                <td>${customer.firstName}</td>
                <td>${customer.lastName}</td>
                <td>${customer.email}</td>                     
                <td>
                    <a href="<c:url value="/readOnly">
                           <c:param name="customerId" value="${customer.id}"/>
                       </c:url>">Details (Read/Only)</a>
                </td>
                <td>
                    <a href="<c:url value="/customer">
                           <c:param name="customerId" value="${customer.id}"/>
                       </c:url>">Update</a>
                </td>
                <td>
                    <a href="<c:url value="/customer">
                           <c:param name="customerId" value="${customer.id}"/>
                       </c:url>">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@include file="/WEB-INF/jspf/footer.jspf" %>