<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客服区域管理</title>
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
			
		});
		//验证电话号码
		jQuery.validator.addMethod("phoneValid", function(value, element) {					
			   var mobile =  /^((0\d{2,3}-\d{7,8})|(1[3567984]\d{9})|(^400-?[0-9]{3}-?[0-9]{4})|(^800-?[0-9]{3}-?[0-9]{4}))$/;	   		   
			   if((mobile.exec(value))?true:false){
				   return true;
			 	}else{
			  		 return false;
		   		} 	   		   
		},"联系方式格式不正确"); 
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="qisRegionHead" action="${ctx}/qis/qisRegionHead/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>负责人：</label></td>
					<td class="width-85">
						<form:input path="name" htmlEscape="false" minlength="2" maxlength="10" class="form-control required"/>
					</td>
				</tr>
		   		<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>手机号：</label></td>
					<td class="width-85">
						<form:input path="phone" htmlEscape="false" maxlength="15" class="form-control required phoneValid"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>邮箱：</label></td>
					<td class="width-85">
						<form:input path="email" htmlEscape="false" maxlength="60" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>负责区域：</label></td>
					<td class="width-85">
						<form:input path="region" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
				</tr>				
		 	</tbody>
		</table>
	</form:form>
</body>
</html>