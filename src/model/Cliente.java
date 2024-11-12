package model;

import util.Util;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente extends Pessoa {

    // Atributos
    private Long id; // Identificador do cliente
    private Boolean fidelidade; // Indica se o cliente é fiel (obrigatório)
    private String observacao; // Comentários adicionais (opcional)

    // Construtor vazio (não faz nada)
    public Cliente(long id, String nomeCompleto, LocalDate dataNascimento, String documento, String pais, String estado, String cidade, boolean fidelidade, String observacao) {}

    // Construtor sem o atributo 'id'
    public Cliente(
            String nomeCompleto,
            LocalDate dataNascimento,
            String documento,
            String pais,
            String estado,
            String cidade,
            Boolean fidelidade,
            String observacao
    ) {
        // Chama o construtor da classe 'Pessoa' para inicializar os atributos comuns
        super(nomeCompleto, dataNascimento, documento, pais, estado, cidade);
        this.fidelidade = fidelidade;
        this.observacao = observacao;
    }

    // Construtor com todos os atributos, incluindo 'id' e 'idPessoa'
    public Cliente(
            Long id,
            String nomeCompleto,
            LocalDate dataNascimento,
            String documento,
            String pais,
            String estado,
            String cidade,
            Long idPessoa,
            Boolean fidelidade,
            String observacao
    ) {
        // Chama o construtor da classe 'Pessoa' para inicializar os atributos comuns
        super(idPessoa, nomeCompleto, dataNascimento, documento, pais, estado, cidade);
        this.id = id;
        this.fidelidade = fidelidade;
        this.observacao = observacao;
    }

    // Getters e Setters

    @Override
    public Long getId() {
        return id;
    }

    // Retorna o 'idPessoa' da classe pai (Pessoa)
    public Long getIdPessoa() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    // Define o 'idPessoa' na classe pai (Pessoa)
    public void setIdPessoa(Long idPessoa) {
        super.setId(idPessoa);
    }

    public Boolean getFidelidade() {
        return fidelidade;
    }

    public void setFidelidade(Boolean fidelidade) {
        this.fidelidade = fidelidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    // Método que retorna uma representação em texto do cliente
    @Override
    public String toString() {
        return "Id: " + id +
                "\n | Nome completo: " + super.getNomeCompleto() +
                "\n | Data de nascimento: " + Util.formatarDataBR(super.getDataNascimento()) +
                "\n | Documento: " + super.getDocumento() +
                "\n | País: " + super.getPais() +
                "\n | Estado: " + super.getEstado() +
                "\n | Cidade: " + super.getCidade() +
                "\n | Fidelidade: " + ((fidelidade) ? "Sim" : "Não") +
                "\n | Observação: " + observacao + "\n\n";
    }

    // Método para comparar se dois objetos 'Cliente' são iguais
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Cliente cliente = (Cliente) o;

        // Verifica se os IDs são iguais
        return Objects.equals(id, cliente.id);
    }

    // Método para gerar o código hash do objeto 'Cliente'
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

}
