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

		.when("/sales/1", {
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

vendasApp.controller('teamController', (function ($rootScope, $http) {
	var url = "http://localhost:8080/teams?format=json";
	$http.get(url)
		.then(function (response) {
			$rootScope.response = response.data;
			console.log(response)
		}).catch(function (response) {
			$rootScope.response = 'ERROR: ' + response.status;
		})

})
);

vendasApp.controller('userController', (function ($rootScope, $http) {
	var url = "http://localhost:8080/users?format=json";
	$http.get(url)
		.then(function (response) {
			$rootScope.response = response.data;
			console.log(response)
		}).catch(function (response) {
			$rootScope.response = 'ERROR: ' + response.status;
		})

})
);

vendasApp.controller('allSalesController', (function ($rootScope, $http) {
	var url = "http://localhost:8080/sales?format=json";
	$http.get(url)
		.then(function (response) {
			$rootScope.response = response.data;
			console.log(response)
		}).catch(function (response) {
			$rootScope.response = 'ERROR: ' + response.status;
		})
})

);

vendasApp.controller('salesByIdController', (function ($rootScope, $http, $routeParams) {
	var BASE_URL = 'http://localhost:8080/';
	var sales = {
		id : 1,
		visited: '',
		deals : '',
		amount : '',
		sellerId : '',
	}
	var url = BASE_URL + 'sales/'+ sales.id;
	$http.get(url) 
		.then(function (response) {
			$rootScope.response = response.data;
			console.log(response)
		}).catch(function (response) {
			$rootScope.response = 'ERROR: ' + response.status;
		})
})
);