<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>章回管理</title>
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
		<h5>章回列表 </h5>
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
		<form:form id="searchForm" modelAttribute="aChapter" action="${ctx}/audio/chapter/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:select path="storyId"  class="form-control input-sm" name="storyId">
					 <form:option value="" label="">==请选择所属故事==</form:option>
					 <form:options items="${storyList}" itemLabel="title" itemValue="storyId" htmlEscape="false"/>
				</form:select>
				<form:input path="title" placeholder="请输入章回名称" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
		<div class="pull-right">
			<shiro:hasPermission name="audio:chapter:add">
				<table:addRow url="${ctx}/audio/chapter/form" title="添加"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>	
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class="sort-column story.title">所属故事</th>
				<th class="sort-column title">标题</th>
				<th class="sort-column price">价格</th>
				<th class="sort-column isFree">免费</th>
				<th class="sort-column status">状态</th>
				<th class="sort-column sort">排序</th>
				<th class="sort-column updateTime">跟新时间</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aChapter">
			<tr>
				<td>${aChapter.story.title}</td>
				<td>${aChapter.title}</td>
				<td>${aChapter.price}</td>
				<td>${aChapter.isFree==true?"免费":"付费"}</td>
				<td>${aChapter.status==true?"上架中":"已下架"}</td>
				<td>${aChapter.sort}</td>
				<td><fmt:formatDate value="${aChapter.updateTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>
					<shiro:hasPermission name="audio:chapter:view">
						<a href="#" onclick="openDialogView('查看章回', '${ctx}/audio/chapter/form?id=${aChapter.chapterId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:chapter:edit">
						<a href="#" onclick="openDialog('修改章回', '${ctx}/audio/chapter/form?id=${aChapter.chapterId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:chapter:delete">
						<a href="${ctx}/audio/chapter/delete?id=${aChapter.chapterId}" onclick="return confirmx('确认要删除该章回吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>	
					<shiro:hasPermission name="audio:chapter:set">
						<a href="${ctx}/audio/chapter/set?id=${aChapter.chapterId}" class="btn btn-primary btn-xs" ><i class="fa fa-gear"></i> 小节设置</a>
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