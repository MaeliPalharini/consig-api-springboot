# API de Consignado 💰🚀

Bem-vindo à API de Consignado! 🎉 Aqui você pode simular solicitações de aprovação de crédito e empréstimos consignados, além de gerenciar essas solicitações de forma eficiente.

## 🚀 Recursos da API
✅ Solicitação de Aprovação – Verifica se um cliente pode solicitar um empréstimo.

✅ Solicitação de Empréstimo – Simula a contratação de um empréstimo consignado.

✅ Listagem de Aprovações – Consulta todas as aprovações de crédito disponíveis.

✅ Atualização de Solicitações – Permite atualizar os dados de uma solicitação.

✅ Cancelamento de Solicitações – Cancela e remove um pedido de empréstimo.

## 🛠 Tecnologias Utilizadas
    🔹 Java 17 
    🔹 Spring Boot 
    🔹 Spring Data JPA 
    🔹 Jakarta Validation 
    🔹 H2 Database (em memória) 
    🔹 Lombok 
    🔹 Gradle 

## 🔥 Como Rodar a API Localmente?
1️⃣ Clone este repositório:

    git clone https://github.com/seu-usuario/api-consignado.git
    cd api-consignado

2️⃣ Rode o projeto com Gradle:

    ./gradlew bootRun

3️⃣ Acesse no navegador ou via Insomnia/Postman:

    http://localhost:8080/api

## 📌 Exemplo de Requisição
Solicitar Aprovação
📍 POST /api/solicitar-aprovacao
📄 Body (JSON):

    {
    "cpf": "15345678900",
    "salario": 5000,
    "nome": "Maeli",
    "idade": 30,
    "vinculoEmpregaticio": "CLT",
    "temContaAtiva": true,
    "gerente": "Lucas Pedro"
    }

📥 Respostas:

✅ 202 Accepted → Aprovado

❌ 409 Conflict → CPF já cadastrado

❌ 400 Bad Request → Dados inválidos

## 🛠 Ferramentas para Teste
Insomnia/Postman – Para testar os endpoints.

H2 Database – Acesse os dados em http://localhost:8080/h2-console

## 📢 Contribuição
💡 Quer contribuir? Fique à vontade para abrir uma issue ou um pull request.

🔗 Contato: maelipalharini@hotmail.com