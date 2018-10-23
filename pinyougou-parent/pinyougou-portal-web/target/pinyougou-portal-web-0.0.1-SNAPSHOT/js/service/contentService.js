//服务层
app.service('contentService',function($http){    	   
	//查询广告根据分类ID
	this.findByCategoryId=function(categoryId){
		return $http.get("content/findByCategoryId.do?categoryId="+categoryId);
	}
});
