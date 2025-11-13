# 📚 API Biblioteca – Sistema de Gerenciamento de Aluguéis de Livros

Este projeto é uma API REST desenvolvida em **Java 17** com **Spring Boot**, que gerencia o fluxo completo de uma biblioteca: autores, livros, locatários e aluguéis.  
Permite cadastrar, atualizar, listar e remover entidades, além de controlar a disponibilidade dos livros e a devolução de exemplares.

---

## 🚀 Tecnologias Utilizadas
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- Lombok
- MySQL
- Maven
- RESTful API
- JUnit + MockMvc (testes)

---

## 🧱 Estrutura do Projeto

O projeto segue arquitetura em camadas:

```src
src/main/java/com/br/biblioteca
│
├── controller   → Endpoints REST
├── models       → Entidades JPA
├── repository   → Interfaces JPA
├── services     → Regras de negócio
└── exceptions   → Exceptions personalizadas
````

## ⚙️ Configurações de Banco de Dados
✔️ MySQL (padrão da aplicação):

Configurado em application.properties:

Banco: biblioteca

Usuário: root

Senha: root123

Tabelas são atualizadas automaticamente (sem apagar dados).

## ▶️ Como Executar o Projeto:

1️⃣ Pré-requisitos:

Java 17 instalado

Maven 3.8+

MySQL em execução com o banco biblioteca criado:

```sql
CREATE DATABASE biblioteca;
```

## 2️⃣ Clonar o repositório:

```bash
git clone <URL_DO_REPOSITORIO>
cd biblioteca
```

## 3️⃣ Rodar a aplicação:

```bash
mvn spring-boot:run
```

## 🧪 Executar os Testes

Os testes utilizam MockMvc + H2, bastando executar:

```bash
mvn test
```

## 📌 Endpoints (exemplos):

POST /api/locatarios

GET /api/locatarios

GET /api/locatarios/{id}

PUT /api/locatarios/{id}

DELETE /api/locatarios/{id}

GET /api/locatarios/buscar?email=


## 🧾 Licença

Este projeto é de uso **livre para fins educacionais** e pode ser adaptado para outros contextos de gestão de acervos e locações.

Desenvolvido com ❤️ em **Java** e **Spring Boot**.
