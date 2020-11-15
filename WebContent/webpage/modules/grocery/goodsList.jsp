<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
		<h5>商品列表 </h5>
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
		<form:form id="searchForm" modelAttribute="groceryGoods" action="${ctx}/grocery/goods/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:input path="name" placeholder="请输入商品名称" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
		<div class="pull-right">
			<table:addRow url="${ctx}/grocery/goods/form" title="商品"></table:addRow><!-- 增加按钮 -->	
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="sort-column picture">缩略图</th>
				<th  class="sort-column name">商品名称</th>
				<th  class="sort-column goodsClassification.name">所属分类</th>
				<th  class="sort-column cprice">现价</th>
				<th  class="sort-column oprice">原价</th>
				<th  class="sort-column status">状态</th>
				<th  class="sort-column isRecommend">推荐</th>
				<th  class="sort-column lable">标签</th>
				<th  class="sort-column sort">排序</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="groceryGoods">
			<tr>
				<td><img src='${groceryGoods.picture}' width="30px" height="30px"/></td>
				<td>${groceryGoods.name}</td>
				<td>${groceryGoods.goodsClassification.name}</td>
				<td>${groceryGoods.cprice}</td>
				<td>${groceryGoods.oprice}</td>
				<td>${groceryGoods.status==true?"上架":"下架"}</td>
				<td>${groceryGoods.isRecommend==true?"是":"否"}</td>
				<td>${groceryGoods.lable}</td>
				<td>${groceryGoods.sort}</td>
				<td>
					<a href="#" onclick="openDialogView('查看商品信息', '${ctx}/grocery/goods/form?id=${groceryGoods.goodsId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
    				<a href="#" onclick="openDialog('修改商品信息', '${ctx}/grocery/goods/form?id=${groceryGoods.goodsId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					<a href="${ctx}/grocery/goods/delete?id=${groceryGoods.goodsId}" onclick="return confirmx('确认要删除该商品吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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