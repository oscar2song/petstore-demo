petStoreModule.controller('PetsController', ['$scope', 'PetFactory', 'allpets', function($scope, PetFactory, allpets) {
	
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