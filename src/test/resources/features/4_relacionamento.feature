# language: pt

Funcionalidade: Relacionamento Motorista e Caminhão

  Cenário: Agregar um motorista a um caminhão invalido
    Dado que eu estou autenticado
    E tenho um id de caminhao invalido
    Quando eu envio uma solicitação POST para "/relacionamento" com id do caminhao errado
    Então o retorno deve ter status 404

  Cenário: Agregar um motorista invalido a um caminhão
    Dado que eu estou autenticado
    E tenho um id de motorista invalido
    Quando eu envio uma solicitação POST para "/relacionamento" com id do motorista errado
    Então o retorno deve ter status 404

  Cenário: Agregar um motorista valido a um caminhão valido
    Dado que eu estou autenticado
    E tenho um id de motorista valido
    E tenho um id de caminhao valido
    Quando eu envio uma solicitação POST para "/relacionamento" com os ids corretos
    Então o retorno deve ter status 200

  Cenário: Buscar todos os Caminhoes com Motoristas
    Dado que eu estou autenticado
    Quando eu envio uma solicitação GET para "/relacionamento" para buscar todos os caminhoes com motorista
    Então o retorno deve conter o status 200
    E o retorno deve conter todos os caminhoes que tem motoristas