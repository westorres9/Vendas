vendasApp.factory('Interceptor', Interceptor) {
    Interceptor.inject = ['$q'];
    function Interceptor($q) {
        
    }
}

vendasApp.factory('tokenGlobalInterceptor', function() {
    return {
        request: function(config) {
            console.log(config);
            return config;
        }
    }
});

vendasApp.config(function ($httpProvider){
    $httpProvider.interceptors.push('tokenGlobalInterceptor');
})