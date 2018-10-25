//搜索服务层
app.service("searchService", function($http){
	this.search=function(searchMap){
		return $http.get("itemsearch/search.do", searchMap);
	}
});