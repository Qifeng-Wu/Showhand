<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类管理</title>
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
		<form:form id="inputForm" modelAttribute="groceryGoodsClassification" action="${ctx}/grocery/goodsClassification/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>分类名称：</label></td>
					<td class="width-85">
						<form:input path="name" htmlEscape="false" maxlength="30" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>分类排序：</label></td>
					<td class="width-85">
						<form:input path="sort" htmlEscape="false" maxlength="5" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>分类状态：</label></td>
					<td class="width-85">
						<div class="i-checks">
						<input type="radio" class="check" ${groceryGoodsClassification.status == false?"":"checked"} name="status" data-radio="iradio_square-green" value="1">
                        <label for="status1">是</label>
                        <input type="radio" class="check" ${groceryGoodsClassification.status == false?"checked":""} name="status" data-radio="iradio_square-green" value="0">
                        <label for="status2">否</label>
					</div>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>