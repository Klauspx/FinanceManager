import dao.LancamentoDAO;
import factory.ConnectionFactory;
import modelo.Lancamento;
import modelo.TipoTransacao;
import modelo.Usuario;
import service.LancamentoService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

private static void mostrarMenu(){
    System.out.println("\n=== MEU GERENCIADOR FINANCEIRO ===");
    System.out.println("1. Ver Extrato (Listar Lançamentos)");
    System.out.println("2. Cadastrar Novo Lançamento");
    System.out.println("3. Deletar Lançamento");
    System.out.println("4. Ver saldo atual");
    System.out.println("5. Editar Lançamento");
    System.out.println("6. Buscar lançcamentos por data");
    System.out.println("9. Sair do Sistema");
    System.out.print("Escolha uma opção: ");
}

private static void listarLancamentosPorUsuario(Scanner teclado, LancamentoService lancamentoService){
    System.out.print("\nDigite o ID do usuário: ");
    int idusuario = teclado.nextInt();
    teclado.nextLine();

    List<Lancamento> meuslancamentos = lancamentoService.listarLancamentosPorUsuario(idusuario);

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
}

private static void listarLancamentosPorPeriodo(Scanner teclado, LancamentoService lancamentoService){
    System.out.print("\nDigite o ID do usuario: ");
    int idusuario = teclado.nextInt();
    teclado.nextLine();

    System.out.print("\nDigite a data de início(aaaa-mm-dd): ");
    LocalDate dataInicio = LocalDate.parse(teclado.nextLine());

    System.out.print("\nDigite a data de fim(aaaa-mm-dd): ");
    LocalDate dataFim = LocalDate.parse(teclado.nextLine());
    if (dataFim.isBefore(dataInicio)) {
        System.out.println("A data final não pode ser anterior à data inicial.");
        return;
    }

    List<Lancamento> meuslancamentos = lancamentoService.listarLancamentosPorPeriodo(idusuario, dataInicio, dataFim);

    if (meuslancamentos.isEmpty()){
        System.out.println("Nenhuma movimentação nesse período ou o usuário informado não existe.");
    }else  {
        for (Lancamento lancamento : meuslancamentos){
            System.out.println("ID: " + lancamento.getIdLancamento() +
                    " | Descrição: " + lancamento.getDescricao() +
                    " | Valor: R$ " + lancamento.getValor() +
                    " | Data: " + lancamento.getData() +
                    " | Tipo: " + lancamento.getTipo() +
                    " | Categoria: " + lancamento.getCategoria()
                );
        }
    }
    lancamentoService.resumoPeriodo(idusuario, dataInicio, dataFim);
}

private static void cadastrarLancamento (Scanner teclado, LancamentoService lancamentoService){
    System.out.println("\n--- Novo Lançamento ---");

    System.out.print("Descrição da transação: ");
    String desc = teclado.nextLine();

    System.out.print("Valor: ");
    BigDecimal valor = new BigDecimal(teclado.nextLine());

    System.out.print("Data (Formato 2026-02-26): ");
    LocalDate data = LocalDate.parse(teclado.nextLine());

    System.out.print("Tipo (RECEITA, DESPESA, INVESTIMENTO): ");
    TipoTransacao tipo = TipoTransacao.valueOf(teclado.nextLine().toUpperCase());

    System.out.print("Categoria: ");
    String cat = teclado.nextLine();

    System.out.print("ID do Usuário dono do lançamento: ");
    int idUser = teclado.nextInt();
    teclado.nextLine();

    Usuario dono = new Usuario(idUser, null, null, null);

    Lancamento novoLancamento = new Lancamento(desc, valor, data, tipo, cat, dono);

    lancamentoService.salvarLancamento(novoLancamento);
}

private static void deletarLancamento(Scanner teclado, LancamentoService lancamentoService){
    System.out.println("Digite o ID do lançamento que você quer apagar: ");
    int lancamentoId = teclado.nextInt();
    teclado.nextLine();

    lancamentoService.excluirLancamento(lancamentoId);
}

private static void verSaldo (Scanner teclado, LancamentoService lancamentoService){
    System.out.print("Digite o ID do usuário para ver o saldo: ");
    int usuarioID = teclado.nextInt();
    teclado.nextLine();

    BigDecimal saldo = lancamentoService.calcularSaldo(usuarioID);

    System.out.println("\nSaldo atual: R$ " + saldo);
}

private static void editarLancamento(Scanner teclado, LancamentoService lancamentoService){

    try {
        System.out.println("\n--- Editar Lançamento ---");

        System.out.print("ID do lançamento: ");
        int id = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Nova descrição: ");
        String desc = teclado.nextLine();

        System.out.print("Novo valor: ");
        BigDecimal valor = new BigDecimal(teclado.nextLine());

        System.out.print("Nova data (yyyy-MM-dd): ");
        LocalDate data = LocalDate.parse(teclado.nextLine());

        System.out.print("Novo tipo (RECEITA, DESPESA, INVESTIMENTO): ");
        TipoTransacao tipo = TipoTransacao.valueOf(teclado.nextLine().toUpperCase());

        System.out.print("Nova categoria: ");
        String categoria = teclado.nextLine();

        Lancamento atualizado = new Lancamento(desc, valor, data, tipo, categoria);

        lancamentoService.atualizarLancamento(atualizado);

        System.out.println("\nLançamento atualizado com sucesso!");

    } catch (IllegalArgumentException erro) {
        System.out.println("\nErro: " + erro.getMessage());
    } catch (Exception e) {
        System.out.println("\nErro inesperado. Verifique os dados digitados.");
    }
}

public static void main(String[] args) throws Exception {
    Scanner teclado = new Scanner(System.in);

    ConnectionFactory factory = new  ConnectionFactory();
    Connection conexao = factory.recuperarConexao();
    LancamentoDAO lancamentoDAO = new LancamentoDAO(conexao);
    LancamentoService lancamentoService = new LancamentoService(lancamentoDAO);

    int opcao = 0;
    
    while(opcao != 9){

        mostrarMenu();
        opcao = teclado.nextInt();
        teclado.nextLine();

        switch(opcao){
            case 1:
                listarLancamentosPorUsuario(teclado, lancamentoService);
                break;

            case 2:
                cadastrarLancamento(teclado, lancamentoService);
                break;

            case 3:
                deletarLancamento(teclado, lancamentoService);
                break;

            case 4:
                verSaldo(teclado, lancamentoService);
                break;

            case 5:
                editarLancamento(teclado, lancamentoService);
                break;

            case 6:
                listarLancamentosPorPeriodo(teclado, lancamentoService);
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