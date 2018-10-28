 //控制层 
app.controller('specificationController' ,function($scope,$controller,specificationService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		specificationService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		specificationService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		specificationService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	//保存 
	$scope.save=function(){		
		alert("save")
		var serviceObject;//服务层对象  				
		if($scope.entity.specification.id!=null){//如果有ID
			alert("修改 ")
			serviceObject=specificationService.update( $scope.entity ); //修改  
		}else{
			alert("增加 ")
			serviceObject=specificationService.add( $scope.entity  );//增加 
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
		alert("批量删除 ")
		//获取选中的复选框			
		if($scope.selectIds == false){
			alert("您还没选择呢！");
			return false;
		}
		if(confirm("确认要删除？")){
			specificationService.dele($scope.selectIds).success(
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
		$scope.selectIds=[];
		specificationService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	$scope.entity={specificationOptionList:[]};
	//添加规格列表
	$scope.addTableRow=function(){
		$scope.entity.specificationOptionList.push({});
	}
	//删除规格列表
	$scope.deleTableRow=function(index){
		$scope.entity.specificationOptionList.splice(index, 1);
	}
});	
