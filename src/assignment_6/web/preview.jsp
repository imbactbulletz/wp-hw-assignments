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
<%--<%--%>
    <%--ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");--%>
<%--%>--%>
Pregled korpe:
<table border="1">
    <tr bgcolor="lightgrey">
        <th>Naziv</th>
        <th>Kolicina</th>
    </tr>
    <c:forEach items="${sessionScope.shoppingCart.getItems()}" var="shoppingCartItem">
    <%--<%--%>
        <%--for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {--%>
    <%--%>--%>
    <form method="POST" action="/add">
        <tr>

            <td>${shoppingCartItem.getProduct().getName()}</td>
            <td><input type="number" min="0" value="${shoppingCartItem.getCount()}" size="3" name="itemCount"></td>
            <input type="hidden" name="itemId" value="${shoppingCartItem.getProduct().getId()}">
            <td> <input type="submit" value="Sacuvaj izmene"></td>
        </tr>
    </form>
    </c:forEach>
</table>

<form method="GET" action="/auth">
    <br>
    <input type="submit" value="Kupi">
</form>

</body>
</html>