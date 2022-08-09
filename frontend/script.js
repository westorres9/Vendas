
var vendasApp = angular.module('vendasApp', ['ngRoute']);

// configure our routes
vendasApp.config(function ($routeProvider) {
	$routeProvider

		// route for the home page
		.when('/', {
			templateUrl: 'pages/home.html',
			controller: 'mainController'
		})

		.when('/login', {
			templateUrl: 'pages/login.html',
			controller: 'loginController'
		})

		// route for the about page
		.when('/teams', {
			templateUrl: 'pages/teams.html',
			controller: 'teamController'
		})

		// route for the contact page
		.when('/sales', {
			templateUrl: 'pages/sales.html',
			controller: 'allSalesController'
		})

		.when("/sales/:id", {
			templateUrl: 'pages/salesdetail.html',
			controller: 'salesByIdController'
		})

		.when('/users', {
			templateUrl: 'pages/user.html',
			controller: 'userController'
		});
});

// create the controller and inject Angular's $scope
vendasApp.controller('mainController', function ($scope) {
	// create a message to display in our view
	$scope.message = 'An Angular Controller injects this text by using $scope. ';
});

vendasApp.controller("loginController", function ($scope, $http, $httpParamSerializerJQLike) {
	$scope.user = { 'grant_type': 'password' };


	$scope.authenticate = function () {
		var CLIENT_ID = 'dsvendas'
		var CLIENT_SECRET = 'dsvendas123'

		$http.post("http://localhost:8080/oauth/token", $httpParamSerializerJQLike($scope.user),
			{
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded',
					'Authorization': 'Basic ' + window.btoa(CLIENT_ID + ':' + CLIENT_SECRET)
				}
			})
			.then(function (response) {
				access_token = response.data.access_token;
				console.log(access_token);
			}).catch(function (response) {
				console.log("Falha" + response.data);
			});
	}
})

vendasApp.controller('teamController', (function ($scope, $http) {
	var url = "http://localhost:8080/teams?format=json";
	$http.get(url)
		.then(function (response) {
			$scope.teams = response.data;
			console.log(response)
		}).catch(function (response) {
			$scope.response = 'ERROR: ' + response.status;
		})

	$scope.team = $scope.team;
	$scope.SelectTeam = function (team) {
		$scope.team = team;
	}

	$scope.InsertTeam = function (user) {
		$http.post("http://localhost:8080/teams", { team })
			.then(function (response) {
				$scope.teams = response;
				delete $scope.team;
				$scope.GetAllTeams();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}

	$scope.UpdateTeam = function (team) {
		$http.put("http://localhost:8080/team/:teamId", { team })
			.then(function (response) {
				$scope.teams = response;
				delete $scope.team;
				$scope.GetAllTeams();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}

	$scope.DeleteTeam = function (team) {
		$http.delete("http://localhost:8080/teams/:teamId", { team })
			.then(function (response) {
				$scope.teams = response;
				delete $scope.team;
				$scope.GetAllTeams();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}
})
);

vendasApp.controller('userController', (function ($scope, $http) {
	var url = "http://localhost:8080/users?format=json";
	$http.get(url)
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
		$http.post("http://localhost:8080/users?format=json", { user })
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
		$http.put(`http://localhost:8080/users/${$scope.user.id}`, { user })
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

vendasApp.controller('allSalesController', (function ($scope, $http) {
	var url = "http://localhost:8080/sales?format=json";
	$http.get(url)
		.then(function (response) {
			$scope.sales = response.data;
			console.log(response)
		}).catch(function (response) {
			$scope.response = 'ERROR: ' + response.status;
		});

	$scope.sale = $scope.sales;
	$scope.SelectSale = function (sale) {
		$scope.sale = sale;
	}

	$scope.InsertSale = function (sale) {
		$http.post("http://localhost:8080/sales", { sale })
			.then(function (response) {
				$scope.sales = response;
				delete $scope.sale;
				$scope.GetAllSales();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}

	$scope.UpdateSale = function (sale) {
		$http.put("http://localhost:8080/sales/:saleId", { sale })
			.then(function (response) {
				$scope.sales = response;
				delete $scope.sale;
				$scope.GetAllSales();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}

	$scope.DeleteSale = function (sale) {
		$http.delete("http://localhost:8080/sales/:saleId", { sale })
			.then(function (response) {
				$scope.sales = response;
				delete $scope.sale;
				$scope.GetAllSales();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}
})

);

vendasApp.controller('salesByIdController', (function ($routeParams) {
	$routeParams.id;
}));

vendasApp.controller('ApagarController', (function ($scope) {
	alert("Tem certeza que deseja apagar");
}))
