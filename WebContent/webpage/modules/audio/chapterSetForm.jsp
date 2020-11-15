<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>章节设置管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
var validateForm;
$(document).ready(function() {
	validateForm = $("#musicForm").validate({
	 	rules: {
	 		node1: {digits:true,min:1},
	 		node2: {digits:true,min:1}
			},
		messages: {
			node1: {digits: "请输入正整数"},
			node2: {digits: "请输入正整数"}
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
	
//参考文档https://blog.csdn.net/u013373006/article/details/82108873?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
	var data = '${aChapter.nodes}';
	if(data) nodesParse(data);//节点解析
	var musics = '${aChapter.musics}';
	if(musics) musicsParse(musics);//背景音乐解析
});
	
</script>
<script>
	//解析节点数据
	function nodesParse(data){
		var datas = data.replace(new RegExp('"{','g'),'{').replace(new RegExp('}"','g'),'}');
		var array = JSON.parse(datas);
		tree.setData('.tree', toTree(array));
	    //给每个li加上id值, 并将获取的值填充到input隐藏域中
	    var li_length = $('.tree li').length;
		var arrays = JSON.parse(datas);
	    for(var i=0;i<li_length;i++){
	    	$('.tree li').eq(i).attr('id',JSON.parse(JSON.stringify(arrays[i])).id);
	    	$('.tree li').eq(i).children('input').eq(0).val(JSON.stringify(arrays[i]));
	    	
	    	//获取音频文件时长并显示到页面
	    	$('#audio_div').append('<audio style="display:none" src="" controls preload="auto" id="audio_duration'+i+'"></audio>')
	    	$("#audio_duration"+i+"").attr('src', JSON.parse(JSON.stringify(arrays[i])).link)
	    	duration(i);
	    } 
	    
	    //移除起始节点的动作图标
	    $('.tree li').eq(0).children('span').eq(0).children('i[class*="fa fa-hand"]').remove();
	}
	
	//获取音频文件时长并显示到页面
	function duration(index){
		var myVid = document.getElementById("audio_duration"+index+"");
		if(myVid != null){
		     myVid.load();
		     myVid.oncanplay = function () {  
				var durations = ' | '+parseInt(myVid.duration)+"秒"
				$('.tree li').eq(index).children('span').eq(0).append(durations);
		      }
		}
	}
	
    function toTree(data) {
        // 删除 所有 children,以防止多次调用
        data.forEach(function (item) {
            delete item.children;
        });
 
        // 将数据存储为 以 id 为 KEY 的 map 索引数据列
        var map = {};
        data.forEach(function (item) {
            map[item.id] = item;
        });
//        console.log(map);
        var val = [];
        data.forEach(function (item) {
            // 以当前遍历项，的pid,去map对象中找到索引的id
            var parent = map[item.pid];
            // 好绕啊，如果找到索引，那么说明此项不在顶级当中,那么需要把此项添加到，他对应的父级中
            if (parent) {
                (parent.children || ( parent.children = [] )).push(item);
            } else {
                //如果没有在map中找到对应的索引ID,那么直接把 当前的item添加到 val结果集中，作为顶级
                val.push(item);
            }
        });
        return val;
    }

</script>

<style type="text/css">
/** tree.css zyj 2018.4.21 */
ul,li{list-style-type:none;}
.tree{display:block;position:relative;padding:0px 15px;}
.tree span{display:inline-block;box-sizing:border-box;height:33px;line-height:30px;min-width:60px;text-align:center;color:#fff;background-color:#1ab394;border:1px solid #1ab394;border-radius:5px;padding:0 8px;}
.tree ul{position:relative;padding-left:60px;margin:0;}
.tree ul>li{position:relative;padding:25px 20px;}
.tree>ul{padding:0;margin:0;}
/** 水平方向连线 */
.tree>ul ul>li:after{content:' ';position:absolute;top:40px;left:-45px;width:65px;border:none;border-top:1px solid #ddd;}
/** 垂直方向连线 */
.tree ul>li:not(:last-child):before{content:' ';position:absolute;top:0;left:-45px;height:100%;border:none;border-left:1px solid #ddd;}
.tree ul>li:last-child:before{content:' ';position:absolute;top:0;left:-45px;height:40px;border:none;border-left:1px solid #ddd;}
/** 控制鼠标移上去的颜色 */
/**.tree span:hover, .tree span:hover+ul span{color:#fff;background-color:#1ab394;}
.tree span:hover, .tree span:hover+ul span, .tree span:hover+ul li:before, .tree span:hover+ul li:after{border-color:#1ab394;} 
*/
/** 折叠图标 */
.tree .fa:before{margin-right:5px;}
.tree .fa-minus-circle, .tree .fa-plus-circle{cursor:pointer;}
</style>
<script type="text/javascript">
/** tree.js zyj 2018.4.22 */
(function(name){
    var tree, outer, defaultDateFormat;
    var htm = '<input type="hidden">'+
    	'<a onclick="addChild(this)" class="btn btn-white btn-sm"><i class="fa fa-plus"></i> 添加子级</a>'+
		'<a onclick="deleteChild(this)" class="btn btn-white btn-sm"><i class="fa fa-times-circle"></i> 删除</a>'+
		'<a onclick="set(this)" class="btn btn-white btn-sm"><i class="fa fa-gear"></i> 设置</a>';
    outer = {
        setData : setData,
    };
    
    defaultDateFormat = {
            unfold : true,
            name : 'name',
            action: 'action',
            status: 'status',
            link: 'link',
            childName : 'children'
    };

    function getDataFormat(dataFormat){
        var index;
        if(!dataFormat){
            return defaultDateFormat;
        }
        for(index in defaultDateFormat){
            dataFormat[index] = typeof dataFormat[index] == 'undefined'? defaultDateFormat[index] : dataFormat[index];
        }
        return dataFormat
    }
    
    function initTreeJs(name){
        var tree;
        if(checkTreeNameUsed(name)){return;}
        window[name] = outer;
        initFoldIcon($('.tree'));
    }
    
    function checkTreeNameUsed(name){
        if(window[name]){
            console.error("The window object name [" + name + "] has been used, tree.js can't be loaded! You can try another name." );
            return true;
        }
        return false;
    }
    
    function initFoldIcon(target){
        target.off('click', 'span>i.fa').on('click', 'span>i.fa', function(e){
            var ele = $(e.target);
            if(ele.hasClass('fa-minus-circle')){
                ele.removeClass('fa-minus-circle').addClass('fa-plus-circle').parent().next('ul').hide(200);
            }else if(ele.hasClass('fa-plus-circle')){
                ele.removeClass('fa-plus-circle').addClass('fa-minus-circle').parent().next('ul').show(200);
            }
        })
    }
    
    function getJqueryObjectBySelector(selector){
        var ele = $(selector);
        if(typeof selector != 'string'){
            console.error("The first parameter jquery selector [" + selector +  "] must be a string!" );
            return;
        }
        if(!ele.hasClass('tree')){
            ele = ele.find('.tree');
        }
        if(ele.length != 1){
            console.error("The selector [" + selector +  "] expect only one element!" );
            return;
        }
        return ele;
    }
    
    function setData(selector, data, dataFormat){
        var ele = getJqueryObjectBySelector(selector);
        if(!ele){return;}
        if(!data){return;}
        if(!data.length){
            data = [data];
        }
        dataFormat = getDataFormat(dataFormat);
        dataFormat.topElement = true;
        ele.empty().append(getTreeList(data, dataFormat));
        initFoldIcon(ele);
    }
    
    function getTreeList(data, dataFormat){
        var i, single, name, action, status, link, children, childDataFormat, array = [];
        childDataFormat = dataFormat.child || dataFormat;
        if(dataFormat.unfold){
            array.push('<ul>');
        }else if(dataFormat.topElement){
            dataFormat.topElement = false;
            array.push('<ul>');
        }else{
            array.push('<ul style="display:none;">');
        }
        for(i=0; i<data.length; i++){
            single = data[i];
            if(typeof dataFormat.name == 'function'){
                name = dataFormat.name(single);
            }else if(typeof dataFormat.name == 'string'){
                name = single[dataFormat.name];
            }else{
                name = single['name'];
            }
            if(typeof dataFormat.action == 'string'){
            	action = single[dataFormat.action];
            }else{
            	action = single['action'];
            }
            if(typeof dataFormat.status == 'string'){
            	status = single[dataFormat.status];
            }else{
            	status = single['status'];
            }
            if(typeof dataFormat.link == 'string'){
            	link = single[dataFormat.link];
            }else{
            	link = single['link'];
            }
            if(typeof dataFormat.childName == 'string'){
                children = single[dataFormat.childName];
            }else{
                children = single['children'];
            }
            array.push('<li>');
            if(status=="0"){
            	array.push('<span style="background-color:#ddd">');
            }else{
            	array.push('<span style="background-color:#1ab394">');
            }         
            if(children && children.length > 0){
                if(dataFormat.unfold){
                    array.push('<i class="fa fa-minus-circle"></i>');
                }else{
                    array.push('<i class="fa fa-plus-circle"></i>');
                }   
                array.push(actionIcon(action));
                array.push('<a style="color:#fff" href="'+link+'" target="_blank">'+name+'</a>');
                array.push('</span>');              
                array.push(htm);
                array.push(getTreeList(children, childDataFormat));
            }else{
            	array.push(actionIcon(action));
            	array.push('<a style="color:#fff" href="'+link+'" target="_blank">'+name+'</a>');
                array.push('</span>');
                array.push(htm);
            }
            
            array.push('</li>');
        }
        array.push('</ul>');       
        return array.join('');
    }
    
    initTreeJs(name);
}('tree'))


function actionIcon(action){
	switch(action) {
    case '1': return ' <i class="fa fa-hand-o-up"></i>'
       break;
    case '2': return ' <i class="fa fa-hand-o-down"></i>'
       break;
    case '3': return ' <i class="fa fa-hand-o-left"></i>'
       break;
    case '4': return ' <i class="fa fa-hand-o-right"></i>'
       break;
    case '5': return ' <i class="fa fa-hand-rock-o"></i>'
       break;
    case '6': return ' <i class="fa fa-hand-stop-o"></i>'
       break;
    default: 
	} 
}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox">
			<div class="ibox-title">
				<div id="audio_div"></div>
				<audio style="display:none" src="" controls preload="auto" id="audio_duration"></audio>
				<h5>章节设置<span style="color:red">（音频请使用MP3格式）</span></h5>
				<h5>
					&emsp;&emsp;<span style="color:#1ab394">上滑 <i class="fa fa-hand-o-up"></i></span>&nbsp;
					<span style="color:#1ab394">下滑 <i class="fa fa-hand-o-down"></i></span>&nbsp;
					<span style="color:#1ab394">左滑 <i class="fa fa-hand-o-left"></i></span>&nbsp;
					<span style="color:#1ab394">右滑 <i class="fa fa-hand-o-right"></i></span>&nbsp;
					<span style="color:#1ab394">双击 <i class="fa fa-hand-rock-o"></i></span>&nbsp;
					<span style="color:#1ab394">摇晃 <i class="fa fa-hand-stop-o"></i></span>
					&emsp;&emsp;所属故事章回：<span style="color:#1ab394">${aChapter.story.title} / ${aChapter.title}</span>
				</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					<a class="close-link"> <i class="fa fa-times"></i></a>
				</div>
			</div>
								
			<div class="ibox-content" style="padding: 0 2% 0 2%; min-height:500px">
				<sys:message content="${message}" />
				<!-- 工具栏 和 查询条件-->
				<div class="row">
					<div class="col-sm-12">
						<div class="tree">
							<ul>
								<li id="0">
								<input type="hidden">
								<span>起始节点</span>								
								<a onclick="addChild(this)" class="btn btn-white btn-sm"><i class="fa fa-plus"></i> 添加子级</a>
								<a onclick="deleteChild(this)" class="btn btn-white btn-sm"><i class="fa fa-times-circle"></i> 删除</a>
								<a onclick="set(this)" class="btn btn-white btn-sm"><i class="fa fa-gear"></i> 设置</a>
							 <!-- <ul>
										<li>
								<span>小节A-A</span>
								<a onclick="set(this)" class="btn btn-white btn-sm"><i class="fa fa-times-circle"></i> 设置</a>
								<a onclick="addChild(this)" class="btn btn-white btn-sm"><i class="fa fa-plus"></i> 添加子级</a>
								<a onclick="deleteChild(this)" class="btn btn-white btn-sm"><i class="fa fa-times-circle"></i> 删除</a>
											<ul>
												<li><span>小节A-A-A</span></li>
												<li><span>小节A-A-B</span></li>
											</ul>
										</li>								
									</ul>  -->
								</li>
							</ul>
						</div>
						<div style="position:absolute;margin-left:82%;top:25px;">
							<button class="btn btn-primary" onclick="musicSet()"> 音乐设置 </button>&emsp;
							<button class="btn btn-primary" onclick="submit()"> 节点提交 </button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	//提交保存
	function submit(){
		var li_length = $('.tree ul li').length;
		var nodeArray = new Array();
		for(var i=0;i<li_length;i++){
			var node_value = $('.tree ul li').eq(i).children('input').eq(0).val();
			if(node_value){
				nodeArray[i] = node_value;
			}			
		}
		if(nodeArray.length<=0){
			layer.msg('请设置节点信息');
			return;
		}
		layer.confirm('是否确认提交信息？', {closeBtn: 0, icon : 3 ,skin : 'layui-layer-molv',btn: ['确认','取消'] //按钮
		}, function(){
			$.ajax({
				url : "${ctx}/audio/chapter/nodesSet",
				type : "POST",
				dataType : "json",
				data : {chapterId: '${aChapter.chapterId}', nodes:JSON.stringify(nodeArray)},
				success : function(result) {
					if (result.success == true) {
						layer.msg('提交数据成功！');
						if(result.body.aChapter.nodes){
							location.reload()
						}
					}
				},
				error : function(xhr, type, errorThrown) {
					layer.msg('系统出错啦！');
				}
			});
		});
	}
	
	
	//添加子节点
	function addChild(thisFlag) {
		if(!$(thisFlag).parent('li').children('input').eq(0).val()){
			layer.msg('请设置当前节点信息');
			return;
		}
		if ($('.tree ul').length >= 10) {
			layer.msg('最多只能添加10级！');
			return;
		} 
		var li_id = $('.tree ul li').length;
		var html = '';
		if($(thisFlag).parent('li').children('ul').length>0){
			html = '<li id="'+li_id+'">'
				+ '<input type="hidden">'
				+ '<span>节点</span>'		
				+ '<a onclick="addChild(this)" class="btn btn-white btn-sm"><i class="fa fa-plus"></i> 添加子级</a>'
				+ '<a onclick="deleteChild(this)" class="btn btn-white btn-sm"><i class="fa fa-times-circle"></i> 删除</a>'
				+ '<a onclick="set(this)" class="btn btn-white btn-sm"><i class="fa fa-gear"></i> 设置</a>'
				+ '</li>';
			$(thisFlag).parent('li').children('ul').append(html);
		}else{
			html = '<ul>'
				+ '<li id="'+li_id+'">'
				+ '<input type="hidden">'
				+ '<span>节点</span>'
				+ '<a onclick="addChild(this)" class="btn btn-white btn-sm"><i class="fa fa-plus"></i> 添加子级</a>'
				+ '<a onclick="deleteChild(this)" class="btn btn-white btn-sm"><i class="fa fa-times-circle"></i> 删除</a>'
				+ '<a onclick="set(this)" class="btn btn-white btn-sm"><i class="fa fa-gear"></i> 设置</a>'
				+ '</li>' + '</ul>';
				$(thisFlag).parent('li').append(html);
			}
		}
		//删除节点
		function deleteChild(thisFlag) {
			if($(thisFlag).parent('li').attr('id')==0){
				layer.msg('起始节点不能删除');return;
			}
			if($(thisFlag).parent('li').parent('ul').children('li').length>1){
				$(thisFlag).parent().remove();
			}else{
				$(thisFlag).parent().parent().remove();
			}		
		}
		//节点素材动作设置
		function set(thisFlag) {
			$('#li_index_hidden').val('');
			var input_value = $(thisFlag).parent().children('input').eq(0).val();
			//如果是编辑
			if(input_value){
				//素材
				$('#link').val(JSON.parse(input_value).link);
				var ht = '<a href="'+JSON.parse(input_value).link+'" url="'+JSON.parse(input_value).link+'" target="_blank">'+JSON.parse(input_value).name+'</a>'+
						  '<a href="javascript:" onclick="linkDel(this);">×</a>';
				$('#link').next('ol').children('li').empty();
				$('#link').next('ol').children('li').append(ht);
				$('#link').val(JSON.parse(input_value).link);
				//背景
			    $('#img').val(JSON.parse(input_value).img);
			    var htm = '<img src="'+JSON.parse(input_value).img+'" url="'+JSON.parse(input_value).img+'" style="max-width:220px;max-height:220px;_height:220px;border:0;padding:3px;">'+
						  '<a href="javascript:" onclick="imgDel(this);">×</a>';
				$('#img').next('ol').children('li').empty();
				$('#img').next('ol').children('li').append(htm);
				$('#img').val(JSON.parse(input_value).img);
				//动作
				var action = JSON.parse(input_value).action;
				switch(action) {
				     case '1': $('#nodeModal input:radio[name="action"]').eq(0).attr('checked');
				        break;
				     case '2': $('#nodeModal input:radio[name="action"]').eq(1).attr('checked');
				        break;
				     case '3': $('#nodeModal input:radio[name="action"]').eq(2).attr('checked');
				        break;
				     case '4': $('#nodeModal input:radio[name="action"]').eq(3).attr('checked');
				        break;
				     case '5': $('#nodeModal input:radio[name="action"]').eq(4).attr('checked');
				        break;
				     case '6': $('#nodeModal input:radio[name="action"]').eq(5).attr('checked');
				        break;
				     default: $('#nodeModal input:radio[name="action"]').eq(0).attr('checked');
				} 
				var status = JSON.parse(input_value).status;
				switch(status) {
				     case '1': $('#nodeModal input:radio[name="status"]').eq(0).attr('checked',true);
				        break;
				     case '0': $('#nodeModal input:radio[name="status"]').eq(1).attr('checked',true);
				        break;
				     default: $('#nodeModal input:radio[name="status"]').eq(0).attr('checked',true);
				} 
			}else{//新添加
				if($('#link').val()){
					$('#link').next('ol').children('li').children('a').eq(1).click();
				}
				$('#link').val('');
				
				if($('#img').val()){
					$('#img').next('ol').children('li').children('a').eq(0).click();
				}
				$('#img').val('');	
			}
			$('#li_index_hidden').val($(".tree li").index($(thisFlag).parents('li')));
			
			//起始节点隐藏动作和状态设置DIV
			if($(thisFlag).parent('li').attr('id')==0){
				$("#status_div").attr('style','display:none')
			}else{
				$("#status_div").attr('style','display:block')
			}
			$("#nodeModal").modal("show");
		}
		
		//设置确定后将数据填充到对应节点
		function modalConfirm() {
			if(!$('#link').val()){//判断有没有选择素材
				layer.msg('请选择素材');
				return;
			}
			if(!$('#img').val()){//判断有没有选择背景
				layer.msg('请选择背景');
				return;
			}
			var li_index = $('#li_index_hidden').val();
			var link_name = '';
			if($('#link').val()){
				link_name = $('#link').next('ol').children('li').children('a').eq(0).text();
				$('.tree li').eq(li_index).children('span').eq(0).text(link_name);
			}

			//动作判断
			var action_value = $('#nodeModal input:radio[name="action"]:checked').val();
			switch(action_value) {
			     case '1': $('.tree li').eq(li_index).children('span').eq(0).prepend(' <i class="fa fa-hand-o-up"></i>')
			        break;
			     case '2': $('.tree li').eq(li_index).children('span').eq(0).prepend(' <i class="fa fa-hand-o-down"></i>')
			        break;
			     case '3': $('.tree li').eq(li_index).children('span').eq(0).prepend(' <i class="fa fa-hand-o-left"></i>')
			        break;
			     case '4': $('.tree li').eq(li_index).children('span').eq(0).prepend(' <i class="fa fa-hand-o-right"></i>')
			        break;
			     case '5': $('.tree li').eq(li_index).children('span').eq(0).prepend(' <i class="fa fa-hand-rock-o"></i>')
			        break;
			     case '6': $('.tree li').eq(li_index).children('span').eq(0).prepend(' <i class="fa fa-hand-stop-o"></i>')
			        break;
			     default: 
			} 
			//状态判断
			var status_value = $('#nodeModal input:radio[name="status"]:checked').val();
			if(status_value == 0){//隐藏
				$('.tree li').eq(li_index).children('span').eq(0).css('background-color','#eee');
			}else{//显示
				$('.tree li').eq(li_index).children('span').eq(0).css('background-color','#1ab394');
			}
			
			//存储值
			var id = $('.tree li').eq(li_index).attr('id');
			var pid = $('.tree li').eq(li_index).parents('li').eq(0).attr('id');
			if(!pid) pid = -1;//无父节点即起始点	
			var nodeValue = {id:id, pid:pid, name:link_name, action:action_value, status:status_value, link:$('#link').val(), img:$('#img').val()};
			$('.tree li').eq(li_index).children('input').eq(0).val(JSON.stringify(nodeValue));	
			
			//去除起始节点动作图标
			$('.tree li').eq(0).children('span').eq(0).children('i[class*="fa fa-hand"]').remove();
			
			//获取音频时长显示到页面
			$("#audio_duration").attr('src', $('#link').val())
			setTimeout(function(){
				var audio = document.getElementById("audio_duration");
				var durations = ' | '+parseInt(audio.duration)+"秒"
				var hh = '<abbr class="duration">'+durations+'</abbr>'
				$('.tree li').eq(li_index).children('span').eq(0).children('abbr').remove();
				$('.tree li').eq(li_index).children('span').eq(0).append(hh);
			}, 3500);
			
			$("#nodeModal").modal("hide");
		}
	</script>
	
	
	<!-- 添加节点 -弹窗-->
	<div class="modal fade" data-backdrop="static" id="nodeModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<input type='hidden' id="li_index_hidden">
					<button data-dismiss="modal" class="close" type="button">×</button>
					<h3 class="modal-title" style="font-size:14px;color:#1ab394;padding:0px 70px 0px 20px;">
						小节信息设置</h3>
				</div>
				<div class="modal-body" style="margin-left:12px;">
					<form id="inputForm" class="">
						<fieldset>
						<div class="form-group">
							<label class="col-xs-12 col-sm-3 col-md-2 control-label"><span
								class="require"><font color="red">* </font></span>素材：</label>
								<div class="col-sm-9 col-xs-12" style="margin-bottom:50px">
									<input id="link" name="link" maxlength="500" class="form-control required" readOnly/>
									<sys:ckfinder input="link" type="files" uploadPath="/material" selectMultiple="false" maxWidth="220" maxHeight="220"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-12 col-sm-3 col-md-2 control-label"><span
								class="require"><font color="red">* </font></span>背景：</label>
								<div class="col-sm-9 col-xs-12" style="margin-bottom:50px">
									<input id="img" name="img" maxlength="500" class="form-control required" readOnly/>
									<sys:ckfinder input="img" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="220" maxHeight="220"/>
							</div>
						</div>
					<div id="status_div">	
						<div class="form-group">
							<label class="col-xs-12 col-sm-3 col-md-2 control-label"><span
								class="require"><font color="red">* </font></span>动作：</label>
								<div class="col-sm-9 col-xs-12" style="margin-bottom:50px">
									<div class="i-checks">
							<input type="radio" class="check" name="action"
								data-radio="iradio_square-green" value="1" checked> <label
								for="status1">上滑</label>
								<input type="radio" class="check" name="action"
								data-radio="iradio_square-green" value="2"> <label
								for="status2">下滑</label>
								<input type="radio" class="check" name="action"
								data-radio="iradio_square-green" value="3"> <label
								for="status3">左滑</label>
								<input type="radio" class="check" name="action"
								data-radio="iradio_square-green" value="4"> <label
								for="status4">右滑</label>
								<input type="radio" class="check" name="action"
								data-radio="iradio_square-green" value="5"> <label
								for="status5">双击</label>
								<input type="radio" class="check" name="action"
								data-radio="iradio_square-green" value="6"> <label
								for="status6">摇晃</label>
						</div>
								<!-- <span class="help-block">
								</span> -->
							</div>
						</div>	
						<div class="form-group">
							<label class="col-xs-12 col-sm-3 col-md-2 control-label"><span
								class="require"><font color="red">* </font></span>状态：</label>
								<div class="col-sm-9 col-xs-12 ">
									<div class="i-checks">
							<input type="radio" class="check" name="status"
								data-radio="iradio_square-green" checked value="1"> <label
								for="status1">显示</label> 
								<input type="radio" class="check" name="status"
								data-radio="iradio_square-green" value="0"> <label
								for="status2">隐藏</label>
						</div>
							</div>
						</div>
					</div>	
						</fieldset>
					</form>			
				</div>
				<div class="modal-footer">	
					<button type="button" class="btn btn-success" onclick="modalConfirm()">确 定</button>	
					<button aria-hidden="true" data-dismiss="modal" class="btn btn-default" type="button">取 消</button>					
				</div>
			</div>
		</div>
	</div>
	
	<!-- 背景音乐设置 -弹窗-->
	<div class="modal fade" data-backdrop="static" id="musicModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button">×</button>
					<h3 class="modal-title" style="font-size:14px;color:#1ab394;padding:0px 70px 0px 20px;">
						背景音乐设置</h3>
				</div>
				<div class="modal-body" style="margin-left:12px;">
					<form id="musicForm" class="">
						<fieldset>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody id="musicTbody">
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>节点：</label></td>
					<td class="width-35">
						<input style="width:50px;float:left" name="node1" maxlength="2" class="form-control required"/> ~ 
						<input style="width:50px;float:right" name="node2" maxlength="2" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">* </font>音乐：</label></td>
					<td class="width-35">
						<select class="form-control required">
							 <option value="">==选择音乐==</option>
							 <c:forEach items="${musicList}" var="aMusic">
							 	<option value='${aMusic.link}'>${aMusic.title}</option>
							 </c:forEach>
						</select>
					</td>
					<td class="width-35">
						<a onclick="javascript:$(this).parent().parent().remove()" class="btn btn-white btn-sm" title="删除音乐"><i class="fa fa-times-circle"></i> 删除</a>
					</td>
				</tr>
		 	</tbody>		 	
		</table>
		<a onclick="addMusic()" class="btn btn-white btn-sm" data-placement="left" title="添加故障明细"><i class="fa fa-plus"></i> 添加</a>
						</fieldset>
					</form>			
				</div>
				<div class="modal-footer">	
					<button type="button" class="btn btn-success" onclick="musicConfirm()">确 定</button>	
					<button aria-hidden="true" data-dismiss="modal" class="btn btn-default" type="button">取 消</button>					
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//点击音乐设置按钮弹窗
		function musicSet(){
			$("#musicModal").modal("show");
		}
		
		 //添加故障明细输入框
		 function addMusic(){
			 var html = '<tr>'+
							'<td class="width-15 active"><label class="pull-right"><font color="red">* </font>节点：</label></td>'+
							'<td class="width-35">'+
								'<input style="width:50px;float:left" name="node1" maxlength="2" class="form-control required"/> ~ '+
								'<input style="width:50px;float:right" name="node2" maxlength="2" class="form-control required"/>'+
							'</td>'+
							'<td class="width-15 active"><label class="pull-right"><font color="red">* </font>音乐：</label></td>'+
							'<td class="width-35">'+
								'<select class="form-control required">'+
									 '<option value="">==选择音乐==</option>'+
									 '<c:forEach items="${musicList}" var="aMusic">'+
									 	'<option value="${aMusic.link}">${aMusic.title}</option>'+
									 '</c:forEach>'+
								'</select>'+
							'</td>'+
							'<td class="width-35">'+
								'<a onclick="javascript:$(this).parent().parent().remove();" class="btn btn-white btn-sm" title="删除音乐"><i class="fa fa-times-circle"></i> 删除</a>'+
							'</td>'+
						'</tr>';
			if ($('#musicTbody tr').length>= 30) {
				layer.msg('最多只能添加10条背景音乐！');
				return false;
			}			
			$("#musicTbody").append(html);			
						
		 }
		 
		//背景音乐设置保存
		function musicConfirm() {
			 if(validateForm.form()){
				  //获取故障明细信息
				  var length = $("#musicTbody tr").length; 
				  var musicArray = new Array();
					var musics = null;
					for(var i=0;i<length;i++){
						var node1 = $("#musicTbody tr:eq("+i+") input").eq(0).val();
						var node2 = $("#musicTbody tr:eq("+i+") input").eq(1).val();
						if(node1 > node2){layer.msg('第'+(i+1)+'条节点序数中后者不能小于前者'); return;}
						var music = $("#musicTbody tr:eq("+i+") select option:selected").val();
						var title = $("#musicTbody tr:eq("+i+") select option:selected").text();
						if(node1==""|| node2=="" || music=="" || title==""){
							continue;
						}
						musicArray[i] = [node1,node2,music,title];
					}
					var musics = {"musics":musicArray};
					var musicsJSONString = JSON.stringify(musics);
				  layer.confirm('是否确认提交信息？', {closeBtn: 0, icon : 3 ,skin : 'layui-layer-molv',btn: ['确认','取消'] //按钮
					}, function(){
						$.ajax({
							url : "${ctx}/audio/chapter/musicSet",
							type : "POST",
							dataType : "json",
							data : {chapterId: '${aChapter.chapterId}', musics:musicsJSONString},
							success : function(result) {
								if (result.success == true) {
									layer.msg('提交数据成功！');
									if(result.body.aChapter.musics){
										location.reload()
										 $("#musicModal").modal("hide");
									}
								}
							},
							error : function(xhr, type, errorThrown) {
								layer.msg('系统出错啦！');
							}
						});
					});
				  return true;
				  $("#musicModal").modal("hide");
			  }	
			 return false;
		}
		
		//页面加载时音乐设置数据解析
		function musicsParse(musics){
			var musicsJSON = JSON.parse(musics);
			var length = musicsJSON.musics.length;	
			$('#musicTbody').empty();
			for(var i=0;i<length;i++){
				 var html = '<tr>'+
					'<td class="width-15 active"><label class="pull-right"><font color="red">* </font>节点：</label></td>'+
					'<td class="width-35">'+
						'<input value="'+musicsJSON.musics[i][0]+'" style="width:50px;float:left" name="node1" maxlength="2" class="form-control required"/> ~ '+
						'<input value="'+musicsJSON.musics[i][1]+'" style="width:50px;float:right" name="node2" maxlength="2" class="form-control required"/>'+
					'</td>'+
					'<td class="width-15 active"><label class="pull-right"><font color="red">* </font>音乐：</label></td>'+
					'<td class="width-35">'+
						'<select class="form-control required">'+
							 '<option value="'+musicsJSON.musics[i][2]+'" selected="selected">'+musicsJSON.musics[i][3]+'</option>'+
							 '<option value="">==选择音乐==</option>'+
							 '<c:forEach items="${musicList}" var="aMusic">'+
							 	'<option value="${aMusic.link}" <c:if test="${aMusic.title eq \"'+musicsJSON.musics[i][3]+'\"}">selected="selected"</c:if>>${aMusic.title}</option>'+
							 '</c:forEach>'+
						'</select>'+
					'</td>'+
					'<td class="width-35">'+
						'<a onclick="javascript:$(this).parent().parent().remove();" class="btn btn-white btn-sm" title="删除音乐"><i class="fa fa-times-circle"></i> 删除</a>'+
					'</td>'+
				'</tr>';
				 $('#musicTbody').append(html);				
			 }
		}
		
	</script>
</body>
</html>