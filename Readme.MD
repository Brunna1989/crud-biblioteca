# 📚 API Biblioteca – Sistema de Gerenciamento de Aluguéis de Livros

Este projeto é uma API REST desenvolvida em **Java 21** com **Spring Boot**, que gerencia o fluxo completo de uma biblioteca: autores, livros, locatários e aluguéis.  
Permite cadastrar, atualizar, listar e remover entidades, além de controlar a disponibilidade dos livros e a devolução de exemplares.

---

## 🚀 Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- Lombok
- H2 Database
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

### 💾 Banco de Dados em Memória (H2)
Acesse em: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:biblioteca`
- **Usuário:** `sa`
- **Senha:** *(vazio)*

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
