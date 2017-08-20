<html>
<body>
<div class="form">
    <p>Please, register</p>
    <form class="register-form" action="" method="post">
        <input name="firstName" type="text" placeholder="first name" required=""/>
        <input name="lastName" placeholder="last name" required=""/>
        <input name="password" type="password" placeholder="password" required=""/>
        <button>create account</button>
        <p class="message">Already registered? <a th:href="@{login}">Sign In</a></p>
    </form>
</div>
</body>
</html>
