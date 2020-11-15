<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>新闻资讯管理</title>
<meta name="decorator" content="default"/>
<script type="text/javascript">
	var layedit, index;
	layui.use('layedit', function(){
		layedit = layui.layedit,$ = layui.jquery;
	  
	  //注意：layedit.set 一定要放在 build 前面，否则配置全局接口将无效。
	  layedit.set({
		  uploadImage: {
		    url: '${ctx}/audio/pictures/upload', //接口url
		    type: 'post' //默认post	    	
		  }
		});
		
	  //构建一个默认的编辑器
	  index = layedit.build('lay_editor');
	  //layedit.getContent(index); //获取编辑器内容
	  //layedit.getText(index); //获取编辑器纯文本内容
	});
	
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  var content = layedit.getContent(index);
		  $("#state_value").val($('input:radio:checked').val());
		  $("#editor_content").val(content);
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
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>内容：</label></td>
					<td class="width-85">
						<textarea class="layui-textarea" id="lay_editor" style="display: none">${fn:escapeXml(aNews.content)}</textarea>
					</td>
				</tr>
				<form id="inputForm" modelAttribute="aNews" action="${ctx}/audio/news/save" method="post" class="form-horizontal">
				<input type="hidden" name="id" value="${aNews.id}"/>
				<input type="hidden" name="state" value="${aNews.state}" id="state_value"/>
				<input type="hidden" name="content" value='${fn:escapeXml(aNews.content)}' id="editor_content"/>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>标题：</label></td>
					<td class="width-85">
						<input name="title" value='${fn:escapeXml(aNews.title)}' maxlength="20" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>标签：</label></td>
					<td class="width-85">
						<input name="label" value='${fn:escapeXml(aNews.label)}' maxlength="10" class="form-control required"/>
					</td>
				</tr>
				<tr>
		             <td class="width-15 active"><label class="pull-right"><font color="red">* </font>摘要：</label></td>
		             <td class="width-85"><textarea name="summary" rows="3" maxlength="200" class="form-control required">${fn:escapeXml(aNews.summary)}</textarea></td> 		         
		        </tr>	
				<%-- <tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>图片：</label></td>
					<td class="width-85">
						<form:input id="pictures" path="picture" htmlEscape="false" maxlength="500" class="form-control required" readOnly="true"/>
						<sys:ckfinder input="pictures" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="220" maxHeight="220"/>
					</td>
				</tr> --%>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>是否显示：</label></td>
					<td class="width-85">
							<div class="i-checks">
							<input type="radio" class="check" ${aNews.state==true?"checked":""} name="state0" data-radio="iradio_square-green" value="1">
	                        <label for="state1">显示</label>
	                        <input type="radio" class="check" ${aNews.state==true?"":"checked"} name="state0" data-radio="iradio_square-green" value="0">
	                        <label for="state2">隐藏</label>
							</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>显示顺序：</label></td>
					<td class="width-85">
						<input name="sort" value='${aNews.sort}' maxlength="2" class="form-control required"/>
						<span>数字越小越靠前显示</span>						
					</td>				
				</tr>
				</form>
		 	</tbody>
		</table>
</body>
</html>