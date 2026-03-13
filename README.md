💰 Gerenciador Financeiro em Java

Este é um sistema de gerenciamento financeiro desenvolvido em **Java** para controle de receitas, despesas e investimentos.

O sistema permite cadastrar usuários, registrar lançamentos financeiros e visualizar relatórios como saldo atual, extrato e resumo mensal.

Este projeto foi desenvolvido com o objetivo de praticar **programação orientada a objetos, JDBC, arquitetura em camadas e manipulação de banco de dados**.

<br>

🚀 Funcionalidades

👤 Usuários
- Cadastrar usuário
- Editar usuário
- Deletar usuário
- Listar usuários

💰 Lançamentos Financeiros
- Cadastrar lançamento
- Editar lançamento
- Deletar lançamento
- Listar lançamentos (extrato)
- Buscar lançamentos por período

📊 Relatórios
- Ver saldo atual
- Resumo financeiro mensal
- Resumo por categoria



🛠 Tecnologias Utilizadas

- **Java**
- **JDBC**
- **PostgreSQL**
- **Programação Orientada a Objetos**
- **Arquitetura em Camadas (DAO, Service, Model)**



📂 Estrutura do Projeto

src<br>
├── dao<br>
│ ├── UsuarioDAO<br>
│ └── LancamentoDAO<br>
│<br>
├── model<br>
│ ├── Usuario<br>
│ ├── Lancamento<br>
│ └── TipoTransacao<br>
│<br>
├── service<br>
│ └── LancamentoService<br>
│<br>
├── factory<br>
│ └── ConnectionFactory<br>
│<br>
└── Main.java<br>

▶️ Como Executar o Projeto

1️⃣ Clone o repositório

2️⃣ Configure o banco de dados PostgreSQL.

3️⃣ Crie as tabelas necessárias no banco.

Exemplo de tabela de usuários:

CREATE TABLE usuarios (<br>
    id SERIAL PRIMARY KEY,<br>
    nome VARCHAR(100),<br>
    email VARCHAR(100),<br>
    senha VARCHAR(100)<br>
);<br>

Exemplo de tabela de lançamentos:

CREATE TABLE lancamentos (<br>
    id SERIAL PRIMARY KEY,<br>
    descricao VARCHAR(100),<br>
    valor NUMERIC,<br>
    data DATE,<br>
    tipo VARCHAR(20),<br>
    categoria VARCHAR(50),<br>
    usuario_id INT,<br>
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)<br>
);

4️⃣ Execute a classe Main.

📚 Aprendizados
- Durante o desenvolvimento deste projeto foram praticados:
- Conexão com banco de dados usando JDBC
- Arquitetura em camadas
- Manipulação de dados financeiros com BigDecimal
- Manipulação de datas com LocalDate
- Uso de Enums
- Criação de relatórios financeiros

👨‍💻 Autor
Desenvolvido por Nadson Klaus.
