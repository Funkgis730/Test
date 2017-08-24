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

<title>My JSP 'Test.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript" src="Js/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$("#test").click(function() {
			$.ajax({
				type : "get",
				url : "Js/userinfo/Information.json",
				async : true,
				success : function(d) {
					var str = "<span style='color:red;'>this is user</span>";
					var arr1 = d.user;
					if (arr1 != null) {
						for (var i = 0; i < arr1.length; i++) { //这里面都是创建li并赋值
							str = str + "<li><p>" + arr1[i].name + "&nbsp;&nbsp;&nbsp;" + arr1[i].age + "</p></li>";
						}
						$("#user").html(str);
					}
				}
			})
		});
	});
</script>

</head>

<body>
	<button id="test">Click</button>
	<h1>Test</h1>
	<div class="div">
		<ul id="user" class="nav">
			<span style="color:red;">this is user information</span>
		</ul>
	</div>

	<form action="servlet/Getjson" method="post">
		<input type="submit" value="click">
	</form>
</body>
</html>
