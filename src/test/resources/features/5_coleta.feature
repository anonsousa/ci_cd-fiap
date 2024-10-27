# language: pt

Funcionalidade: Cadastro e Gerenciamento de Coletas

  Cenário: Cadastro de coleta com CEP inválido
    Dado que eu estou autenticado
    Quando eu envio uma solicitação POST para "/coleta" com os dados da coleta:
      | cep          | numeroCasa | tipoResiduo | volumePeso | informacoesAdicionais         |
      | 12345678     | 10         | ORGANICO    | 20.5       | Coleta programada para amanhã |
    Então o status da resposta deve ser 400

  Cenário: Cadastro de coleta com Numero inválido
    Dado que eu estou autenticado
    Quando eu envio uma solicitação POST para "/coleta" com os dados da coleta:
      | cep          | numeroCasa | tipoResiduo | volumePeso | informacoesAdicionais         |
      | 12912660     |            | ORGANICO    | 20.5       | Coleta programada para amanhã |
    Então o status da resposta deve ser 400

  Cenário: Cadastro de coleta com Informacoes Adiconais inválidas
    Dado que eu estou autenticado
    Quando eu envio uma solicitação POST para "/coleta" com os dados da coleta:
      | cep          | numeroCasa    | tipoResiduo | volumePeso | informacoesAdicionais         |
      | 12912660     |    167        | ORGANICO    |   23.5     | Solicitação de coleta de resíduos agendada para a próxima semana. Cliente solicita que a coleta seja realizada na parte da manhã, com preferência para horários antes das 10h. Lembrete: confirmar acesso ao local com antecedência devido a restrições de entrada.|
    Então o status da resposta deve ser 400

  Cenário: Cadastro de Coleta Valida
    Dado que eu estou autenticado
    Quando eu envio uma solicitação POST para "/coleta" com os dados da coleta:
      | cep          | numeroCasa  | tipoResiduo | volumePeso | informacoesAdicionais         |
      | 12912-660    |     154    | ORGANICO    | 20.5       | Coleta de garrafas             |
    Então o status da resposta deve ser 201
    E devo ter o response com todos os dados corretos

  Cenário: Atualizar Coleta
    Dado que eu estou autenticado
    E tenho um ID de coleta valido
    Quando eu envio uma solicitação de atualizacao PUT para "/coleta" com os dados:
      | cep          | numeroCasa  | tipoResiduo | volumePeso | informacoesAdicionais         |
      | 12912-660    |     167    | ORGANICO    | 25.5       | Coleta de garrafas platicas    |
    Então o status da resposta deve ser 200
    E devo ter o response com todos os dados corretos

  Cenário: Buscar Coleta com Id invalido
    Dado que eu estou autenticado
    E tenho um ID de coleta invalido
    Quando eu envio uma solicitação GET para "/coleta" com a coleta invalida
    Então a resposta deve conter o status 404

  Cenário: Buscar Coleta com Id valido
    Dado que eu estou autenticado
    E tenho um ID de coleta valido
    Quando eu envio uma solicitação GET para "/coleta" com a coleta valida
    Então o status da resposta deve ser 200

  Cenário: Buscar coletas com Id de caminhão invalido
    Dado que eu estou autenticado
    E tenho um Id de Caminhao Invalido 
    Quando eu envio uma solicitação GET para "/coleta/caminhao" com o caminhao invalido
    Então a resposta do page response deve conter o status 200
    E o corpo do Page Response vazio

  Cenário: Buscar coletas com Id de caminhão valido
    Dado que eu estou autenticado
    E tenho um Id de Caminhao Valido
    Quando eu envio uma solicitação GET para "/coleta/caminhao" com o caminhao valido
    Então a resposta do page response deve conter o status 200
    E o corpo do Page Response nao pode estar vazio

  Cenário: Buscar Coletas Ativas
    Dado que eu estou autenticado
    Quando eu envio uma solicitação de coletas ativas GET para "/coleta/status/ativo"
    Então a resposta do page response deve conter o status 200
    E o corpo do Page Response nao pode estar vazio

  Cenário: Marcar Coleta como Coletado
    Dado que eu estou autenticado
    E tenho um ID de coleta valido
    Quando eu envio uma solicitação de finalizar coleta POST para "/coleta/end" com o id valido
    Então o status da resposta deve ser 200
    E o corpo da resposta com uma Coleta com o status COLETADO

  Cenário: Buscas Coletas Coletadas
    Dado que eu estou autenticado
    Quando eu envio uma solicitação para buscar coletas coletadas GET para "/coleta/status/coletado"
    Então a resposta do page response deve conter o status 200
    E o corpo da resposta com as coletas coletadas

  Cenário: Buscas Todas as Coletas
    Dado que eu estou autenticado
    Quando eu envio uma solicitação GET para "/coleta" para buscar todas as coletas
    Então a resposta do page response deve conter o status 200
    E o corpo da resposta com todas as coletas