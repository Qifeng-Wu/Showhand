<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>服务机构管理</title>
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
				
				rules: {
			 		code: {digits:true}
					},
				messages: {
					code: {digits: "请输入6位数字"}
				}, 
				
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
		<form:form id="inputForm" modelAttribute="qisServiceStation" action="${ctx}/qis/qisServiceStation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>机构编号：</label></td>
					<td class="width-35">
						<form:input path="code" htmlEscape="false" minlength="6" maxlength="6" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>所属区域：</label></td>
					<td class="width-35">
						<form:select path="regionId"  class="form-control required" name="regionId">
						<form:option value="" label="">==请选择所属区域==</form:option>
						<form:options items="${regionList}" itemLabel="region" itemValue="id" htmlEscape="false"/>
						</form:select>
					</td>
				
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>机构全称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="70" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>机构简称：</label></td>
					<td class="width-35">
						<form:input path="shortname" htmlEscape="false" maxlength="20" class="form-control required"/>
					</td>				
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>客服经理：</label></td>
					<td class="width-35">
						<form:input path="manager" htmlEscape="false" maxlength="10" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>手机号：</label></td>
					<td class="width-35">
						<form:input path="phone" htmlEscape="false" maxlength="15" class="form-control required phoneValid"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>服务热线：</label></td>
					<td class="width-35">
						<form:input path="telephone" htmlEscape="false" maxlength="15" class="form-control required phoneValid"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>所属省份：</label></td>
					<td class="width-35">
						<form:input path="province" htmlEscape="false" maxlength="10" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>所在市：</label></td>
					<td class="width-35">
						<form:input path="city" htmlEscape="false" maxlength="10" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>所在区县：</label></td>
					<td class="width-35">
						<form:input path="area" htmlEscape="false" maxlength="10" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>营业地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>