<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>回访问卷管理</title>
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
		<form:form id="inputForm" modelAttribute="aQuestionnaire" action="${ctx}/audio/questionnaire/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
		         <td class="active"><label class="pull-right"><font color="red">* </font>小区：</label></td>
		         <td><form:input path="community" htmlEscape="false" maxlength="15" class="form-control required"/></td>
		        </tr>
		   		<tr>
		         <td class="active"><label class="pull-right"><font color="red">* </font>房号：</label></td>
		         <td><form:input path="room" htmlEscape="false" maxlength="15" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>姓名：</label></td>
		         <td><form:input path="name" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		        </tr>
		        <tr>
		         <td class="active"><label class="pull-right"><font color="red">* </font>验房者编号：</label></td>
		         <td><form:input path="inspector" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>总体满意度：</label></td>
		         <td><form:input path="question1" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		        </tr>
		       	<tr>
		         <td class="active"><label class="pull-right"><font color="red">* </font>房屋质量满意度：</label></td>
		         <td><form:input path="question2" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>房屋设计整体满意度：</label></td>
		         <td><form:input path="question3" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		        </tr>
		        <tr>
		         <td class="active"><label class="pull-right"><font color="red">* </font>公共区域装饰及施工质量的满意度：</label></td>
		         <td><form:input path="question4" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>园区绿化及设计的满意度：</label></td>
		         <td><form:input path="question5" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		        </tr>
		        <tr>
		         <td class="active"><label class="pull-right"><font color="red">* </font>收楼的整体安排和服务的满意度：</label></td>
		         <td><form:input path="question6" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>意见或建议：</label></td>
		         <td><form:input path="remark" htmlEscape="false" maxlength="10" class="form-control required"/></td>
		        </tr>	     
		 	</tbody>
		</table>
	</form:form>
</body>
</html>