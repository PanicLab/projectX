<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
    <title>Бык-корова</title>
</head>
<body>

    <div class="wrapper">
        <div>
            <p>Это страничка игры. Здесь развиваются все события.</p>
            <p>(Тест) игрок: ${user.name}</p>
            <p>(Тест) попытка № ${game.stageCount + 1}</p>
            <p>(Тест) число: ${game.number}</p>
        </div>
        <div>
            <c:forEach var="stage" items="${game.stageList}" varStatus="current">
                <div>
                <c:import url="stage.jsp">
                    <c:param name="stage_number" value="${stage.attemptCount}"/>
                    <c:param name="attempt" value="${stage.attempt}"/>
                    <c:param name="legend" value="${stage.legend}"/>
                </c:import>
                </div>
            </c:forEach>
        </div>

        <c:choose>
            <c:when test="${game.over}">
                <p>Победа!</p>
            </c:when>
            <c:otherwise>
                <div>
                    <c:import url="attempt.jsp">
                        <c:param name="digit_1" value="${requestScope.digit_1}"/>
                        <c:param name="digit_2" value="${requestScope.digit_2}"/>
                        <c:param name="digit_3" value="${requestScope.digit_3}"/>
                        <c:param name="digit_4" value="${requestScope.digit_4}"/>
                    </c:import>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
