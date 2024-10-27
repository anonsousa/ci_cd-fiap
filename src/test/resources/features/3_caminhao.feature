# language: pt

Funcionalidade: Cadastro e Gerenciamento de Caminhoes

  Cenário: Cadastro de um caminhão com placa inválida
    Dado que eu estou autenticado
    Quando eu envio uma solicitação POST para "/caminhao" com os dados:
      | placa  | modelo       | renavam     |
      | ABC123 | Caminhão XYZ | 12345678901 |
    Então eu devo receber um status 400

  Cenário: Cadastro de um caminhão sem modelo
    Dado eu envio uma solicitação POST para "/caminhao" com os dados:
      | placa   | modelo | renavam     |
      | ABC1234 |        | 12345678901 |
    Então eu devo receber um status 400

  Cenário: Cadastro de um caminhão sem renavem
    Dado eu envio uma solicitação POST para "/caminhao" com os dados:
      | placa   | modelo       | renavam |
      | ABC1234 | Caminhão XYZ |         |
    Então eu devo receber um status 400

  Cenário: Encontrar um caminhão existente
    Dado que eu estou autenticado
    E tenho um ID invalido
    Quando eu envio uma solicitação invalida GET para o endpoint "/caminhao"
    Então eu devo receber um status 400

  Cenário: Cadastro de um caminhão com dados válidos
    Dado que eu estou autenticado
    Quando eu envio uma solicitação POST para "/caminhao" com os dados do caminhao:
      | placa   | modelo       | renavam     |
      | ABC1234 | Caminhão XYZ | 12345678901 |
    Então eu devo receber um status 201
    E a resposta deve conter os dados do caminhão

  Cenário: Encontrar um caminhão valido
    Dado que eu estou autenticado
    E tenho um ID valido
    Quando eu envio uma solicitação GET para o endpoint "/caminhao"
    Então eu devo receber um status 200

  Cenário: Encontrar caminhoes registrados
    Dado que eu estou autenticado
    Quando eu envio uma solicitação GET para o endpoint "/caminhao" buscando os motoristas
    Então eu devo receber um status 200
    E o retorno deve conter todos os caminhoes registrados


  Cenário: Atualizar um caminhão existente
    Dado que eu estou autenticado
    E tenho um ID invalido
    Quando eu envio uma solicitação PUT para "/caminhao" com os dados de atualização:
      | placa   | modelo       | renavam        |
      | ABC1234 | Modelo XYZ   | 12345678901    |
    Então eu devo receber um status 200
    E a resposta deve conter os dados do caminhão
