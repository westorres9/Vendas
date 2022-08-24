vendasApp.config(function ($routeProvider) {
	$routeProvider

		// route for the home page
		.when('/', {
			templateUrl: 'pages/home/home.html',
			controller: 'mainController'
		})

		.when('/login', {
			templateUrl: 'pages/login/login.html',
			controller: 'loginController'
		})

		// route for the about page
		.when('/teams', {
			templateUrl: 'pages/teams/teams.html',
			controller: 'teamController'
		})

		// route for the contact page
		.when('/sales', {
			templateUrl: 'pages/sales/sales.html',
			controller: 'allSalesController'
		})

		.when("/sales/:id", {
			templateUrl: 'pages/sales/salesdetail.html',
			controller: 'salesByIdController'
		})

		.when('/users', {
			templateUrl: 'pages/users/user.html',
			controller: 'userController'
		});
});