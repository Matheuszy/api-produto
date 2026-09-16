# API Produto

API Responsável por gerenciar produtos.

## 🗒️ Informações

- Projeto referência para treinamento da Alura.

## 📋 Pré-requisitos

- [IntelliJ Community](https://www.jetbrains.com/idea/download/)
- [Docker](https://www.docker.com/get-started/)

## 🌳 Variáveis de ambiente

| Nome                   | Valor             |
|------------------------|-------------------|
| spring.profiles.active | local,infra_local |

## 📦 Construindo

``` sh
mvn clean install -DskipTests
```

## 👌 Executando Testes

``` sh
mvn test
```

## ▶️ Executando a Aplicação

``` sh
docker compose up
```

``` sh
java -jar -Dspring.profiles.active=local,infra_local application/target/api-produto.application-0.0.1-SNAPSHOT.jar
```

## 📌 Versão

- [SemVer](https://semver.org/lang/pt-BR/)

## ✒ Autores

- [Rodrigo de Sordi](https://github.com/rodsordi)
