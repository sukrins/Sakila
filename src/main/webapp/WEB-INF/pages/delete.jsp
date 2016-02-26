<%-- 
    Document   : delete
    Created on : Feb 26, 2016, 1:46:25 AM
    Author     : sukrins
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jspf/header.jspf" %>

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
                 
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@include file="/WEB-INF/jspf/footer.jspf" %>