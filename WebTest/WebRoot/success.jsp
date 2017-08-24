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

<title>My JSP 'success.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<div align="center">
		<h1>登录成功!</h1>
		<br> <br>

		<%
			String[] isUserCookie = request.getParameterValues("isUseCookie");
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String account = request.getParameter("account");
			request.getSession().setAttribute("account", account);
			
			if (isUserCookie != null && isUserCookie.length > 0) {
				Cookie usernameCookie = new Cookie("account", request.getParameter("account"));
				Cookie passwordCookie = new Cookie("password", request.getParameter("password"));

				usernameCookie.setMaxAge(864000);
				passwordCookie.setMaxAge(864000);

				response.addCookie(usernameCookie);
				response.addCookie(passwordCookie);
			} else {
				Cookie[] cookies = request.getCookies();
				if (cookies != null && cookies.length > 0) {
					for (Cookie c : cookies) {
						if (c.getName().equals("account") || c.getName().equals("password")) {
							c.setMaxAge(0);
							response.addCookie(c);
						}
					}
					System.out.println("clear cookies!");
				}
			}
		%>

		<a href="User_info.jsp">查看用户信息</a>
	</div>
</body>
</html>
