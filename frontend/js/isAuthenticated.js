vendasApp.factory('isAuthenticated', isAutenticated);
function isAutenticated() {
	const token = AuthService().getToken;
	return token;
}