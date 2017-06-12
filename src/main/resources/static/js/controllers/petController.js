petStoreModule.controller('PetsController', ['$scope', 'PetFactory', 'allpets', function($scope, PetFactory, allpets, authService) {
	
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

	$scope.init = function () {
		$scope.pets = [];
		$scope.query = null;
		$scope.loadPets();
	}

	$scope.loadPets = function () {
		petService.getAll($scope.query)
			.then(function (response) {
				$scope.pets = response.data;
				console.log(response.data);
				$scope.dataRetrieved = true;
			});
	}

	$scope.delete = function (pet) {
		petService.delete(pet.id).then(function (response) {
			$scope.pets = _.without($scope.pets, pet)
		});
	}

	var handleError = function (response) {
		$scope.serverErrors = response.data;
	};

	$scope.add = function () {
		$scope.pet = {};
		$scope.upload = {};
		$scope.serverErrors = {};

		$scope.performAction = function () {
			petService.create($scope.pet, $scope.upload.csvFile)
				.then(function (response) {
					$scope.pet.url = $scope.upload.url;
					$scope.pet.id = response.data;
					$scope.pets.push($scope.pet);
					$scope.modalInstance.close();
				}, handleError);
		};

		$scope.handleError = function (response) {
			$scope.errorHandler.serverErrors = response;
		};
	};

	$scope.$watch('query', function (newValue, oldValue) {
		if (oldValue != newValue) {
			$scope.loadPets();
		}
	});

	$scope.logout = function () {
		$location.path('/login');
	};

	$scope.hasPermission = function (permission) {
		if (!authService.currentUser()) {
			return false;
		}

		return authService.currentUser().authorities.indexOf(permission) > -1;
	}

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