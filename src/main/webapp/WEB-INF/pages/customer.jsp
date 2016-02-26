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

<form method="POST" action="<c:url value="/customer"/>">
    <fieldset>
        <p>
        <legend>Customer's information:</legend>
        </p>
        <div>
            <label for="customerId">Customer ID</label>
            <input type="number" name="customerId" id="customerId" value="${customer.id}"/>
        </div>
        <div>
            <label for="customerId">Customer First Name</label>
            <input type="text" name="firstName" id="firstName" value="${customer.firstName}"/>
        </div>
        <div>
            <label for="customerId">Customer Last Name</label>
            <input type="text" name="lastName" id="lastName" value="${customer.lastName}"/>
        </div>
        <div>
            <label for="email">Customer Email</label>
            <input type="email" name="email" id="email" value="${customer.email}"/>
        </div>
        <input type="submit" name="submitCustomer" id="submitCustomer"/>
    </fieldset>
</form>

<%@include file="/WEB-INF/jspf/footer.jspf" %>