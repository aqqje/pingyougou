//搜索控制层
app.controller("searchController",function($scope,searchSerivce){
	//搜索
	$scope.search=function(){
		searchSerivce.search($scope.searchMap).success(function(response){
			$scope.resultMap = response;
		});
	}
	
	$scope.searchMap={'keywords':'','category':'','brand':'','spec':{}} // 搜索对象
	
	//添加搜索选项
	$scope.addSearchItem=function(key,keyValue){
		if(key=='category'||key=='brand'){
			$scope.searchMap[key]=keyValue;
		}else{
			$scope.searchMap.spec[key]=keyValue;
		}
		$scope.search();//执行搜索
	}
	//移除搜索选项
	$scope.removeSearchItem=function(key){
		if(key=='category'||key=='brand'){
			$scope.searchMap[key]='';
		}else{
			delete $scope.searchMap.spec[key];
		}
		$scope.search();//执行搜索
	}
});