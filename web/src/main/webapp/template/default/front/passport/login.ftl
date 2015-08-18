<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>登录</title>
    <style>.error{color:red;}</style>
</head>
<body>

<#if loginResult?exists>
<div class="error">${loginResult.msg}</div>
</#if>
<form action="" method="post">
    用户名：<input type="text" name="username" value="<@shiro.principal/>"><br/>
    密码：<input type="password" name="password"><br/>
    自动登录：<input type="checkbox" name="rememberMe" value="true"><br/>
    <#if useCaptcha?exists && useCaptcha>
        验证码：<input type="text" name="captcha" value=""><img src="/common/anon/validateImg/1/ajax" onclick="this.src='/common/anon/validateImg/1/ajax?'+Math.random()" title="验证码" alt="点击更改"><br/>
    </#if>
    <input type="submit" value="登录">
</form>

</body>
</html>