<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 26.08.2017
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
    <title>Error</title>
</head>
<body>
    <fieldset>
        <legend>${requestScope.head}</legend>
        <div>
            ${requestScope.message}
        </div>
        <p>Пожалуйста, попробуйте <a href="authentication?action=login">еще раз</a> или
            <a href="authentication?action=register">зарегистрируйтесь</a></p>
    </fieldset>
</body>
</html>
