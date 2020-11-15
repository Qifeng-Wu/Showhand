<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户故事分配管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		
		$(function(){
		    $("#checkAll").click(function() {	    			       
		     	var $subBox = $("input[name='box']");
		     	if($subBox.length == $("input[name='box']:checked").length ? true : false){
		     		$('input[name="box"]').removeAttr("checked"); 
		     	}else{
		     		$('input[name="box"]').attr("checked","true"); 
		     	}
		     });
		});
		
		//点击分配触发事件
		function sendStoryToFan(){
			  var str="";
			  var ids="";
			  $("input[name='box']").each(function(){
			    if(true == $(this).is(':checked')){
			      str+=$(this).attr("id")+",";
			    }
			  });
			  if(str.substr(str.length-1)== ','){
			    ids = str.substr(0,str.length-1);
			  }
			  if(ids == ""){
				  layer.alert('至少选择一个故事！', {skin: 'layui-layer-hong',closeBtn:0,icon:7});
				return;
			  }				  
			layer.alert('确认所选故事要分配给该用户吗?', {
				skin: 'layui-layer-hong',btn: ['确认','取消'],closeBtn:0,icon:3
				}, function(){					
					$("#idsHidden").val(ids);
					$("#sendForm").attr("target",top.getActiveTab().attr("name"))
					$("#sendForm").submit();
					top.layer.closeAll();;//关闭对话框。
					}); 
			} 	
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox">
	<div class="ibox-title">
		<h5>故事列表 </h5>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	<form action="${ctx}/audio/item/free/sendStoryToFan" method="post" id="sendForm" target="iframe0">
		<input type="hidden" name="openId" value="${openId}"/>
		<input id="idsHidden" name="ids" type="hidden"/>
	</form>
	<!-- 工具栏 和 查询条件-->
	<div class="row" style="margin-bottom:20px">
	<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="aStory" action="${ctx}/audio/story/anotherList?openId=${openId}" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group pull-left">				
				<form:input path="title" placeholder="请输入故事标题查询" style="width:250px" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
		          	<button type="button" class="btn btn-sm btn-primary" onclick="search();"> <i class="fa fa-search"></i> 搜索</button>		          	
			 </div>	
		</form:form>
		<div class="pull-right">
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" id="checkAll" title="全选"><i class="fa fa-check"></i> 全选</button>		
	       	<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sendStoryToFan()" title="分配"><i class="fa fa-send"></i> 分配</button>		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
 	<table id="contentTable" class="table-bordered table-condensed dataTables-example dataTable no-footer">
	<tbody>
		<c:forEach items="${page.list}" var="aStory">
			<div class="col-sm-6">
                <div class="contact-box">
                        <div class="col-sm-4">
                            <div class="text-center">
                            <c:if test="${not empty aStory.picture}">
                                <img alt="image" class="img-circle" style="height:60px;width:60px;" src="${aStory.picture}">
                            </c:if>
                            <c:if test="${empty aStory.picture}">
                                <img alt="image" class="img-circle" style="height:60px;width:60px;" 
                                src="${ctxStatic}/common/login/images/icon_logo.png">
                            </c:if>
                            </div>
                        </div>
                        <div class="col-sm-8">
                            <h4>标题：${aStory.title}</h4>                          
                        </div>
                        <div class="col-sm-1 pull-right">
                            <input type="checkbox" id="${aStory.storyId}" name="box">
                       	  </div>
                        <div class="clearfix"></div>
                </div>
            </div>
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