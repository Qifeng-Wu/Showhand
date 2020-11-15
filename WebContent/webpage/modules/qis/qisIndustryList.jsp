<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>行业类别管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>行业类别列表 </h5>
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
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="qisIndustry" action="${ctx}/qis/qisIndustry/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group pull-left">
			<form:input path="name" placeholder="输入行业类别名称查询" style="width:350px" htmlEscape="false" maxlength="15"  class="form-control input-sm"/>
          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 查询</button>
          	<button type="button" class="btn btn-sm btn-primary" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		 </div>	
	</form:form>
	<div class="pull-right">
		<%-- <shiro:hasPermission name="qis:qisIndustry:add"> --%>
			<table:addRow url="${ctx}/qis/qisIndustry/form" title="行业类别"></table:addRow><!-- 增加按钮 -->
		<%-- </shiro:hasPermission>
		<shiro:hasPermission name="qis:qisIndustry:edit"> --%>
		    <table:editRow url="${ctx}/qis/qisIndustry/form" title="行业类别" id="contentTable"></table:editRow><!-- 编辑按钮 -->
		<%-- </shiro:hasPermission>
		<shiro:hasPermission name="qis:qisIndustry:del"> --%>
			<table:delRow url="${ctx}/qis/qisIndustry/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
		<%-- </shiro:hasPermission>
		<shiro:hasPermission name="qis:qisIndustry:import"> --%>
       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>	
	</div>
	<br/>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column name">行业类别名称</th>
				<th  class="sort-column addTime">添加或编辑时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qisIndustry">
			<tr>
				<td> <input type="checkbox" id="${qisIndustry.id}" class="i-checks"></td>
				<td>${qisIndustry.name}</td>
				<td><fmt:formatDate value="${qisIndustry.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="#" onclick="openDialogView('查看行业类别', '${ctx}/qis/qisIndustry/form?id=${qisIndustry.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
    				<a href="#" onclick="openDialog('修改行业类别', '${ctx}/qis/qisIndustry/form?id=${qisIndustry.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					<a href="${ctx}/qis/qisIndustry/delete?id=${qisIndustry.id}" onclick="return confirmx('确认要删除该行业类别吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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