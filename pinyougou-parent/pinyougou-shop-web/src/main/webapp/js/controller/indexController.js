app.controller("indexController",function($scope,loginService){
	//登录用户名
	$scope.getLoginName=function(){
		loginService.getLoginName().success(
			function(response){
				$scope.loginName=response.loginName;
			}
		);
	}

	//页面初始化
	$scope.pageInit=function(){
		$scope.getLoginName();
	}
});