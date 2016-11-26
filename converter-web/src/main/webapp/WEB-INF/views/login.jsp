<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login page</title>
    <link rel="stylesheet" type="text/css" href="resources/css/login.css"/>
</head>
<body>
<div class="messages">
    <c:if test="${empty param}">
        <div class="welcomeMessage">
            <span><h3>Please enter your email and password to login.</h3></span>
        </div>
    </c:if>
    <c:if test="${param.containsKey('error')}">
        <div class="wrongLogin">
            <span><h3>Wrong email or password! Please try again</h3></span>
        </div>
    </c:if>
    <c:if test="${param.containsKey('logout')}">
        <div class="successfulLogout">
            <span><h3>You're successfully logged out</h3></span>
        </div>
    </c:if>
</div>
<form:form method="POST" action="/converter/login" >
    <table>
        <tr>
            <td><label for="email">Email</label></td>
            <td><input id="email" name="email"/></td>
        </tr>

        <tr>
            <td><label for="password">Password</label></td>
            <td><input type="password" id="password" name="password"/></td>
        </tr>

        <tr>
            <td/>
            <td>
                <div>
                 <span>
                   <input type="submit" value="Login"/>
                  </span>

                 <span class="registerLink">
                   <a href="/converter/registration">Register</a>
                 </span>
                </div>
            </td>
            <td>

            </td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form:form>
</body>
</html>
