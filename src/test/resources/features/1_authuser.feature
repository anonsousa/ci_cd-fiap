# language: pt

Funcionalidade: Registro e Login de Usuário
  Para garantir que o sistema registre e autentique usuários corretamente

  Cenário: Registro bem-sucedido de um novo usuário
    Quando eu envio uma solicitação POST para "/auth/register" com os dados do usuário:
      | nome      | email                  | senha      |
      | Antonio   | antonio@gmail.com      | password1   |
    Então a resposta deve ter o status 201
    E a resposta deve conter os dados que foram cadastrados previamente

  Cenário: Login mal-sucedido de um usuário nao registrado
    Quando eu envio uma solicitação POST para "/auth/login" com os dados de autenticacao do usuário:
      | email                  | senha      |
      | antoniu@gmail.com      | password2   |
    Então a resposta deve ser o status 403

  Cenário: Login bem-sucedido de um usuário registrado
    Quando eu envio uma solicitação POST para "/auth/login" com os dados de autenticacao do usuário:
      | email                  | senha      |
      | antonio@gmail.com      | password1   |
    Então a resposta deve ser o status 200
    E a resposta deve conter um token de autenticação
    E a resposta deve ter o retorno de acordo com o json de contrato do token

  Cenário: Procurar usuário por ID
    E eu tenho o ID do usuário registrado
    Quando eu envio uma solicitação GET para "/usuario" com o ID do usuário
    Então a resposta da solicitacao deve ter o status 200
    E a resposta deve conter os dados do usuário que eu loguei


  Cenário: Atualização bem-sucedida de um usuário
    E eu tenho o ID do usuário registrado
    Quando eu envio uma solicitação PUT para "/usuario" com os seguintes dados atualizados:
      | nome            | email                | senha      |
      | Antonio Souza   | antonio@gmail.com | password1  |
    Então a resposta da solicitacao deve ter o status 200
    E a resposta deve conter os dados atualizados do usuário:
      | nome             | email                |
      | Antonio Souza    | antonio@gmail.com |