# 🛒 PDV API - Sistema de Ponto de Venda

> Status: 🚧 Em Desenvolvimento (Fase 2 Concluída - Segurança & Usuários)

Uma API REST robusta desenvolvida com **Java 21** e **Spring Boot 3** para gerenciar as operações de caixa de um supermercado. O projeto foca em boas práticas de engenharia de software, segurança (Spring Security) e escalabilidade.

## 💡 Motivação

A inspiração para este projeto nasceu da observação cotidiana em supermercados. Frequentemente, noto que os sistemas comerciais atuais apresentam interfaces complexas, lentidão e gargalos de integração que resultam em filas e experiências frustrantes tanto para operadores quanto para clientes.

O objetivo deste projeto é investigar esses desafios técnicos na prática: será uma limitação da tecnologia legada ou da arquitetura do software?

Este é um projeto de **autoestudo intensivo** com o objetivo de aprimorar minhas habilidades em:
* **Java e Spring Boot** (Ecossistema completo).
* **Segurança Avançada** (Autenticação, Criptografia e Permissões).
* **Arquitetura de Software** (Código limpo, SOLID e escalável).

Desenvolvi um roadmap inicial com as funções essenciais, mas o projeto evoluirá constantemente com novas funcionalidades para simular um cenário real e robusto.

## 🚀 Tecnologias Utilizadas

* **Java 21** (LTS)
* **Spring Boot 3.x**
* **Spring Data JPA** (Hibernate)
* **Spring Security** (Autenticação & Autorização)
* **BCrypt** (Criptografia de senhas)
* **H2 Database** (Banco em memória para desenvolvimento rápido)
* **PostgreSQL** (Banco para produção - planejado)
* **Maven** (Gerenciamento de dependências)

## ⚙️ Roadmap e Funcionalidades

### ✅ Fase 1: Fundação (Concluído)
- [x] Configuração do ambiente e arquitetura em camadas.
- [x] **Módulo de Produtos:** CRUD completo.
- [x] Regras de Negócio: Validação de duplicidade de código de barras.

### ✅ Fase 2: Segurança e Usuários (Concluído)
- [x] **Gestão de Usuários:** Entidades e Repositórios.
- [x] **Criptografia:** Implementação de Hash de senha com BCrypt.
- [x] **Perfis de Acesso:** Diferenciação entre `SUPERVISOR` e `ATENDENTE`.
- [x] **Segurança:** Bloqueio de rotas via Spring Security (Basic Auth).

### ✅ Fase 3: O Core da Venda (Concluído)
- [x] **Abertura de Venda:** Criação de carrinho vinculado ao usuário logado.
- [x] **Adição de Itens:** Busca de produtos e cálculo automático de subtotal.
- [x] **Snapshots de Preço:** O item grava o valor do produto no momento da venda.
- [x] **Finalização:** Encerramento da venda com escolha de forma de pagamento.
- [x] **Segurança Avançada:** Implementação de `@PreAuthorize` para proteger remoção de itens.

### ⚪ Fase 4: Gestão de Caixa e Financeiro (Próximo Passo)
- [ ] **Controle de Turno:** Endpoints para Abertura e Fechamento de Caixa (Turno do dia).
- [ ] **Sistema de Sangria:** Monitoramento de saldo em tempo real e retirada de valores.
- [ ] **Fidelidade:** Cadastro de Cliente (CPF) e pontuação na venda.
- [ ] **Nota Fiscal:** Simulação de emissão de documento fiscal.

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- Java JDK 21 instalado.
- Maven instalado (ou use o wrapper `mvnw` incluído no projeto).

### Passos

1. Clone o repositório:
```
git clone https://github.com/arthurml97/pdv-api.git
```

3. Acesse a pasta do projeto:
```
cd pdv-api
```

4. Execute a aplicação:
```
./mvnw spring-boot:run
```

5. A API estará rodando em `http://localhost:8080`.

6. O Banco de Dados (H2 Console) estará disponível em `http://localhost:8080/h2-console`.
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User:** `sa`
- **Password:** `password`


## 🔌 Documentação da API (Endpoints Principais)

O projeto utiliza **HTTP Basic Auth**. Para acessar rotas protegidas, é necessário enviar o cabeçalho de autenticação (`Authorization`).

### 👤 Usuários
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| `POST` | `/api/usuarios` | Cadastra um novo usuário (Supervisor/Atendente) | 🔓 Público (Inicialização) |

### 📦 Produtos
| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| `GET` | `/api/produtos` | Lista todos os produtos | 🔒 Requer Login |
| `POST` | `/api/produtos` | Cadastra um novo produto | 🔒 Requer Login |

## 🗂️ Estrutura do Projeto

O projeto segue rigorosamente a **Arquitetura em Camadas**:

* `src/main/java/br/com/tutum/portfolio/pdvapi`:
 * 📂 `config`: Configurações globais (ex: `SecurityConfig`).
 * 📂 `controller`: Camada REST (onde chegam as requisições HTTP).
 * 📂 `service`: Regras de negócio, validações e criptografia.
 * 📂 `repository`: Camada de acesso a dados (Interfaces JPA).
 * 📂 `model`: Entidades (Banco de Dados) e Enums.

## 👨‍💻 Autor

Desenvolvido por **Arthur Liscano** como parte do portfólio de Desenvolvimento Backend Java.
