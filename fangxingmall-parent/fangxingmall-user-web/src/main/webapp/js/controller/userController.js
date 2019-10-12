 //控制层 
app.controller('userController' ,function($scope,$controller   ,userService){	
	
	//注册用户
	$scope.reg=function(){
		
		//比较两次输入的密码是否一致
		if($scope.password!=$scope.entity.password){
			alert("两次输入密码不一致，请重新输入");
			$scope.entity.password="";
			$scope.password="";
			return ;			
		}
		//新增短信注册 
//		userService.add($scope.entity,$scope.smscode).success(
//			function(response){
//				alert(response.message);
//			}		
//		);
		//新增邮箱注册
		userService.addByEmail($scope.entity,$scope.emailcode).success(
			function(response){
				alert(response.message);
			}		
		);
		
	}
    
	//发送验证码
	$scope.sendCode=function(){
		if($scope.entity.phone==null || $scope.entity.phone==""){
			alert("请填写手机号码");
			return ;
		}
		
		userService.sendCode($scope.entity.phone  ).success(
			function(response){
				alert(response.message);
			}
		);		
	}
	
	//发送邮箱验证码
	$scope.sendEmailCode=function(){
		if($scope.entity.email==null || $scope.entity.email==""){
			alert("请填写邮箱");
			return ;
		}
		
		userService.sendEmailCode($scope.entity.email  ).success(
			function(response){
				alert(response.message);
			}
		);		
	}
	
});	
