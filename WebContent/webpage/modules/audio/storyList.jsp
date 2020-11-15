<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>故事管理</title>
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
		<h5>故事列表 </h5>
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
		<form:form id="searchForm" modelAttribute="aStory" action="${ctx}/audio/story/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:select path="status"  class="form-control input-sm" name="status">
					 <form:option value="" label="">==请选择状态==</form:option>
					 <form:option value="1" label="已发布"> 已发布 </form:option>
					 <form:option value="0" label="已下架"> 已下架 </form:option>
				</form:select>
				<form:input path="title" placeholder="请输入故事名称" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
		<div class="pull-right">
			<shiro:hasPermission name="audio:story:add">
				<table:addRow url="${ctx}/audio/story/form" title="添加"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>	
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class="sort-column picture">缩略图</th>
				<th class="sort-column title">标题</th>
				<th class="sort-column price">价格</th>
				<th class="sort-column status">状态</th>
				<th class="sort-column recommend">推荐</th>
				<th class="sort-column sort">排序</th>
				<th class="sort-column chapterCount">章数</th>
				<th class="sort-column fansCount">用户数</th>
				<th class="sort-column sumMoney">交易额</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aStory">
			<tr>
				<td><img src='${aStory.picture}' width="30px" height="30px"/></td>
				<td>${aStory.title}</td>
				<td>${aStory.price}</td>
				<td>${aStory.status==true?"已发布":"已下架"}</td>
				<td>${aStory.recommend==true?"是":"否"}</td>
				<td>${aStory.sort}</td>
				<td>${aStory.chapterCount}</td>
				<td>
					<a href="#" onclick="openDialogView('查看用户', '${ctx}/audio/story/fans?storyId=${aStory.storyId}','800px', '500px')" class="btn btn-info btn-xs" >${aStory.fansCount}</a>
				</td>
				<td>
					<a href="#" onclick="openDialogView('查看交易', '${ctx}/audio/story/order?storyId=${aStory.storyId}','800px', '500px')" class="btn btn-info btn-xs" >${aStory.sumMoney>0?aStory.sumMoney:"0.00"}</a>
				</td>
				<td>
					<shiro:hasPermission name="audio:story:view">
						<a href="#" onclick="openDialogView('查看故事', '${ctx}/audio/story/form?id=${aStory.storyId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:story:edit">
						<a href="#" onclick="openDialog('修改故事', '${ctx}/audio/story/form?id=${aStory.storyId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:story:delete">
						<a href="${ctx}/audio/story/delete?id=${aStory.storyId}" onclick="return confirmx('确认要删除该故事吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>	
					<shiro:hasPermission name="audio:chapter:list">
						<a href="${ctx}/audio/chapter/list?storyId=${aStory.storyId}" class="btn  btn-primary btn-xs" ><i class="fa fa-external-link-square"></i> 查看章回</a>
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