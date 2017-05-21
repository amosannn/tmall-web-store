<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<div>
	<a href="${contextPath}"><img id="simpleLogo" src="img/site/simpleLogo.png" class="simpleLogo"></a>

	<form action="foresearch" method="post">
	<div class="simpleSearchDiv pull-right">
		<input type="text" name="keyword" placeholder="眼镜 手表" value="${param.keyword}">
		<button type="submit" class="searchButton">搜索</button>
		<div class="searchBelow">
			<c:forEach items="${cs}" var="c" varStatus="st">
				<c:if test="${st.count>=5&&st.count<=7}">
					<span>
						<a href="forecategory?cid=${c.id}">${c.name}</a>
						<c:if test="${st.count!=7}">
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