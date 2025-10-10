# 🏦 CoopBank API

API RESTful desenvolvida em **Spring Boot** com banco de dados **H2 em memória**, para gerenciamento de **correntistas, contas e transações bancárias**.

---

## 🚀 Tecnologias utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Web
- Spring Data JPA (Hibernate)
- Banco H2 (em memória)
- Maven

---

## ⚙️ Como rodar o projeto localmente

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/flaviojsdev/coopbank-api.git
cd coopbank-api

mvn spring-boot:run

3️⃣ Acessar no navegador

API: http://localhost:8080

Console H2: http://localhost:8080/h2-console

Credenciais do H2:

JDBC URL: jdbc:h2:mem:coopbank
Usuário: sa
Senha: (vazio)

🗄️ Estrutura do Banco (schema.sql)

O H2 é criado automaticamente, mas o esquema equivalente seria:

CREATE TABLE correntista (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL
);

CREATE TABLE conta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tipo VARCHAR(20) NOT NULL,
  saldo DOUBLE,
  limite DOUBLE,
  correntista_id BIGINT,
  FOREIGN KEY (correntista_id) REFERENCES correntista(id)
);

CREATE TABLE transacao (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  conta_id BIGINT,
  tipo VARCHAR(20),
  valor DOUBLE,
  data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (conta_id) REFERENCES conta(id)
);

📡 Endpoints principais
Método	Endpoint	Descrição
POST	/correntistas	Cadastrar correntista
GET	/correntistas	Listar correntistas
POST	/contas/corrente	Criar conta corrente
POST	/contas/poupanca	Criar conta poupança
POST	/contas/{id}/deposito	Realizar depósito
POST	/contas/{id}/saque	Realizar saque
POST	/contas/poupanca/{id}/rendimento?taxa=X	Aplicar rendimento (poupança)
🧪 Exemplos de requisições (JSON)
➕ Criar correntista

POST http://localhost:8080/correntistas

{
  "nome": "João Silva"
}

🏦 Criar conta corrente

POST http://localhost:8080/contas/corrente

{
  "correntistaId": 1,
  "saldoInicial": 1000.0,
  "limite": 500.0
}

💰 Criar conta poupança

POST http://localhost:8080/contas/poupanca

{
  "correntistaId": 1,
  "saldoInicial": 2000.0
}

💵 Fazer depósito

POST http://localhost:8080/contas/1/deposito

{
  "valor": 300.0
}

💸 Fazer saque

POST http://localhost:8080/contas/1/saque

{
  "valor": 150.0
}

📈 Aplicar rendimento na poupança

POST http://localhost:8080/contas/poupanca/1/rendimento?taxa=0.05

📂 Estrutura do projeto
src/
 ├─ main/java/br/com/cooperativa/coopbank_api/
 │   ├─ controller/        # Controladores REST
 │   ├─ model/             # Entidades JPA
 │   ├─ repository/        # Interfaces JPA
 │   └─ CoopbankApiApplication.java
 └─ resources/
     ├─ application.properties
     └─ schema.sql (opcional)

💡 Observações

O banco H2 é reiniciado a cada execução (por ser em memória).

Todos os endpoints podem ser testados via Postman ou Insomnia.

O projeto segue as boas práticas REST e separação de responsabilidades.

✍️ Autor: Flávio J. S.

📅 Ano: 2025
