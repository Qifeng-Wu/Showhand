<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>触屏动作管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  //设置name值
			  var name = $("#action option:selected").text();
			  $("#name").val(name)
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
			
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="aTouch" action="${ctx}/audio/touch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>名称：</label></td>
					<td class="width-85">
						<input type="hidden" name="name" id="name">
						<form:select path="action" name="action" class="form-control required" id="action">
							 <form:option value="" label="">==请选择动作==</form:option>
							 <form:option value="1" label="上滑"> 上滑 </form:option>
							 <form:option value="2" label="下滑"> 下滑 </form:option>
							 <form:option value="3" label="左滑"> 左滑 </form:option>
							 <form:option value="4" label="右滑"> 右滑 </form:option>
							 <form:option value="5" label="双击"> 双击 </form:option>
							 <form:option value="6" label="摇晃"> 摇晃 </form:option>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>音频：</label></td>
					<td class="width-85">
						<form:input id="audio" path="audio" htmlEscape="false" maxlength="600" class="form-control required" readOnly="true"/>
					<sys:ckfinder input="audio" type="files" uploadPath="/touch" selectMultiple="false" maxWidth="220" maxHeight="220"/>
					</td>
				</tr>	        		     
		 	</tbody>
		</table>
	</form:form>
</body>
</html>