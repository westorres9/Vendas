
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

vendasApp.controller("loginController", function ($scope, $http, $httpParamSerializerJQLike , AuthService) {
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
			.then(function (response, $window) {
				const loginResponse = response.data;
				console.log('response', response)
				console.log(AuthService.setToken);
				AuthService.setToken(loginResponse);
				console.log('log2', AuthService.getToken());
			}).catch(function (response) {
				console.log("Falha" + response.data);
			});
	}
})

vendasApp.controller('teamController', (function ($scope, $http, AuthService) {
	var url = "http://localhost:8080/teams";

	const token = AuthService.getToken();

	
	$http.get(url,
	{
		headers: {
			'Authorization': 'Bearer ' +  token.access_token
		}
	})
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
		$http.put("http://localhost:8080/team/" + team.id, { team })
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
		$http.delete("http://localhost:8080/teams/" + team.id, { team })
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

vendasApp.controller('allSalesController', (function ($scope, $http, AuthService) {
	var url = "http://localhost:8080/sales";
	const token = AuthService.getToken();

	
	$http.get(url,
	{
		headers: {
			'Authorization': 'Bearer ' +  token.access_token
		}
	})
		.then(function (response) {
			$scope.sales = response.data;
			console.log(response)
			console.log(AuthService.getToken())
		}).catch(function (response) {
			$scope.response = 'ERROR: ' + response.status;
		});

	$scope.sale = $scope.sales;
	$scope.SelectSale = function (sale) {
		$scope.sale = sale;
		JSON.stringify(sale)
		console.log(sale);
	}

	$scope.InsertSale = function (url, sale) {
		$http.post(url,
			{
				headers: {
					'Authorization': 'Bearer ' +  token.access_token
				}
			}, JSON.stringify(sale))
			.then(function (response) {
				
				$scope.sales = response;
				delete $scope.sale;
				$scope.GetAllSales();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}

	$scope.UpdateSale = function (sale,AuthService) {
		const token = AuthService.getToken();
		$http.put(url,
			{
				headers: {
					'Authorization': 'Bearer ' +  token.access_token
				}
			}, { sale })
			.then(function (response) {
				$scope.sales = response;
				delete $scope.sale;
				$scope.GetAllSales();
			})
			.catch(function (response) {
				$scope.response = 'ERROR: ' + response.status;
			});
	}

	$scope.DeleteSale = function (sale,AuthService) {
		const token = AuthService.getToken();

		$http.delete(url,
			{
				headers: {
					'Authorization': 'Bearer ' +  token.access_token
				}
			}, { sale })
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



vendasApp.factory('AuthService', AuthService);
function AuthService () {
	return {
	 
	  setToken: function (token) {
		window.localStorage.setItem('access_token', angular.toJson(token));
		console.log('authService.setToken', token)
	  },
	  getToken: function () {
		let token = window.localStorage.getItem('access_token');
		return angular.fromJson(token);
		console.log(window.localStorage.getItem('access_token'))
	},
	};
  }

vendasApp.factory('isAuthenticated', isAutenticated);
function isAutenticated() {
	const token = AuthService().getToken;
	return token;
}