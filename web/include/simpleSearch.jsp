<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
<link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
<script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
</head>

<div>
	<a href="${contextPath}"><img src="img/site/simpleLogo.png" class="simpleLogo"></a>

	<form action="foresearch" method="post">
	<div class="simpleSearchDiv pull-right">
		<input type="text" name="keyword" placeholder="眼镜 手表" value="${param.keyword}">
		<button type="submit" class="searchButton">搜索</button>
		<div class="searchBelow">
			<c:forEach items="${cs}" var="c" varStatus="st">
				<c:if test="study.count>=3&&st.count<=8">
					<span>
						<a href="foreservlet?cid=${c.id}">${c.name}</a>
						<c:if test="st.count!=8">
							<span>|</span>
						</c:if>
					</span>
				</c:if>
			</c:forEach>
		</div>
	</div>
	</form>
	<div style="clear: both;"></div>
</div>