<%--
  Created by IntelliJ IDEA.
  User: Wybz
  Date: 21.04.2018.
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login stranica</title>
</head>
<body>
<div id="page-wrap" align="center">
    <h1>Login page</h1>

    <div id="login-area">
        <form method="POST" action="/auth">
            <table cellspacing = 0 cellpadding = 3 border = 0>
                <tr>
                    <td>Korisnicko ime:</td>
                    <td><input type="text"  name="username"></td>
                </tr>

                <tr>
                    <td>Sifra:</td>
                    <td><input type="password"  name="password"></td>
                </tr>

                <tr>
                    <td><input name="submit" type="submit" value="Login"></td>
                </tr>
            </table>
        </form>
        <form method="get" action="/register.jsp">
            <input name="submit" type="submit" value="Register">
        </form>
        <%
            if(session.getAttribute("badAcreditives") != null){ %>
            <h1>Wrong username/password!</h1>
        <%} %>
    </div>
</div>
</body>
</html>
