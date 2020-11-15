<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
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
		<form:form id="inputForm" modelAttribute="groceryOrder" action="${ctx}/grocery/order/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>用户头像：</label></td>
		         <td><img src="${groceryOrder.fans.avatar}" width="80%" height='112%'/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>用户昵称：</label></td>
		         <td><form:input path="fans.nickname" htmlEscape="false" maxlength="30" class="form-control required"/></td>		 	        		        
		      </tr>				        		     						        		  		     
			  <tr>
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>用户姓名：</label></td>
		         <td><form:input path="fansAddress.name" htmlEscape="false" maxlength="30" class="form-control required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">* </font>手机号码：</label></td>
		         <td><form:input path="fansAddress.phone" htmlEscape="false" maxlength="100"  class="form-control required"/></td>	        		         
		      </tr>
		      <tr>
		      	<td class="active"><label class="pull-right"><font color="red">* </font>订单编号：</label></td>
		         <td><form:input path="orderNo" htmlEscape="false" maxlength="100" class="form-control required"/></td>
			 	  <td class="active"><label class="pull-right"><font color="red">* </font>支付金额：</label></td>
		         <td><form:input path="payMoney" htmlEscape="false" maxlength="100" class="form-control required"/></td>        
		      </tr>					        		     						        		  		     	      				        		     						        		  		     
		      <tr>		      	     
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>订单状态：</label></td>
			 	 <td><input value="<c:if test='${groceryOrder.status eq "1"}'>待发货（买家已支付）</c:if><c:if test='${groceryOrder.status eq "2"}'>待买家确认收货</c:if><c:if test='${groceryOrder.status eq "3"}'>买家已确认收货</c:if>" maxlength="100" class="form-control required"/></td>	
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>购买时间：</label></td>
		      	 <td><input id="payTime" name="payTime" type="text" maxlength="50" readOnly class="form-control"
							value="<fmt:formatDate value="${groceryOrder.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>        
		      </tr>					        		     						        		  		     
		      <tr>
		      	<td class="active"><label class="pull-right"><font color="red">* </font>收货地址：</label></td>
		         <td><input value="${groceryOrder.fansAddress.province}${groceryOrder.fansAddress.city}${groceryOrder.fansAddress.area}${groceryOrder.fansAddress.address}" maxlength="100" class="form-control required"/></td>
			 	 <td class="active"><label class="pull-right"><font color="red">* </font>留言备注：</label></td>
		         <td><form:input path="remark" htmlEscape="false" maxlength="100" class="form-control required"/></td>        
		      </tr>					        		     						        		  		     
		      <tr>
		      	 <td class="active"><label class="pull-right"><font color="red">* </font>发货时间：</label></td>
		      	 <td><input id="sendTime" name="sendTime" type="text" maxlength="50" readOnly class="form-control"
							value="<fmt:formatDate value="${groceryOrder.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td> 
							 <td class="active"><label class="pull-right"><font color="red">* </font>收货时间：</label></td>
		      	 <td><input id="receiveTime" name="receiveTime" type="text" maxlength="50" readOnly class="form-control"
							value="<fmt:formatDate value="${groceryOrder.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>        
		      </tr>					        		     						        		  		     
		 	</tbody>
		</table>
	</form:form>
</body>
</html>