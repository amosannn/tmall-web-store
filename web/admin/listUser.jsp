<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
var deleteUserId = 0;
var deleteUser = false;

$(function(){
	$("a.deleteUserLink").click(function(){
		console.log("click delete button");
		deleteUserId = $(this).attr("uid");
		deleteUser = false;
		$("#deleteConfirmModal").modal("show");
	});

	$("button.deleteConfirmButton").click(function(){
		deleteUser = true;
		$("#deleteConfirmModal").modal('hide');
	});

	$("#deleteConfirmModal").on('hidden.bs.modal', function(e){
		if(deleteUser){
			var page = "admin_user_delete";
			$.post(
				page,
				{"uid": deleteUserId},
				function(result){
					if("success" == result)
						$("tr.userListTR[uid="+deleteUserId+"]").hide();
						location.href="admin_user_list";
				}

				);
		}
	})

});
</script>

<title>用户管理</title>


<div class="workingArea">
	<h1 class="label label-info" >用户管理</h1>

	<br>
	<br>
	
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>用户名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${us}" var="u">
				<tr class="userListTR" uid="${u.id}">
					<td>${u.id}</td>
					<td>${u.name}</td>
					<td>
						
							<a href="#nowhere" class="deleteUserLink" uid="${u.id}">
								<button class="btn btn-primary btn-xs">删除</button>
							</a>
						
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="pageDiv">
		<%@include file="../include/admin/adminPage.jsp" %>
	</div>
	
	
</div>

<%@include file="../include/admin/adminFooter.jsp"%>
