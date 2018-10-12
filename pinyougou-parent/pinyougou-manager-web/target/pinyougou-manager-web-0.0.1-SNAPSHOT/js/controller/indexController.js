app.controller("indexController",function($scope,loginService){
	 $scope.SetIFrameHeight=function(){
	  	  var iframeid=document.getElementById("iframe"); //iframe id
	  	  if (document.getElementById){
	  		iframeid.height =document.documentElement.clientHeight;			   	   
		  }
	 }
	 //获取登录名
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