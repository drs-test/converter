<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Home page</title>
    <link rel="stylesheet" type="text/css" href="resources/css/home.css"/>
</head>
<body>
<div class="header">
    <span class="userName">User: ${loggedUser}</span>
    <span>
        <a href="/converter/logout">Logout</a>
    </span>
</div>
    <div>
        <form:form method="POST" action="/converter/home" commandName="conversion">
            <table>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td><form:label path="sourceCurrency">Source Currency</form:label></td>
                    <td><form:select path="sourceCurrency">
                        <form:option value="" label="Please Select"/>
                        <form:options items="${sourceCurrencies}" itemLabel="currencyCode" itemValue="currencyCode"/>
                    </form:select>&nbsp;*</td>
                    <td><form:errors path="sourceCurrency" cssClass="valiadation_error"/></td>
                </tr>
                <tr>
                    <td><form:label path="targetCurrency">Target Currency</form:label></td>
                    <td><form:select path="targetCurrency">
                        <form:option value="" label="Please Select"/>
                        <form:options items="${targetCurrencies}" itemLabel="currencyCode" itemValue="currencyCode"/>
                    </form:select>&nbsp;*</td>
                    <td><form:errors path="targetCurrency" cssClass="valiadation_error"/></td>
                </tr>
                <tr>
                    <td><form:label path="validOn">Date</form:label></td>
                    <td><form:input path="validOn" placeholder="yyyy-MM-dd"/>&nbsp;*</td>
                    <td><form:errors path="validOn" cssClass="valiadation_error"/></td>
                </tr>
                <tr>
                    <td/>
                    <td class="submit_button">
                        <input type="submit" value="Submit"/>
                    </td>
                    <td/>
                </tr>
            </table>
        </form:form>
    </div>
<c:if test="${not empty conversions}">
    <div>
        <table>
        <thead>
        <tr>
            <th>Source Currency</th>
            <th>Target Currency</th>
            <th>Exchange Rate</th>
            <th>Valid On</th>
            <th>Queried on</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${conversions}" var="conversion">
            <tr>
                <td><c:out value="${conversion.sourceCurrency}" /></td>
                <td><c:out value="${conversion.targetCurrency}"/></td>
                <td><c:out value="${conversion.rate}"/></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${conversion.validOn}"/></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${conversion.createdOn}"/></td>

            </tr>
        </c:forEach>
        </tbody>
        </table>
    </div>
</c:if>
</body>
</html>
