<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>店铺信息管理</title>
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
	
    //验证电话号码
    jQuery.validator.addMethod("isphone", function(value, element) {
    	
	   var length = value.length; 
	   var mobile1 = /^\d+$/; 
	   var mobile2 =  /^(\d{3,4}-?){1,2}\d+$/g; 	   
	   
	   if((mobile1.exec(value))?true:false){
			   return true;
		 }else if((mobile2.exec(value))?true:false){
			   return true;
	   }else{
		   return false;
	   } 	   	
	   
    },"输入的号码格式不正确");
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox">
	<div class="ibox-title">
		<h5>店铺信息 </h5>
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
    
    <div class="ibox-content" style="padding-bottom:70px">
		<form:form id="inputForm" modelAttribute="groceryPlatform" action="${ctx}/grocery/platform/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="platformId"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>店铺名称：</label></td>
					<td class="width-85">
						<form:input path="name" htmlEscape="false" maxlength="30" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>店铺图片：</label></td>
					<td class="width-85">
						<form:input id="image" path="picture" htmlEscape="false" maxlength="500" class="form-control required" readOnly="true"/>
					 	<sys:ckfinder input="image" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="200" maxHeight="200"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>联系电话：</label></td>
					<td class="width-85">
						<form:input path="phone" htmlEscape="false" maxlength="30" class="form-control required isphone"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>营业时间：</label></td>
					<td class="width-85">									
						<div class="input-group item">	
							<form:input path="startTime" htmlEscape="false" maxlength="30" class="form-control required" placeholder="点击选择开始时间" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})"/>			 	
							<span class="input-group-addon">至</span>
							<form:input path="endTime" htmlEscape="false" maxlength="30" class="form-control required" placeholder="点击选择结束时间" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})"/>															 	
						</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>门店地址：</label></td>
					<td class="width-85">
						<form:input path="address" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>店铺公告：</label></td>
					<td class="width-85">
						<form:input path="notice" htmlEscape="false" maxlength="100" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>店铺描述：</label></td>
					<td class="width-85">
						<form:textarea path="description" htmlEscape="false" maxlength="1000" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>店铺图片集：</label></td>
					<td class="width-85">
						<form:input id="photo" path="photos" htmlEscape="false" maxlength="5000" class="form-control required" readOnly="true"/>
					 <sys:ckfinder input="photo" type="images" uploadPath="/photo" selectMultiple="true" maxWidth="200" maxHeight="200"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>首页左边图片：</label></td>
					<td class="width-85">
						<form:input id="leftPic" path="leftPic" htmlEscape="false" maxlength="500" class="form-control required" readOnly="true"/>
					 	<sys:ckfinder input="leftPic" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="200" maxHeight="200"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>首页右上图片：</label></td>
					<td class="width-85">
						<form:input id="rightTopPic" path="rightTopPic" htmlEscape="false" maxlength="500" class="form-control required" readOnly="true"/>
					 	<sys:ckfinder input="rightTopPic" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="200" maxHeight="200"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>首页右下图片：</label></td>
					<td class="width-85">
						<form:input id="rightBottomPic" path="rightBottomPic" htmlEscape="false" maxlength="500" class="form-control required" readOnly="true"/>
					 	<sys:ckfinder input="rightBottomPic" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="200" maxHeight="200"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<div class="pull-right" style="margin-top:20px">
			<button type="submit" class="btn btn-sm btn-primary"> <i class="fa fa-check"></i> 保存</button>
		</div>
	</form:form>
	</div>
	</div>
</div>
</body>
</html>