package dao;

import connection.ConexaoMySQL;
import exception.PessoaException;
import model.Cliente;
import model.Pessoa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteDAO implements DAO<Cliente> {

    @Override
    public ArrayList<Cliente> selecionar() throws PessoaException {
        try {
            String sql = "SELECT " +
                    "c.id, p.nome_completo, p.data_nascimento, p.documento, p.pais, p.estado, p.cidade, c.fidelidade, c.observacao, c.id_pessoa " +
                    "FROM cliente c " +
                    "JOIN pessoa p ON p.id = c.id";
            Statement declaracao = ConexaoMySQL.get().createStatement();
            ResultSet resultado = declaracao.executeQuery(sql);

            ArrayList<Cliente> clientes = new ArrayList<>();
            while(resultado.next()) {
                Cliente cliente = new Cliente(
                        resultado.getLong("id"),
                        resultado.getString("nome_completo"),
                        resultado.getDate("data_nascimento").toLocalDate(),
                        resultado.getString("documento"),
                        resultado.getString("pais"),
                        resultado.getString("estado"),
                        resultado.getString("cidade"),
                        resultado.getLong("id_pessoa"),
                        resultado.getBoolean("fidelidade"),
                        resultado.getString("observacao")
                );
                clientes.add(cliente);
            }
            return clientes;

        } catch (SQLException e) {
            throw new PessoaException("Erro ao selecionar clientes: " + e.getMessage());
        }
    }

    @Override
    public Boolean inserir(Cliente cliente) throws PessoaException {
        try {
            PessoaDAO pessoaDAO = new PessoaDAO();
            pessoaDAO.inserir(cliente);

            Pessoa pessoaInserida = pessoaDAO.selecionarUltima();

            String sql = "INSERT INTO cliente " +
                    "(id_pessoa, fidelidade, observacao) " +
                    "VALUES (?,?,?)";

            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setLong(1, pessoaInserida.getId());
            preparacao.setBoolean(2, cliente.getFidelidade());
            preparacao.setString(3, cliente.getObservacao());
            return preparacao.executeUpdate() > 0;

        } catch (Exception e) {
            throw new PessoaException("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    @Override
    public Boolean atualizar(Cliente cliente) throws PessoaException {
        try {
            Pessoa pessoa = new Pessoa(cliente.getIdPessoa(), cliente.getNomeCompleto(), cliente.getDataNascimento(), cliente.getDocumento(), cliente.getPais(), cliente.getEstado(), cliente.getCidade());
            PessoaDAO pessoaDAO = new PessoaDAO();
            pessoaDAO.atualizar(pessoa);


            String sql = "UPDATE cliente " +
                         "SET fidelidade = ?, observacao = ? " +
                         "WHERE id = ?";

            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setBoolean(1, cliente.getFidelidade());
            preparacao.setString(2, cliente.getObservacao());
            preparacao.setLong(3, cliente.getId());
            return preparacao.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PessoaException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public Boolean deletar(Long id) throws PessoaException {
        try {
            String sql = "DELETE FROM cliente WHERE id = ?";
            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setLong(1, id);
            return preparacao.executeUpdate() > 0;

        } catch (Exception e) {
            throw new PessoaException("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente selecionarPorId(Long id) throws PessoaException {
        try {
            String sql = "SELECT c.id, c.fidelidade, c.observacao, " +
                          "c.id_pessoa, p.nome_completo, p.data_nascimento, p.documento, p.pais, p.estado, p.cidade " +
                          "FROM cliente c " +
                          "JOIN pessoa p ON p.id = c.id_pessoa " +
                          "WHERE c.id = ?";
            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setLong(1, id);
            ResultSet resultado = preparacao.executeQuery();

            if(resultado.next()) {
                return new Cliente(
                        resultado.getLong("id"),
                        resultado.getString("nome_completo"),
                        resultado.getDate("data_nascimento").toLocalDate(),
                        resultado.getString("documento"),
                        resultado.getString("pais"),
                        resultado.getString("estado"),
                        resultado.getString("cidade"),
                        resultado.getLong("id_pessoa"),
                        resultado.getBoolean("fidelidade"),
                        resultado.getString("observacao")
                );
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new PessoaException("Erro ao selecionar cliente por ID: " + e.getMessage());
        }
    }
}
