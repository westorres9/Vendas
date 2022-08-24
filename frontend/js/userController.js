vendasApp.controller('userController', (function ($scope, $http, AuthService) {
	var url = "http://localhost:8080/users";

	const token = AuthService.getToken();

	
	$http.get(url,
	{
		headers: {
			'Authorization': 'Bearer ' +  token.access_token
		}
	})
		.then(function (response) {
			$scope.users = response.data;
			console.log(response)
		}).catch(function (response) {
			$scope.response = 'ERROR: ' + response.status;
		})

	$scope.user = $scope.users;
	$scope.SelectUser = function (user) {
		$scope.user = user;
	}

	$scope.InsertUser = function (user) {
		$http.post(url,
			{
				headers: {
					'Authorization': 'Bearer ' +  token.access_token
				}
			}, { user })		
			.then(function (response) {
				$scope.users = response;
				delete $scope.user;
				$scope.GetAllUsers();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}

	$scope.UpdateUser = function (user) {
		$http.put(`http://localhost:8080/users/${user.id}`, {
			headers: {
				'Authorization': 'Bearer ' +  token.access_token
			}
		}, { user })
			.then(function (response) {
				$scope.users = response;
				delete $scope.user;
				$scope.GetAllUsers();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}

	$scope.DeleteUser = function (user) {
		$http.delete(`http://localhost:8080/users/${$scope.user.id}`, { user })
			.then(function (response) {
				$scope.users = response;
				delete $scope.user;
				$scope.GetAllUsers();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}
})
);