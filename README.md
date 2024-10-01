# Collect-MS - Sistema de Coleta de Resíduos

Este projeto é uma aplicação Spring Boot desenvolvida para gerenciar a coleta de resíduos, utilizando H2 como banco de dados em memória e Flyway para controle de migrações de banco de dados. A aplicação está configurada para ser containerizada utilizando Docker.

## Tecnologias

- **Java 21 (Eclipse Temurin)**
- **Maven**
- **Spring Boot**
    - Spring Data JPA
    - Flyway
    - H2 Database
- **Docker**
- **Docker Compose**

## Pré-requisitos

- **Docker** instalado: [Instruções de instalação do Docker](https://docs.docker.com/get-docker/)
- **Docker Compose** instalado: [Instruções de instalação do Docker Compose](https://docs.docker.com/compose/install/)

### Rodar a Aplicacao

Após o build, você pode iniciar a aplicação executando:

```bash
    docker-compose up --build
```

### Variáveis de Ambiente

As variaveis de ambiente utilizadas na aplicação são configuradas diretamente no arquivo `compose.yaml`. Abaixo estão as principais variáveis que controlam o comportamento da aplicação:

- **PROFILE**: Define o perfil da aplicação Spring Boot. Pode ser `dev` (desenvolvimento) ou `prd` (produção).
- **DATABASE_URL**: Define a URL de conexão ao banco de dados.
- **DATABASE_USER**: Define o nome de usuário para conectar ao banco de dados.
- **DATABASE_PWD**: Define a senha para conectar ao banco de dados.

Abaixo esta o exemplo de como essas variaveis estão configuradas no arquivo `compose.yaml`:

```yaml
version: '3.8'

services:
  api:
    build: .
    ports:
      - "8080:8080"
    environment:
      - PROFILE=dev
      - DATABASE_URL=jdbc:h2:mem:collect-prd
      - DATABASE_USER=sa
      - DATABASE_PWD=password



