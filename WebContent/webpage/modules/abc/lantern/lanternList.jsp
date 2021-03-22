<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>元宵节 猜灯谜</title>
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
		<h5>用户信息列表 </h5>
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
		<form:form id="searchForm" modelAttribute="lantern" action="${ctx}/abc/lantern/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="ids" name="ids" type="hidden"/>
			<div class="form-group pull-left">
				<select name="status" class="form-control input-sm">
					 <option value="">全部</option>
					 <option value="0" <c:if test='${lantern.status == 0}'>  selected='selected'  </c:if>>未答题</option>
					 <option value="1" <c:if test='${lantern.status == 1}'>  selected='selected'  </c:if>>已答题</option>
					 <option value="2" <c:if test='${lantern.status == 2}'>  selected='selected'  </c:if>>已领取</option>
				</select>
				<form:input path="project" placeholder="请输入项目名称" style="width:130px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
				<form:input path="room" placeholder="请输入房间号码" style="width:130px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
				<form:input path="name" placeholder="请输入业主姓名" style="width:130px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
				<form:input path="phone" placeholder="请输入手机号码" style="width:130px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		        <button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
		        <button type="button" class="btn btn-sm btn-primary" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			 </div>	
		</form:form>
		<div class="pull-right">
				<button class="btn btn-white btn-sm" onclick="openDialog('活动时间设置', '${ctx}/abc/lantern/activityTime','800px', '500px')" data-toggle="tooltip" data-placement="top"><i class="fa fa-hourglass-2"> 活动时间</i></button>
				<table:delRow url="${ctx}/abc/lantern/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
       			<table:exportExcel url="${ctx}/abc/lantern/export"></table:exportExcel><!-- 导出按钮 -->
	       	<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th class="sort-column status">状态</th>
				<th class="sort-column score">答题分数</th>
				<th class="sort-column sopendTime">答题用时</th>
				<!-- <th class="sort-column answerStartTime">开始时间</th> -->
				<!--<th class="sort-column answerEndTime">结束时间</th> -->
				<th class="sort-column project">项目名称</th>
				<th class="sort-column room">房间号码</th>
				<th class="sort-column name">业主姓名</th>
				<th class="sort-column phone">手机号</th>
				<th class="sort-column address">收货地址</th>
				<th class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="lantern">
			<tr>
				<td> <input type="checkbox" id="${lantern.openId}" class="i-checks"></td>
				<td>
					<c:choose>
						<c:when test="${lantern.status==1}">
							已答题
						</c:when>
						<c:when test="${lantern.status==2}">
							已领取
						</c:when>
						<c:otherwise>
							未答题
						</c:otherwise>
					</c:choose>
				</td>
				<td>${lantern.score}</td>
				<td>${lantern.spendTime}</td>
				<!--<td><fmt:formatDate value="${lantern.answerStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>-->
				<!--<td><fmt:formatDate value="${lantern.answerEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>-->
				<td>${lantern.project}</td>
				<td>${lantern.room}</td>
				<td>${lantern.name}</td>
				<td>${lantern.phone}</td>
				<td>${lantern.address}</td>
				<td>
					<a href="${ctx}/abc/lantern/delete?id=${lantern.openId}" onclick="return confirmx('确认要删除该条记录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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