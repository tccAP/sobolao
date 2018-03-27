function ApostaController($scope, $http, $route) {


    var baseUrl = "http://localhost:8090/bolao";
    $scope.allNumbers = [];
    $scope.newAposta = {};
    $scope.newAposta.bolao = {};
    $scope.bolao = $scope.newAposta.bolao;
    $scope.newAposta.numerosArray = [];
    $scope.jogos = [
        {"name": "Mega-Sena"},
        {"name": "Loto-Facil"},
        {"name": "Quina"},
        {"name": "Timemania"}
    ];

    $scope.propabilidadesValores = [
        {"quantidadeNumeros": 6, "valor": 3.50, "probSena": 50063860, "probQuina": 154518, "probQuadra": 2332},
        {"quantidadeNumeros": 7, "valor": 24.50, "probSena": 7151980, "probQuina": 44981, "probQuadra": 1038},
        {"quantidadeNumeros": 8, "valor": 98.00, "probSena": 1787995, "probQuina": 17192, "probQuadra": 539},
        {"quantidadeNumeros": 9, "valor": 294.00, "probSena": 595998, "probQuina": 7791, "probQuadra": 312},
        {"quantidadeNumeros": 10, "valor": 735.00, "probSena": 238399, "probQuina": 3973, "probQuadra": 195},
        {"quantidadeNumeros": 11, "valor": 1617.00, "probSena": 108363, "probQuina": 2211, "probQuadra": 129},
        {"quantidadeNumeros": 12, "valor": 3234.00, "probSena": 54182, "probQuina": 1317, "probQuadra": 90},
        {"quantidadeNumeros": 13, "valor": 6006.00, "probSena": 29175, "probQuina": 828, "probQuadra": 65},
        {"quantidadeNumeros": 14, "valor": 10510.50, "probSena": 16671, "probQuina": 544, "probQuadra": 48},
        {"quantidadeNumeros": 15, "valor": 17517.50, "probSena": 10003, "probQuina": 370, "probQuadra": 37}
    ];

    $scope.getValores = function () {
        if ($scope.newAposta.numerosArray.length >= 6) {
            for (i = 0; i < $scope.propabilidadesValores.length; i++) {
                if ($scope.newAposta.numerosArray.length === $scope.propabilidadesValores[i].quantidadeNumeros) {
                    return $scope.propabilidadesValores[i];
                }
            }
        }
        return {};
    }

    for (i = 1; i <= 60; i++) {
        $scope.allNumbers.push(i);
    }

    $scope.addNumero = function (numero) {
        if ($scope.newAposta.numerosArray.indexOf(numero) < 0) {
            $scope.newAposta.numerosArray.push(numero);
        }
    }
    $scope.removeNumero = function (element) {
        var index = $scope.newAposta.numerosArray.indexOf(element);
        $scope.newAposta.numerosArray.splice(index, 1);
    }
    $scope.clickAllNumbers = function (number) {
        if ($scope.newAposta.numerosArray.indexOf(number) < 0) {
            $scope.addNumero(number);
        } else {
            $scope.removeNumero(number);
        }
        $scope.newAposta.valor = $scope.getValores().valor;
        ;
    }


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
    $scope.pad = function (n, width, z) {
        z = z || '0';
        n = n + '';
        return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
    }

    function replaceAll(str, needle, replacement) {
        return str.split(needle).join(replacement);
    }

    $scope.save = function () {
        $scope.newAposta.bolao = JSON.parse($scope.newAposta.bolao);
        $scope.newAposta.numeros = replaceAll($scope.newAposta.numerosArray.toString(), ',', ';');
        if (!$scope.newAposta.id) {
            $scope.adicionaAposta();
        } else {
            $scope.atualizaAposta();
        }
    }

    $scope.adicionaAposta = function () {
        $http.post(baseUrl + '/aposta/', $scope.newAposta).success(function (response) {
            getListAposta();
        }).error(function (error) {
            console.log(error);
            getListAposta();
        });
        $scope.newAposta = {};
    }

    $scope.atualizaAposta = function () {
        $http.put(baseUrl + '/aposta/', $scope.newAposta).success(function (response) {
            getListAposta();
        }).error(function (error) {
            console.log(error);
            getListAposta();
        });
        $scope.newAposta = {};
    }


    function getListAposta() {
        $scope.usuarios = [];
        if (!!$scope.bolao.id) {
            $http.get(baseUrl + '/aposta/findByBolao/' + $scope.bolao.id)
                .success(function (data) {
                    $scope.apostas = data;
                })
                .error(function (error) {
                    console.log("Retorno: " + error);
                });
        }
    }

    getListAposta();

    $scope.deleteAposta = function (id) {

        $http.delete(baseUrl + '/aposta/' + id)
            .success(function (response) {
                getListAposta();
            })
            .error(function (error) {
                getListAposta();
                console.log("Retorno: " + error);
            });
    }

    $scope.$watch('newAposta.bolao', function (newValue, oldValue) {
        if (typeof(newValue) != 'object') {
            $scope.bolao = JSON.parse(newValue);

            getListAposta();

        }
    });

}