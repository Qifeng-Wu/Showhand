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
					  $("#technologyInputForm").submit();
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
							'</tr>';		
				$("#faultTbody").append(html);
			}
			
			validateQualityCenterForm = $("#qualityCenterForm").validate({			
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
			
			validateForm = $("#technologyInputForm").validate({
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
			
			/*================批量客诉状态判断================*/
			//批量客诉状态判断
			var state = $("#state").val();
			var userId = $("#userId").val();
			var nextPerson = $("#nextPerson").val();
			$("#submitterIdName").val(getUserInfo($("#submitterIdNameHidden").val())[0]);
			//是否需要转签
			var isSign = $("#isSign").val();
			if(isSign=="是"){
				var signDepartment = $("#signDepartment").val();				
				$("#signDepartment").val(getDepartmentsByIds(signDepartment));
			}
			//是否需要长期方案解析
			var isLongMeasure = $("input[name='isLongMeasure']:checked").val();
			if(isLongMeasure=="是"){
				var departmentIds = $("#attributionDepartment").val();				
				$("#attributionDepartmentName").val(getDepartmentsByIds(departmentIds));
				$("#attributionDepartmentTr").show();
			}
			if(state=="1"){//初始插入	，待客服技术处理
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						$("#technologyInputForm").show();
						$("#technologyFormDiv").show();
						$("#technologyFoot").show();
					}				
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				}
			}else if(state=="2"){//客服技术驳回，待客服处理
				$("#technologyInputForm").show();
				$("#technologyFormDiv").hide();
				$("#technologyRefusedDiv").show();
				$("#technologyManName").val(getUserInfo($("#technologyManNameHidden").val())[0]);
			}else if(state=="3"){//客服技术确认处理完，待质量中心经理审核			
				$("#technologyInputForm").show();
				$("#technologyFormDiv").show();
				$("#technologyhandleTr").show();
				$("#technologyManName1").val(getUserInfo($("#technologyManNameHidden1").val())[0]);
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						$("#qualityManagerFoot").show();
					}				
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				}
			}else if(state=="4"){//品质保障部经理驳回，待客服技术部处理
				$("#technologyInputForm").show();
				$("#technologyFormDiv").show();
				$("#technologyhandleTr").show();
				$("#technologyManName1").val(getUserInfo($("#technologyManNameHidden1").val())[0]);
				
				$("#qualityManagerRefusedDiv").show();
				$("#qualityManagerName1").val(getUserInfo($("#qualityManagerNameHidden1").val())[0]);
				
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						$("#technologyFoot").show();
					}				
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				}
			}else if(state=="5"){//品质保障部经理确认，待QA工程师处理
				$("#technologyInputForm").show();
				$("#technologyFormDiv").show();
				$("#technologyhandleTr").show();
				$("#technologyManName1").val(getUserInfo($("#technologyManNameHidden1").val())[0]);
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						$("#causeAnalysisDiv").show();
						$("#QAHandleFoot").show();
						$("#part2").show();
					}				
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				} 
			}else if(state=="6"){//QA工程师处理,待转签部门处理
				$("#technologyInputForm").show();
				$("#technologyFormDiv").show();
				$("#technologyhandleTr").show();
				$("#technologyManName1").val(getUserInfo($("#technologyManNameHidden1").val())[0]);
				$("#QAHandleDiv").show();
				$("#qaHandlerName").val(getUserInfo($("#qaHandlerNameHidden").val())[0]);
				
				$("#part2").show();
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						$("#causeAnalysisDiv").show();
						$("#problemConclusionTr").hide();
						$("#transferSignButton").hide();
						$("#QAHandleFoot").show();
					}				
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				} 
			}else if(state=="7"){//转签部门处理,质量验证处理
				$("#technologyInputForm").show();
				$("#technologyFormDiv").show();
				$("#technologyhandleTr").show();
				$("#technologyManName1").val(getUserInfo($("#technologyManNameHidden1").val())[0]);
				$("#QAHandleDiv").show();
				$("#qaHandlerName").val(getUserInfo($("#qaHandlerNameHidden").val())[0]);
				
				$("#part2").show();
				$("#causeAnalysisDiv").show();
				$("#signHandlerName").val(getUserInfo($("#signHandlerNameHidden").val())[0]);
				$("#signHandlerTr").show();
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						$("#temporaryProgramme").attr("readOnly","true");
						$("#causeAnalysis").attr("readOnly","true");
						$("#transferSignButton").hide();
						$("#QAHandleFoot").show();
					}else{
						//隐藏问题性质及结论输入文本
						$("#problemConclusionTr").hide();
					}				
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				} 
			}else if(state=="8"){//质量中心（QA部门）验证处理，待客服技术处理
				$("#technologyInputForm").show();
				$("#technologyFormDiv").show();
				$("#technologyhandleTr").show();
				$("#technologyManName1").val(getUserInfo($("#technologyManNameHidden1").val())[0]);
				$("#QAHandleDiv").show();
				$("#qaHandlerName").val(getUserInfo($("#qaHandlerNameHidden").val())[0]);
				
				$("#part2").show();
				$("#causeAnalysisDiv").show();
				if($("#isSign").val()=="是"){					
					$("#signHandlerName").val(getUserInfo($("#signHandlerNameHidden").val())[0]);
					$("#signHandlerTr").show();
					$("#verifyHandlerName").val(getUserInfo($("#verifyHandlerNameHidden").val())[0]);
					$("#QAVerifyHandlerTr").show();
				}else{
					$("#isSign").val("否");
					$("#signDepartment").val("无");
				}
				
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						
					}			
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				}
			}else if(state=="9"){//客服技术部门验证有效性
				$("#technologyInputForm").show();
				$("#technologyFormDiv").show();
				$("#technologyhandleTr").show();
				$("#technologyManName1").val(getUserInfo($("#technologyManNameHidden1").val())[0]);
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						$("#part2").show();
					}				
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				} 
			}else if(state=="10"){//QA经理验证客诉状态处理
				$("#technologyInputForm").show();
				$("#technologyFormDiv").show();
				$("#technologyhandleTr").show();
				$("#technologyManName1").val(getUserInfo($("#technologyManNameHidden1").val())[0]);
				if(nextPerson!=null && nextPerson!="" && nextPerson!="null"){
					if(isExist(userId,nextPerson)){
						$("#part2").show();
					}				
				}else{
					layer.alert('系统出错啦，无待处理人！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
				} 
			}
			
		});
		
		//客服技术部处理驳回
		function technologyRefuse(){
			top.layer.prompt({title: '请输入驳回原因', formType: 2}, function(text, inde){
    			$("#technologyRefusedReasonHidden").val(text);
    			$("#technologyRefusedForm").submit();
    			top.layer.close(inde);
			});
		}
		//质量中心经理审核确认驳回
		function qualityManagerRefuse(){
			top.layer.prompt({title: '请输入驳回原因', formType: 2}, function(text, inde){
    			$("#qualityManagerRefusedReasonHidden").val(text);
    			$("#qualityManagerRefusedForm").submit();
    			top.layer.close(inde);
			});
		}
		
		//验证电话号码
		jQuery.validator.addMethod("phoneValid", function(value, element) {					
			   var mobile =  /^((0\d{2,3}-\d{7,8})|(1[3567984]\d{9})|(^400-?[0-9]{3}-?[0-9]{4})|(^800-?[0-9]{3}-?[0-9]{4}))$/;	   		   
			   if((mobile.exec(value))?true:false){
				   return true;
			 	}else{
			  		 return false;
		   		} 	   		   
		},"联系方式格式不正确"); 
		
		//判断字符串在不在另一个字符串中
		function isExist(str,strs){
			var exist = false;
			if(str!=null&&str!="null"&&str!=""&&strs!=null&&strs!="null"&&strs!=""){
				if(strs.indexOf(",")>0){
					var strsArray = strs.split(",");
					for(var i=0;i<strsArray.length;i++){
						if(str==strsArray[i]){
							exist = true;
							break;
						}
					}
				}else{
					if(str==strs){
						exist = true;
					}
				}
			}
			return exist;
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	
	<!-- Part1 基本信息（由客服中心填写） -->
	<div class="ibox">
	<div class="ibox-title">
		<h5>Part1 > 批量问题基本信息 </h5>
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
		<form:form modelAttribute="qisComplaint" class="form-horizontal">
		<input type="hidden" value="${fns:getUser().id}" id="userId">
		<input type="hidden" value="${qisComplaint.state}" id="state">
		<input type="hidden" value="${qisComplaint.nextPerson}" id="nextPerson">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<h5 style="margin-top:25px">基本信息 </h5>
		<table class="table table-bordered table-condensed dataTables-example dataTable no-footer">
		   <tbody>
	   			<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>填表人：</label></td>
					<td class="width-35">
						<input value='' id="submitterIdName" maxlength="100" class="form-control required"/>														
						<input type="hidden" id="submitterIdNameHidden" value="${qisComplaint.submitterId}"/>														
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>填表时间：</label></td>
					<td class="width-35">
						<input name="submitTime" type="text" maxlength="20" class="form-control required"
							value="<fmt:formatDate value="${qisComplaint.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>														
					</td>
				</tr>								
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
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>							
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>问题发生时间：</label></td>
					<td class="width-35">
						<input id="problemTime" name="problemTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisComplaint.problemTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>						
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>客户使用时间：</label></td>
					<td class="width-35">
						<input id="useTime" name="useTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisComplaint.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>	
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
				</tr>
			</c:forEach> 			
		 	</tbody>		 	
		</table>
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
	
	<form:form id="technologyInputForm" style="display:none" modelAttribute="qisComplaint" action="${ctx}/qis/qisComplaint/technologyAssaySave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div style="display:none" id="technologyFormDiv">
			<h5 style="margin-top:25px">客服技术部处理信息 </h5>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>							
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>原因分析及处理方案：</label></td>
						<td class="width-35">
							<form:textarea path="technologyAssay" htmlEscape="false" maxlength="2000" class="form-control required"/>
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>问题性质：</label></td>
						<td class="width-35">
							<div class="i-checks">
							<input type="radio" class="check" ${qisComplaint.problemType eq '新增问题'?"checked":""} name="problemType" data-radio="iradio_square-green" value="新增问题">
	                        <label for="problemType1">新增问题</label>
	                        <input type="radio" class="check" ${qisComplaint.problemType eq '新增问题'?"":"checked"} name="problemType" data-radio="iradio_square-green" value="历史问题">
	                        <label for="problemType2">历史问题</label>
							</div>
						</td>
					</tr>
					<tr style="display:none" id="technologyhandleTr">
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>客服技术处理人：</label></td>
						<td class="width-35">
							<input value='' id="technologyManName1" maxlength="100" class="form-control required"/>														
							<input type="hidden" id="technologyManNameHidden1" value="${qisComplaint.technologyMan}"/>														
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>处理时间：</label></td>
						<td class="width-35">
							<input name="technologyHandleTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisComplaint.technologyHandleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>														
						</td>
					</tr>
			 	</tbody>
			</table>
		</div>
		<div style="display:none" id="technologyRefusedDiv">
			<h5 style="margin-top:25px">客服技术部驳回信息 </h5>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>							
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>驳回原因：</label></td>
						<td class="width-85">
							<form:textarea path="technologyRefusedReason" htmlEscape="false" maxlength="2000" class="form-control required"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>驳回人：</label></td>
						<td class="width-85">
							<input value='' id="technologyManName" maxlength="100" class="form-control required"/>														
							<input type="hidden" id="technologyManNameHidden" value="${qisComplaint.technologyMan}"/>														
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>驳回时间：</label></td>
						<td class="width-85">
							<input name="technologyHandleTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisComplaint.technologyHandleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
						</td>
					</tr>
			 	</tbody>
			</table>
		</div>
		<div style="display:none" id="qualityManagerRefusedDiv">
			<h5 style="margin-top:25px">质量中心经理驳回信息 </h5>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			   <tbody>							
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>驳回原因：</label></td>
						<td class="width-85">
							<form:textarea path="qualityManagerRefusedReason" htmlEscape="false" maxlength="2000" class="form-control required"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>驳回人：</label></td>
						<td class="width-85">
							<input value='' id="qualityManagerName1" maxlength="100" class="form-control required"/>														
							<input type="hidden" id="qualityManagerNameHidden1" value="${qisComplaint.qualityManager}"/>														
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>驳回时间：</label></td>
						<td class="width-85">
							<input name="qualityManagerHandleTime" type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisComplaint.qualityManagerHandleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
						</td>
					</tr>
			 	</tbody>
			</table>
		</div>
	</form:form>
	
	<!-- 客服技术部驳货触发事件form -->
	<form action="${ctx}/qis/qisComplaint/technologyRefuse" method="post" id="technologyRefusedForm">
		<input type="hidden" name="id" value="${qisComplaint.id}"/>
		<input type="hidden" name="technologyRefusedReason" id="technologyRefusedReasonHidden"/>
	</form>	
	<!-- 质量中心经理审核确认驳货触发事件form -->
	<form action="${ctx}/qis/qisComplaint/qualityManagerRefuse" method="post" id="qualityManagerRefusedForm">
		<input type="hidden" name="id" value="${qisComplaint.id}"/>
		<input type="hidden" name="qualityManagerRefusedReason" id="qualityManagerRefusedReasonHidden"/>
	</form>	
	
	</div>
	</div>
	<div style="display:none" id="technologyFoot">
		<a class="btn btn-danger" style="width:50%;float:left" onclick="technologyRefuse()">驳回</a>
		<a class="btn btn-primary" style="width:50%;float:left" onclick="doSubmit()">提交</a>
	</div>
	<div style="display:none" id="qualityManagerFoot">
		<a class="btn btn-danger" style="width:50%;float:left" onclick="qualityManagerRefuse()">驳回</a>
		<a class="btn btn-primary" href="#" onclick="openDialog('选择QA工程师', '${ctx}/qis/qisComplaint/findQAEngineer?complaintId=${qisComplaint.id}','800px', '500px')" style="width:50%;float:left">确认并选择QA工程师处理</a>
	</div>
	<br/>
	<br/>
	<br/>
	</div>
	</div>
	
	<!-- Part2 原因及措施（由质量中心填写） -->
	<div class="ibox" style="display:none" id="part2">
	<div class="ibox-title">
		<h5>Part2 > 批量问题原因分析及措施验证</h5>
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
		<form:form id="qualityCenterForm" modelAttribute="qisComplaint" action="${ctx}/qis/qisComplaint/qualityCenterSave" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div style="display:none" id="QAHandleDiv">
			<h5 style="margin-top:25px">处理信息 </h5>
			<table class="table table-bordered">
			   <tbody>							
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>是否转签：</label></td>
						<td class="width-35">
							<input value="${qisComplaint.isSign}" id="isSign" class="form-control required"/>														
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>转签部门：</label></td>
						<td class="width-35">
							<input value="${qisComplaint.signDepartment}" id="signDepartment" class="form-control required"/>															
						</td>
					</tr>	
					<tr>	
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>QA处理人：</label></td>
						<td class="width-35">
							<input value='' id="qaHandlerName" maxlength="100" class="form-control required"/>														
							<input type="hidden" id="qaHandlerNameHidden" value="${qisComplaint.qaHandler}"/>														
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>处理时间：</label></td>
						<td class="width-35">
							<input type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisComplaint.qaHandlerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>														
						</td>
					</tr>
					<tr style="display:none" id="signHandlerTr">	
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>会签处理人：</label></td>
						<td class="width-35">
							<input value='' id="signHandlerName" maxlength="100" class="form-control required"/>														
							<input type="hidden" id="signHandlerNameHidden" value="${qisComplaint.signHandler}"/>														
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>会签时间：</label></td>
						<td class="width-35">
							<input type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisComplaint.signHandlerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>														
						</td>
					</tr>
					<tr style="display:none" id="QAVerifyHandlerTr">
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>QA验证人：</label></td>
						<td class="width-35">
							<input value='' id="verifyHandlerName" maxlength="100" class="form-control required"/>														
							<input type="hidden" id="verifyHandlerNameHidden" value="${qisComplaint.verifyHandler}"/>														
						</td>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>验证时间：</label></td>
						<td class="width-35">
							<input type="text" maxlength="20" class="form-control required"
								value="<fmt:formatDate value="${qisComplaint.verifyHandlerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>														
						</td>
					</tr>
			 	</tbody>
			</table>		
		</div>
		
		<div style="display:none" id="causeAnalysisDiv">
			<h5 style="margin-top:25px">原因及措施 </h5>
			<table class="table table-bordered">
			   <tbody>							
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>临时方案：</label></td>
						<td class="width-35">
							<form:textarea path="temporaryProgramme" id="temporaryProgramme" htmlEscape="false" maxlength="500" class="form-control required"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>原因分析：</label></td>
						<td style="width:85%">
							<form:textarea path="causeAnalysis" id="causeAnalysis" htmlEscape="false" maxlength="500" class="form-control required"/>
						</td>
					</tr>
				<div id="problemConclusionTr">	
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>问题性质及结论：</label></td>
						<td style="width:85%">
							<form:textarea path="problemConclusion" htmlEscape="false" maxlength="500" class="form-control required"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>是否需要长期方案：</label></td>
						<td style="width:85%">
							<input style="margin-left:35px" type="radio" value="是" ${qisComplaint.isLongMeasure eq "是"?"checked":""} onclick="isLongMeasure1()" name="isLongMeasure"> 是
	                        <input style="margin-left:35px" type="radio" value="否" ${qisComplaint.isLongMeasure eq "是"?"":"checked"} onclick="isLongMeasure2()" name="isLongMeasure"> 否 
						</td>
					</tr>
					<script type="text/javascript">
						function isLongMeasure1(){
							$("#attributionDepartmentTr").show();
						}
						function isLongMeasure2(){
							$("#attributionDepartmentTr").hide();
							$("#attributionDepartment").val("");
							$("#attributionDepartmentName").val("");
						}
					</script>
					<tr style="display:none" id="attributionDepartmentTr">
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>责任归属部门：</label></td>
						<td style="width:85%">
							<input value='' id="attributionDepartmentName" readOnly maxlength="100" class="form-control required"/>	
							<input type = "hidden" value='${qisComplaint.attributionDepartment}' id="attributionDepartment" name="attributionDepartment" readOnly maxlength="500" class="form-control required"/>	
							<span class="input-group-btn" style="position: absolute;right: 118px;bottom:30px">  
         			  		 <a class="btn btn-primary" onclick="chooseDepartment()"><i class="fa fa-search"></i> 选择部门</a>    			   
      						</span>
						</td>
					</tr>
				</div>		
			 	</tbody>
			</table>
		</div>
		</form:form>
		
		<div style="display:none" id="causAnalysisDiv">
			<h5 style="margin-top:25px">方案有效性验证 </h5>
			<table class="table table-bordered">
			   <tbody>							
					<tr>
						<td class="width-15 active"><label class="pull-right"><font color="red">* </font>方案有效性：</label></td>
						<td class="width-35">
							<textarea name="temporaryProgramme" id="temporaryProgramme" maxlength="100" class="form-control required"></textarea>
						</td>
					</tr>
			 	</tbody>
			</table>		
		</div>
	</div>	
	</div>
	<div style="display:none" id="QAHandleFoot">
		<a id="transferSignButton" class="btn btn-danger" href="#" onclick="openDialog('选择转签部门', '${ctx}/qis/qisComplaint/findDepartment?complaintId=${qisComplaint.id}','800px', '500px')" style="width:50%;float:left">转签</a>
		<a class="btn btn-primary" style="width:50%;float:left" onclick="qualityCenterFormSubmit()">提交</a>
	</div>
	<br/>
	<br/>
	<br/>
	</div>
	</div>
 </div> 
 <script type="text/javascript">
 //确认选择部门
 function chooseDepartmentConfirm(){
	 var str="";
	 var ids="";
	 var strName="";
	 var names="";
	  $("#departmentListTbody tr td input:checkbox").each(function(){
	    if(true == $(this).is(':checked')){
	      str+=$(this).attr("id")+",";
	      strName+=$(this).attr("class")+",";
	    }
	  });
	  if(str.substr(str.length-1)== ','){
	    ids = str.substr(0,str.length-1);
	  }
	  if(strName.substr(strName.length-1)== ','){
		 names = strName.substr(0,strName.length-1);
	  }
	  if(ids == "" || names == ""){
		  layer.alert('至少选择一个部门！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
		return false;
	  }				
	$("#attributionDepartment").val(ids);
	$("#attributionDepartmentName").val(names);

 }
 
 
 //选择部门
 function chooseDepartment(){
		$.ajax({
			url : "${ctx}/qis/qisComplaint/findDepartments",
			type : "POST",
			dataType : "json",
			async : false,
			success : function(result) {
				if(result.success==true){
					var departmentList = result.body.departmentList;
					if(departmentList.length>0){
						$("#departmentListTbody").empty();
						for(var i=0;i<departmentList.length;i++){
							var html = '<tr>'+
											'<td> <input type="checkbox" name="department" id="'+departmentList[i].id+'" class="'+departmentList[i].name+'"></td>'+
											'<td>'+departmentList[i].name+'</td>'+
											'<td>'+departmentList[i].code+'</td>'+
											'<td>'+departmentList[i].master+'</td>'+
										'</tr>';
							$("#departmentListTbody").append(html);	
						}
		
					}
					
				}						
			},
			error : function(xhr, type, errorThrown) {
				layer.alert('系统出错啦！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
			}
		});
	 $("#inputModal").modal("show");
 }
 //质量中心提交
 function qualityCenterFormSubmit(){
	  if(validateQualityCenterForm.form()){
		  	layer.confirm('是否确认提交信息？', {closeBtn: 0, icon : 3 ,skin : 'layui-layer-molv',btn: ['确认','取消'] //按钮
			}, function(){
				  $("#qualityCenterForm").submit();
			});
		  return true;
	  }	
	  return false;
 }
 //根据用户主键编号获取用户信息
 function getUserInfo(userId){
	var name = null;
	var phone = null;
	if(userId==null || userId=="" || userId=="null"){
		return [name, phone];
	}
	$.ajax({
		url : "${ctx}/sys/user/getInfo",
		type : "POST",
		dataType : "json",
		data : {userId:userId},
		async : false,
		success : function(result) {
			name = result.name;
			phone = result.phone;						
		},
		error : function(xhr, type, errorThrown) {
			layer.alert('系统出错啦！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
		}
	});
	return [name, phone];
} 
 //根据部门主键获取部门信息
 function getDepartmentsByIds(departmentIds){
	var departmentNames = "";
	if(departmentIds==null || departmentIds=="" || departmentIds=="null"){
		return departmentNames;
	}
	$.ajax({
		url : "${ctx}/qis/qisComplaint/findDepartmentsByIds",
		type : "POST",
		dataType : "json",
		data : {departmentIds:departmentIds},
		async : false,
		success : function(result) {
			if(result.success==true){
				var list = result.body.departmentList;
				if(list.length>0){
					for(var i=0;i<list.length;i++){
						departmentNames += list[i].name + ',';
					}
					if(departmentNames.substr(departmentNames.length-1)== ','){
						departmentNames = departmentNames.substr(0,departmentNames.length-1);
					}
				}	
			}else{
				layer.alert(result.message, {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
			}					
		},
		error : function(xhr, type, errorThrown) {
			layer.alert('系统出错啦！', {skin: 'layui-layer-warning',closeBtn:0,icon:7 });
		}
	});
	return departmentNames;
} 
 </script>
 
 	<!-- 选择部门 -->
	<div class="modal fade" tabindex="-1" id="inputModal" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button">×</button>
					<h4 class="modal-title">选择部门</h4>
				</div>
				<div class="modal-body">
					<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
						<thead>
							<tr>
								<th><input type="checkbox" class="i-checks"></th>
								<th class="sort-column name">名称</th>
								<th class="sort-column no">部门编码</th>
								<th class="sort-column master">负责人</th>
							</tr>
						</thead>
						<tbody id="departmentListTbody">
							<tr>
								<td> <input type="checkbox" name="department" id="${office.id}" class="i-checks"></td>
								<td>1</td>
								<td>3</td>
								<td>4</td>
							</tr>
						</tbody>
					</table>		
				</div>
				<div class="modal-footer">			
					<button data-dismiss="modal" class="btn btn-warning btn-sm" type="button" onclick="chooseDepartmentConfirm()">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>