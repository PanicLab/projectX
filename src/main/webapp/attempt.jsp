<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
</head>

<body>
    <div style="display: inline-block">
        <form action="game" method="post">
            <c:set var="digit_1" value="0" scope="page"/>
            <c:if test="${not empty param.digit_1}">
                <c:set var="digit_1" value="${param.digit_1}"/>
            </c:if>
            <c:set var="digit_2" value="1" scope="page"/>
            <c:if test="${not empty param.digit_2}">
                <c:set var="digit_2" value="${param.digit_2}"/>
            </c:if>
            <c:set var="digit_3" value="2" scope="page"/>
            <c:if test="${not empty param.digit_3}">
                <c:set var="digit_3" value="${param.digit_3}"/>
            </c:if>
            <c:set var="digit_4" value="3" scope="page"/>
            <c:if test="${not empty param.digit_4}">
                <c:set var="digit_4" value="${param.digit_4}"/>
            </c:if>
            <input type="number" name="digit_1" min="0" max="9" value="${pageScope.digit_1}"/>
            <input type="number" name="digit_2" min="0" max="9" value="${pageScope.digit_2}"/>
            <input type="number" name="digit_3" min="0" max="9" value="${pageScope.digit_3}"/>
            <input type="number" name="digit_4" min="0" max="9" value="${pageScope.digit_4}"/>
            <button>Угадать</button>
        </form>
    </div>
</body>
</html>
