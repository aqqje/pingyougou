// 品牌控制层（brandController）
app.controller("brandController", function($scope,$controller,brandService){
	
	$controller("baseController",{$scope:$scope});
	
	// 获取品牌列表数据
	$scope.findAll=function(){
		brandService.findAll().success(
			function(response){
				$scope.list=response;
			}
		);
	}
	// 获取分页数据
	$scope.findPage=function(page, rows){
		brandService.findPage(page, rows).success(
			function(response){
				$scope.list=response.rows; // 每页数据实体
				$scope.paginationConf.totalItems=response.total;// 更新数据总记录数
			}		
		);
	}

	
	// 保存（新增与修改）
	$scope.save=function(){
		var object=null;
		if($scope.entity.id != null){
			object=brandService.update($scope.entity); // 修改
		}else{
			object=brandService.add($scope.entity); // 新增
		}
		// 发送add请求
		object.success(
			function(response){
				if(response.success){// 成功
					$scope.reloadList(); // 刷新品牌列表
					alert(response.message);
				}else{// 失败
					alert(response.message);
				}
			}		
		);
		// 获取信息并判断是否成功
	}
	
	// 根据ID查询
	$scope.findOne=function(id){
		brandService.findOne(id).success(
			function(response){
				$scope.entity=response;
			}		
		);
	}
	// 批量删除品牌
	$scope.dele=function(){
		if($scope.selectIds == false){
			alert("您还没选择呢！");
			return false;
		}
		if(confirm("确认要删除？")){
			brandService.dele($scope.selectIds).success(
				function(response){
					if(response.success){// 成功
						// 更新数据列表
						$scope.reloadList();
						$scope.selectIds=[];
						alert(response.message);
					}else{//失败
						alert(response.message);
					}
				}
			);
		}
	}
	$scope.searchEntity={}; // 定义搜索对象
	// 条件查询
	$scope.search=function(page,rows){
		$scope.selectIds=[];
		brandService.search(page, rows, $scope.searchEntity).success(
			function(response){
				$scope.list=response.rows; // 每页数据实体
				$scope.paginationConf.totalItems=response.total;// 更新数据总记录数
			}		
		);
	}
});