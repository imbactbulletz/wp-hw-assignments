<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="beans.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Pocetna strana</title>
</head>

<body>

<form method="GET" action="/">
    <c:choose>
        <c:when test="${sessionScope.searchCriteria == null}">
            <input type="text" name="search" value=""><input type="submit" value="Pretrazi">
        </c:when>
        <c:otherwise>
            <input type="text" name="search" value= ${sessionScope.searchCriteria}><input type="submit" value="Pretrazi">
        </c:otherwise>
    </c:choose>

</form>
Raspolozivi proizvodi:
<table border="1">
    <tr bgcolor="lightgrey">
        <th>Naziv</th>
        <th>Cena</th>
        <th>&nbsp;</th>
    </tr>
    <c:if test="${sessionScope.searchCriteria == null}">
        <c:forEach items="${sessionScope.products.values()}" var="p">
            <form method="GET" action="/add">
                <tr>
                    <td>${p.getName()}</td>
                    <td>${p.getPrice()}</td>
                    <td><input type="text" size="3" name="itemCount">
                        <input type="hidden" name="itemId" value="${p.getId()}">
                        <input type="submit" value="Dodaj"></td>
                </tr>
            </form>
        </c:forEach>
    </c:if>

    <c:if test="${sessionScope.searchCriteria != null}">
        <c:forEach items="${sessionScope.products.values()}" var="p">
            <c:if test="${fn:startsWith(p.getName(), sessionScope.searchCriteria)}">
                <form method="GET" action="/add">
                    <tr>
                        <td>${p.getName()}</td>
                        <td>${p.getPrice()}</td>
                        <td><input type="text" size="3" name="itemCount">
                            <input type="hidden" name="itemId" value="${p.getId()}">
                            <input type="submit" value="Dodaj"></td>
                    </tr>
                </form>
            </c:if>
        </c:forEach>
    </c:if>
</table>
<form method="GET" action="/preview">
    <br>
    <input type="submit" value="Pregled Korpe">
</form>

<form method="GET" action="/auth">
    <br>
    <input type="submit" value="Kupi proizvode">
</form>

</body>
</html>