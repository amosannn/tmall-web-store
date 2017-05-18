<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

	<a href="${contextPath}">
		<img id="logo" src="img/site/logo.gif" class="logo">
	</a>

	<form method="post" action="foresearch">
		<div class="searchDiv">
			<input type="text" name="keyword" value="${param.keyword}" placeholder="空调 冰箱" >
			<button type="submit" class="searchButton">搜索</button>
			<div class="searchBelow">
				<c:forEach items="${cs}" var="c" varStatus="st">
					<c:if test="${st.count>=5 && st.count<=8}">
						<span>
							<a href="forecategory?cid=${c.id}">
								${c.name}
							</a>
							<c:if test="${st.count!=8}">
								<span>|</span>
							</c:if>
						</span>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</form>