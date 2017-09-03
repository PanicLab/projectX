<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
    <title>Бык-корова</title>
</head>
<body>

    <div class="simple_block">
        <h2 class="colored_text">БЫК-КОРОВА</h2>
    </div>
    <div class="simple_block">
        <p>Компьютер загадывает 4-х значное число. Цифры загаданного числа не повторяются. </p>
        <p>Задача - угадать загаданное число. Число попыток не ограничено.</p>
        <p>После каждой попытки компьютер сообщает, сколько цифр угадано. Начнем?</p>
    </div>
    <div class="simple_block">
        <p>Чтобы начать игру <a href="authentication?action=login">войдите</a> или
            <a href="authentication?action=register">зарегистрируйтесь</a></p>
    </div>

</body>
</html>
