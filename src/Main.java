import dao.LancamentoDAO;
import dao.UsuarioDAO;
import factory.ConnectionFactory;
import modelo.Lancamento;
import modelo.TipoTransacao;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public static void main(String[] args) throws SQLException {
    ConnectionFactory factory = new  ConnectionFactory();
    Connection conexao = factory.recuperarConexao();
    UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
    LancamentoDAO lancamentoDAO = new LancamentoDAO(conexao);
//SALVAR USUARIOS
//    Usuario novousuario = new Usuario(null, "Klaus", "klaus@gmail.com", "klaus123");
//
//    usuarioDAO.salvar(novousuario);


//LISTAR USUARIOS
//    List<Usuario> meusUsuarios = usuarioDAO.listarTodos();
//
//    for (Usuario usuario : meusUsuarios) {
//        System.out.println(usuario.getEmail());

//    usuarioDAO.deletar(1);

//ATUALIZAR USUARIOS
//    Usuario usuarioEditado = new Usuario(2, "Nadson Klaus", "klausplima@gmail.com", "klaus321");
//
//    usuarioDAO.atualizar(usuarioEditado);
//
//    List<Usuario> meusUsuarios = usuarioDAO.listarTodos();
//
//    System.out.println("--- Lista de Usuários Atualizada ---");
//    for (Usuario usuario : meusUsuarios) {
//        System.out.println("ID: " + usuario.getIdUsuario() + " | Nome: " + usuario.getNome());
//    }

//NOVO LACAMENTO
//    Usuario donoDoLancamento = new Usuario(2, "Nadson Klaus", "klausplima@gmail.com", "klaus321");
//    Lancamento novaDespesa = new Lancamento(
//            "Compra de um teclado mecânico",
//            new java.math.BigDecimal("250"),
//            java.time.LocalDate.now(),
//            TipoTransacao.DESPESA,
//            "Eletrônicos",
//            donoDoLancamento
//    );
//
//    lancamentoDAO.salvar(novaDespesa);
//    System.out.println("Lançamento efetuado com sucesso!");

//LISTAR LANCAMENTOS
//    List<Lancamento> meusLancamentos = lancamentoDAO.listarTodos();
//
//    System.out.println("--- Minhas Movimentações Financeiras ---");
//    for (Lancamento lancamento : meusLancamentos) {
//        System.out.println("Descrição: " + lancamento.getDescricao() +
//                " | Valor: R$ " + lancamento.getValor() +
//                " | Data: " + lancamento.getData() +
//                " | Categoria: " + lancamento.getCategoria() +
//                " | ID do Dono: " + lancamento.getUsuario().getIdUsuario());
//    }

//DELETAR LANCAMENTO
    int idParaApagar = 1;
    lancamentoDAO.deletar(idParaApagar);

    List<Lancamento> meusLancamentos = lancamentoDAO.listarTodos();

    System.out.println("--- Lista de Lançamentos Atualizada ---");
    for (Lancamento lancamento : meusLancamentos) {
        System.out.println("ID: " + lancamento.getIdLancamento() +
                " | Descrição: " + lancamento.getDescricao() +
                " | Valor: R$ " + lancamento.getValor());
    }
    conexao.close();
}