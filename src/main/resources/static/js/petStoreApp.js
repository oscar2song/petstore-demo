var petStoreModule = angular.module('petStoreApp', [ 'ngRoute', 'ui.bootstrap',
		'ngAnimate' ]);

petStoreModule.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'partials/pets.html',
		controller : 'PetsController',
		resolve : {
			allpets : getPets
		}
	}).when('/pet', {
		templateUrl : 'partials/petCreation.html',
		controller : 'PetCreationController'
	}).when('/pet/:petId', {
		templateUrl : 'partials/pet.html',
		controller : 'PetController',
		resolve : {
			pet : getPet
		}
	}).when('/login', {
		templateUrl: 'partials/login.html',
		controller: 'LoginController',
	}).otherwise({
		redirectTo : '/'
	});
} ]);

function getPets(PetFactory) {
	return PetFactory.getPets();
}

function getPet($route, PetFactory) {
	return PetFactory.getPet($route.current.params.petId);
}