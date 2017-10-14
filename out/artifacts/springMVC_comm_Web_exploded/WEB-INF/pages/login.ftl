<#include "/comm/taglibs.ftl" />
<html>
<head>
    <title>login</title>
    <script type="text/javascript" src="${contextPath}/js/jquery-1.9.0.min.js"></script>
    <script>
        function r() {
            var username = document.getElementById("username");
            var pass = document.getElementById("password");
            if (username.value == "") {
                alert("请输入用户名!");
                username.focus();
                return;
            }
            if (pass.value == "") {
                alert("请输入密码");
                return;
            }
            $("#loginForm").submit();
        }
    </script>
</head>
<body>
<form id="loginForm" action="${contextPath}/login" method="post">
    <table width="350" bgcolor="#ccffcc" style="border-color" border="1">
        <tr align=center>
            <td>用户名aa</td>
            <td><input type="text" name="username" id="username"></td>
        </tr>
        <tr align=center>
            <td>密 码</td>
            <td><input type="password" name="password" id="password"></td>
        </tr>
        <#--<tr>-->
            <#--<TD></TD>-->
            <#--<td><input type="checkbox" name="rememberMe" />自动登陆</td>-->
        <#--</tr>-->
        <tr align=center>
            <td colspan="2">
                <p style="color: red"><#if errorMessage??>${errorMessage}</#if></p>
                <input type="button" value="登 录" onclick="r();"/>
            </td>
        </tr>

    </table>
</form>
</body>
</html>