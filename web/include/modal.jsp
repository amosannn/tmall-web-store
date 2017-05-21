<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<div class="modal" id="loginModal" tabindex="-1" role="dialog">
	<div class="modal-dialog loginDivInProductPageModalDiv">
			<div class="modal-content">
					<div class="loginDivInProductPage">
						<div class="loginErrorMessageDiv">
							<div class="alert alert-danger">
								<button type="button" class="close" data-dismiss="alert" aria-label="close"></button>
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

						<div>
							<button class="btn btn-block redButton loginSubmitButton" type="submit">登录</button>
						</div>

						<div class="login-links">
							<a href="#nowhere">忘记密码</a> 
							<a href="register.jsp"">免费注册</a> 
						</div>
						
					</div>
			</div>
	</div>
</div>