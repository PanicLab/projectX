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
    </div>
</body>
</html>
