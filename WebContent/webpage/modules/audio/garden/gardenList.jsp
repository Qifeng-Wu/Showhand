<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>远洋大河宸章菜园秒杀管理</title>
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
		<h5>大河宸章业主菜园信息列表 </h5>
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
		<form:form id="searchForm" modelAttribute="aGarden" action="${ctx}/audio/garden/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="ids" name="ids" type="hidden"/>
			<div class="form-group pull-left">
				<select name="isGet" class="form-control input-sm">
					 <option value="">全部</option>
					 <option value="true" <c:if test='${aGarden.isGet == true}'>  selected='selected'  </c:if>>已秒杀</option>
					 <option value="false" <c:if test='${aGarden.isGet == false}'>  selected='selected'  </c:if>>未秒杀</option>
				</select>
				<form:input path="room" placeholder="请输入房号" style="width:130px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
				<form:input path="telephone" placeholder="请输入联系电话" style="width:130px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
				<form:input path="name" placeholder="请输入姓名" style="width:130px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
				<form:input path="phone" placeholder="请输入手机号" style="width:130px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
				<form:input path="garden" placeholder="请输入菜园编号" style="width:130px" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
		        <button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
		        <button type="button" class="btn btn-sm btn-primary" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			 </div>	
		</form:form>
		<div class="pull-right">
			<shiro:hasPermission name="audio:garden:add">
				<table:addRow url="${ctx}/audio/garden/form" title="添加"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>	
			<shiro:hasPermission name="audio:garden:edit">
			    <table:editRow url="${ctx}/audio/garden/form" title="编辑" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="audio:garden:delete">
				<table:delRow url="${ctx}/audio/garden/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>	
			<shiro:hasPermission name="audio:garden:export">
	       		<table:importExcel url="${ctx}/audio/garden/import"></table:importExcel><!-- 导入按钮 -->
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="audio:garden:export">
       			<table:exportExcel url="${ctx}/audio/garden/export"></table:exportExcel><!-- 导出按钮 -->
      		</shiro:hasPermission>
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th class="sort-column room">房号</th>
				<th class="sort-column telephone">联系电话</th>
				<th class="sort-column name">姓名</th>
				<th class="sort-column phone">手机号</th>
				<th class="sort-column isGet">状态</th>
				<th class="sort-column garden">菜园编号</th>
				<th class="sort-column getTime">秒杀时间</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aGarden">
			<tr>
				<td> <input type="checkbox" id="${aGarden.gardenId}" class="i-checks"></td>
				<td>${aGarden.room}</td>
				<td>${aGarden.telephone}</td>
				<td>${aGarden.name}</td>
				<td>${aGarden.phone}</td>
				<td>${aGarden.isGet==true?"已秒杀":"未秒杀"}</td>
				<td>${aGarden.garden}</td>
				<td><fmt:formatDate value="${aGarden.getTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<shiro:hasPermission name="audio:garden:view">
						<a href="#" onclick="openDialogView('查看业主菜园信息', '${ctx}/audio/garden/form?id=${aGarden.gardenId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:garden:edit">
						<a href="#" onclick="openDialog('修改业主信息', '${ctx}/audio/garden/form?id=${aGarden.gardenId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:garden:delete">
						<a href="${ctx}/audio/garden/delete?id=${aGarden.gardenId}" onclick="return confirmx('确认要删除该条记录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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