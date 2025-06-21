# Crédito API

API RESTful para consulta de créditos constituídos.

---

## Tecnologias Utilizadas

- Java 21, Spring Boot, Spring Data JPA, Hibernate
- PostgreSQL
- Kafka (mensageria)
- Flyway (migração de banco)
- Docker e Docker Compose
- JUnit, Mockito (testes automatizados)
- Swagger (documentação da API)

---

## Configuração do Banco de Dados

Antes de executar a aplicação ou rodar as migrações com Flyway, é necessário criar manualmente o banco de dados utilizado pela aplicação.

### Criando o banco `credito_db` no PostgreSQL

Execute o comando SQL:

```sql
CREATE DATABASE credito_db;
```

## Para realizar Build Maven

Altere as credenciais da base de dados no arquivo myFlywayConfig.conf

### Gerando o artefato e criando tabela já populada

Execute o comando mvn:

```
mvn clean flyway:migrate package -Dflyway.configFiles=./myFlywayConfig.conf
```

## Criar uma rede Docker externa compartilhada
    sudo docker network create credito-network

## Para realizar Deploy Docker
    sudo docker-compose down
	sudo docker-compose up --build

## Swagger
	http://localhost:8080/swagger-ui/index.html

## Docker
	https://hub.docker.com/r/mespindula/credito-api