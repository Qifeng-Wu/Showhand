<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>意见反馈管理</title>
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
			
			//动态解析图片集
			var pictures = $("#picturesHidden").val();
			if(pictures){
				var picturesArray = pictures.split(",");
				if(picturesArray.length>0){
					for(var i=0;i<picturesArray.length;i++){
						var html = '<img src='+picturesArray[i]+' width="150px" height="150px" style="margin-right:100px"/>';
						$('#pictures').append(html);
					}
				}
			}
			
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
		<form:form id="inputForm" modelAttribute="groceryFeedback" action="${ctx}/grocery/feedback/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<input type="hidden" id="picturesHidden" value='${groceryFeedback.pictures}'/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>反馈类型：</label></td>
					<td class="width-85">
						<form:input path="type" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>反馈内容：</label></td>
					<td class="width-85">
						<form:textarea path="description" htmlEscape="false" rows="6" maxlength="300" class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>图片集：</label></td>
					<td id='pictures'>
				 	</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>