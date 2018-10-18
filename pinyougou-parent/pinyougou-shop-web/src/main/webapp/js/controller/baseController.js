app.controller("baseController",function($scope){
	
	// 分页配置
	$scope.paginationConf={
			currentPage: 1,
		 	totalItems: 10,
		 	itemsPerPage: 10,
		 	perPageOptions: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
		 	onChange: function(){
		 		$scope.reloadList();
		}
	};
	
	// 刷新数据列表 
	$scope.reloadList=function(){
		//切换页码
		$scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
	}
	
	// 复选框选中ID的集合
	$scope.selectIds=[];
	// 更新复选框
	$scope.updateSelectiont=function($event, id){
		if($event.target.checked){// 如果被选中<-->添加到selectIds集合中
			$scope.selectIds.push(id);
			console.log($scope.selectIds);
		}else{
			var idx=$scope.selectIds.indexOf(id);
			$scope.selectIds.splice(idx, 1);// 删除
		}
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
	
	//在list集合中根据某key的值查询对象
//    $scope.searchObjectByKey=function(list,key,keyValue){
//    	for(var i=0; i<list.length; i++){
//    		if(list[i][key]==keyValue){
//    			return list[i];
//    		}
//    	}
//    	return null;
//    }
    
    
    
    
    
    $scope.searchObjectByKey=function(list,key,keyValue){
    	for(var i=0; i<list.length; i++){
    		if(list[i][key]==keyValue){
    			return list[i];
    		}
    	}
    	return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
});