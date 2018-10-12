 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller,typeTemplateService,brandService,specificationService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		typeTemplateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		typeTemplateService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(
			function(response){
				$scope.entity= response;	
				//字符串转为JSON数据
				$scope.entity.specIds= JSON.parse($scope.entity.specIds);
				$scope.entity.brandIds= JSON.parse($scope.entity.brandIds);
				$scope.entity.customAttributeItems= JSON.parse($scope.entity.customAttributeItems);
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTemplateService.update( $scope.entity ); //修改  
		}else{
			serviceObject=typeTemplateService.add( $scope.entity  );//增加 
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
			typeTemplateService.dele($scope.selectIds).success(
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
		typeTemplateService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	//下拉品牌列表数据 
    $scope.brandList={data:[]};
    
    //读取品牌列表
    $scope.findBrandOptionList=function(){
    	brandService.selectOptionList().success(
    		function(response){
    			$scope.brandList={data:response};
    		}
    	);
    }
    //下拉规格列表数据 
    $scope.specificationList={data:[]};
    //读取规格列表
    $scope.findSpecOptionList=function(){
    	specificationService.findOptionList().success(
    		function(response){
    			$scope.specificationList={data:response};
    		}
    	);
    }
    //页面初始化
    $scope.pageInit=function(){
    	$scope.findSpecOptionList();
    	$scope.findBrandOptionList();
    }
    //列表初始化
    $scope.optionInit=function(){
    	$scope.entity.brandIds=[];
    	$scope.entity.specIds=[];
    	$scope.entity={customAttributeItems:[]};
    }
    
    //添加扩展属性列
    $scope.entity={customAttributeItems:[]};
    $scope.addTableRow=function(){
    	$scope.entity.customAttributeItems.push({});
    }
    //删除扩展属性列表
    $scope.deleTableRow=function(index){
    	$scope.entity.customAttributeItems.splice(index,1);
    }
    //JSON转字符串
    $scope.jsonToString=function(jsonString, key){
    	var json=JSON.parse(jsonString);
    	var value="";
    	for(var i=0; i<json.length; i++){
    		if(i>0){
    			value+=", ";
    		}
    		value+=json[i][key];
    	}
    	return value;
    }
});	
