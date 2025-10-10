# CoopBank API 🏦

API RESTful em Spring Boot e H2 para gerenciamento de correntistas, contas e transações bancárias.

## 🚀 Tecnologias
- Java 8+
- Spring Boot 2.7+
- Spring Data JPA / Hibernate
- Banco de Dados H2 (em memória)
- Maven

## ⚙️ Como rodar

```bash
mvn clean package
mvn spring-boot:run

Acesse a API: http://localhost:8080

Console H2: http://localhost:8080/h2-console

Credenciais H2:

JDBC URL: jdbc:h2:mem:coopbank

Usuário: sa

Senha: (vazio)

📡 Endpoints principais
Método	Endpoint	Descrição
POST	/correntistas	Cria correntista
GET	/correntistas	Lista todos os correntistas
POST	/contas/corrente?correntistaId=X&saldo=Y&limite=Z	Cria conta corrente
POST	/contas/poupanca?correntistaId=X&saldo=Y	Cria conta poupança
POST	/contas/{id}/deposito?valor=X	Deposita valor na conta
POST	/contas/{id}/saque?valor=X	Saca valor da conta
POST	/contas/poupanca/{id}/rendimento?taxa=X	Aplica rendimento mensal na poupança

Observação:

Conta Corrente permite saque até saldo + limite.

Conta Poupança permite saque apenas até o saldo.

Juros em conta corrente ainda não implementados.

📦 Exemplo de uso (JSON)
Criar correntista
POST /correntistas
{
  "nome": "João Silva"
}

Criar conta corrente
POST /contas/corrente?correntistaId=1&saldo=100&limite=200

Criar conta poupança
POST /contas/poupanca?correntistaId=1&saldo=500

Depósito
POST /contas/1/deposito?valor=100

Saque
POST /contas/1/saque?valor=50

Aplicar rendimento na poupança
POST /contas/poupanca/2/rendimento?taxa=0.05

Correntista
   └─ id, nome
   └─ possui várias Contas
          ├─ Conta Corrente
          │     ├─ id, saldo, limite
          │     └─ operações:
          │           ├─ Depósito
          │           └─ Saque (até saldo + limite)
          └─ Conta Poupança
                ├─ id, saldo
                └─ operações:
                      ├─ Depósito
                      ├─ Saque (até saldo)
                      └─ Aplicar rendimento (mensal, taxa X)

