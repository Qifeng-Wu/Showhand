<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>粉丝用户列表</title>
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
		<h5>粉丝用户列表 </h5>
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
		<form:form id="searchForm" modelAttribute="groceryFans" action="${ctx}/grocery/fans/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:input path="nickname" placeholder="请输入微信昵称" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
		<div class="pull-right">
	       	<a href="${ctx}/grocery/fans/clerkList" class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left"  title="订单管理员"><i class="fa fa-user"></i> 订单管理员列表</a>	
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>			
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="sort-column avatar">微信头像</th>
				<th  class="sort-column nickname">微信昵称</th>
				<th  class="sort-column sex">性别</th>
				<th  class="sort-column addtime">首次访问时间</th>
				<th  class="sort-column updatetime">最近访问时间</th>
				<th  class="sort-column clerk">订单管理员</th>
				<th  class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="groceryFans">
			<tr>
				<td><img src='${groceryFans.avatar}' width="30px" height="30px"/></td>
				<td>${groceryFans.nickname}</td>
				<td>
					<c:if test='${groceryFans.sex eq "0"}'>未设置</c:if>
					<c:if test='${groceryFans.sex eq "1"}'>男</c:if>
					<c:if test='${groceryFans.sex eq "2"}'>女</c:if>
				</td>
				<td><fmt:formatDate value="${groceryFans.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${groceryFans.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${groceryFans.clerk==true?'是':'否'}</td>				
				<td>
				<c:if test='${groceryFans.clerk==false}'>
					<a href="${ctx}/grocery/fans/settingClerk?openId=${groceryFans.openId}" onclick="return confirmx('确认要设置为订单管理员吗？', this.href)"   class="btn btn-primary btn-xs"><i class="fa fa-user"></i> 设置为订单管理员</a>
				</c:if>					
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