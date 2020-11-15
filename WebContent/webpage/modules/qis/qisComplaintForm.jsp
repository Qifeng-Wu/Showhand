<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客诉流程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  //获取故障明细信息
			  var length = $("#faultTbody tr").length; 
			  var faultArray = new Array();
				var faults = null;
				for(var i=0;i<length;i++){
					var sn = $("#faultTbody tr:eq("+i+") input").eq(0).val();
					var fault = $("#faultTbody tr:eq("+i+") input").eq(1).val();
					if(sn==""||fault==""){
						continue;
					}
					faultArray[i] = [sn,fault];
				}
				var faults = {"faults":faultArray};
				var faultsJSONString = JSON.stringify(faults);
				$("#faults").val(faultsJSONString);
			  layer.confirm('是否确认提交信息？', {closeBtn: 0, icon : 3 ,skin : 'layui-layer-molv',btn: ['确认','取消'] //按钮
				}, function(){
					  $("#inputForm").submit();
				});
			  return true;
		  }	
		  return false;
		}
		$(document).ready(function() {
			//若是新增则给个默认故障明细输入框
			if($("#faultTbody tr").length<=0){
				var html = '<tr>'+
								'<td class="width-15 active"><label class="pull-right"><font color="red">* </font>序列号：</label></td>'+
								'<td class="width-35">'+
									'<input maxlength="30" class="form-control required"/>'+
								'</td>'+
								'<td class="width-15 active"><label class="pull-right"><font color="red">* </font>故障现象：</label></td>'+
								'<td class="width-35">'+
									'<input maxlength="100" class="form-control required"/>'+
								'</td>'+
								'<td class="width-35">'+
									'<a onclick="deleteFault(this,null)" class="btn btn-white btn-sm" title="删除故障明细"><i class="fa fa-times-circle"></i> 删除</a>'+
								'</td>'+
							'</tr>';		
				$("#faultTbody").append(html);
			}
			
			validateForm = $("#inputForm").validate({
			 	rules: {
			 		orderQuantity: {digits:true},
			 		failQuantity: {digits:true}
					},
				messages: {
					orderQuantity: {digits: "请输入正整数"},
					failQuantity: {digits: "请输入正整数"}
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
			
			laydate({
	            elem: '#problemTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
	            isclear : false,
				istime : true,
	        	format:'YYYY-MM-DD hh:mm'
			});
			laydate({
	            elem: '#useTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
	            isclear : false,
				istime : true,
	            format:'YYYY-MM-DD hh:mm'
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
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox">
	<div class="ibox-title">
		<h5>发起客诉流程 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content" style="padding:0 6% 0 6%">
	<sys:message content="${message}"/>	
	<!-- 工具栏 和 查询条件-->
	<div class="row">
	<div class="col-sm-12">
		<form:form id="inputForm" modelAttribute="qisComplaint" action="${ctx}/qis/qisComplaint/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<h5 style="margin-top:25px">基本信息 </h5>
		<table class="table table-bordered table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>事件主题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>产品类型：</label></td>
					<td class="width-35">
						<form:input path="productType" htmlEscape="false" maxlength="30" class="form-control required"/>
					</td>
				</tr>								
		 	</tbody>
		</table>
		<h5 style="margin-top:25px">客户信息 </h5>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>客户名称：</label></td>
					<td class="width-35">
						<form:input path="customerName" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>客户地址：</label></td>
					<td class="width-35">
						<form:input path="customerAddress" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>客户联系人：</label></td>
					<td class="width-35">
						<form:input path="customerContact" htmlEscape="false" maxlength="10" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>客户联系方式：</label></td>
					<td class="width-35">
						<form:input path="customerPhone" htmlEscape="false" maxlength="20" class="form-control required phoneValid"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<h5 style="margin-top:25px">前端信息 </h5>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>区域经理姓名：</label></td>
					<td class="width-35">
						<form:input path="regionalManagerName" htmlEscape="false" maxlength="10" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>区域经理联系方式：</label></td>
					<td class="width-35">
						<form:input path="regionalManagerPhone" htmlEscape="false" maxlength="20" class="form-control required phoneValid"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>现场工程师姓名：</label></td>
					<td class="width-35">
						<form:input path="fieldEngineerName" htmlEscape="false" maxlength="10" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>现场工程师联系方式：</label></td>
					<td class="width-35">
						<form:input path="fieldEngineerPhone" htmlEscape="false" maxlength="20" class="form-control required phoneValid"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<h5 style="margin-top:25px">产品使用环境 </h5>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>			
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>使用地点：</label></td>
					<td class="width-35">
						<form:input path="usePlace" htmlEscape="false" maxlength="30" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>所接外设：</label></td>
					<td class="width-35">
						<form:input path="connectedPeripheral" htmlEscape="false" maxlength="30" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>自装软件：</label></td>
					<td class="width-35">
						<form:input path="selfInstalledSoftware" htmlEscape="false" maxlength="30" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>BIOS版本：</label></td>
					<td class="width-35">
						<form:input path="biosVersion" htmlEscape="false" maxlength="20" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>易教版本：</label></td>
					<td class="width-35">
						<form:input path="easyVersion" htmlEscape="false" maxlength="20" class="form-control required"/>
					</td>
				</tr>			
		 	</tbody>
		</table>
		<h5 style="margin-top:25px">问题信息 </h5>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>							
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>问题发生时间：</label></td>
					<td class="width-35">
						<input id="problemTime" name="problemTime" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
								value="<fmt:formatDate value="${qisComplaint.problemTime}" pattern="yyyy-MM-dd hh:mm:ss"/>"/>						
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>客户使用时间：</label></td>
					<td class="width-35">
						<input id="useTime" name="useTime" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
								value="<fmt:formatDate value="${qisComplaint.useTime}" pattern="yyyy-MM-dd hh:mm:ss"/>"/>	
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>涉及订单数量：</label></td>
					<td class="width-35">
						<form:input path="orderQuantity" htmlEscape="false" maxlength="7" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>不良数量：</label></td>
					<td class="width-35">
						<form:input path="failQuantity" htmlEscape="false" maxlength="7" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>不良率：</label></td>
					<td class="width-35">
						<form:input path="failRate" htmlEscape="false" maxlength="7" class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<h5 style="margin-top:25px">故障明细 </h5>
		<input type="hidden" name="faults" id="faults">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody id="faultTbody">
		   	<c:forEach items="${complaintFaultList}" var="qisComplaintFault">							
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>序列号：</label></td>
					<td class="width-35">
						<input value='${qisComplaintFault.sn}' maxlength="30" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>故障现象：</label></td>
					<td class="width-35">
						<input value='${qisComplaintFault.fault}' maxlength="100" class="form-control required"/>
					</td>
					<td class="width-35">
						<a onclick="deleteFault(this,'${qisComplaintFault.id}')" class="btn btn-white btn-sm" title="删除故障明细"><i class="fa fa-times-circle"></i> 删除</a>
					</td>
				</tr>
			</c:forEach> 			
		 	</tbody>		 	
		</table>
		<a onclick="addFault()" class="btn btn-white btn-sm" data-placement="left" title="添加故障明细"><i class="fa fa-plus"></i> 添加</a>
		<h5 style="margin-top:25px">问题描述与分析 </h5>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>							
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>事件描述及客户诉求：</label></td>
					<td class="width-35">
						<form:textarea path="problemDescription" htmlEscape="false" maxlength="2000" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>现场故障复判和分析：</label></td>
					<td class="width-35">
						<form:textarea path="fieldAssay" htmlEscape="false" maxlength="2000" class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
	</div>
	</div>
	<div style="margin:20px 30% 0px 30%">
		<a class="btn btn-primary" style="width:120%" onclick="doSubmit()">提交</a>
	</div>
	<br/>
	<br/>
	<br/>
	</div>
	</div>
 </div> 
 <script type="text/javascript">
 //添加故障明细输入框
 function addFault(){
	 var html = '<tr>'+
					'<td class="width-15 active"><label class="pull-right"><font color="red">* </font>序列号：</label></td>'+
					'<td class="width-35">'+
						'<input maxlength="30" class="form-control required"/>'+
					'</td>'+
					'<td class="width-15 active"><label class="pull-right"><font color="red">* </font>故障现象：</label></td>'+
					'<td class="width-35">'+
						'<input maxlength="100" class="form-control required"/>'+
					'</td>'+
					'<td class="width-35">'+
						'<a onclick="deleteFault(this,null)" class="btn btn-white btn-sm" title="删除故障明细"><i class="fa fa-times-circle"></i> 删除</a>'+
					'</td>'+
				'</tr>';
	if ($('#faultTbody tr').length>= 30) {
		layer.msg('最多只能添加30条故障明细！');
		return false;
	}			
	$("#faultTbody").append(html);			
				
 }
 //删除故障明细输入框
 function deleteFault(thisFlag,faultId){
	 if(faultId && faultId!=null && faultId!="" && faultId!="null"){
		 layer.alert('确认要彻底删除数据吗?', {
				skin: 'layui-layer-danger',btn: ['确认','取消'],closeBtn:0,icon:3
				}, function(){
					$.ajax({
						url : "${ctx}/qis/qisComplaint/fault/delete",
						type : "POST",
						dataType : "json",
						data : {id:faultId},
						success : function(result) {
							if (result.success == true) {
								$(thisFlag).parent().parent().remove(); 
								layer.msg('删除成功！');
							}
						},
						error : function(xhr, type, errorThrown) {
							layer.msg('系统出错啦！');
						}
					});
				});
	 }else{
		$(thisFlag).parent().parent().remove(); 
	 }	
 }
 </script>
</body>
</html>