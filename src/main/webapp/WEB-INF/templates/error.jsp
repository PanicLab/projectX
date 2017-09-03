<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
    <title>Error</title>
</head>
<body>
    <fieldset class="simple_block">
        <legend>${requestScope.head}</legend>
        <div>
            ${requestScope.message}
        </div>
        <p>Пожалуйста, попробуйте <a href="authentication?action=login">еще раз</a> или
            <a href="authentication?action=register">зарегистрируйтесь</a></p>
    </fieldset>
</body>
</html>
