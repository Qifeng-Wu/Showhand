<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>章回管理</title>
<meta name="decorator" content="default" />
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
			
			//免费时 价格默认为0
			if($("#isFree1").attr("checked")){
				$("#price").val('0')
			}		
			$("#isFreeDiv .iCheck-helper:eq(0)").click(function(){
				$("#price").val('0')
			});
		});
		
		//验证价格格式
		jQuery.validator.addMethod("priceValid", function(value, element) {					
			   var reg = /^((?:0)|(?:[1-9]\d*))(?:\.\d{1,2})?$/; 	   		   
			   if((reg.exec(value))?true:false){
				   return true;
			   }else if(value==""){
					 return true;
				}else{
				   return false;
			   } 	   		   
		},"请输入正确格式");
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="aChapter"
		action="${ctx}/audio/chapter/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<table
			class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>			
				<tr>
					<td class="width-15 active"><label class="pull-right"><font
							color="red">* </font>标题：</label></td>
					<td class="width-85">
						<form:select path="storyId" class="form-control required" name="storyId">
					 	<form:option value="" label="">==请选择所属故事==</form:option>
					 	<form:options items="${storyList}" itemLabel="title" itemValue="storyId" htmlEscape="false"/>
					 	</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font
							color="red">* </font>标题：</label></td>
					<td class="width-85"><form:input path="title"
							htmlEscape="false" maxlength="30" class="form-control required" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font
							color="red">* </font>价格：</label></td>
					<td class="width-85"><form:input path="price"
							htmlEscape="false" maxlength="7" id="price"
							class="form-control required priceValid" /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font
							color="red">* </font>免费：</label></td>
					<td class="width-85">
						<div class="i-checks" id="isFreeDiv">
							<input type="radio" class="check" id="isFree1"
								${aChapter.isFree == true?"checked":""} name="isFree"
								data-radio="iradio_square-green" value="1"> <label
								for="isFree1">免费</label> <input type="radio" class="check"
								${aChapter.isFree == true?"":"checked"} name="isFree"
								data-radio="iradio_square-green" value="0"> <label
								for="isFree2">付费</label>
						</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font
							color="red">* </font>状态：</label></td>
					<td class="width-85">
						<div class="i-checks">
							<input type="radio" class="check"
								${aChapter.status == true?"checked":""} name="status"
								data-radio="iradio_square-green" value="1"> <label
								for="status1">上架</label> <input type="radio" class="check"
								${aChapter.status == true?"":"checked"} name="status"
								data-radio="iradio_square-green" value="0"> <label
								for="status2">下架</label>
						</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font
							color="red">* </font>排序：</label></td>
					<td class="width-85"><form:input path="sort"
							htmlEscape="false" maxlength="5" class="form-control required" />
					</td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>