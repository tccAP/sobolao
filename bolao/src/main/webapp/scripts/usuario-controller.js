function UsuarioController($scope, $http, $route) {


    var baseUrl = "http://localhost:8090/bolao";
    $scope.usuarios = [];
    $scope.newUsuario = {};


    function getListUsuario() {
        $scope.usuarios = [];
        $http.get(baseUrl + '/usuario/')
            .success(function (data) {
                $scope.usuarios = data;
            })
            .error(function (error) {
                console.log("Retorno: " + error);
            });
    }

    getListUsuario();



    $scope.save = function () {

        if($scope.newUsuario.confirmaSenha === $scope.newUsuario.senha){
            if(!$scope.newUsuario.id){
                $scope.adicionaUsuario();
            }else{
                $scope.atualizaUsuario();
            }
        }else{
            alert('Confirmação de senha incorreta!');
        }
    }

    $scope.adicionaUsuario = function () {
        $http.post(baseUrl + '/usuario/' ,$scope.newUsuario).success(function (response) {
            getListUsuario();
        }).error(function (error) {
            console.log(error);
            getListUsuario();
        });
        $scope.newUsuario = {};
    }

    $scope.atualizaUsuario = function () {
        $http.put(baseUrl + '/usuario/', $scope.newUsuario).success(function (response) {
            getListUsuario();
        }).error(function (error) {
            console.log(error);
            getListUsuario();
        });
        $scope.newUsuario = {};
    }

    $scope.deleteUsuario = function (id) {

        $http.delete(baseUrl + '/usuario/' + id)
            .success(function (response) {
                getListUsuario();
            })
            .error(function (error) {
                getListUsuario();
                console.log("Retorno: " + error);
            });
    }

    $scope.selectUsuario = function (usuario) {
        $scope.newUsuario = usuario;
    }

}