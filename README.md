# 🐾 EncontreSeuPet API

API desenvolvida em **Spring Boot** como parte da **pós-graduação em Arquitetura de Software do Instituto Infnet**.  
O sistema tem como objetivo auxiliar na **busca, registro e acompanhamento de pets desaparecidos**, conectando pessoas que perderam seus animais com quem os encontrou.


<p align="center">
  <img src="https://github.com/user-attachments/assets/b35b30de-6283-4aad-9357-3870372a3ed4" 
       alt="Fernandes - O gato que inspirou o projeto" 
       width="220" 
       height="180" 
       style="border-radius: 12px; box-shadow: 0 2px 6px rgba(0,0,0,0.3);" />
</p>
<p align="center">
  💚 <strong>Em memória de <em>Fernandes</em>, o gato que inspirou este projeto.</strong>
</p>


---

## ⚙️ Funcionalidades Principais

- 🐕 **Cadastro de Pets:** registre informações detalhadas sobre o animal (nome, espécie, cor, descrição e status).  
- 👤 **Gestão de Usuários:** cada usuário pode cadastrar e gerenciar seus próprios pets.  
- 👀 **Avistamentos:** registre quando e onde um pet foi visto.  
- 📍 **Localização:** cada avistamento possui latitude, longitude e endereço associados.  
- 🔔 **Notificações:** usuários podem receber alertas sobre novos avistamentos.  
- 📊 **Consulta e Filtros:** visualize pets desaparecidos, encontrados ou adotados.


---
<img width="1562" height="900" alt="hLVDRjj64BuBq3iCr4EI5ZOS-z88hH5QOb4LCcL8ib7qjYB7yZOabzrTgcYA7Fe0VOYWXoA7d8e-WLwitvBwMKciMyJYHUBCximtCz_CBbyRAxGjixJTijAcJ1JciSgjvWcNDsmfk1dG2VMbc6kH2KgOUgamXWqz_rQo5lgdKbRYligM7tEfNhHRxPPv8_D2O0SP-mki6gjbNBlPgc9" src="https://github.com/user-attachments/assets/e3f48c8e-db24-48bd-a263-e03297e3b711" />

---

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

---

Para **facilitar a integração** e garantir **consistência entre todos os endpoints**, a arquitetura de resposta da API foi **padronizada**.  
Todas as respostas seguem um modelo unificado, simplificando o consumo e o tratamento de erros por aplicações externas.

## 🧱 Retorno Base

```json
{
  "success": true,
  "count": 1,
  "data": {},
  "message": null
}
```

| Campo | Tipo | Descrição |
|-------|------|------------|
| `success` | boolean | Indica se a operação foi bem-sucedida |
| `count` | number | Quantidade de itens retornados (0 para listas vazias ou operações sem retorno) |
| `data` | object / array | Corpo da resposta com os dados solicitados |
| `message` | string | Mensagem adicional (erro, validação ou informação de status) |

---

## ⚠️ Tratamento Padronizado de Erros

Todas as exceções são tratadas globalmente via `GlobalExceptionHandler`, garantindo respostas uniformes e status HTTP coerentes com o padrão REST.

| Código | Tipo de Erro | Exemplo de Resposta |
|---------|---------------|--------------------|
| **400** | Erro de validação (Bean Validation) | `{ "success": false, "message": "nome: O nome do pet é obrigatório." }` |
| **404** | Entidade não encontrada | `{ "success": false, "message": "Pet não encontrado com ID: 99" }` |
| **409** | Violação de integridade | `{ "success": false, "message": "Violação de integridade no banco de dados." }` |
| **500** | Erro inesperado | `{ "success": false, "message": "Erro inesperado: NullPointerException" }` |

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5**
- **Spring Data JPA / Hibernate**
- **Jakarta Validation**
- **Lombok**
- **Banco de dados H2 (para ambiente de desenvolvimento)**
- **Maven** como gerenciador de dependências

