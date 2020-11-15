<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>物流运费管理</title>
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
		 		province: {required:true},
		 		freight: {digits:true}
				},
			messages: {
				province: {required: "请选择省份"},
				freight: {digits: "请输入正整数"}
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
		<form:form id="inputForm" modelAttribute="groceryFreight" action="${ctx}/grocery/freight/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>省份：</label></td>
					<td class="width-85">
						<select class="form-control required" name="province">
							<option value="">==省份==</option>
							<option value="北京" <c:if test='${groceryFreight.province == "北京"}'>selected='selected'</c:if>>北京</option>
							<option value="上海" <c:if test='${groceryFreight.province == "上海"}'>selected='selected'</c:if>>上海</option>
							<option value="天津" <c:if test='${groceryFreight.province == "天津"}'>selected='selected'</c:if>>天津</option>
							<option value="重庆" <c:if test='${groceryFreight.province == "重庆"}'>selected='selected'</c:if>>重庆</option>
							<option value="江苏" <c:if test='${groceryFreight.province == "江苏"}'>selected='selected'</c:if>>江苏</option>
							<option value="浙江" <c:if test='${groceryFreight.province == "浙江"}'>selected='selected'</c:if>>浙江</option>
							<option value="安徽" <c:if test='${groceryFreight.province == "安徽"}'>selected='selected'</c:if>>安徽</option>
							<option value="福建" <c:if test='${groceryFreight.province == "福建"}'>selected='selected'</c:if>>福建</option>
							<option value="江西" <c:if test='${groceryFreight.province == "江西"}'>selected='selected'</c:if>>江西</option>
							<option value="山东" <c:if test='${groceryFreight.province == "山东"}'>selected='selected'</c:if>>山东</option>
							<option value="河南" <c:if test='${groceryFreight.province == "河南"}'>selected='selected'</c:if>>河南</option>
							<option value="河北" <c:if test='${groceryFreight.province == "河北"}'>selected='selected'</c:if>>河北</option>
							<option value="湖南" <c:if test='${groceryFreight.province == "湖南"}'>selected='selected'</c:if>>湖南</option>
							<option value="湖北" <c:if test='${groceryFreight.province == "湖北"}'>selected='selected'</c:if>>湖北</option>
							<option value="广东" <c:if test='${groceryFreight.province == "广东"}'>selected='selected'</c:if>>广东</option>
							<option value="广西" <c:if test='${groceryFreight.province == "广西"}'>selected='selected'</c:if>>广西</option>
							<option value="海南" <c:if test='${groceryFreight.province == "海南"}'>selected='selected'</c:if>>海南</option>
							<option value="辽宁" <c:if test='${groceryFreight.province == "辽宁"}'>selected='selected'</c:if>>辽宁</option>
							<option value="吉林" <c:if test='${groceryFreight.province == "吉林"}'>selected='selected'</c:if>>吉林</option>
							<option value="黑龙江" <c:if test='${groceryFreight.province == "黑龙江"}'>selected='selected'</c:if>>黑龙江</option>
							<option value="四川" <c:if test='${groceryFreight.province == "四川"}'>selected='selected'</c:if>>四川</option>
							<option value="贵州" <c:if test='${groceryFreight.province == "贵州"}'>selected='selected'</c:if>>贵州</option>
							<option value="山西" <c:if test='${groceryFreight.province == "山西"}'>selected='selected'</c:if>>云南</option>
							<option value="山西" <c:if test='${groceryFreight.province == "山西"}'>selected='selected'</c:if>>山西</option>
							<option value="陕西" <c:if test='${groceryFreight.province == "陕西"}'>selected='selected'</c:if>>陕西</option>
							<option value="甘肃" <c:if test='${groceryFreight.province == "甘肃"}'>selected='selected'</c:if>>甘肃</option>
							<option value="宁夏" <c:if test='${groceryFreight.province == "宁夏"}'>selected='selected'</c:if>>宁夏</option>
							<option value="青海" <c:if test='${groceryFreight.province == "青海"}'>selected='selected'</c:if>>青海</option>							
							<option value="新疆" <c:if test='${groceryFreight.province == "新疆"}'>selected='selected'</c:if>>新疆</option>
							<option value="西藏" <c:if test='${groceryFreight.province == "西藏"}'>selected='selected'</c:if>>西藏</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>运费：</label></td>
					<td class="width-85">
						<form:input path="freight" htmlEscape="false" maxlength="3" class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>