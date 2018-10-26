// 搜索服务层
app.service("searchSerivce",function($http){
	this.search=function(searchMap){
		return $http.post("itemSearch/search.do",searchMap);
	}
});