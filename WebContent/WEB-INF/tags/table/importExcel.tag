<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入controller的url --%>
<button id="btnImport" class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" title="导入"><i class="fa fa-folder-open-o"></i> 导入</button>
<div id="importBox" class="hide">
		<form id="importForm" action="${url}" method="post" enctype="multipart/form-data"
			 style="padding-left:20px;text-align:center;width: 400px;" onsubmit="return clicks();"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px;float: left;"/>
			<span  style="margin-right:15px;float:right;margin-top: -25px;" ><input type="submit" id="inputExcel" class="btn btn-sm" style="width:80px;background-color: #1ab395;color: #fff " value="导 入" ></span>
			<br/><br/>
			<span style="float: left;">导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！</span><br/>　　
			
			<script type="text/javascript">
			function clicks(){
				$("#importForm").attr("target",top.getActiveTab().attr("name"));
				var file = $("#uploadFile").val();
				if(file==""){
					top.layer.alert('请选择文件！', {icon: 0, title:'提示'});
					return false;
				}
				if(file.length>4){
					if(file.length==5){
						var xlsSuffix=file.substring(file.length-4,file.length);
						if(xlsSuffix!=".xls"){
							top.layer.alert('格式不正确，请选择格式为:xlsx/xls后缀的excel文件.', {icon: 0, title:'提示'});
							return false;
						}
					}
					var xlsxSuffix=file.substring(file.length-5,file.length);
					var xlsSuffix=file.substring(file.length-4,file.length);
					
					if(xlsxSuffix==".xlsx"||xlsSuffix==".xls"){
						$("#inputExcel").attr("type","button");
						return true;
					}
					
					top.layer.alert('格式不正确，请选择格式为:xlsx/xls后缀的excel文件.', {icon: 0, title:'提示'});
					return false;
					
				}else{
					top.layer.alert('格式不正确，请选择格式为:xlsx/xls后缀的excel文件.', {icon: 0, title:'提示'});
					return false;
				}
				top.layer.close(index);
			}
			
			</script>
		</form>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#btnImport").click(function(){
		top.layer.open({
		    type: 1, 
		    area: [500, 300],
		    title:"导入数据",
		    content:$("#importBox").html() ,
		    btn: ['下载模板', '取消'],
			    btn1: function(index, layero){
				  window.location.href='${url}/template';
			  },
		    btn3: function(index, layero){
    	       		/* $("#importForm").submit(); */
				    top.layer.close(index);
			  },
			 
			  btn2: function(index){ 
				  top.layer.close(index);
    	       }
		}); 
	});
    
});

</script>