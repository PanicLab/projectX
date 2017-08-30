<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
</head>
<body>
    <div style="display: inline-block">
        <textarea title="" readonly>${param.stage_number}</textarea>
        <textarea title="" readonly>${param.attempt}</textarea>
        <textarea title="" readonly>${param.legend}</textarea>
    </div>
</body>
</html>
