package service;

import dao.LancamentoDAO;
import modelo.Lancamento;
import modelo.TipoTransacao;

import java.math.BigDecimal;
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
}