# Sistema de Coleta de Resíduos

Este projeto é uma aplicação Spring Boot desenvolvida para gerenciar a coleta de resíduos, utilizando H2 como banco de dados em memória no ambiente de testes e MySql no ambiente de producao. A aplicação está configurada para ser containerizada utilizando Docker.

## Tecnologias

- **Java 21 (Eclipse Temurin)**
- **Maven**
- **Spring**
    - Spring Validation 
    - Spring Security
    - Spring Data JPA
    - H2 Database (DEV)
    - MySql Database (PROD)
- **Lombok**
- **Docker**
- **Docker Compose**

## Pré-requisitos

- **Docker** instalado: [Instruções de instalação do Docker](https://docs.docker.com/get-docker/)
- **Docker Compose** instalado: [Instruções de instalação do Docker Compose](https://docs.docker.com/compose/install/)

### Rodar a Aplicacao

Após o build, você pode iniciar a aplicação (em ambiente de desenvolvimento com H2) executando:

```bash
docker compose up --build
```
## Testes unitarios

```bash
./mvnw test
```

### Variáveis de Ambiente

As variaveis de ambiente utilizadas na aplicação são configuradas diretamente no arquivo `compose.yaml`. Abaixo estão as principais variáveis que controlam o comportamento da aplicação:

- **PROFILE**: Define o perfil da aplicação Spring Boot. Pode ser `dev` (desenvolvimento) ou `prd` (produção).
- **DATABASE_URL**: Define a URL de conexão ao banco de dados. (no ambiente de dev estamos usando o banco de dados h2, entao a url esta sendo parametrizada diretamente no application.properties)
- **DATABASE_USER**: Define o nome de usuário para conectar ao banco de dados.
- **DATABASE_PWD**:Define a senha para conectar ao banco de dados.
- **JWT_TOKEN**:Define o secret do JWT

Abaixo esta o exemplo de como essas variaveis estão configuradas no arquivo `compose.yaml` (-DATABASE_URL esta definido apenas para o ambiente de PROD, o ambiente de DEV esta parametrizando com a url do banco diretamente no application-dev.properties pois o banco sobe apenas para testes, sendo o h2 mais leve para este proposito):

```yaml
services:

  db:
    container_name: mysql
    image: "mysql"
    ports:
      - "3306:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=root_pass
    healthcheck:
      test: [ "CMD", "mysqladmin", "ping", "-h", "localhost" ]
      interval: 10s    # Intervalo entre as verificações de saúde
      timeout: 5s      # Tempo de espera para a verificação
      retries: 5

  api:
    depends_on:
      db:
        condition: service_healthy
    build: .
    ports:
      - "8080:8080"
    environment:
      - PROFILE=dev
      - DATABASE_URL=jdbc:h2:mem:collect-dev
      - DATABASE_USER=userdev
      - DATABASE_PWD=password
      - JWT_SECRET=api
```

Caso queira modificar para utilizar o perfil prd, aqui esta a confirguracao do `compose.yaml` necessaria:

```yaml
services:

  db:
    container_name: mysql
    image: "mysql"
    ports:
        - "3306:3306"
    environment:
        - MYSQL_ROOT_PASSWORD=root_pass
    healthcheck:
      test: [ "CMD", "mysqladmin", "ping", "-h", "localhost" ]
      interval: 10s    # Intervalo entre as verificações de saúde
      timeout: 5s      # Tempo de espera para a verificação
      retries: 5

  api:
    depends_on:
      db:
        condition: service_healthy
    build: .
    ports:
      - "8080:8080"
    environment:
      - PROFILE=prd
      - DATABASE_URL=jdbc:mysql://db:3306/residue_collection?createDatabaseIfNotExist=true
      - DATABASE_USER=root
      - DATABASE_PWD=root_pass
      - JWT_SECRET=api
```