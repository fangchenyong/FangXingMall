app.controller("baseController",function($scope){
	//分页控件
	$scope.paginationConf = {
		currentPage: 1,
		totalItems: 10,
		itemsPerPage: 10,
		perPageOptions: [10, 20, 30, 40, 50],
		onChange: function(){
			$scope.reloadList();
		}
	};
	//刷新列表
	$scope.reloadList = function(){
		$scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage)
	};
	//用户勾选的
	$scope.selectIds = [];
	$scope.updateSelection = function($event,id){
		if($event.target.checked==true){
			$scope.selectIds.push(id);
		}else{
			var index = $scope.selectIds.indexOf(id);
			$scope.selectIds.splice(index,1)
		}
	};
	//提取json字符串数据中某个属性，返回拼接字符串 逗号分隔
	$scope.jsonToString=function(jsonString,key){
		var json=JSON.parse(jsonString);//将json字符串转换为json对象
		var value="";
		for(var i=0;i<json.length;i++){		
			if(i>0){
				value+=",";
			}
			value+=json[i][key];			
		}
		return value;
	}

});