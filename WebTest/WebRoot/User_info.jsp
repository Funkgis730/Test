<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>

<%@page
	import="org.piesat.zzh.model.MysqlConn, java.sql.Connection, org.piesat.zzh.model.MysqlMethod"%>
<%@page import="org.piesat.zzh.model.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'User_info.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<script type="text/javascript">
	function reTurn() {
		document.getElementById("form1").action = "Login.jsp";
		document.getElementById("form1").submit();
	}
	
	function message(){
	alert("登录成功！");
	}
</script>

<%
	Connection conn = MysqlConn.getConnection();
	String account = (String) session.getAttribute("account");

	MysqlMethod method = new MysqlMethod(conn);
	User user_info = method.getUserInfo(account);
%>


<body onload="message()">
	<div align="center">
		<form action="servlet/ExportWord" id="form1" method="post">
			<table>
				<tr>
					<td><h2>用户信息</h2></td>
				</tr>

				<tr>
					<td>账户:</td>
					<td><%=user_info.getAccount()%></td>
				</tr>

				<tr>
					<td>姓名:</td>
					<td><%=user_info.getUsername()%></td>
				</tr>

				<tr>
					<td>性别:</td>
					<td><%=user_info.getGender()%></td>
				</tr>

				<tr>
					<td>年龄:</td>
					<td><%=user_info.getAge()%></td>
				</tr>

				<tr>
					<td>学校:</td>
					<td><%=user_info.getSchool()%></td>
				</tr>

				<tr>
					<td>专业:</td>
					<td><%=user_info.getMajor()%></td>
				</tr>

				<tr>
					<td>照片:</td>
					<td><img src="<%=user_info.getPicture() %>"  width="128" height="128"></td>
				</tr>

				<tr>
					<td><br></td>
					<td><br></td>
					<td colspan="2" align="center"><input type="button" value="返回"
						onclick="reTurn()">&nbsp;&nbsp;&nbsp;<input type="submit"
						value="导出文档"></td>
				</tr>

			</table>
		</form>
	</div>
</body>

</html>
