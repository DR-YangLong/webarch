<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>注册</title>
</head>
<body>
<form action="/reg" method="post">
    用户名：<input type="text" value="${(user.username)!}" name="username" maxlength="36"><br/>
    <#--如果返回了用户对象，说明验证没有通过-->
<#if user??>
    <#--验证的属性值-->
    <@spring.bind "user.username" />
    <#--错误信息-->
    <@spring.showErrors "<br>"/>
</#if>
   密码：<input type="password" value="${(user.password)!}" name="password" maxlength="36"><br/>
<#if user??>
    <@spring.bind "user.password" />
    <@spring.showErrors "<br>"/>
</#if>
    角色：<input type="text" value="${(user.roles)!}" name="roles"><br/>
<#if user??>
    <@spring.bind "user.roles" />
    <@spring.showErrors "<br>"/>
</#if>
    <input type="submit" value="注册">
</form>
</body>
</html>