<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客服批量问题管理</title>
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
		<h5>客服批量问题信息列表 </h5>
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
		<form:form id="searchForm" modelAttribute="qisServiceComplaint" action="${ctx}/qis/qisServiceComplaint/" method="post" class="form-inline">
			<input name="type" type="hidden" value="${type}"/>
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->	
			<div class="form-group pull-left">
				<form:select path="state" class="form-control m-b">
					<form:option value="" label="问题状态"/>
					<form:option value="0" label="已结案"/>
					<form:option value="2" label="已取消"/>
					<form:option value="1" label="待区域经理处理"/>
					<form:option value="4" label="待客服技术处理"/>
					<form:option value="5" label="客服技术驳回，待区域经理处理"/>
					<form:option value="6" label="待质量中心处理"/>
					<form:option value="7" label="质量中心驳回，待客服技术处理"/>
				</form:select>
				<form:input path="serialNumber" placeholder="输入文件编号、单位名称、站点编号、故障描述查询" style="width:350px" htmlEscape="false" maxlength="30"  class=" form-control input-sm"/>
	          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 查询</button>
	          	<button type="button" class="btn btn-sm btn-primary" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			 </div>	
		</form:form>
		<div class="pull-right">
			<shiro:hasPermission name="qis:qisServiceComplaint:add">
				<a href="${ctx}/qis/qisServiceComplaint/form" class="btn btn-white btn-sm" ><i class="fa fa-plus"></i> 添加</a>
				<%-- <table:addRow url="${ctx}/qis/qisServiceComplaint/form" title="客诉信息"></table:addRow><!-- 增加按钮 --> --%>
			</shiro:hasPermission>
			<shiro:hasPermission name="qis:qisServiceComplaint:edit">
			    <table:editRow url="${ctx}/qis/qisServiceComplaint/form" title="客诉信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="qis:qisServiceComplaint:del">
				<table:delRow url="${ctx}/qis/qisServiceComplaint/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="qis:qisServiceComplaint:import">
				<table:importExcel url="${ctx}/qis/qisServiceComplaint/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="qis:qisServiceComplaint:export"> --%>
	       		<table:exportExcel url="${ctx}/qis/qisServiceComplaint/export"></table:exportExcel><!-- 导出按钮 -->
	       	<%-- </shiro:hasPermission> --%>
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column serialNumber">文件编号</th>
				<th  class="sort-column company">单位名称</th>
				<th  class="sort-column customerTypeId">客户类型</th>
				<th  class="sort-column industryId">行业类别</th>
				<th  class="sort-column engineer">工程师</th>
				<th  class="sort-column stationCode">站点编号</th>
				<th  class="sort-column createTime">创建时间</th>
				<th  class="sort-column state">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qisServiceComplaint">
			<tr>
				<td> <input type="checkbox" id="${qisServiceComplaint.id}" class="i-checks"></td>
				<td>${qisServiceComplaint.serialNumber}</td>
				<td>${qisServiceComplaint.company}</td>
				<td>${qisServiceComplaint.customerType.name}</td>
				<td>${qisServiceComplaint.industry.name}</td>
				<td>${qisServiceComplaint.engineer}</td>
				<td>${qisServiceComplaint.stationCode}</td>
				<td><fmt:formatDate value="${qisServiceComplaint.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
				 	<c:if test='${qisServiceComplaint.state eq "0"}'><span style="color:red">已结案</span></c:if>
				 	<c:if test='${qisServiceComplaint.state eq "1"}'><span style="color:red">待区域经理处理</span></c:if>
					<c:if test='${qisServiceComplaint.state eq "2"}'><span style="color:red">发文人已取消</span></c:if>
					<c:if test='${qisServiceComplaint.state eq "3"}'><span style="color:red">区域经理驳回，待客服处理</span></c:if>
					<c:if test='${qisServiceComplaint.state eq "4"}'><span style="color:red">待客服技术处理</span></c:if>
					<c:if test='${qisServiceComplaint.state eq "5"}'><span style="color:red">客服技术驳回，待区域经理处理</span></c:if>
					<c:if test='${qisServiceComplaint.state eq "6"}'><span style="color:red">待质量中心处理</span></c:if>
					<c:if test='${qisServiceComplaint.state eq "7"}'><span style="color:red">质量中心驳回，待客服技术处理</span></c:if>	
				</td>
				<td>					
					<a href="${ctx}/qis/qisServiceComplaint/form?id=${qisServiceComplaint.id}" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
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