<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>活动时间管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {			
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			laydate({
	            elem: '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
	            isclear : false,
				istime : true,
	        	format:'YYYY-MM-DD hh:mm'
			});
			laydate({
	            elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
	            isclear : false,
				istime : true,
	            format:'YYYY-MM-DD hh:mm'
	        });
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="activityTime" action="${ctx}/abc/lantern/activityTimeSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="timeId" value='${activityTime.timeId}'/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
		         <td class="active"><label class="pull-right"><font color="red">* </font>开始时间：</label></td>
		         <td><input id="startTime" name="startTime" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
					value="<fmt:formatDate value="${activityTime.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>结束时间：</label></td>
		         <td><input id="endTime" name="endTime" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
					value="<fmt:formatDate value="${activityTime.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
		        </tr>   
		 	</tbody>
		</table>
	</form:form>
</body>
</html>