# language: pt

Funcionalidade: Deletar Entidades Usadas Para os testes

  Cenário: Deletar Coleta
    Dado que eu estou autenticado
    E tenho um ID de coleta valida
    Quando eu envio uma solicitação DELETE para "/coleta" com a coleta que desejo deletar
    Então a resposta da delecao deve conter o status 204

  Cenário: Deletar Relacionamento Motorista x Caminhao
    Dado que eu estou autenticado
    E tenho um ID de um caminhao
    Quando eu envio uma solicitação DELETE para "/relacionamento" com o caminhao que desejo deletar o relacionamento
    Então a resposta da delecao deve conter o status 200
    E o corpo da resposta deve conter a mensagem de delecao

  Cenário: Deletar Motorista
    Dado que eu estou autenticado
    E tenho um ID de um motorista
    Quando eu envio uma solicitação DELETE para "/motorista" com o motorista que desejo deletar
    Então a resposta da delecao deve conter o status 204

  Cenário: Deletar Caminhao
    Dado que eu estou autenticado
    E tenho um ID de um caminhao para deletar
    Quando eu envio uma solicitação DELETE para "/caminhao" com o caminhao que desejo deletar
    Então a resposta da delecao deve conter o status 204

  Cenário: Deletar Usuario
    Dado que eu estou autenticado
    E tenho um ID de um usuario
    Quando eu envio uma solicitação DELETE para "/usuario" com o usuario que desejo deletar
    Então a resposta da delecao deve conter o status 204



