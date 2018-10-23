 //控制层 
app.controller('contentCategoryController' ,function($scope,$controller,contentCategoryService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		contentCategoryService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		contentCategoryService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		contentCategoryService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=contentCategoryService.update( $scope.entity ); //修改  
		}else{
			serviceObject=contentCategoryService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除
	$scope.dele=function(){
		if($scope.selectIds == false){
			alert("您还没选择呢！");
			return false;
		}
		if(confirm("确认要删除？")){
			contentCategoryService.dele($scope.selectIds).success(
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
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		contentCategoryService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
});	
