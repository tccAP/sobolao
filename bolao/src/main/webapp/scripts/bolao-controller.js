function BolaoController($scope, $http, $route) {


    var baseUrl = "http://sobolao-sobolao.193b.starter-ca-central-1.openshiftapps.com/bolao";
    $scope.boloes = [];
    $scope.newBolao = {};
    $scope.usuariosPossiveis =[];
    $scope.newBolao.participantes = [];

    function getListBolao() {
        $scope.boloes = [];
        $http.get(baseUrl + '/bolao/')
            .success(function (data) {
                $scope.boloes = data;
            })
            .error(function (error) {
                console.log("Retorno: " + error);
            });
    }

    getListBolao();

    function getListUsuario() {
        $scope.usuarios = [];
        $http.get(baseUrl + '/usuario/')
            .success(function (data) {
                $scope.usuarios = data;
                $scope.usuariosPossiveis = angular.copy(data);
            })
            .error(function (error) {
                console.log("Retorno: " + error);
            });
    }
    getListUsuario();


    $scope.passToSelected = function(element){
        $scope.newBolao.participantes.push(element);
        var index = $scope.usuariosPossiveis.indexOf(element);
        $scope.usuariosPossiveis.splice(index, 1);
    }
    $scope.passToPossible = function(element){
        $scope.usuariosPossiveis.push(element);
        var index = $scope.newBolao.participantes.indexOf(element);
        $scope.newBolao.participantes.splice(index, 1);
    }

    $scope.save = function () {
        $scope.newBolao.organizador = JSON.parse($scope.newBolao.organizador);

        if(!$scope.newBolao.id){
            $scope.adicionaBolao();
        }else{
            $scope.atualizaBolao();
        }
        getListUsuario();
    }

    $scope.adicionaBolao = function () {
        $http.post(baseUrl + '/bolao/', $scope.newBolao).success(function (response) {
            getListBolao();
        }).error(function (error) {
            console.log(error);
            getListBolao();
        });
        $scope.newBolao = {};
    }

    $scope.atualizaBolao = function () {
        $http.put(baseUrl + '/bolao/', $scope.newBolao).success(function (response) {
            getListBolao();
        }).error(function (error) {
            console.log(error);
            getListBolao();
        });
        $scope.newBolao = {};
    }

    $scope.deleteBolao = function (id) {

        $http.delete(baseUrl + '/bolao/' + id)
            .success(function (response) {
                getListBolao();
            })
            .error(function (error) {
                getListBolao();
                console.log("Retorno: " + error);
            });
    }

    $scope.selectBolao = function (bolao) {
        $scope.newBolao = bolao;
    }

}