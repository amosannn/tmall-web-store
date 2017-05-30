<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<script>
$(document).ready(function(){
	var backgroundRightPosition = $("#loginBackgroundImg").offset().left;
	var loginSmallDivPosition = backgroundRightPosition + 770;
	$("#loginSmallDiv").css("left",loginSmallDivPosition);
	console.log("backgroundRightPosition: " + backgroundRightPosition);
	console.log("loginSmallDivPosition: " + loginSmallDivPosition);
});
$(window).resize(function(){
	var backgroundRightPosition = $("#loginBackgroundImg").offset().left;
	var loginSmallDivPosition = backgroundRightPosition + 770;
	$("#loginSmallDiv").css("left",loginSmallDivPosition);
	console.log("backgroundRightPosition: " + backgroundRightPosition);
	console.log("loginSmallDivPosition: " + loginSmallDivPosition);
});
</script>


<div class="loginDiv" style="position: relative;">
	<div class="simpleLogo">
		<a href="${contextPath}"><img src="img/site/simpleLogo.png"></a>
	</div>

	<img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">

	<form class="loginForm" action="forelogin" method="post">
		<div id="loginSmallDiv" class="loginSmallDiv">
			<div class="loginErrorMessageDiv">
				<div class="alert alert-danger">
					<button type="button" close="close" data-dismiss="alert" alert-label="close"></button>
					<span class="errorMessage"></span>
				</div>
			</div>
			<div class="login_acount_text">账户登录</div>
			<div class="loginInput">
				<span class="loginInputIcon">
					<span class="glyphicon glyphicon-user"></span>
				</span>
				<input type="text" name="name" id="name" placeholder="会员名/邮箱/手机号">
			</div>
			<div class="loginInput">
				<span class="loginInputIcon">
					<span class="glyphicon glyphicon-lock"></span>
				</span>
				<input type="password" name="password" id="password">
			</div>
			<div style="margin-top:20px">
				<button class="btn btn-block redButton" type="submit">登录</button>
			</div>
			<div class="login-links">
				<a href="#nowhere">忘记密码</a> 
				<a href="register.jsp">免费注册</a> 
			</div>
		</div>
	</form>
</div>