# 📚 API Biblioteca – Sistema de Gerenciamento de Aluguéis de Livros

Este projeto é uma API REST desenvolvida em **Java 17** com **Spring Boot**, que gerencia o fluxo completo de uma biblioteca: autores, livros, locatários e aluguéis.  
Permite cadastrar, atualizar, listar e remover entidades, além de controlar a disponibilidade dos livros e a devolução de exemplares.

---

## 🚀 Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- Lombok
- MySQL
- Maven
- RESTful API

---

## 🚀 Endpoints Principais

### 👩‍💼 Autores
- **POST** `/api/biblioteca/autores` → Cadastra um novo autor
- **PUT** `/api/biblioteca/autores/{id}` → Atualiza um autor existente
- **GET** `/api/biblioteca/autores` → Lista todos os autores
- **GET** `/api/biblioteca/autores/nome/{nome}` → Busca autor pelo nome
- **DELETE** `/api/biblioteca/autores/{id}` → Remove um autor

### 📖 Livros
- **POST** `/api/biblioteca/livros` → Cadastra livro e vincula autores
- **PUT** `/api/biblioteca/livros/{id}` → Atualiza livro existente
- **GET** `/api/biblioteca/livros` → Lista todos os livros
- **GET** `/api/biblioteca/livros/{id}` → Busca livro por ID
- **GET** `/api/biblioteca/livros/disponiveis` → Lista livros disponíveis
- **GET** `/api/biblioteca/livros/alugados` → Lista livros alugados
- **GET** `/api/biblioteca/livros/autor/{nomeAutor}` → Lista livros por autor
- **DELETE** `/api/biblioteca/livros/{id}` → Remove livro

### 🧍 Locatários
- **POST** `/api/biblioteca/locatarios` → Cadastra um locatário
- **GET** `/api/biblioteca/locatarios` → Lista locatários
- **GET** `/api/biblioteca/locatarios/{id}/livros` → Lista livros alugados por um locatário
- **DELETE** `/api/biblioteca/locatarios/{id}` → Remove locatário

### 🔄 Aluguéis
- **POST** `/api/biblioteca/alugueis` → Cria um aluguel com locatário e livros
- **GET** `/api/biblioteca/alugueis` → Lista todos os aluguéis
- **PUT** `/api/biblioteca/alugueis/{id}/finalizar` → Finaliza aluguel (devolução de livros)

---

## 🔁 Fluxo Recomendado de Testes

1️⃣ **POST /autores** → cadastra autores  
2️⃣ **POST /livros** → cadastra livros vinculando autores  
3️⃣ **POST /locatarios** → cadastra locatários  
4️⃣ **POST /alugueis** → cria aluguéis com livros e locatários  
5️⃣ **PUT /alugueis/{id}/finalizar** → devolve livros  
6️⃣ **GET /livros/disponiveis** → confirma que os livros foram devolvidos

---

## 🧰 Extras

## 💾 Acesso ao Banco de Dados MySQL para Testes da Aplicação

Para revisar e testar a aplicação, siga os passos abaixo para acessar o banco de dados **MySQL** localmente e validar as tabelas geradas automaticamente pelo **Spring Boot**.

---

### 🔧 Configurações do Banco de Dados
As configurações utilizadas pela aplicação estão definidas no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=root123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
````

## 🖥️ Acessando o MySQL via CMD (Windows)

## 1 - Certifique-se de que o serviço MySQL está ativo:

```bash
net start MySQL80
````
## 2 - Acesse o MySQL com o usuário root:

````bash

mysql -u root -p
````
## 3 -  Liste os bancos disponíveis para confirmar a existência do banco da aplicação:

````bash
SHOW DATABASES;
````


Você deverá ver algo como:

```psql
+--------------------+
| Database           |
+--------------------+
| biblioteca         |
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
````

## 4 - Selecione o banco de dados da aplicação:

```psql
USE biblioteca;
````

## 🧩 5 - Mostrar todas as tabelas do banco:

```psql
SHOW tables;
````

## Exemplo de resultado esperado:

````+---------------------+
| Tables_in_biblioteca |
+---------------------+
| alugueis            |
| autores             |
| livros              |
| locatarios          |
| livros_autores      |
+---------------------+
````    


# ✅ Observações:

O banco é criado automaticamente ao executar a aplicação, caso ainda não exista.

O usuário padrão utilizado é root com a senha root123.

O dialeto do Hibernate está configurado para MySQL8Dialect, garantindo compatibilidade total com o MySQL 8.0.

Certifique-se de que o serviço MySQL80 está em execução antes de rodar a aplicação.

---

### 🔐 Transações
As operações críticas estão anotadas com `@Transactional` para garantir consistência entre as tabelas.

### ⚠️ Tratamento de Erros
O controller utiliza `@ExceptionHandler` para capturar e exibir mensagens legíveis em caso de:

- `EntityNotFoundException`
- `IllegalStateException`
- `IllegalArgumentException`

---

## 🧾 Licença

Este projeto é de uso **livre para fins educacionais** e pode ser adaptado para outros contextos de gestão de acervos e locações.

Desenvolvido com ❤️ em **Java** e **Spring Boot**.
