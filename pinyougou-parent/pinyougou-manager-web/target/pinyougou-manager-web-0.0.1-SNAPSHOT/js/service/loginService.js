app.service("loginService",function($http){
	this.getLoginName=function(){
		return $http("../login/getLoginName.do");
	}
});