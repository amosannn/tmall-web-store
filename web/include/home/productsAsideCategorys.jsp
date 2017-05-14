<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<c:forEach items="${cs}" var="c">
	<div class="productsAsideCategorys" cid="${c.id}">
		<c:forEach items="${c.productsByRow}" var="ps">
			<div class="row show1">
				<c:forEach items="ps" var="p">
					<c:if test="!empty p.subTitle">
						<a href="foreproduct?${p.id}">
							<c:forEach items="${fn:split(p.subTitle, ' '}" var="title" varStatue="st">
								<c:if test="st.index==0">
									${title}
								</c:if>
							</c:forEach>
						</a>
					</c:if>
				</c:forEach>
				<div class="seperator"></div>
			</div>
		</c:forEach>
	</div>
</c:forEach>