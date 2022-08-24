vendasApp.factory('tokenGlobalInterceptor', Interceptor);
    Interceptor.inject = ['$q'];
    function Interceptor($q) {
    return {
        request: function(config) {
            const token = AuthService().getToken();
                config.headers['Authorization'] = 'Bearer ' + token.access_token;
                console.log(config);
                return config;
            },
            responseError: function(error) {
                if (error.status === 401 || error.status === 403) {
                    console.log(config);
                }
                return $q.reject(error);
            }
        }
            return config;
            
        };
    
vendasApp.config(function ($httpProvider){
    $httpProvider.interceptors.push('tokenGlobalInterceptor');
})