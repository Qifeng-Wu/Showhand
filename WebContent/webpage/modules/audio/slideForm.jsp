<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>幻灯片管理</title>
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
					sort: {digits:true}
					},
				messages: {
					sort: {digits: "请输入正整数"}
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
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="aSlide" action="${ctx}/audio/slide/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>名称：</label></td>
					<td class="width-85">
						<form:input path="title" htmlEscape="false" maxlength="30" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>图片：</label></td>
					<td class="width-85">
						<form:input id="slideImage" path="picture" htmlEscape="false" maxlength="500" class="form-control required" readOnly="true"/>
						<sys:ckfinder input="slideImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="220" maxHeight="220"/>
					</td>
				</tr>
				<tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">* </font>排序：</label></td>
		         <td class="width-85">
						<form:input path="sort" htmlEscape="false" maxlength="5" class="form-control required"/>
				</td>		         
		      </tr>		        		     
			  <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">* </font>描述：</label></td>
		         <td class="width-85"><form:textarea path="description" style="height:140px" htmlEscape="false" maxlength="7000" class="form-control required"/></td> 		         
		      </tr>		        		     
		 	</tbody>
		</table>
	</form:form>
</body>
</html>