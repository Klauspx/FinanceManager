import dao.LancamentoDAO;
import factory.ConnectionFactory;
import modelo.Lancamento;
import modelo.TipoTransacao;
import modelo.Usuario;
import service.LancamentoService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public static void main(String[] args) throws Exception {
    Scanner teclado = new Scanner(System.in);

    ConnectionFactory factory = new  ConnectionFactory();
    Connection conexao = factory.recuperarConexao();
    LancamentoDAO lancamentoDAO = new LancamentoDAO(conexao);
    LancamentoService lancamentoService = new LancamentoService(lancamentoDAO);

    int opcao = 0;

    while(opcao != 9){
        System.out.println("\n=== MEU GERENCIADOR FINANCEIRO ===");
        System.out.println("1. Ver Extrato (Listar Lançamentos)");
        System.out.println("2. Cadastrar Novo Lançamento");
        System.out.println("3. Deletar Lançamento");
        System.out.println("4. Ver saldo atual");
        System.out.println("9. Sair do Sistema");
        System.out.print("Escolha uma opção: ");

        opcao = teclado.nextInt();
        teclado.nextLine();

        switch(opcao){
            case 1:
                System.out.println("\n--- Seu Extrato ---");
                List<Lancamento> meuslancamentos = lancamentoDAO.listarTodos();
                if (meuslancamentos.isEmpty()){
                    System.out.println("Você ainda não tem nenhuma movimentação cadastrada.");
                }else {
                    for (Lancamento lancamento : meuslancamentos){
                        System.out.println("ID: " + lancamento.getIdLancamento() +
                                " | Descrição: " + lancamento.getDescricao() +
                                " | Valor: R$ " + lancamento.getValor() +
                                " | Data: " + lancamento.getData() +
                                " | Tipo: " + lancamento.getTipo());
                    }
                }
                break;

            case 2:
                System.out.println("\n--- Novo Lançamento ---");

                System.out.print("Descrição da transação: ");
                String desc = teclado.nextLine();

                System.out.print("Valor: ");
                java.math.BigDecimal valor = new java.math.BigDecimal(teclado.nextLine());

                System.out.print("Data (Formato 2026-02-26): ");
                java.time.LocalDate data = java.time.LocalDate.parse(teclado.nextLine());

                System.out.print("Tipo (RECEITA, DESPESA, INVESTIMENTO): ");
                TipoTransacao tipo = TipoTransacao.valueOf(teclado.nextLine().toUpperCase());

                System.out.print("Categoria: ");
                String cat = teclado.nextLine();

                System.out.print("ID do Usuário dono do lançamento: ");
                int idUser = teclado.nextInt();
                teclado.nextLine();

                Usuario dono = new Usuario(idUser, null, null, null);

                Lancamento novoLancamento = new Lancamento(desc, valor, data, tipo, cat, dono);

                lancamentoDAO.salvar(novoLancamento);
                break;

            case 3:
                System.out.println("Digite o ID do lançamento que você quer apagar: ");
                int id = teclado.nextInt();
                teclado.nextLine();
                lancamentoDAO.deletar(id);
                break;

            case 4:
                System.out.print("Digite o ID do usuário: ");
                int idUserSaldo = teclado.nextInt();
                teclado.nextLine();

                BigDecimal saldo = lancamentoService.calcularSaldo(idUserSaldo);

                System.out.println("Saldo atual: R$ " + saldo);
                break;
            case 9:
                System.out.println("\nSaindo... Até logo!");
                break;
            default:
                System.out.println("\nOpção inválida! Digite 1, 2, 3 ou 9.");
        }
    }
    teclado.close();
    conexao.close();
}