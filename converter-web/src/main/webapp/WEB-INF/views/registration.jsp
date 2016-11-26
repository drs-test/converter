<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Registration page</title>
    <link rel="stylesheet" type="text/css" href="resources/css/registration.css"/>
</head>
<body>
<form:form method="POST" action="/converter/registration" commandName="user">
    <table>
        <tr>
            <td><form:label path="email">Email</form:label></td>
            <td><form:input path="email" />&nbsp;*</td>
            <td><form:errors path="email" cssClass="valiadation_error"/></td>
        </tr>
        <tr>
            <td><form:label path="dateOfBirth">DOB</form:label></td>
            <td><form:input path="dateOfBirth" placeholder="yyyy-MM-dd"/>&nbsp;*</td>
            <td><form:errors path="dateOfBirth" cssClass="valiadation_error"/></td>
        </tr>

        <tr>
            <td><form:label path="country">Country</form:label></td>
            <td><form:select path="country">
                <form:option value="" label="Please Select"/>
                <form:options items="${countries}" itemLabel="displayCountry" itemValue="displayCountry"/>
            </form:select>&nbsp;*</td>
            <td><form:errors path="country" cssClass="valiadation_error"/></td>
        </tr>

        <tr>
            <td><form:label path="zipCode">ZIP</form:label></td>
            <td><form:input path="zipCode" />&nbsp;*</td>
            <td><form:errors path="zipCode" cssClass="valiadation_error"/></td>
        </tr>

        <tr>
            <td><form:label path="city">City</form:label></td>
            <td><form:input path="city" />&nbsp;*</td>
            <td><form:errors path="city" cssClass="valiadation_error"/></td>
        </tr>

        <tr>
            <td><form:label path="address">Address</form:label></td>
            <td><form:input path="address" />&nbsp;*</td>
            <td><form:errors path="address" cssClass="valiadation_error"/></td>
        </tr>

        <tr>
            <td><form:label path="password">Password</form:label></td>
            <td><form:password path="password" />&nbsp;*</td>
            <td><form:errors path="password" cssClass="valiadation_error"/></td>
        </tr>
        <tr>
            <td><form:label path="repeatedPassword">Repeat password</form:label></td>
            <td><form:password path="repeatedPassword" />&nbsp;*</td>
            <td><form:errors path="repeatedPassword" cssClass="valiadation_error"/></td>
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
</body>
</html>
