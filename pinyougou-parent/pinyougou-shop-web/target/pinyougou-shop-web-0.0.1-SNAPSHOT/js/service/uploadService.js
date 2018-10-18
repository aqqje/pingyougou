//文件上传服务层
app.service("uploadService", function($http){
	this.uploadFile=function(){
		var formData = new formData();
		formData.append("file",file.files[0]); // file 文件上传框的name
	    return $http({
	    	method: 'POST',
	    	url: '../upload.do',
	    	data: formData,
	    	headers: {'Content-Type':undefind},
	    	transformRequest: angular.identity
	    });
	}
});
