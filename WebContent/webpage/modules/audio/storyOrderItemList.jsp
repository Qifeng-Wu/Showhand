<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>故事交易记录</title>
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
		<h5>故事交易记录列表 </h5>
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
		<form:form id="searchForm" modelAttribute="aOrderItem" action="${ctx}/audio/story/order" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input name="storyId" type="hidden" value="${aOrderItem.storyId}"/>
			<div class="form-group pull-left">
				<form:input path="title" placeholder="用户昵称、订单编号、故事名称、商品名称查询" style="width:330px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
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
				<th  class="sort-column fans.avatar">微信头像</th>
				<th  class="sort-column fans.nickname">微信昵称</th>
				<th class="sort-column type">订单类型</th>
				<th class="sort-column order.orderNo">订单编号</th>
				<th class="sort-column story.title">所属故事</th>
				<th class="sort-column title">商品名称</th>
				<th class="sort-column price">商品价格</th>
				<th class="sort-column createTime">创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aOrderItem">
			<tr>
				<td><img src='${aOrderItem.fans.avatar}' width="30px" height="30px"/></td>
				<td>${aOrderItem.fans.nickname}</td>
				<td>
					<c:if test='${aOrderItem.type eq "0"}'>整个故事购买</c:if>
					<c:if test='${aOrderItem.type eq "1"}'>单个章回购买</c:if>
				</td>
				<td>${aOrderItem.order.orderNo}</td>
				<td>${aOrderItem.story.title}</td>
				<td>${aOrderItem.title}</td>
				<td>${aOrderItem.price}</td>
				<td><fmt:formatDate value="${aOrderItem.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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