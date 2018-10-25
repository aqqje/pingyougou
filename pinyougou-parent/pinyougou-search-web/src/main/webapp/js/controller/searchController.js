// 搜索控制层
app.controller("searchController",function($scope,searchService){
	
	$scope.search=function(){
		searchService.search($scope.searchMap).success(function(response){
			$scope.resultMap = response;
		});
	}
});