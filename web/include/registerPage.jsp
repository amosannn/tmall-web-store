<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<script>
$(function(){
	<c:if test="${!empty msg}">
		$("span.errorMessage").html("${msg}");
		$("div.registerErrorMessageDiv").css("visibility","visible")
	</c:if>

	$(".registerForm").submit(function(){
		if(0==$("#name").val().length){
			$("span.errorMessage").html("请输入用户名");
			$("div.registerErrorMessageDiv").css("visibbility","visible");
			return false;
		}
		if(0==$("#password").val().length){
			$("span.errorMessage").html("请输入密码");
			$("div.registerErrorMessageDiv").css("visibility","visible");
			return false;
		}
		if(0==$("#repeatpassword").val().length){
			$("span.errorMessage").html("请输入重复密码");
			$("div.registerErrorMessageDiv").css("visibility","visible");
			return false;
		}
		if($("#repeatpassword").val() != $("#password").val()){
			$("span.errorMessage").html("两次密码不一致，请重新输入");
			$("div.registerErrorMessageDiv").css("visibility","visible");
			return false;
		}

		return true;
	});
});
</script>

<form methor="post" action="foreregister" class="registerForm">
	<div class="registerDiv">
		<div class="registerErrorMessageDiv">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert" arial-label="Close"></button>
				<span class="errorMessage"></span>
			</div>
		</div>
	
		<table class="registerTable" align="center">
			<tr>
				<td class="registerTip registerTableLeftTD">注册信息</td>
				<td></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">用户名</td>
				<td class="registerTableRightTD"><input type="text" id="name" name="name" placeholder="会员名一旦设置成功，无法修改" ></td>
			</tr>
			<tr>
				<td class="registerTip registerTableLeftTD">设置登陆密码</td>
				<td class="registerTableRightTD">登陆时验证，保护账号信息</td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">登陆密码</td>
				<td class="registerTableRightTD"><input type="password" id="password" name="password" placeholder="登陆时验证，保护账号信息"></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">密码确认</td>
				<td class="registerTableRightTD"><input type="password" id="repeatpassword" placeholder="请再次输入你的密码"></td>
			</tr>
			<tr>
				<td class="registerButtonTD" colspan="2">
					<a href="registerSuccess.jsp"><button>提    交</button></a>
				</td>
			</tr>

		</table>		
	</div>
</form>