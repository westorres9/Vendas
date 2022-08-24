vendasApp.controller('teamController', (function ($scope, $http, AuthService) {
	var url = "http://localhost:8080/teams";

	const token = AuthService.getToken();

	
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