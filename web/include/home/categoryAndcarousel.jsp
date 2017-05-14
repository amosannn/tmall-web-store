<img src="img/site/catear.png" id="catear" class="catear"/>
<div class="categoryWithCarousel">

	<div class="headbar">
		<div class="head">
			<span style="margin-left: 10px" class="glyphicon glyphicon-th-list"></span>
			<span style="margin-left: 10px">商品分类</span>
		</div>

		<div class="rightMenu">
			<span><a href=""><img src=""></a></span>
			<span><a href=""><img src=""></a></span>
			<c:forEach items="${cs}" var="c" varStatue="st">
				<c:if test="st.count<=4">
					<span><a href="forecategory?${c.id}">${c.name}</a></span>
				</c:if>
			</c:forEach>
		</div>

	</div>
	
	<div style="position: relative">
		<%@include file="categoryMenu.jsp" %>
	</div>

	<div style="position: relative;left: 0;top: 0;">
		<%@include file="productsAsideCategorys.jsp" %>
	</div>

	<%@include file="carousel.jsp" %>

	<div class="carouselBackgroundDiv"></div>
</div>