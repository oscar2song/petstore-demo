'use strict';

petStoreModule.controller('AppController', function ($scope, $q, authService, $location, $rootScope) {

	$rootScope.$on("$routeChangeStart", function (event, next) {
		if (next.originalPath == '/login') {
			authService.resetUser();
			return;
		}

		if (!authService.currentUser() || !authService.currentUser().authenticated) {
			authService.resetUser();
			$location.url('/login');
		}
	});
});
