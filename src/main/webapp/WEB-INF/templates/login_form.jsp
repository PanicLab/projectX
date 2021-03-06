<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
</head>
<body>
    <fieldset>
        <legend>${param.message}</legend>
        <form method="post" action="game_menu">
            <table cellpadding="2" cellspacing="2">
                <tr>
                    <td>Имя</td>
                    <td> <input type="text" name="firstName" placeholder="Имя" required="required"></td>
                </tr>
                <tr>
                    <td>Пароль</td>
                    <td> <input type="password" name="password" placeholder="Пароль" required="required"></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td> <input type="submit" value="${param.on_click_button_action}"></td>
                </tr>
            </table>
            <input type="hidden" name="mode" value="${param.action}">
        </form>
    </fieldset>
</body>
</html>
