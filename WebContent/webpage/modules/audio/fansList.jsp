<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>微信用户列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		layui.use('layer', function(){ 
		    var $ = layui.jquery, layer = layui.layer;
		 	layer.photos({
			  photos: '#layer-photos-pictures'
		 	});  
		}); 
		
		//备注
		function remark(openId){
    		top.layer.prompt({title: '请输入备注信息', formType: 2}, function(text, inde){
    			$("#openIdHidden").val('');
    			$("#remarkHidden").val('');
    			$("#openIdHidden").val(openId);
    			$("#remarkHidden").val(text);
    			$("#remarkForm").submit();
    			top.layer.close(inde);
			});				
		}
		
		top.layer.closeAll();
		//点击故事分配按钮触发事件
		function assignStory(title,url,width,height){
			top.layer.open({
			    type: 2,  
			    area: [width, height],
			    title: title,
		        maxmin: true, //开启最大化最小化按钮
			    content: url ,
			    btn: ['关闭'],
			    cancel: function(index){ 
			       }
			}); 	
			
		}
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
    
    <div class="ibox-content" id="layer-photos-pictures">
	<sys:message content="${message}"/>	
	<form action="${ctx}/audio/fans/remark" method="post" id="remarkForm">
		<input id="openIdHidden" name="openId" type="hidden"/>
		<input id="remarkHidden" name="remark" type="hidden"/>
	</form>	
	<!-- 工具栏 和 查询条件-->
	<div class="row">
	<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="aFans" action="${ctx}/audio/fans/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">
				<form:input path="nickname" placeholder="请输入微信昵称、身份编号查询" style="width:250px" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		        <button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 查询</button>
		        <button type="button" class="btn btn-sm btn-primary" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			 </div>	
		</form:form>
		<div class="pull-right">
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
				<th  class="sort-column cid">身份编号</th>
				<th  class="sort-column sex">性别</th>
				<th  class="sort-column addtime">首次访问时间</th>
				<th  class="sort-column updatetime">最近访问时间</th>
				<th  class="sort-column status">状态</th>
				<th  class="sort-column remark">备注</th>
				<th  class="sort-column">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aFans">
			<tr>
				<td><img src='${aFans.avatar}' width="30px" height="30px"/></td>
				<td>${aFans.nickname}</td>
				<td>${aFans.cid}</td>
				<td>
					<c:if test='${aFans.sex eq "0"}'>未设置</c:if>
					<c:if test='${aFans.sex eq "1"}'>男</c:if>
					<c:if test='${aFans.sex eq "2"}'>女</c:if>
				</td>
				<td><fmt:formatDate value="${aFans.addtime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td><fmt:formatDate value="${aFans.updatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>
					<c:if test='${aFans.status eq "0"}'>正常</c:if>
					<c:if test='${aFans.status eq "1"}'>禁用</c:if>
				</td>
				<td>${aFans.remark}</td>
				<td>
					<shiro:hasPermission name="audio:fans:remark">
    					<a href="#" onclick="remark('${aFans.openId}')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 备注</a>
    				</shiro:hasPermission>
					<c:if test='${aFans.status eq "0"}'>
						<shiro:hasPermission name="audio:fans:forbid">
							<a href="${ctx}/audio/fans/forbid?status=1&openId=${aFans.openId}" onclick="return confirmx('确认要禁用该用户吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-ban"></i> 禁用</a>
						</shiro:hasPermission>
					</c:if>	
					<c:if test='${aFans.status eq "1"}'>
						<shiro:hasPermission name="audio:fans:forbid">
							<a href="${ctx}/audio/fans/forbid?status=0&openId=${aFans.openId}" onclick="return confirmx('确认要恢复该用户吗？', this.href)" class="btn btn-info btn-xs"><i class="fa fa-repeat"></i> 恢复</a>
						</shiro:hasPermission>
					</c:if>					
					<a href="#" onclick="openDialogView('查看分配故事信息', '${ctx}/audio/item/free/list?openId=${aFans.openId}','800px', '500px')" class="btn btn-primary btn-xs" ><i class="fa fa-search-plus"></i> 已分配故事查看</a>
					<shiro:hasPermission name="audio:story:anotherList">
						<a href="#" onclick="assignStory('分配故事', '${ctx}/audio/story/anotherList?openId=${aFans.openId}','800px', '500px')" class="btn btn-warning btn-xs" ><i class="fa fa-send"></i> 故事分配</a>
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