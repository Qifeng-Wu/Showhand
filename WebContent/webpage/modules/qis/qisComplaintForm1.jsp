<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客诉信息管理</title>
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
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="tfComplaint" action="${ctx}/tf/tfComplaint/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件编号：</label></td>
					<td class="width-35">
						<form:input path="serialnumber" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">提交人（流程发起者）：</label></td>
					<td class="width-35">
						<form:input path="submitter" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">提交时间：</label></td>
					<td class="width-35">
						<form:input path="submittime" htmlEscape="false" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">事件主题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">产品类型：</label></td>
					<td class="width-35">
						<form:input path="producttype" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">客户名称：</label></td>
					<td class="width-35">
						<form:input path="customername" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">客户地址：</label></td>
					<td class="width-35">
						<form:input path="customeraddress" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">客户联系人：</label></td>
					<td class="width-35">
						<form:input path="customercontact" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">客户联系方式：</label></td>
					<td class="width-35">
						<form:input path="customerphone" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">区域经理姓名：</label></td>
					<td class="width-35">
						<form:input path="regionalmanagername" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">区域经理联系方式：</label></td>
					<td class="width-35">
						<form:input path="regionalmanagerphone" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">现场工程师姓名：</label></td>
					<td class="width-35">
						<form:input path="fieldengineername" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">现场工程师联系方式：</label></td>
					<td class="width-35">
						<form:input path="fieldengineerphone" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">使用地点：</label></td>
					<td class="width-35">
						<form:input path="useplace" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">所接外设：</label></td>
					<td class="width-35">
						<form:input path="connectedperipheral" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">自装软件：</label></td>
					<td class="width-35">
						<form:input path="selfinstalledsoftware" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">BIOS版本：</label></td>
					<td class="width-35">
						<form:input path="biosversion" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">易教版本：</label></td>
					<td class="width-35">
						<form:input path="easyversion" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">问题发生时间：</label></td>
					<td class="width-35">
						<form:input path="problemtime" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">客户使用时间：</label></td>
					<td class="width-35">
						<form:input path="usetime" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">涉及订单数量：</label></td>
					<td class="width-35">
						<form:input path="orderquantity" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">不良数量：</label></td>
					<td class="width-35">
						<form:input path="failquantity" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">不良率：</label></td>
					<td class="width-35">
						<form:input path="failrate" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">事件描述及客户诉求：</label></td>
					<td class="width-35">
						<form:input path="problemdescription" htmlEscape="false" maxlength="3000" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">现场故障复判和分析：</label></td>
					<td class="width-35">
						<form:input path="fieldassay" htmlEscape="false" maxlength="3000" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">客服技术部分析及处理方案：</label></td>
					<td class="width-35">
						<form:input path="technologyassay" htmlEscape="false" maxlength="3000" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">问题性质：</label></td>
					<td class="width-35">
						<form:input path="problemtype" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">客服技术部处理人：</label></td>
					<td class="width-35">
						<form:input path="technologyman" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>