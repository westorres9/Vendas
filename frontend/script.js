	// create the module and name it scotchApp
	var scotchApp = angular.module('scotchApp', ['ngRoute']);

	// configure our routes
	scotchApp.config(function($routeProvider) {
	  $routeProvider

	  // route for the home page
	    .when('/', {
	    templateUrl: 'pages/home.html',
	    controller: 'mainController'
	  })

	  // route for the about page
	  .when('/about', {
	    templateUrl: 'pages/about.html',
	    controller: 'aboutController'
	  })

	  // route for the contact page
	  .when('/sales', {
	    templateUrl: 'pages/sales.html',
	    controller: 'salesController'
	  });
	});

	// create the controller and inject Angular's $scope
	scotchApp.controller('mainController', function($scope) {
	  // create a message to display in our view
	  $scope.message = 'An Angular Controller injects this text by using $scope. ';
	});

	scotchApp.controller('aboutController', function($scope) {
	  $scope.message = 'This site uses templateUrl and controllers.';
	});

	scotchApp.controller('salesController', (function($rootScope,$http) { 
			var url = "http://localhost:8080/sales?format=json";
			$http.get(url)
			  .then(function(response) {
				$rootScope.response = response.data;
				console.log(response)
			}).catch(function(response) {
				$rootScope.response = 'ERROR: ' + response.status;
			})     
	   })
);