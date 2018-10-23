 //控制层 
app.controller('contentController' ,function($scope,$controller,contentService,uploadService,contentCategoryService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		contentService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		contentService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		contentService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=contentService.update( $scope.entity ); //修改  
		}else{
			serviceObject=contentService.add( $scope.entity  );//增加 
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
			contentService.dele($scope.selectIds).success(
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
		contentService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//图片上传
	$scope.uploadFile=function(){
		uploadService.uploadFile().success(function(response){
			if(response.success){//如果上传成功获取image_url
				$scope.entity.pic=response.message;
			}else{
				alert(response.message);
			}
		}).error(function(){
			alert("上传发生错误");
		});
	}
    
	$scope.findContentCategoryList=function(){
		contentCategoryService.findAll().success(function(response){
			$scope.contentCategoryList=response;
		});
	}
	//广告状态
	$scope.statusList=['无效','有效']
});	
