package service;

import dao.LancamentoDAO;
import modelo.Lancamento;
import modelo.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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