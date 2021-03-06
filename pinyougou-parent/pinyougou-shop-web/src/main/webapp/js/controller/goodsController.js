 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location,goodsService,uploadService,itemCatService,typeTemplateService){	
	
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
	$scope.findOne=function(){			
		var id = $location.search()["id"];//获取参数值
		if(id==null){
			return null;
		}
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;	
				//向富文本添加信息
				editor.html($scope.entity.goodsDesc.introduction);
				//显示图片
				$scope.entity.goodsDesc.itemImages=  
					JSON.parse($scope.entity.goodsDesc.itemImages);
				//显示扩展列表 
				$scope.entity.goodsDesc.customAttributeItems=  JSON.parse($scope.entity.goodsDesc.customAttributeItems);
				//规格				
				$scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);	
				//SKU列表规格列转换				
				for( var i=0;i<$scope.entity.itemList.length;i++ ){
					$scope.entity.itemList[i].spec = JSON.parse( $scope.entity.itemList[i].spec);		
				}	

			}
		);				
	}
	
	//根据规格名称和选项名称返回是否被勾选
	$scope.checkAttributeValue=function(specName,optionName){
		var items= $scope.entity.goodsDesc.specificationItems;
		var object= $scope.searchObjectByKey(items,'attributeName',specName);
		if(object==null){
			return false;
		}else{
			if(object.attributeValue.indexOf(optionName)>=0){
				return true;
			}else{
				return false;
			}
		}			
	}

	
	
	//保存 
	$scope.save=function(){			
		//提取文本编辑器的值
		$scope.entity.goodsDesc.introduction=editor.html();	
		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					alert('保存成功');	
					location.href="goods.html";//跳转到商品列表页
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
    
	 $scope.entity={goodsDesc:{itemImages:[],specificationItems:[]}};//定义页面实体结构
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
	
	/**
	 * 一级分类
	 */
	$scope.selectItemcatList=function(){
		itemCatService.findByParentId(0).success(function(response){
			$scope.Itemcat1List=response;
		});
	}
	/**
	 * 二级分类
	 */
	$scope.$watch("entity.goods.category1Id", function(newValue,oleValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.Itemcat2List=response;
		});
	});
	/**
	 * 二级分类
	 */
	$scope.$watch("entity.goods.category2Id", function(newValue,oleValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.Itemcat3List=response;
		});
	});
	/**
	 * 模版ID
	 */
	$scope.$watch("entity.goods.category3Id", function(newValue,oleValue){
		itemCatService.findOne(newValue).success(function(response){
			$scope.entity.goods.typeTemplateId=response.typeId;
		});
	});
	/**
	 * 品牌,规格下拉列表
	 */
	$scope.$watch("entity.goods.typeTemplateId", function(newValue,oleValue){
		//品牌列表
		typeTemplateService.findOne(newValue).success(function(response){
			$scope.typeTemplate=response;
			$scope.typeTemplate.brandIds=JSON.parse( $scope.typeTemplate.brandIds );
			//扩展属性
			if($location.search()['id']==null){
				$scope.entity.goodsDesc.customAttributeItems=JSON.parse( $scope.typeTemplate.customAttributeItems );
			}
		});
		//规格列表
		typeTemplateService.findSpecList(newValue).success(function(response){
			$scope.specList=response;
		});
	});
 	//规格列表
	
	$scope.updateSpecAttribute=function($event,name, value){
		var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,"attributeName", name);
		if(object != null){//存在 attributeValue 对象
			if($event.target.checked){//勾选
				object.attributeValue.push(value);				
			}else{
				object.attributeValue.splice(object.attributeValue.indexOf(value), 1)//移除选项
				if(object.attributeValue.length==0){
					$scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object), 1);
				}
			}
		}else{//不存在
			$scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]})
		}
	}
	
	//创建SKU列表
	$scope.createItemList=function(){
		$scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0'}];//初始化
		var items = $scope.entity.goodsDesc.specificationItems;
		for(var i=0;i<items.length;i++){
			$scope.entity.itemList=addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue)
		}
	}
	addColumn=function(list,columnName,columnValues){
		var newList=[];//新列
		for(var i=0; i<list.length; i++){
			var oldRow=list[i];
			for(var j=0; j<columnValues.length; j++){
				var newRow = JSON.parse( JSON.stringify( oldRow ) ); // 深克隆
				newRow.spec[columnName]=columnValues[j];
				newList.push(newRow);
			}
		}
		return newList;
	}
	//商品状态 0：未审核 ，1：已审核，2：审核未通过，3：关闭
	$scope.status=['未审核','已审核','审核未通过','关闭'];
	
	$scope.itemCatList=[];//商品分类列表
	$scope.findItemCatList=function(){
		itemCatService.findAll().success(function(response){
			for(var i=0; i<response.length; i++){
				$scope.itemCatList[response[i].id]=response[i].name;
			}
		});
	}
});	
