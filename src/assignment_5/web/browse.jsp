<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="beans.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Pocetna strana</title>
</head>

<body>
<%
   Products products = (Products) session.getAttribute("products");
    String searchCriteria = (String)session.getAttribute("searchCriteria");
%>
<form method="GET" action="/">
    <input type="text" name="search" value='<%=searchCriteria == null?"":searchCriteria%>'><input type="submit" value="Pretrazi">
</form>
Raspolozivi proizvodi:
<table border="1">
    <tr bgcolor="lightgrey">
        <th>Naziv</th>
        <th>Cena</th>
        <th>&nbsp;</th>
    </tr>
    <%
        if(searchCriteria == null){
        for (Product p : products.values()) {
    %>
    <form method="GET" action="/add">
        <tr>
            <td><%=p.getName()%></td>
            <td><%=p.getPrice()%></td>
            <td><input type="text" size="3" name="itemCount">
                <input type="hidden" name="itemId" value="<%=p.getId()%>">
                <input type="submit" value="Dodaj"></td>
        </tr>
    </form>
    <%
            }
        }
        else{
            for(Product p: products.values()){
                if(p.getName().startsWith(searchCriteria)){%>
                <form method="GET" action="/add">
                <tr>
                    <td><%=p.getName()%></td>
                    <td><%=p.getPrice()%></td>
                    <td><input type="text" size="3" name="itemCount">
                    <input type="hidden" name="itemId" value="<%=p.getId()%>">
                    <input type="submit" value="Dodaj"></td>
                </tr>
                </form>
                <%} %>
            <%}%>
        <%}%>
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