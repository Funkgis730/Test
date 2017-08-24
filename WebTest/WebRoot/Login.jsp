<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'Login.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	function reg() {
		document.getElementById("MyForm").action = "register.jsp";
		document.getElementById("MyForm").submit();
	}
</script>
</head>

<%
	String account = "";
	String password = "";
	Cookie[] cookies = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (Cookie c : cookies) {
			if (c.getName().equals("account")) {
				account = c.getValue();
			}
			if (c.getName().equals("password")) {
				password = c.getValue();
			}
		}
	}
%>

<h1>系统登录</h1>
<hr>

<body>

	<form id="MyForm" name="MyForm" action="servlet/Login" method="post">
		<div align="center">
			<table>
				<tr>
					<td>账号:<input type="text" name="account" value="<%=account%>" /></td>
				</tr>

				<tr>
					<td>密码:<input type="password" name="password" 
						value="<%=password%>" /></td>
				</tr>

				<tr>
					<td colspan="2" align="center"><input type="checkbox"
						name="isUseCookie">十天内记住登录状态</td>
				</tr>

				<tr>
					<td colspan="2" align="center"><input type="submit" value="登录"> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="注册" onclick="reg()"></td>
				</tr>

			</table>
		</div>
	</form>
	
</body>

</html>
