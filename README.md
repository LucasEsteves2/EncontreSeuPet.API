# 🐾 EncontreSeuPet API

API desenvolvida em **Spring Boot** como parte da **pós-graduação em Arquitetura de Software do Instituto Infnet**.  
O sistema tem como objetivo auxiliar na **busca, registro e acompanhamento de pets desaparecidos**, conectando pessoas que perderam seus animais com quem os encontrou.

> 💚 Em memória de **Fernandes**, o gato que inspirou este projeto.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5**
- **Spring Data JPA / Hibernate**
- **Jakarta Validation**
- **Lombok**
- **Banco de dados H2 (para ambiente de desenvolvimento)**
- **Maven** como gerenciador de dependências

---
<img width="1562" height="900" alt="hLVDRjj64BuBq3iCr4EI5ZOS-z88hH5QOb4LCcL8ib7qjYB7yZOabzrTgcYA7Fe0VOYWXoA7d8e-WLwitvBwMKciMyJYHUBCximtCz_CBbyRAxGjixJTijAcJ1JciSgjvWcNDsmfk1dG2VMbc6kH2KgOUgamXWqz_rQo5lgdKbRYligM7tEfNhHRxPPv8_D2O0SP-mki6gjbNBlPgc9" src="https://github.com/user-attachments/assets/e3f48c8e-db24-48bd-a263-e03297e3b711" />


## 📍 Principais Endpoints

| Controller | Método | Rota | Descrição |
|-------------|:------:|------|------------|
| **UsuárioController** | `POST` | `/usuarios` | Cadastra um novo usuário |
|  | `GET` | `/usuarios/{id}` | Consulta um usuário específico |
|  | `DELETE` | `/usuarios/{id}` | Remove um usuário do sistema |
| **PetController** | `POST` | `/pets` | Cadastra um novo pet |
|  | `GET` | `/pets` | Lista todos os pets |
|  | `GET` | `/pets/{id}` | Busca um pet pelo ID |
|  | `GET` | `/pets/status/{status}` | Lista pets por status (desaparecido, encontrado, etc.) |
|  | `GET` | `/pets/usuario/{usuarioId}` | Lista pets cadastrados por um usuário |
|  | `GET` | `/pets/search?nome=` | Busca pets por nome |
|  | `DELETE` | `/pets/{id}` | Remove um pet do sistema |
| **AvistamentoController** | `POST` | `/avistamentos` | Registra um novo avistamento de pet |
|  | `GET` | `/avistamentos` | Lista todos os avistamentos |
|  | `GET` | `/avistamentos/{id}` | Busca um avistamento específico |
|  | `GET` | `/avistamentos/pet/{petId}` | Lista avistamentos de um pet |
|  | `GET` | `/avistamentos/usuario/{usuarioId}` | Lista avistamentos feitos por um usuário |
|  | `GET` | `/avistamentos/nao-verificados` | Lista avistamentos ainda não verificados |
|  | `DELETE` | `/avistamentos/{id}` | Remove um avistamento |
