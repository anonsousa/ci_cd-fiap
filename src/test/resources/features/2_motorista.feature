# language: pt

Funcionalidade: Cadastro e Gerenciamento de Motoristas
  Para garantir que o sistema registre e gerencie motoristas corretamente

  Cenário: Cadastro bem-sucedido de um motorista
    Dado que eu estou autenticado
    Quando eu envio uma solicitação POST para "/motorista" com os dados do motorista:
      | nome   | email           | telefone     | carteiraHabilitacao |
      | João   | joao@gmail.com  | 11999999999  | 123456789012        |
    Então a resposta do motorista deve ter o status 201
    E a resposta deve conter os dados do motorista

  Cenário: Buscar Motorista Invalido Por Id
    Dado que eu estou autenticado
    Quando eu envio uma solicitação GET para "/motorista" com o ID do motorista invalido
    Então a resposta da busca do motorista deve ter o status 404

  Cenário: Buscar Motorista Por Id
    Dado que eu estou autenticado
    E que eu tenho o id do motorista
    Quando eu envio uma solicitação GET para "/motorista" com o ID do motorista
    Então a resposta da busca do motorista deve ter o status 200
    E a resposta deve conter os dados do motorista buscado

  Cenário: Buscar Todos os Motoristas
    Dado que eu estou autenticado
    Quando eu envio uma solicitação GET para "/motorista"
    Então a resposta da busca dos motoristas deve ter o status 200
    E o retorno deve conter todos os motoristas cadastrados

  Cenário: Atualizar Motorista
    Dado que eu estou autenticado
    E que eu tenho o id do motorista
    Quando eu envio uma solicitação PUT para "/motorista" com os dados do motorista atualizados:
      | nome         | email           | telefone     | carteiraHabilitacao |
      | João Silva   | joao@gmail.com  | 11999999976  | 123436182013        |
    Então a resposta da atualizacao deve ter o status 200
    E a resposta deve conter os dados do motorista
