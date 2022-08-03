// create the module and name it scotchApp
var vendasApp = angular.module('vendasApp', ['ngRoute']);

// configure our routes
vendasApp.config(function ($routeProvider) {
	$routeProvider

		// route for the home page
		.when('/', {
			templateUrl: 'pages/home.html',
			controller: 'mainController'
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

vendasApp.controller('teamController', (function ($scope, $http) {
	var url = "http://localhost:8080/teams?format=json";
	$http.get(url)
		.then(function (response) {
			$scope.response = response.data;
			console.log(response)
		}).catch(function (response) {
			$scope.response = 'ERROR: ' + response.status;
		})
})
);

vendasApp.controller('userController', (function ($scope, $http) {
	var url = "http://localhost:8080/users?format=json";
	$http.get(url)
		.then(function (response) {
			$scope.response = response.data;
			console.log(response)
		}).catch(function (response) {
			$scope.response = 'ERROR: ' + response.status;
		})
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
		$scope.SelectSale = function(sale) {
			$scope.sale = sale;
		}

		$scope.InsertSale = function(sale) {
			$http.post("http://localhost:8080/sales", { sale } )
			.then(function(response) {
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

vendasApp.controller('ApagarController', (function($scope) {
	alert("Tem certeza que deseja apagar");
}))
