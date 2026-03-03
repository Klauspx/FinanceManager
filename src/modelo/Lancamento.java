package modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Lancamento {
    private Integer idLancamento;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private TipoTransacao tipo;
    private String categoria;
    private Usuario usuario;

    public Lancamento(String descricao, BigDecimal valor, LocalDate data, TipoTransacao tipo, String categoria, Usuario usuario) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
        this.usuario = usuario;
    }
    public Lancamento(String descricao, BigDecimal valor, LocalDate data, TipoTransacao tipo, String categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public Integer getIdLancamento() {
        return idLancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setIdLancamento(Integer idLancamento) {
        this.idLancamento = idLancamento;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
