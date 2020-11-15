<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>QA工程师列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			 var str="";
			  var ids="";
			  $("tbody tr td input:checkbox").each(function(){
			    if(true == $(this).is(':checked')){
			      str+=$(this).attr("id")+",";
			    }
			  });
			  if(str.substr(str.length-1)== ','){
			    ids = str.substr(0,str.length-1);
			  }
			  if(ids == ""){
				  layer.alert('至少选择一名QA工程师！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				return false;
			  }
			  
			  layer.confirm('是否确认提交信息？', {closeBtn: 0, icon : 3 ,skin : 'layui-layer-molv',btn: ['确认','取消'] //按钮
				}, function(){					
					  $("#QAEngineerUserIds").val(ids);
					  $("#QAEngineerUserIdsForm").attr("target",top.getActiveTab().attr("name"))
					  $("#QAEngineerUserIdsForm").submit();
					  top.layer.closeAll();;//关闭对话框。
				});
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox">
	<div class="ibox-title">
		<h5>QA工程师列表 </h5>
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
	<%-- <div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="user" action="${ctx}/qis/qisComplaint/findQAEngineer" method="post" class="form-inline">
			<div class="form-group pull-left">
				<form:input path="name" placeholder="输入项目名称、项目编号、客户名称查询" style="width:300px" htmlEscape="false" maxlength="40"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>
			 </div>	
		</form:form>
	</div> --%>
	</div>
	<form id="QAEngineerUserIdsForm" action="${ctx}/qis/qisComplaint/qualityManagerConfirm" method="post" class="form-inline">
		<input type="hidden" id="QAEngineerUserIds" name="QAEngineerUserIds">
		<input type="hidden" value='${complaintId}' name="id">
	</form>
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th class="sort-column photo">头像</th>
				<th class="sort-column name">姓名</th>
				<th class="sort-column no">工号</th>
				<th class="sort-column phone">电话</th>
				<th class="sort-column email">邮箱</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${QAEngineerList}" var="user">
			<tr>
				<td> <input type="checkbox" id="${user.id}" class="i-checks"></td>
				<td><img src='${user.photo}' width="30px" height="30px"/></td>
				<td>${user.name}</td>
				<td>${user.no}</td>
				<td>${user.phone}</td>
				<td>${user.email}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>	
		<!-- 分页代码 -->
	<%-- <table:page page="${page}"></table:page> --%>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>