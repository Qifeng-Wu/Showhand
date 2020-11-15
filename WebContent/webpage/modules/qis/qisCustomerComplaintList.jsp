<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户问题管理</title>
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
		<h5>客户问题信息列表 </h5>
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
		<form:form id="searchForm" modelAttribute="qisCustomerComplaint" action="${ctx}/qis/qisCustomerComplaint/" method="post" class="form-inline">
			<input name="type" type="hidden" value="${type}"/>
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->	
			<div class="form-group pull-left">
				<form:select path="state" class="form-control m-b">
					<form:option value="" label="问题状态"/>
					<form:option value="0" label="已结案"/>
					<form:option value="2" label="已取消"/>
					<form:option value="1" label="待区域经理分配站点"/>
					<form:option value="3" label="待服务站点预约上门"/>
					<form:option value="4" label="待服务站点上门服务"/>
					<form:option value="5" label="待区域经理处理"/>
					<form:option value="6" label="待客服技术处理"/>
					<form:option value="7" label="客服技术驳回，待区域经理处理"/>
					<form:option value="8" label="待质量中心处理"/>
					<form:option value="9" label="质量中心驳回，待客服技术处理"/>
					<form:option value="10" label="待客户评价"/>
				</form:select>
				<form:input path="serialNumber" placeholder="输入文件编号、单位名称、联系人、所属省份、故障现象查询" style="width:410px" htmlEscape="false" maxlength="30"  class=" form-control input-sm"/>
	          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 查询</button>
	          	<button type="button" class="btn btn-sm btn-primary" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			 </div>	
		</form:form>
		<div class="pull-right">
			<shiro:hasPermission name="qis:qisCustomerComplaint:add">
				<a href="${ctx}/qis/qisCustomerComplaint/form" class="btn btn-white btn-sm" ><i class="fa fa-plus"></i> 添加</a>
				<%-- <table:addRow url="${ctx}/qis/qisCustomerComplaint/form" title="客诉信息"></table:addRow><!-- 增加按钮 --> --%>
			</shiro:hasPermission>
			<shiro:hasPermission name="qis:qisCustomerComplaint:edit">
			    <table:editRow url="${ctx}/qis/qisCustomerComplaint/form" title="客诉信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="qis:qisCustomerComplaint:del">
				<table:delRow url="${ctx}/qis/qisCustomerComplaint/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="qis:qisCustomerComplaint:import">
				<table:importExcel url="${ctx}/qis/qisCustomerComplaint/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="qis:qisCustomerComplaint:export"> --%>
	       		<table:exportExcel url="${ctx}/qis/qisCustomerComplaint/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column contact">联系人</th>
				<th  class="sort-column phone">联系电话</th>
				<th  class="sort-column province">所属省份</th>
				<th  class="sort-column createTime">创建时间</th>
				<th  class="sort-column state">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qisCustomerComplaint">
			<tr>
				<td> <input type="checkbox" id="${qisCustomerComplaint.id}" class="i-checks"></td>
				<td>${qisCustomerComplaint.serialNumber}</td>
				<td>${qisCustomerComplaint.company}</td>
				<td>${qisCustomerComplaint.province}</td>
				<td>${qisCustomerComplaint.contact}</td>
				<td>${qisCustomerComplaint.phone}</td>
				<td><fmt:formatDate value="${qisCustomerComplaint.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
				 	<c:if test='${qisCustomerComplaint.state eq "0"}'><span style="color:red">已结案</span></c:if>
				 	<c:if test='${qisCustomerComplaint.state eq "1"}'><span style="color:red">待区域经理分配站点</span></c:if>
					<c:if test='${qisCustomerComplaint.state eq "2"}'><span style="color:red">客户已取消</span></c:if>
					<c:if test='${qisCustomerComplaint.state eq "3"}'><span style="color:red">待服务站点预约上门</span></c:if>
					<c:if test='${qisCustomerComplaint.state eq "4"}'><span style="color:red">服务站点已预约，待上门服务</span></c:if>
					<c:if test='${qisCustomerComplaint.state eq "5"}'><span style="color:red">待客服区域经理处理</span></c:if>
					<c:if test='${qisCustomerComplaint.state eq "6"}'><span style="color:red">待客服技术处理</span></c:if>
					<c:if test='${qisCustomerComplaint.state eq "7"}'><span style="color:red">客服技术驳回，待区域经理处理</span></c:if>	
					<c:if test='${qisCustomerComplaint.state eq "8"}'><span style="color:red">待质量中心处理</span></c:if>	
					<c:if test='${qisCustomerComplaint.state eq "9"}'><span style="color:red">质量中心驳回，待客服技术处理</span></c:if>	
					<c:if test='${qisCustomerComplaint.state eq "10"}'><span style="color:red">待客户评价</span></c:if>	
				</td>
				<td>					
					<a href="${ctx}/qis/qisCustomerComplaint/form?id=${qisCustomerComplaint.id}" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
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