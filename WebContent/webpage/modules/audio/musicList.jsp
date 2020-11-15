<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>音乐管理</title>
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
		<h5>音乐列表 </h5>
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
		<form:form id="searchForm" modelAttribute="aMusic" action="${ctx}/audio/music/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:input path="title" placeholder="请输入名称查询" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
		<div class="pull-right">
			<shiro:hasPermission name="audio:music:add">
				<table:addRow url="${ctx}/audio/music/form" title="添加"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>	
			<shiro:hasPermission name="audio:music:edit">
				<table:editRow url="${ctx}/audio/music/form" title="编辑" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="audio:music:delete">
				<table:delRow url="${ctx}/audio/music/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>				
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th  class="sort-column picture">缩略图</th>
				<th  class="sort-column title">名称 （点击播放）</th>
				<th  class="sort-column singer">演唱者</th>
				<th  class="sort-column lyricist">作词</th>
				<th  class="sort-column composer">作曲</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aMusic">
			<tr>
				<td><input type="checkbox" id="${aMusic.musicId}" class="i-checks"></td>
				<td><img src='${aMusic.picture}' width="30px" height="30px"/></td>
				<td><a href="${aMusic.link}" target="_blank">${aMusic.title}</a></td>
				<td>${aMusic.singer}</td>
				<td>${aMusic.lyricist}</td>
				<td>${aMusic.composer}</td>
				<td>
					<shiro:hasPermission name="audio:music:view">
						<a href="#" onclick="openDialogView('查看音乐', '${ctx}/audio/music/form?id=${aMusic.musicId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:music:edit">
						<a href="#" onclick="openDialog('修改音乐', '${ctx}/audio/music/form?id=${aMusic.musicId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:music:delete">
						<a href="${ctx}/audio/music/delete?id=${aMusic.musicId}" onclick="return confirmx('确认要删除该音乐吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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