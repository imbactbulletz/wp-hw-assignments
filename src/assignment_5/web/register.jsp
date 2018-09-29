<%--
  Created by IntelliJ IDEA.
  User: Wybz
  Date: 21.04.2018.
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stranica za registraciju</title>
<body>
<div id="page-wrap" align="center">
    <h1>Stranica za registraciju</h1>

    <div id="register-area">
        <form method="post" action="/register">
            <table cellspacing = 0 cellpadding = 3 border = 0>
                <tr>
                    <td>Korisnicko ime:</td>
                    <td><input type="text" name="username"></td>
                </tr>

                <tr>
                    <td>Sifra:</td>
                    <td><input type="password" name="password"></td>
                </tr>

                <tr>
                    <td><input name="submit" type="submit" value="Registruj se"></td>
                </tr>
            </table>
        </form>
        <%
            if(session.getAttribute("userAlreadyExists") != null){ %>
        <h1>Korisnik sa tim korisnickim imenom vec postoji!</h1>
        <%} %>
    </div>
</div>
</body>
</html>

