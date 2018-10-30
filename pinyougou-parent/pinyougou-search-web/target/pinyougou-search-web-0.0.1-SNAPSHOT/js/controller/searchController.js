//搜索控制层
app.controller("searchController",function($scope,$location,searchSerivce){
	//搜索
	$scope.search=function(){
		$scope.searchMap.pageNo = parseInt($scope.searchMap.pageNo);
		searchSerivce.search($scope.searchMap).success(function(response){
			$scope.resultMap = response;
			buildPageLabel();//构建分页栏
		});
	}
	
	$scope.searchMap={'keywords':'','brand':'','category':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sortField':'','sort':''} // 搜索对象
	
	//添加搜索选项
	$scope.addSearchItem=function(key,keyValue){
		if(key=='category'||key=='brand'||key=='price'){
			$scope.searchMap[key]=keyValue;
		}else{
			$scope.searchMap.spec[key]=keyValue;
		}
		$scope.search();//执行搜索
	}
	//移除搜索选项
	$scope.removeSearchItem=function(key){
		if(key=='brand'||key=='category'||key=='price'){
			$scope.searchMap[key]='';
		}else{
			delete $scope.searchMap.spec[key];
		}
		$scope.search();//执行搜索
	}
	//构建分页栏 
	buildPageLabel=function(){
		$scope.pageLable = [];
		var maxPageNo=$scope.resultMap.totalPages;//分页总数
		var firstPage=1;//开始页
		var lastPage=maxPageNo;//截止页
		$scope.firstDot=true;//前面有点
		$scope.lastDot=true;//后面有点
		if(maxPageNo>5){//如果总分页数大于5，部分显示
			if($scope.searchMap.pageNo<=3){//当前页小于等于3（不足5页）
				lastPage=5;//显示前5页
				$scope.firstDot=false;//前面没点
			}else if($scope.searchMap.pageNo > maxPageNo -2){//最后几页（不足5页）
				firstPage=maxPageNo-4;//显示后5页
				$scope.lastDot=false;//后面没点
			}else{ //当前页处于中间
				firstPage = $scope.searchMap.pageNo - 2;
				lastPage = $scope.searchMap.pageNo + 2;
			}
		}else{//总分页不足5页
			$scope.firstDot=false;//前面没点
			$scope.lastDot=false;//后面没点
		}
		for(var i=firstPage; i<=lastPage; i++){
			$scope.pageLable.push(i);
		}
	}
	
	//页码不可用样式
	$scope.isTopPage=function(){
		//判断是不是首页
		if($scope.searchMap.pageNo==1){
			return true;
		}else{
			return false;
		}
	}
	$scope.isEndPage=function(){
		//判断是不是尾页
		if($scope.searchMap.pageNo==$scope.resultMap.totalPages){
			return true;
		}else{
			return false;
		}
	}
	
	//提交页码查询
	$scope.queryByPage=function(pageNo){
		//页码验证
		if(pageNo<1||pageNo>$scope.resultMap.totalPages){
			return;
		}
		$scope.searchMap.pageNo=pageNo;
		$scope.search();
	}
	//排序搜索
	$scope.sortSearch=function(sortField,sort){
		$scope.searchMap.sort = sort;
		$scope.searchMap.sortField = sortField;
		$scope.search();//执行搜索
	}
	//隐藏非关键字品牌
	$scope.keywordsIsBrand=function(){
		for(var i=0; i<$scope.resultMap.brandList.size(); i++){
			if($scope.searchMap.keywords.indexOf(scope.resultMap.brandList[i].text)>=0){//如果包含返回
				return true;
			}
			return false;
		}
	}
	//接收首页搜索参数并进行搜索
	$scope.keyLocation=function(){
		$scope.searchMap.keywords=$location.search()['keywords'];
		$scope.search();//执行搜索
	}
});