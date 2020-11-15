<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户问题信息</title>
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
		
		//根据主键memberId获取企业成员信息
		function getInfo(memberId){
			var userId = null;
			var name = null;
			var department = null;
			var position = null;
			var isleader = null;
			if(memberId==null || memberId=="" || memberId=="null"){
				return [userId, name, department, position, isleader];
			}
			$.ajax('/QIS/qis-api/member/info', {
				data : {
					memberId : memberId			
				},
				dataType : 'json',//服务器返回json格式数据
				type : 'post',//HTTP请求类型
				async : false,
				success : function(result) {
					if(result.success == true){
						userId = result.body.member.userId;
						name = result.body.member.name;
						department = result.body.member.department;
						position = result.body.member.position;
						isleader = result.body.member.isleader;
					}else{
						layer.alert(result.message, {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
					}				
				},
				error : function(xhr, type, errorThrown) {
					layer.alert(result.message, {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				}
			});
			return [userId, name, department, position, isleader];
		}
				
		
		/*================客户问题状态判断================*/
		var state = $("#state").val();
		if(state=="1"){//初始插入	，待区域经理分配服务站点
			
		}else if(state=="2"){//该问题已取消
			
		}else if(state=="3"){//待服务站点预约上门
			$("#part2").show();
			$("#service_station_info_div").show();
		}else if(state=="4"){//服务站点已预约，待上门服务
			$("#part2").show();
			$("#service_station_info_div").show();
			$("#service_station_appointment_info_div").show();
		}else if(state=="5"){//待客服区域经理处理
			$("#part2").show();
			$("#service_station_info_div").show();
			$("#service_station_appointment_info_div").show();
			$("#service_station_handle_info_div").show();
		}else if(state=="6"){//待客服技术处理
			$("#part2").show();
			$("#service_station_info_div").show();
			$("#service_station_appointment_info_div").show();
			$("#service_station_handle_info_div").show();
			$("#part3").show();
			$("#preliminaryOpinionManName").val(getInfo($("#preliminaryOpinionManHidden").val())[1]);
		}else if(state=="7"){//客服技术驳回，待区域经理处理
			$("#part2").show();
			$("#service_station_info_div").show();
			$("#service_station_appointment_info_div").show();
			$("#service_station_handle_info_div").show();
			$("#part3").show();
			$("#preliminaryOpinionManName").val(getInfo($("#preliminaryOpinionManHidden").val())[1]);
			$("#technologyManName_1").val(getInfo($("#technologyManHidden").val())[1]);
			$("#service_technology_refused").show();
		}else if(state=="8"){//待质量中心处理
			$("#part2").show();
			$("#service_station_info_div").show();
			$("#service_station_appointment_info_div").show();
			$("#service_station_handle_info_div").show();
			$("#part3").show();
			$("#preliminaryOpinionManName").val(getInfo($("#preliminaryOpinionManHidden").val())[1]);
			$("#technologyManName_2").val(getInfo($("#technologyManHidden").val())[1]);
			$("#service_technology_handle").show();
		}else if(state=="9"){//质量中心驳回，待客服技术处理
			$("#part2").show();
			$("#service_station_info_div").show();
			$("#service_station_appointment_info_div").show();
			$("#service_station_handle_info_div").show();
			$("#part3").show();
			$("#preliminaryOpinionManName").val(getInfo($("#preliminaryOpinionManHidden").val())[1]);
			$("#technologyManName_2").val(getInfo($("#technologyManHidden").val())[1]);
			$("#qaHandlerName_1").val(getInfo($("#qaHandlerHidden").val())[1]);
			$("#service_technology_handle").show();
			$("#qa_refused").show();
		}else if(state=="10"){//待客户评价
			$("#part2").show();
			$("#service_station_info_div").show();
			$("#service_station_appointment_info_div").show();
			$("#service_station_handle_info_div").show();
			$("#part3").show();
			$("#preliminaryOpinionManName").val(getInfo($("#preliminaryOpinionManHidden").val())[1]);
			$("#technologyManName_2").val(getInfo($("#technologyManHidden").val())[1]);
			$("#qaHandlerName_2").val(getInfo($("#qaHandlerHidden").val())[1]);
			$("#service_technology_handle").show();
			$("#qa_handle").show();
		}else if(state=="0"){//完结
			$("#part2").show();
			$("#service_station_info_div").show();
			$("#service_station_appointment_info_div").show();
			$("#service_station_handle_info_div").show();
			$("#part3").show();
			$("#preliminaryOpinionManName").val(getInfo($("#preliminaryOpinionManHidden").val())[1]);
			$("#technologyManName_2").val(getInfo($("#technologyManHidden").val())[1]);
			$("#qaHandlerName_2").val(getInfo($("#qaHandlerHidden").val())[1]);
			$("#service_technology_handle").show();
			$("#qa_handle").show();
			$("#customer_evaluation").show();
		}
		
	});
		
	layui.use('layer', function(){ 
	    var $ = layui.jquery, layer = layui.layer;
	 	layer.photos({
		  photos: '#layer-photos-pictures'
	 	});  
	}); 
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight" id="layer-photos-pictures">
	<sys:message content="${message}"/>	
	<!-- 客户问题基本信息 -->
	<div class="ibox">
	<div class="ibox-title">
		<h5>Part1 > 客户问题基本信息 </h5>
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
	<!-- 工具栏 和 查询条件-->
	<div class="row">
	<div class="col-sm-12" id="layer-photos-pictures">
		<form:form id="inputForm" modelAttribute="qisCustomerComplaint" action="${ctx}/qis/qisCustomerComplaint/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" value="${qisCustomerComplaint.state}" id="state">
		<h5 style="margin-top:25px">基本信息 </h5>
		<table class="table table-bordered table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件编号：</label></td>
					<td class="width-35">
						<form:input path="serialNumber" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">创建时间：</label></td>
					<td class="width-35">
						<input name="createTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisCustomerComplaint.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>								
				<tr>
					<td class="width-15 active"><label class="pull-right">单位名称：</label></td>
					<td class="width-35">
						<form:input path="company" htmlEscape="false" class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">所属省份：</label></td>
					<td class="width-35">
						<form:input path="province" htmlEscape="false" maxlength="100" class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">联系人姓名：</label></td>
					<td class="width-35">
						<form:input path="contact" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">联系方式：</label></td>
					<td class="width-35">
						<form:input path="phone" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">故障现象：</label></td>
					<td class="width-35">
						<form:textarea path="failPhenomenon" htmlEscape="false" maxlength="1000" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">不良SN码：</label></td>
					<td class="width-35">
					 	<c:forEach items="${fn:split(qisCustomerComplaint.failSNs, ',')}" var="sn">
							<p>${sn}<p/>
						</c:forEach> 
					</td>
				</tr>							
				<tr>
					<td class="width-15 active"><label class="pull-right">故障图片：</label></td>
					<td class="width-35">
						<c:forEach items="${fn:split(qisCustomerComplaint.failPictures, ',')}" var="fp">
							<img src="${fp}" width="25%" height="100px" style="margin:0 2%"/>
						</c:forEach> 
					</td>
				</tr>							
		 	</tbody>
		</table>
	</form:form>
	</div>
	</div>
	<br/>
	<br/>
	<br/>
	</div>
	</div>
	
	<!-- 服务站点处理信息 -->
	<div class="ibox" style="display:none" id="part2">
	<div class="ibox-title">
		<h5>Part2 > 服务站点处理信息 </h5>
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
	<!-- 工具栏 和 查询条件-->
	<div class="row">
	<div class="col-sm-12" id="layer-photos-pictures">
		<form:form id="inputForm" modelAttribute="qisCustomerComplaint" action="${ctx}/qis/qisCustomerComplaint/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div style="display:none" id="service_station_info_div">
		<h5 style="margin-top:25px">分配服务站点信息 </h5>
		<table class="table table-bordered table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">站点名称：</label></td>
					<td class="width-35">
						<form:input path="serviceStation.name" htmlEscape="false" maxlength="100" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">站点编号：</label></td>
					<td class="width-35">
						<form:input path="serviceStation.code" htmlEscape="false" maxlength="30" class="form-control required"/>
					</td>
				</tr>								
				<tr>
					<td class="width-15 active"><label class="pull-right">客服经理：</label></td>
					<td class="width-35">
						<form:input path="serviceStation.manager" htmlEscape="false" maxlength="100" class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">联系方式：</label></td>
					<td class="width-35">
						<form:input path="serviceStation.phone" htmlEscape="false" maxlength="100" class="form-control"/>
					</td>
				</tr>							
		 	</tbody>
		</table>
		</div>
		<div style="display:none" id="service_station_appointment_info_div">
		<h5 style="margin-top:25px">服务站点预约信息 </h5>
		<table class="table table-bordered table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">预约人姓名：</label></td>
					<td class="width-35">
						<form:input path="appointmentManName" htmlEscape="false" maxlength="100" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">预约时间：</label></td>
					<td class="width-35">
						<input name="createTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisCustomerComplaint.appointmentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>														
		 	</tbody>
		</table>
		</div>
		<div style="display:none" id="service_station_handle_info_div">
		<h5 style="margin-top:25px">客服现场处理信息 </h5>
		<c:forEach items="${qisServiceComplaintList}" var="qisServiceComplaint">
		<table class="table table-bordered table-condensed dataTables-example dataTable no-footer">
		   <tbody>							
				<tr>
					<td class="width-15 active"><label class="pull-right">单位名称：</label></td>
					<td class="width-35">
						<input name="company" value="${qisServiceComplaint.company}" class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">单位地址：</label></td>
					<td class="width-35">
						<input name="address" value="${qisServiceComplaint.address}" class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">客户类型：</label></td>
					<td class="width-35">
						<input value="${qisServiceComplaint.customerType.name}" class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">行业类别：</label></td>
					<td class="width-35">
						<input value="${qisServiceComplaint.industry.name}" class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">工程师姓名：</label></td>
					<td class="width-35">
						<input value="${qisServiceComplaint.engineer}" class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">联系方式：</label></td>
					<td class="width-35">
						<input value="${qisServiceComplaint.phone}" class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">不良数量：</label></td>
					<td class="width-35">
						<input value="${qisServiceComplaint.failQuantity}" class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">采购数量：</label></td>
					<td class="width-35">
						<input value="${qisServiceComplaint.purchaseQuantity}" class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">使用环境：</label></td>
					<td class="width-35">
						<input value="${qisServiceComplaint.environmentDescroption}" class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">创建时间：</label></td>
					<td class="width-35">
						<input name="createTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisServiceComplaint.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">故障描述：</label></td>
					<td class="width-35">
						<textarea name="failDescription" maxlength="2000" class="form-control required">${qisServiceComplaint.failDescription}</textarea>
					</td>
					<td class="width-15 active"><label class="pull-right">验证分析：</label></td>
					<td class="width-35">
						<textarea name="fieldAssayDescription" maxlength="2000" class="form-control required">${qisServiceComplaint.fieldAssayDescription}</textarea>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">现场环境图片：</label></td>
					<td class="width-35">
					 	<c:forEach items="${fn:split(qisServiceComplaint.environmentPictures, ',')}" var="ep">
							<img src="${ep}" width="25%" height="100px" style="margin:0 2%"/>
						</c:forEach> 
					</td>
					<td class="width-15 active"><label class="pull-right">分析验证图片：</label></td>
					<td class="width-35">
						<c:forEach items="${fn:split(qisServiceComplaint.analysisPictures, ',')}" var="ap">
							<img src="${ap}" width="25%" height="100px" style="margin:0 2%"/>
						</c:forEach> 
					</td>
				</tr>							
		 	</tbody>
		</table>
		
		<h5 style="margin-top:25px">故障明细 </h5>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   	<c:forEach items="${qisServiceComplaint.serviceComplaintSnList}" var="qisServiceComplaintSn">							
				<tr>
					<td class="width-15 active"><label class="pull-right">序列号：</label></td>
					<td class="width-35">
						<input value='${qisServiceComplaintSn.sn}' maxlength="30" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">故障现象：</label></td>
					<td class="width-35">
						<input value='${qisServiceComplaintSn.description}' maxlength="500" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">故障图片：</label></td>
					<td class="width-35">
					<c:if test='${qisServiceComplaintSn.pictures ne "[]"}'>
						<c:forEach items="${fn:split(fn:replace(fn:replace(fn:replace(qisServiceComplaintSn.pictures, ']', ''), '[', ''), '\"', ''), ',')}" var="snp">
							<img src="${snp}" width="25%" height="100px" style="margin:0 2%"/>
						</c:forEach> 
					</c:if>
					</td>
				</tr>
			</c:forEach> 			
		 	</tbody>		 	
		</table>
		</c:forEach> 
		</div>
	</form:form>
	</div>
	</div>
	<br/>
	<br/>
	<br/>
	</div>
	</div>
	
	<!-- 客户问题处理信息 -->
	<div class="ibox" style="display:none" id="part3">
	<div class="ibox-title">
		<h5>Part3 > 客户问题处理信息</h5>
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
	<!-- 工具栏 和 查询条件-->
	<div class="row">
	<div class="col-sm-12">
		<form:form id="inputForm" modelAttribute="qisCustomerComplaint" action="${ctx}/qis/qisCustomerComplaint/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<h5 style="margin-top:25px">区域经理处理信息 </h5>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>							
		   		<tr>
					<td class="width-15 active"><label class="pull-right">区域经理：</label></td>
					<td class="width-35">
						<input value='' id="preliminaryOpinionManName" maxlength="100" class="form-control required"/>														
						<input type="hidden" id="preliminaryOpinionManHidden" value="${qisCustomerComplaint.preliminaryOpinionMan}"/>
					</td>
					<td class="width-15 active"><label class="pull-right">处理时间：</label></td>
					<td class="width-35">
						<input name="preliminaryOpinionTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisCustomerComplaint.preliminaryOpinionTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
		   		<tr>
					<td class="width-15 active"><label class="pull-right">是否批量问题：</label></td>
					<td class="width-35">
						<form:input path="isBatchProblem" htmlEscape="false" maxlength="10" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">初步处理意见：</label></td>
					<td class="width-35">
						<form:input path="preliminaryOpinion" htmlEscape="false" maxlength="500" class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div style="display:none" id="service_technology_refused">
			<h5 style="margin-top:25px;color:#ff0000">客服技术驳回信息 </h5>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>							
			   		<tr>
						<td class="width-15 active"><label class="pull-right">驳回人：</label></td>
						<td class="width-35">
							<input value='' id="technologyManName_1" maxlength="100" class="form-control required"/>	
						</td>
						<td class="width-15 active"><label class="pull-right">驳回时间：</label></td>
						<td class="width-35">
							<input name="technologyHandleTime" type="text" maxlength="20" class="form-control required"
									value="<fmt:formatDate value="${qisCustomerComplaint.technologyHandleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
						</td>
					</tr>
			   		<tr>
						<td class="width-15 active"><label class="pull-right">驳回原因：</label></td>
						<td class="width-35">
							<form:input path="technologyRefusedReason" htmlEscape="false" maxlength="100" class="form-control"/>
						</td>
					</tr>
			 	</tbody>
			</table>	
		</div>
						
		<div style="display:none" id="service_technology_handle">
			<h5 style="margin-top:25px">客服技术处理信息 </h5>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>							
			   	  <tr>
					<td class="width-15 active"><label class="pull-right">处理人：</label></td>
					<td class="width-35">
						<input value='' id="technologyManName_2" maxlength="100" class="form-control required"/>														
						<input type="hidden" id="technologyManHidden" value="${qisCustomerComplaint.technologyMan}"/>
					</td>
					<td class="width-15 active"><label class="pull-right">处理时间：</label></td>
					<td class="width-35">
						<input name="technologyHandleTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisCustomerComplaint.technologyHandleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
		   		<tr>
					<td class="width-15 active"><label class="pull-right">问题性质：</label></td>
					<td class="width-35">
						<form:input path="problemType" htmlEscape="false" maxlength="10" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">原因分析及处理方案：</label></td>
					<td class="width-35">
						<form:input path="technologyAssay" htmlEscape="false" maxlength="500" class="form-control "/>
					</td>
				</tr>
			 	</tbody>
			</table>	
		</div>				
		<div style="display:none" id="qa_refused">
			<h5 style="margin-top:25px;color:#ff0000">质量中心驳回信息 </h5>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>							
			   		<tr>
						<td class="width-15 active"><label class="pull-right">驳回人：</label></td>
						<td class="width-35">
							<input value='' id="qaHandlerName_1" maxlength="100" class="form-control required"/>
						</td>
						<td class="width-15 active"><label class="pull-right">驳回时间：</label></td>
						<td class="width-35">
							<input name="qaHandlerTime" type="text" maxlength="20" class="form-control required"
									value="<fmt:formatDate value="${qisCustomerComplaint.qaHandlerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
						</td>
					</tr>
			   		<tr>
						<td class="width-15 active"><label class="pull-right">驳回原因：</label></td>
						<td class="width-35">
							<form:input path="qaRefusedReason" htmlEscape="false" maxlength="100" class="form-control"/>
						</td>
					</tr>
			 	</tbody>
			</table>	
		</div>
						
		<div style="display:none" id="qa_handle">
			<h5 style="margin-top:25px">质量中心处理信息 </h5>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>							
			   	  <tr>
					<td class="width-15 active"><label class="pull-right">处理人：</label></td>
					<td class="width-35">
						<input value='' id="qaHandlerName_2" maxlength="100" class="form-control required"/>														
						<input type="hidden" id="qaHandlerHidden" value="${qisCustomerComplaint.qaHandler}"/>
					</td>
					<td class="width-15 active"><label class="pull-right">处理时间：</label></td>
					<td class="width-35">
						<input name="qaHandlerTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisCustomerComplaint.qaHandlerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
		   		<tr>
					<td class="width-15 active"><label class="pull-right">责任归属：</label></td>
					<td class="width-35">
						<form:input path="problemConclusion" htmlEscape="false" maxlength="10" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">临时方案：</label></td>
					<td class="width-35">
						<form:input path="temporaryProgramme" htmlEscape="false" maxlength="10" class="form-control "/>
					</td>
				</tr>
		   		<tr>
					<td class="width-15 active"><label class="pull-right">根本原因：</label></td>
					<td class="width-35">
						<form:textarea path="causeAnalysis" htmlEscape="false" maxlength="2000" class="form-control required"/>
					</td>
				</tr>
			 	</tbody>
			</table>	
		</div>	
					
		<div style="display:none" id="customer_evaluation">
			<h5 style="margin-top:25px">客户评价信息 </h5>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>							
			   	  <tr>
					<td class="width-15 active"><label class="pull-right">满意程度：</label></td>
					<td class="width-35">
						<form:input path="evaluationType" htmlEscape="false" maxlength="20" class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">评价时间：</label></td>
					<td class="width-35">
						<input name="qaHandlerTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisCustomerComplaint.evaluateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
		   		<tr>
					<td class="width-15 active"><label class="pull-right">评价内容：</label></td>
					<td class="width-35">
						<form:textarea path="evaluation" htmlEscape="false" maxlength="600" class="form-control required"/>
					</td>
				</tr>
			 	</tbody>
			</table>	
		</div>				
		</form:form>
		
	</div>	
	</div>
	<br/>
	<br/>
	<br/>
	</div>
	</div>
	
 </div> 
</body>
</html>