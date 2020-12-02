<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>回访问卷管理</title>
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
		<h5>回访问卷列表 </h5>
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
		<form:form id="searchForm" modelAttribute="aQuestionnaire" action="${ctx}/audio/questionnaire/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:input path="name" placeholder="请输入房号、姓名、验房者编号查询" style="width:300px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
		<div class="pull-right">
			<shiro:hasPermission name="audio:questionnaire:add">
				<table:addRow url="${ctx}/audio/questionnaire/form" title="添加"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>	
			<shiro:hasPermission name="audio:questionnaire:edit">
			    <table:editRow url="${ctx}/audio/questionnaire/form" title="客编辑" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="audio:questionnaire:export">
	       		<table:exportExcel url="${ctx}/audio/questionnaire/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th class="sort-column name">姓名</th>
				<th class="sort-column inspector">验房者编号</th>
				<th class="sort-column question1">总体满意度</th>
				<th class="sort-column question2">房屋质量满意度</th>
				<th class="sort-column question3">房屋设计整体满意度</th>
				<th class="sort-column question4">公共区域装饰及施工质量的满意度</th>
				<th class="sort-column question5">园区绿化及设计的满意度</th>
				<th class="sort-column question6">收楼的整体安排和服务的满意度</th>
				<th class="sort-column remark">意见或建议</th>
				<th class="sort-column createTime">创建时间</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aQuestionnaire">
			<tr>
				<td> <input type="checkbox" id="${aQuestionnaire.questionnaireId}" class="i-checks"></td>
				<td>${aQuestionnaire.room}</td>
				<td>${aQuestionnaire.name}</td>
				<td>${aQuestionnaire.inspector}</td>
				<td>${aQuestionnaire.question1}分</td>
				<td>${aQuestionnaire.question2}分</td>
				<td>${aQuestionnaire.question3}分</td>
				<td>${aQuestionnaire.question4}分</td>
				<td>${aQuestionnaire.question5}分</td>
				<td>${aQuestionnaire.question6}分</td>
				<td>${aQuestionnaire.remark}</td>
				<td><fmt:formatDate value="${aQuestionnaire.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>
					<shiro:hasPermission name="audio:questionnaire:view">
						<a href="#" onclick="openDialogView('查看回访问卷', '${ctx}/audio/questionnaire/form?id=${aQuestionnaire.questionnaireId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:questionnaire:edit">
						<a href="#" onclick="openDialog('修改回访问卷', '${ctx}/audio/questionnaire/form?id=${aQuestionnaire.questionnaireId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="audio:questionnaire:delete">
						<a href="${ctx}/audio/questionnaire/delete?id=${aQuestionnaire.questionnaireId}" onclick="return confirmx('确认要删除该回访问卷吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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