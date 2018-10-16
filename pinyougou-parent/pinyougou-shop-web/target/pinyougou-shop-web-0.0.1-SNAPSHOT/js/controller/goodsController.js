 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService,uploadService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	
	
	//保存 
	$scope.add=function(){			
		alert("11111111")
		$scope.entity.goodsDesc.introduction=editor.html();
		goodsService.add($scope.entity).success(
			function(response){
				if(response.success){
					alert("保存成功");
					//重新查询 
		        	$scope.reloadList();//重新加载
		        	//清空
		        	$scope.entity={};
		        	editor.html("");
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	/**
	 * 上传文件
	 */
	$scope.uploadFile=function(){
		uploadService.uploadFile().success(function(response){
			if(response.success){//如果上传成功获取image_url
				$scope.image_entity.url=response.message;
			}else{
				alert(response.message);
			}
		}).error(function(){
			alert("上传发生错误");
		});
	}
    
	 $scope.entity={goods:{},goodsDesc:{itemImages:[]}};//定义页面实体结构
	/**
	 * 添加图片列表
	 */
	$scope.add_image_item=function(){
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity)
	}
	/**
	 * 列表中移除图片
	 */
	$scope.remove_image_item=function(index){
		$scope.entity.goodsDesc.itemImages.splice(index, 1);
	}
});	
