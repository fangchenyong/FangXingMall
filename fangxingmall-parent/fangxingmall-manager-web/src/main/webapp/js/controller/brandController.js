app.controller("brandController", function($scope, $controller, brandService) {
	//继承
	$controller("baseController",{$scope:$scope});
	
	//查询品牌列表
	$scope.findAll = function() {
		brandService.findAll().success(function(response) {
			$scope.list = response;
		});
	};

	$scope.findPage = function(page, size) {
		brandService.findPage(page, size).success(function(response) {
			$scope.list = response.rows;
			$scope.paginationConf.totalItems = response.total;
		});
	};
	//新增和保存
	$scope.save = function() {
		var object = null;
		if ($scope.entity.id != null) {
			object = brandService.update($scope.entity);
		} else {
			object = brandService.add($scope.entity);
		}
		object.success(function(response) {
			if (response.success) {
				$scope.reloadList();
				alert(response.message);
			} else {
				alert(response.message);
			}
		});
	};

	//查询实体
	$scope.findOne = function(id) {
		brandService.findOne(id).success(function(response) {
			$scope.entity = response;
		});
	};

	//删除
	$scope.dele = function() {
		brandService.dele($scope.selectIds).success(function(response) {
			if (response.success) {
				$scope.reloadList();
				$scope.selectIds = []
				alert(response.message);
			} else {
				alert(response.message);
			}
		});
	};

	$scope.searchEntity = {};
	//条件查询 
	$scope.search = function(page, size) {
		debugger
		brandService.search(page, size, $scope.searchEntity).success(
				function(response) {
					$scope.list = response.rows;//显示当前页数据 	
					$scope.paginationConf.totalItems = response.total;//更新总记录数 
				});

	}
});