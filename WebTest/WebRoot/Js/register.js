function checkemail(field) {
	with (field)
	{
	var apos = value.indexOf("@");
	var dotpos = value.lastIndexOf(".");
	if (apos < 1 || dotpos - apos < 2) {
		alert("无效邮箱！");
		return false;
	} else
		return true;
	}
}

function checkpass(field) {
	with(field){
	if (value.length < 6) {
		alert("密码长度不少于6!");
		return false;
	} else return true;
	}
}



function validate(thisform) {
	with(thisform){
	if (!checkpass(password)) {
		return false;
	} else if (!checkemail(email)) {
		return false;
	}
	}
}

function sub(){
	var flag=confirm("是否提交表单？");
	return flag;
}

function check(){
	var acc=document.getElementById("account");
	var x=document.getElementById("checkmessage");
	
}