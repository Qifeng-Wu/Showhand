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
		<form:form id="searchForm" modelAttribute="groceryOrder" action="${ctx}/grocery/order/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:select id="status" path="status" class="form-control m-b">
					<form:option value="" label="订单状态"/>
					<form:option value="1" label="待发货"/>
					<form:option value="2" label="待收货（已发货）"/>
					<form:option value="3" label="已收货（交易完成）"/>
				</form:select>
				<form:input path="orderNo" placeholder="输入订单编号、用户姓名、手机号码查询" style="width:400px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
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
				<th class="sort-column fansAddress.name">用户姓名</th>
				<th class="sort-column fansAddress.phone">手机号码</th>
				<th class="sort-column orderNo">订单编号</th>
				<th class="sort-column payMoney">支付金额</th>
				<th class="sort-column payType">支付方式</th>
				<th class="sort-column payTime">购买时间</th>
				<th class="sort-column status">订单状态</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="groceryOrder">
			<tr>
				<td><img src='${groceryOrder.fans.avatar}' width="30px" height="30px"/></td>
				<td>${groceryOrder.fans.nickname}</td>
				<td>${groceryOrder.fansAddress.name}</td>
				<td>${groceryOrder.fansAddress.phone}</td>
				<td>${groceryOrder.orderNo}</td>
				<td>${groceryOrder.payMoney}</td>
				<td>${groceryOrder.payType}</td>
				<td><fmt:formatDate value="${groceryOrder.payTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>
					<c:if test='${groceryOrder.status eq "1"}'>待发货</c:if>
					<c:if test='${groceryOrder.status eq "2"}'>待收货</c:if>
					<c:if test='${groceryOrder.status eq "3"}'>已收货</c:if>
				</td>
				<td>
					<a href="#" onclick="openDialogView('查看订单信息', '${ctx}/grocery/order/form?id=${groceryOrder.orderId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					<a href="#" onclick="openDialogView('查看订单商品信息', '${ctx}/grocery/order/item/list?orderId=${groceryOrder.orderId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-search-plus"></i> 商品</a>
					<c:if test='${groceryOrder.status eq "1"}'>   				
					<a href="${ctx}/grocery/order/fahuo?orderId=${groceryOrder.orderId}" onclick="return confirmx('确认已经发货吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-paper-plane"></i> 发货</a>
					</c:if>
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