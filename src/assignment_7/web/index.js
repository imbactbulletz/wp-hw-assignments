var app = angular.module("recipesApplication", ["ngRoute"]); // ovo ngRoute u zagradi znaci da smo dodali ngRoute kao dependency

app.config(function($routeProvider) { // koristi se za rutiranje izmedju stranica
    $routeProvider
        .when("/", { // ukoliko se zatrazi root
            templateUrl : "login.html", // include-uje login.html kao template
            controller : "CtrlLogin" // dodaje kontroler na include-ovani template
        })
        .when("/register", { // ukoliko se zatrazi root
            templateUrl : "register.html", // include-uje register.html kao template
            controller : "CtrlRegister" // dodaje kontroler na include-ovani template
        })
        .when("/dashboard", { // ukoliko se zatrazi root
            templateUrl : "dashboard.html", // include-uje register.html kao template
            controller : "CtrlDashboard"
        })
        .otherwise({ // ukoliko se nesto neocekivano zatrazi redirektujemo na handler za root
            redirectTo: "/"
        });
});

// pravimo factory koji koristi $http kako bi komunicirao sa serverom
app.factory('ServiceOperator', ['$http', function($http){
    var service = {}; // rezultat poziva servisa ce biti u ovoj promenljivi

    service.loginOperator = function(operator){ // ova funkcija kao ulazni parametar dobija objekat operator
        return $http.post("http://localhost:8080/domaci/rest/operators/login", operator); // salje post metodu ka serveru, kao i sam objekat a zatim vraca odgovor od servera
    };

    service.registerOperator = function(operator){ // ova funkcija kao ulazni parametar dobija objekat operator
        return $http.post("http://localhost:8080/domaci/rest/operators/register", operator); // salje post metodu ka serveru, kao i sam objekat a zatim vraca odgovor od servera
    };

    return service; // vracamo rezultat poziva servisa
}

]);

app.factory('ServiceRecipe', ['$http', function($http){
    var service = {};

    service.getAllRecipes = function(){
      return $http.get("http://localhost:8080/domaci/rest/recipes"); // vraca listu objekata
    };

    service.addRecipe = function(recipe){
      return $http.post("http://localhost:8080/domaci/rest/recipes", recipe); // dodaje recept na server
    };

    service.searchByName = function(name){
      return $http.get("http://localhost:8080/domaci/rest/recipes/search=".concat(name)); // pravi zahtev /search={imerecepta}
    };

    service.getPage = function(pageNumber,pageSize){
        return $http.get("http://localhost:8080/domaci/rest/recipes/searchPage=".concat(pageNumber,',',pageSize,',','*')); // pravi /search=pageNumber,pageSize,*
    };
    return service;
}]);


app.controller('CtrlLogin', ['$scope','ServiceOperator', '$location', function($scope, ServiceOperator, $location){
    $scope.operator = {};

    $scope.loginOperator = function(){
        var operator;

        operator = angular.copy($scope.operator); // kopira objekat 'operator' iz scope-a u lokalnu promenljivu

        ServiceOperator.loginOperator(operator).then(function(response){ // pokusava da uloguje operatera a zatim obavlja neku akciju
            var returned_value = response.data; // podaci koje nam je vratio server (login za operatera moze biti true ili false)


            if(returned_value){ // ukoliko su akreditivi korisnika tacni
                $location.path("/dashboard");  // redirektujemo korisnika na dashboard
            }

            else{ // ukoliko akreditivi nisu tacni
                $scope.errorMessage = "Wrong username/password!"; // menjamo innerhtml od errorMessage
            }
        });

    };

    $scope.redirectToRegister = function(){ // redirektuje korisnika na register.html
        $location.path("/register");
    };
}]);

app.controller('CtrlRegister', ['$scope','ServiceOperator', '$location', function($scope, ServiceOperator, $location){
    $scope.operator = {};

    $scope.registerOperator = function(){
        var operator;

        operator = angular.copy($scope.operator); // kopira objekat 'operator' iz scope-a u lokalnu promenljivu

        ServiceOperator.registerOperator(operator).then(function(response){ // pokusava da uloguje operatera a zatim obavlja neku akciju
            var returned_value = response.data; // podaci koje nam je vratio server (login za operatera moze biti true ili false)


            if(returned_value){ // uspesna registracija
                $location.path("/dashboard"); // redirektujemo korisnika na dashboard
            }

            else{ // neuspesna registracija
                $scope.errorMessage = "User with such username already exists!"; // menjamo innerhtml od errormessage-a
            }
        });

    };

}]);

app.controller('CtrlDashboard', ['$scope','ServiceRecipe', function($scope, ServiceRecipe){
    $scope.recipes = [];
    $scope.recipe = {};

    $scope.getAllRecipes = function(){
        $scope.recipes = ServiceRecipe.getAllRecipes().then(function(response){ // vraca recepte od servera a zatim ih postavlja u svoj model
           $scope.recipes = response.data;
        });
    };

    $scope.addRecipe = function(){
        var recipe = angular.copy($scope.recipe);

        ServiceRecipe.addRecipe(recipe).then(function(response){ // dodaje recept
            var returned_value = response.data;

            if(returned_value){ // ukoliko je uspesno dodavanje
                $scope.getAllRecipes(); // ucitava recepte ponovo
            }
        });
    };

    $scope.searchByName = function(){
            var name = $scope.searchCriteria; // regex za pretragu
            $scope.recipes = ServiceRecipe.searchByName(name).then(function(response){ // salje serveru zahtev
               $scope.recipes = response.data; // postavlja dobijene podatke u svoj model
            });
    };

    $scope.getPage = function(pageNumber, pageSize){
        $scope.recipes = ServiceRecipe.getPage(pageNumber, pageSize).then(function(response){ // servis salje getPage=x,y,* zahtev serveru
            $scope.recipes = response.data; // postavlja dobijene podatke u svoj model
        });
    };
}]);