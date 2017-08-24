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

<title>My JSP 'register.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<script type="text/javascript" src="Js/register.js" charset="utf-8"></script>

<body>

	<div align="center">
		<form action="servlet/Register" method="post"
			enctype="multipart/form-data" onsubmit="return validate(this)">
			<table>
				<tr>
					<td align="center">
						<h1>用户注册</h1>
					</td>
				</tr>

				<tr>
					<td>账户：</td>
					<td><input type="text" name="account" onchange="check()"><p id="checkmessage"><%
							String flag = (String) session.getAttribute("isRepeat");
							if (flag != null && flag.equals("true")) {
								out.println("账户名称重复！" );
							}
						%></p></td>
				</tr>
				
				<tr>
					<td>姓名：</td>
					<td><input type="text" name="username"></td>
				</tr>

				<tr>
					<td>密码：</td>
					<td><input type="password" name="password"></td>
				</tr>

				<tr>
					<td>性别：</td>
					<td colspan="2"><input type="radio"
						name="gender" value="男">男 &nbsp;&nbsp;&nbsp;<input type="radio" name="gender"
						value="女">女</td>

				</tr>

				<tr>
					<td>年龄：</td>
					<td><input type="text" name="age"></td>
				</tr>

				<tr>
					<td>邮箱：</td>
					<td><input type="text" name="email"></td>
				</tr>

				<tr>
					<td>学校：</td>
					<td><input type="text" name="school"></td>
				</tr>

				<tr>
					<td>专业：</td>
					<td><input type="text" name="major"></td>
				</tr>

				<tr>
					<td>爱好：</td>
					<td colspan="2" align="center"><input type="checkbox"
						name="favorites" value="足球">足球&nbsp;&nbsp;&nbsp;
					<input type="checkbox"
						name="favorites" value="网球">网球&nbsp;&nbsp;&nbsp;
					<input type="checkbox"
						name="favorites" value="篮球">篮球</td>
				</tr>

				<tr>
					<td>上传头像</td>
					<td align="center"><input type="file" name="uploadFile" /></td>
				</tr>

				<tr>
					<td><br></td>
					<td colspan="3" align="center"><input type="submit" value="提交"  onclick="sub()"></td>
				</tr>

			</table>

		</form>
	</div>
</body>
</html>
