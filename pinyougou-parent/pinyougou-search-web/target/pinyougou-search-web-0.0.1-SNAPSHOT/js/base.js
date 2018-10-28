// 品牌模块（pinyougou）
var app=angular.module("pinyougou", []);
//$sce写成过渡器
app.filter('trustHtml',['$sce', function($sce){
	return function(data){
		return $sce.trustAsHtml(data);
	}
}]);
