<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单商品详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox">
	<div class="ibox-title">
		<h5>订单列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>	
	<!-- 工具栏 和 查询条件-->
	<div class="row">
	<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="groceryOrderItem" action="${ctx}/grocery/order/item/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:input path="name" placeholder="输入订单编号、商品名称查询" style="width:400px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
		<div class="pull-right">
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered tabl table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class="sort-column picture">商品图片</th>
				<th class="sort-column order.orderNo">订单编号</th>
				<th class="sort-column name">商品名称</th>
				<th class="sort-column price">商品价格</th>
				<th class="sort-column quantity">购买数量</th>
				<th class="sort-column">合计金额</th>
				<th class="sort-column createTime">创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="groceryOrderItem">
			<tr>
				<td><img src='${groceryOrderItem.picture}' width="30px" height="30px"/></td>
				<td>${groceryOrderItem.order.orderNo}</td>
				<td>${groceryOrderItem.name}</td>
				<td>${groceryOrderItem.price}</td>
				<td>${groceryOrderItem.quantity}</td>
				<td>${groceryOrderItem.price*groceryOrderItem.quantity}</td>
				<td><fmt:formatDate value="${groceryOrderItem.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>