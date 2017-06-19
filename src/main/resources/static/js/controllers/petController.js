petStoreModule.controller('PetsController', ['$scope', 'PetFactory', 'allpets','$location','authService' ,function($scope, PetFactory, allpets,$location,authService) {
	
	$scope.pets = allpets;
	
	$scope.deletePet = deletePet;
	$scope.errorMessage = '';
	
	function deletePet(id) {
		PetFactory.deletePet(id).then(function(pets) {
			$scope.errorMessage = '';
			$scope.pets = pets;
		}, function(msg){
			$scope.errorMessage = msg;
			console.log(msg);
		});
	}
	
	$scope.logout = function () {
		$location.path('/login');
	};

	$scope.hasPermission = function (permission) {
		if (!authService.currentUser()) {
			return false;
		}
		console.log("input:"+permission);
		console.log("search:"+authService.currentUser().authorities.indexOf(permission));
		return authService.currentUser().authorities.indexOf(permission) > -1;
	};

	$scope.signedIn = function () {
		return authService.currentUser();
	}
	
}]);


petStoreModule.controller('PetCreationController', ['$scope', '$timeout', 'PetFactory', 
                                            function($scope, $timeout, PetFactory) {		
	resetForm();
	$scope.loading = false;
	$scope.isNewPetCreated = false;
	$scope.isPetNameWrong = false;
    $scope.submit = submit;
    
    function submit(){
    	if ($scope.petName) {
    		$scope.isPetNameWrong = false;
    		var newPet = preparePetObject($scope.petName, $scope.petCategory, $scope.petPhotoUrls, $scope.petTags, $scope.petStatus);
    		addPetToStore(newPet);
    	}else if(!$scope.petName){
    		$scope.isPetNameWrong = true;
    	}
    }
    
    function resetForm(){
    	$scope.petName = '';
        $scope.petCategory = '';
        $scope.petPhotoUrls = '';
        $scope.petTags = '';
        $scope.petStatus = 'available';
        
        $scope.isPetNameWrong = false;
        $scope.errorMessage = '';
    }
    
    function preparePetObject(name, category, photoUrls, tagsString, status){
    	if(photoUrls){
    		var trimmedPhotoUrls = photoUrls.trim();
    		var photoUrlsList = trimmedPhotoUrls.split(';');
    	}
    	if(tagsString){
    		var tags = tagsString.split(';');
    	}
    	
    	var pet = {
			"category" : {
				"name" : category
			},
			"name" : name || "missing name",
			"photoUrls" : photoUrlsList || [],
			"tags" : tags || [],
			"status" : status || "available"
    	};
    	return pet;
    }
    
	function addPetToStore(newPet) {		
		$scope.loading = true;
		PetFactory.addPet(newPet).then(function(addedPet) {
			$scope.loading = false;
			$scope.pets = PetFactory.pets;
			
			displayNewPetCreatedInfo();
			resetForm();
		}, function(msg){
			$scope.loading = false;
			$scope.errorMessage = msg;
			console.log(msg);
			$scope.pets = PetFactory.pets;
		});
	}
	
	function displayNewPetCreatedInfo(){
		$scope.isNewPetCreated = true;
		$timeout(function () {
			$scope.isNewPetCreated = false;
	    }, 2000);
	}
}]);


petStoreModule.controller('PetController', ['$scope', '$window', 'PetFactory', 'pet', 
                                            function($scope, $window, PetFactory, pet) {	
	$scope.pet = pet;
	
	$scope.deletePet = deletePet;	
	$scope.errorMessage = '';	
	
	function deletePet() {
		$scope.loading = true;
		PetFactory.deletePet($scope.pet.id).then(function(pets) {
			$scope.loading = false;
			$scope.errorMessage = '';
			$window.location.href = '#/';
		}, function(msg){
			$scope.loading = false;
			$scope.errorMessage = msg;
			console.log('Error trying to delete pet with id(' + $scope.pet.id + '): ' + msg);
		});
	}
}]);


petStoreModule.controller('LoginController', function ($scope, $location, authService, $http, $rootScope) {
	$scope.dataLoading = false;

	$scope.login = function () {
		$scope.dataLoading = true;

		authService.login($scope.credentials)
			.then(function (data) {
				if (data.authenticated) {
					$location.path("/");
					$scope.error = false;
				} else {
					$location.path("/login");
					$scope.error = true;
				}

				$scope.dataLoading = false;
			}, function () {
				$location.path("/login");
				$scope.error = true;

				$scope.dataLoading = false;
			});
	};
});

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

