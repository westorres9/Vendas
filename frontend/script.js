
var vendasApp = angular.module('vendasApp', ['ngRoute']);



vendasApp.controller('mainController', function ($scope) {
	
	$scope.message = 'An Angular Controller injects this text by using $scope. ';
});
