<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
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
		<form:form id="searchForm" modelAttribute="aOrder" action="${ctx}/audio/order/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:input path="orderNo" placeholder="输入订单编号、用户昵称、用户编号查询" style="width:300px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
		<div class="pull-right">
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class="sort-column fans.avatar">用户头像</th>
				<th class="sort-column fans.nickname">用户姓名</th>
				<th class="sort-column fans.cid">用户编号</th>
				<th class="sort-column orderNo">订单编号</th>
				<th class="sort-column payMoney">支付金额</th>
				<th class="sort-column payType">支付方式</th>
				<th class="sort-column payTime">购买时间</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aOrder">
			<tr>
				<td><img src='${aOrder.fans.avatar}' width="30px" height="30px"/></td>
				<td>${aOrder.fans.nickname}</td>
				<td>${aOrder.fans.cid}</td>
				<td>${aOrder.orderNo}</td>
				<td>${aOrder.payMoney}</td>
				<td>${aOrder.payType}</td>
				<td><fmt:formatDate value="${aOrder.payTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>
					<shiro:hasPermission name="audio:order:view">
						<a href="#" onclick="openDialogView('查看订单信息', '${ctx}/audio/order/form?id=${aOrder.orderId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:order:item:list">
						<a href="#" onclick="openDialogView('查看订单商品信息', '${ctx}/audio/order/item/list?orderId=${aOrder.orderId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-search-plus"></i> 商品</a>
					</shiro:hasPermission>
				</td>
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