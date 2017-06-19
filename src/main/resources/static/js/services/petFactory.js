petStoreModule.factory('PetPathFactory', function(){
	return {
		addPetPath: function(){ return '/petstore/pet';},
		getPetPath: function(id){ return '/petstore/pet/' + id;},
		getPetsPath: function(){ return '/petstore/pets';},
		deletePetPath: function(id){ return '/petstore/pet/' + id;}
	};
});

petStoreModule.factory('PetFactory', ['$http', '$q', 'PetPathFactory', function($http, $q, PetPathFactory){
	var factory = {
		
		pets: [],
		
		addPet: function(newPet){
			var url = PetPathFactory.addPetPath();
			
			var deferred = $q.defer();
			
			$http.post(url, newPet).then(function(response) {
					var newPet = response.data;
					factory.pets.push(newPet);
					deferred.resolve(newPet);
				}, function(response) {
					deferred.reject(response.data.message);
			});
			return deferred.promise;
		},

		getPet: function(id) {
			var url = PetPathFactory.getPetPath(id);
			
			var deferred = $q.defer();
			
			$http.get(url).then(function(response) {
					var pet = response.data;
					if(pet === ''){
						deferred.reject('No pet found with id=' + id);
					}else{
						deferred.resolve(response.data);
					}
				}, function(response) {
					deferred.reject(response.data.message);
			});
			return deferred.promise;
		},
		
		getPets: function() {
			var url = PetPathFactory.getPetsPath();
			
			var deferred = $q.defer();
			
			$http.get(url).then(function(response) {
					factory.pets = response.data;
					deferred.resolve(factory.pets);
				}, function(response) {
					deferred.reject(response.data.message);
			});
			return deferred.promise;
		},

		deletePet: function(id) {
			var url = PetPathFactory.deletePetPath(id);
			
			var deferred = $q.defer();
			
			$http.delete(url).then(function(response) {
					var updatedPets = response.data;
					factory.pets = updatedPets;
					deferred.resolve(factory.pets);
				},function(response) {
					deferred.reject(response.data.message);
			});
			return deferred.promise;
		}
	}
	return factory;
}]);



petStoreModule.factory('authService', ['$http', '$q',  'PetPathFactory', function ($http, $q) {
	var currUser;

	return {
		login: function (credentials) {
			var fd = new FormData();
			fd.append('username', credentials.username);
			fd.append('password', credentials.password);

			var deferred = $q.defer();

			$http({
				url: '/login',
				method: 'POST',
				headers: {'Content-Type': undefined},
				data: fd
			}).then(function (response) {
				if (response.data.name !== null) {
					currUser = response.data;
				}
				deferred.resolve(currUser);
			}, function () {
				deferred.resolve(null);
			});

			return deferred.promise;
		},
		currentUser: function () {
			return currUser;
		},
		resetUser: function () {
			currUser = null;
		}
	};
}]);