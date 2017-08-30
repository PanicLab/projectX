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
            <input type="number" name="digit_1" min="0" max="9" placeholder="0" value="0"/>
            <input type="number" name="digit_2" min="0" max="9" placeholder="1" value="1"/>
            <input type="number" name="digit_3" min="0" max="9" placeholder="2" value="2"/>
            <input type="number" name="digit_4" min="0" max="9" placeholder="3" value="3"/>
            <button>Угадать</button>
        </form>
    </div>
</body>
</html>
