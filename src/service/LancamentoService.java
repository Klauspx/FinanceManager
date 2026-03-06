package service;

import dao.LancamentoDAO;
import modelo.Lancamento;
import modelo.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class LancamentoService {

    private LancamentoDAO lancamentoDAO;

    public LancamentoService(LancamentoDAO lancamentoDAO) {
        this.lancamentoDAO = lancamentoDAO;
    }

    public BigDecimal calcularSaldo(int usuarioId) {
        BigDecimal saldo = BigDecimal.ZERO;

        List<Lancamento> lancamentos = lancamentoDAO.buscarPorUsuario(usuarioId);

        for (Lancamento lancamento : lancamentos) {
            if (lancamento.getTipo() == TipoTransacao.RECEITA) {
                saldo = saldo.add(lancamento.getValor());

            } else if (lancamento.getTipo() == TipoTransacao.DESPESA) {
                saldo = saldo.subtract(lancamento.getValor());
            }
        }
        return saldo;
    }

    public List<Lancamento> listarLancamentosPorUsuario (int usuarioId) {
        return lancamentoDAO.buscarPorUsuario(usuarioId);
    }

    public List<Lancamento> listarLancamentosPorPeriodo(int usuarioId, LocalDate dataInicio, LocalDate dataFim) {
        return lancamentoDAO.buscarPorPeriodo(usuarioId, dataInicio, dataFim);
    }

    public void resumoPeriodo(int usuarioId, LocalDate inicio, LocalDate fim){

        List<Lancamento> lista = lancamentoDAO.buscarPorPeriodo(usuarioId, inicio, fim);

        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        for(Lancamento l : lista){

            if(l.getTipo() == TipoTransacao.RECEITA){
                totalReceitas = totalReceitas.add(l.getValor());
            }

            if(l.getTipo() == TipoTransacao.DESPESA){
                totalDespesas = totalDespesas.add(l.getValor());
            }
        }

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        System.out.println("\n--- Resumo do período ---");
        System.out.println("Total de Receitas: R$ " + totalReceitas);
        System.out.println("Total de Despesas: R$ " + totalDespesas);
        System.out.println("Saldo do período: R$ " + saldo);
    }

    public void resumoPorCategoria (int usuarioID){

        List<Lancamento> lancamentos = lancamentoDAO.buscarPorUsuario(usuarioID);

        Map<String, BigDecimal> resumo = new HashMap<String,BigDecimal>();

                for( Lancamento lancamento : lancamentos){
                    String categoria = lancamento.getCategoria();
                    BigDecimal valor = lancamento.getValor();

                    if (!resumo.containsKey(categoria)){
                        resumo.put(categoria, valor);

                    }else {
                        BigDecimal valorAtual = resumo.get(categoria);
                        BigDecimal novoTotal = valorAtual.add(valor);

                        resumo.put(categoria, novoTotal);
                    }
                }
        System.out.println("\n--- Resumo por Categoria ---");

        for (String categoria : resumo.keySet()) {

            BigDecimal total = resumo.get(categoria);

            System.out.println(categoria + " : R$ " + total);
        }
    }

    public void resumoMensal (int usuarioID, int mes, int ano){

        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

        List<Lancamento> lista = lancamentoDAO.buscarPorPeriodo(usuarioID, dataInicio, dataFim);

        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;
        BigDecimal totalInvestimentos = BigDecimal.ZERO;

        for(Lancamento lancamento : lista){
            if(lancamento.getTipo() == TipoTransacao.RECEITA){
                totalReceitas = totalReceitas.add(lancamento.getValor());

            }else if(lancamento.getTipo() == TipoTransacao.DESPESA){
                totalDespesas = totalDespesas.add(lancamento.getValor());

            }else if(lancamento.getTipo() == TipoTransacao.INVESTIMENTO){
                totalInvestimentos = totalInvestimentos.add(lancamento.getValor());
            }
        }

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        Month mesNome = Month.of(mes);
        String nomeMes = mesNome.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

        System.out.println("\n--- Resumo do Mês de " + nomeMes + " de " + ano + " ---");
        System.out.println("Total de Receitas: R$ " + totalReceitas);
        System.out.println("Total de Despesas: R$ " + totalDespesas);
        System.out.println("Investimentos: R$ " + totalInvestimentos);
        System.out.println("Saldo final do mês: R$ " + saldo);
    }

    public void salvarLancamento (Lancamento lancamento) {
        this.lancamentoDAO.salvar(lancamento);
    }

    public void excluirLancamento (int lancamentoId) {
        this.lancamentoDAO.deletar(lancamentoId);
    }

    public void atualizarLancamento(Lancamento lancamento) {

        if (lancamento == null) {
            throw new IllegalArgumentException("Lançamento não pode ser nulo.");
        }

        if (lancamento.getDescricao() == null || lancamento.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        }

        if (lancamento.getValor() == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo.");
        }

        if (lancamento.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero.");
        }

        if (lancamento.getData() == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }

        if (lancamento.getData().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data não pode ser futura.");
        }

        if (lancamento.getTipo() == null) {
            throw new IllegalArgumentException("Tipo da transação é obrigatório.");
        }

        if (lancamento.getCategoria() == null || lancamento.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria não pode ser vazia.");
        }

        this.lancamentoDAO.atualizar(lancamento);
    }
}