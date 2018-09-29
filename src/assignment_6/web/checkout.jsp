<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="beans.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Pocetna strana</title>
</head>

<body>

Kupili ste:
<table border="1">
    <tr bgcolor="lightgrey">
        <th>Naziv</th>
        <th>Kolicina</th>
    </tr>
    <c:forEach items="${sessionScope.shoppingCart.getItems()}" var="shoppingCartItem">

    <form method="GET" action="/add">
        <tr>
            <td>${shoppingCartItem.getProduct().getName()}</td>
            <td>${shoppingCartItem.getCount()}</td>
        </tr>
    </form>
    </c:forEach>
</table>


</body>
</html>