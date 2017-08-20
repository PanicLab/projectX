<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <fieldset>
        <legend>${message}</legend>
        <form method="post" action="">
            <table cellpadding="2" cellspacing="2">
                <tr>
                    <td>Имя</td>
                    <td> <input type="text" name="firstName" placeholder="Имя" required></td>
                </tr>
                <tr>
                    <td>Фамилия</td>
                    <td> <input type="text" name="lastName" placeholder="Фамилия" required></td>
                </tr>
                <tr>
                    <td>Пароль</td>
                    <td> <input type="password" name="password" placeholder="Пароль" required></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td> <input type="submit" value="${on_click_button_action}"></td>
                </tr>
            </table>
        </form>
    </fieldset>
</body>
</html>
