//搜索控制层
app.controller("searchController",function($scope,searchSerivce){
	$scope.search=function(){
		searchSerivce.search($scope.searchMap).success(function(response){
			$scope.resultMap = response;
		});
	}
});