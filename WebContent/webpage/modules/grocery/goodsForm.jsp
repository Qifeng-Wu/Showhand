<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
					sort: {digits:true},
					sold: {digits:true}
					},
				messages: {
					sort: {digits: "请输入正整数"},
					sold: {digits: "请输入正整数"}
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
		<form:form id="inputForm" modelAttribute="groceryGoods" action="${ctx}/grocery/goods/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">* </font>名称：</label></td>
		         <td><form:input path="name" htmlEscape="false" maxlength="38" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>分类：</label></td>
		         <td><form:select path="classificationId"  class="form-control required" name="classificationId">
					 <form:option value="" label="">==请选择商品分类==</form:option>
					 <form:options items="${classificationList}" itemLabel="name" itemValue="classificationId" htmlEscape="false"/>
					 </form:select>
				 </td>
		      </tr>
		      <tr>
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>现价：</label></td>
		         <td><form:input path="cprice" htmlEscape="false" maxlength="7" class="form-control required priceValid"/></td>	        
		         <td class="active"><label class="pull-right"><font color="red">* </font>原价：</label></td>
		         <td><form:input path="oprice" htmlEscape="false" maxlength="7" class="form-control required priceValid"/></td>
		      </tr>
		      <tr>
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>图片：</label></td>
		         <td><form:input id="image" path="picture" htmlEscape="false" maxlength="500" class="form-control required" readOnly="true"/>
					 <sys:ckfinder input="image" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="200" maxHeight="200"/>
				 </td>
				 <td class="active"><label class="pull-right"><font color="red">* </font>图片集：</label></td>
		         <td><form:input id="photo" path="photos" htmlEscape="false" maxlength="5000" class="form-control required" readOnly="true"/>
					 <sys:ckfinder input="photo" type="images" uploadPath="/photo" selectMultiple="true" maxWidth="200" maxHeight="200"/>
				 </td>		        		         
		      </tr>
			  <tr> 	        
		         <td class="active"><label class="pull-right"><font color="red">* </font>状态：</label></td>
		         <td><div class="i-checks">
						<input type="radio" class="check" ${groceryGoods.status == true?"checked":""} name="status" data-radio="iradio_square-green" value="1">
                        <label for="status1">上架</label>
                        <input type="radio" class="check" ${groceryGoods.status == true?"":"checked"} name="status" data-radio="iradio_square-green" value="0">
                        <label for="status2">下架</label>
					</div>
				 </td>
				 <td class="active"><label class="pull-right"><font color="red">* </font>推荐：</label></td>
		         <td><div class="i-checks">
						<input type="radio" class="check" ${groceryGoods.isRecommend == true?"checked":""} name="isRecommend" data-radio="iradio_square-green" value="1">
                        <label for="isRecommend1">是</label>
                        <input type="radio" class="check" ${groceryGoods.isRecommend == true?"":"checked"} name="isRecommend" data-radio="iradio_square-green" value="0">
                        <label for="isRecommend2">否</label>
					</div>
				 </td>	
		      </tr>					        		     						        		  		     
			  <tr>
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>已售：</label></td>
		         <td><form:input path="sold" htmlEscape="false" maxlength="5" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>标签：</label></td>
		         <td><form:input path="lable" placeholder="热卖、推荐、新品等" htmlEscape="false" maxlength="2"  class="form-control required"/></td>	        		         
		      </tr>
		      <tr>
		      	<td class="active"><label class="pull-right"><font color="red">* </font>排序：</label></td>
		         <td><form:input path="sort" htmlEscape="false" maxlength="5" class="form-control required"/></td>
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>描述：</label></td>
		         <td><form:textarea path="description" htmlEscape="false" maxlength="1000" class="form-control required"/></td>        
		      </tr>					        		     						        		  		     
		 	</tbody>
		</table>
	</form:form>
</body>
</html>