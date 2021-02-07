<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>小区未被秒杀菜园信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var selectedFarmArray1 = new Array();
			var selectedFarmArray2 = new Array();
			var selectedFarmArray3 = new Array();
			var selectedFarmArray4 = new Array();
			selectedFarmArray1 = selectedFarmArray(1);
			selectedFarmArray2 = selectedFarmArray(2);
			selectedFarmArray3 = selectedFarmArray(3);
			selectedFarmArray4 = selectedFarmArray(4);
			var arr = [selectedFarmArray1.length,selectedFarmArray2.length,selectedFarmArray3.length,selectedFarmArray4.length];

			var length = 46-Math.min.apply(null, arr);
			for(var i=0;i<length;i++){
				$("tbody").append('<tr><td></td><td></td><td></td><td></td></tr>')
			}
			
			var arr1 = getArrDifference(selectedFarmArray1,farmArry1);
			var arr2 = getArrDifference(selectedFarmArray2,farmArry2);
			var arr3 = getArrDifference(selectedFarmArray3,farmArry3);
			var arr4 = getArrDifference(selectedFarmArray4,farmArry4);
			$("thead th span").eq(0).text('（'+arr1.length+'）');
			$("thead th span").eq(1).text('（'+arr2.length+'）')
			$("thead th span").eq(2).text('（'+arr3.length+'）')
			$("thead th span").eq(3).text('（'+arr4.length+'）')
			for(var i=0;i<arr1.length;i++){
				$("tbody tr").eq(i).children('td').eq(0).text(arr1[i])
			}
			for(var i=0;i<arr2.length;i++){
				$("tbody tr").eq(i).children('td').eq(1).text(arr2[i])
			}
			for(var i=0;i<arr3.length;i++){
				$("tbody tr").eq(i).children('td').eq(2).text(arr3[i])
			}
			for(var i=0;i<arr4.length;i++){
				$("tbody tr").eq(i).children('td').eq(3).text(arr4[i])
			}
			
		});
		
		var farmArry1 = ['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20',
						'21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40',
						'41','42','43','44','45','46'];
		var farmArry2 = ['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20',
			'21','22','23','24','25','26','27','28','29','30','31','32'];
		var farmArry3 = ['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20',
			'21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38'];
		var farmArry4 = ['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20',
			'21','22','23','24','25','26','27','28','29','30','31','32','33','34'];
		//获取各个小区已被秒杀菜园编号数组
		function selectedFarmArray(community){
			var array = new Array();
			$.ajax({
				url : "https://wu.stephen7.top/Showhand/a-api/farm/list",
				type : "POST",
				dataType : "json",
				async: false,
				data : {community: community, filter:"1"},
				success : function(result) {
					if (result.success == true) {
						var list = result.body.list;
						if(list.length>0){
							for(var i=0; i<list.length; i++){
								if(list[i].farm){
									array[i] = list[i].farm;
								}
							}
						}	
					}
				},
				error : function(xhr, type, errorThrown) {
					layer.msg('系统出错啦！');
				}
			});
			return array;
		}
		
		//获取两个数组不同元素
		function getArrDifference(arr1, arr2) {
		    return arr1.concat(arr2).filter(function(v, i, arr) {
		      return arr.indexOf(v) === arr.lastIndexOf(v);
		    });
		  }
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox">
	<div class="ibox-title">
		<h5>菜园未被秒杀编号列表 </h5>
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
    
    <div class="ibox-content">
	<sys:message content="${message}"/>	
	<!-- 工具栏 和 查询条件-->
	<div class="row">
	<div class="col-sm-12">
	</div>
	</div>
	
	<!-- 表格 -->
	<table class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th class="sort-column">大河宸章<span></span></th>
				<th class="sort-column">上河宸章<span></span></th>
				<th class="sort-column">上塘宸章<span></span></th>
				<th class="sort-column">远洋香奈<span></span></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>