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
