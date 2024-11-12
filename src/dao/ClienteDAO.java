package dao;

import connection.ConexaoMySQL;
import exception.PessoaException;
import model.Cliente;
import model.Pessoa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteDAO implements DAO<Cliente> {

    // Método para selecionar todos os clientes do banco de dados
    @Override
    public ArrayList<Cliente> selecionar() throws PessoaException {
        try {
            // Comando SQL para buscar dados de cliente e pessoa (tabelas unidas)
            String sql = "SELECT " +
                    "c.id, p.nome_completo, p.data_nascimento, p.documento, p.pais, p.estado, p.cidade, c.fidelidade, c.observacao, c.id_pessoa " +
                    "FROM cliente c " +
                    "JOIN pessoa p ON p.id = c.id";
            Statement declaracao = ConexaoMySQL.get().createStatement();  // Criação da declaração SQL
            ResultSet resultado = declaracao.executeQuery(sql);  // Execução do comando SQL

            // Lista para armazenar os clientes retornados
            ArrayList<Cliente> clientes = new ArrayList<>();
            while(resultado.next()) {  // Para cada linha no resultado da consulta
                // Criação de um objeto Cliente com os dados retornados
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
                // Adiciona o cliente à lista
                clientes.add(cliente);
            }
            return clientes;  // Retorna a lista de clientes

        } catch (SQLException e) {
            throw new PessoaException("Erro ao selecionar clientes: " + e.getMessage());  // Trata erros de SQL
        }
    }

    // Método para inserir um novo cliente no banco de dados
    @Override
    public Boolean inserir(Cliente cliente) throws PessoaException {
        try {
            // Criação de um objeto PessoaDAO para inserir dados na tabela pessoa
            PessoaDAO pessoaDAO = new PessoaDAO();
            pessoaDAO.inserir(cliente);  // Insere os dados da pessoa associada ao cliente

            // Recupera o último ID de pessoa inserido
            Pessoa pessoaInserida = pessoaDAO.selecionarUltima();

            // Comando SQL para inserir um novo cliente com base no ID da pessoa
            String sql = "INSERT INTO cliente " +
                    "(id_pessoa, fidelidade, observacao) " +
                    "VALUES (?,?,?)";

            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);  // Prepara o comando SQL
            preparacao.setLong(1, pessoaInserida.getId());  // Define o ID da pessoa
            preparacao.setBoolean(2, cliente.getFidelidade());  // Define o status de fidelidade
            preparacao.setString(3, cliente.getObservacao());  // Define a observação do cliente

            return preparacao.executeUpdate() > 0;  // Executa a inserção e retorna true se bem-sucedido

        } catch (Exception e) {
            throw new PessoaException("Erro ao inserir cliente: " + e.getMessage());  // Trata exceções
        }
    }

    // Método para atualizar os dados de um cliente existente
    @Override
    public Boolean atualizar(Cliente cliente) throws PessoaException {
        try {
            // Atualiza os dados da pessoa associada ao cliente
            Pessoa pessoa = new Pessoa(cliente.getIdPessoa(), cliente.getNomeCompleto(), cliente.getDataNascimento(), cliente.getDocumento(), cliente.getPais(), cliente.getEstado(), cliente.getCidade());
            PessoaDAO pessoaDAO = new PessoaDAO();
            pessoaDAO.atualizar(pessoa);  // Atualiza os dados da pessoa

            // Comando SQL para atualizar as informações de fidelidade e observação do cliente
            String sql = "UPDATE cliente " +
                    "SET fidelidade = ?, observacao = ? " +
                    "WHERE id = ?";

            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);  // Prepara o comando SQL
            preparacao.setBoolean(1, cliente.getFidelidade());  // Define o novo valor de fidelidade
            preparacao.setString(2, cliente.getObservacao());  // Define a nova observação
            preparacao.setLong(3, cliente.getId());  // Define o ID do cliente a ser atualizado

            return preparacao.executeUpdate() > 0;  // Executa a atualização e retorna true se bem-sucedido

        } catch (Exception e) {
            e.printStackTrace();  // Imprime o erro no console para depuração
            throw new PessoaException("Erro ao atualizar cliente: " + e.getMessage());  // Trata exceções
        }
    }

    // Método para deletar um cliente pelo seu ID
    @Override
    public Boolean deletar(Long id) throws PessoaException {
        try {
            // Comando SQL para deletar o cliente pelo seu ID
            String sql = "DELETE FROM cliente WHERE id = ?";
            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);  // Prepara o comando SQL
            preparacao.setLong(1, id);  // Define o ID do cliente a ser deletado

            return preparacao.executeUpdate() > 0;  // Executa a deleção e retorna true se bem-sucedido

        } catch (Exception e) {
            throw new PessoaException("Erro ao deletar cliente: " + e.getMessage());  // Trata exceções
        }
    }

    // Método para selecionar um cliente pelo seu ID
    @Override
    public Cliente selecionarPorId(Long id) throws PessoaException {
        try {
            // Comando SQL para buscar um cliente específico pelo seu ID
            String sql = "SELECT c.id, c.fidelidade, c.observacao, " +
                    "c.id_pessoa, p.nome_completo, p.data_nascimento, p.documento, p.pais, p.estado, p.cidade " +
                    "FROM cliente c " +
                    "JOIN pessoa p ON p.id = c.id_pessoa " +
                    "WHERE c.id = ?";
            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);  // Prepara o comando SQL
            preparacao.setLong(1, id);  // Define o ID do cliente a ser consultado
            ResultSet resultado = preparacao.executeQuery();  // Executa a consulta SQL

            // Se encontrar o cliente, cria e retorna o objeto Cliente
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
                return null;  // Se não encontrar, retorna null
            }

        } catch (Exception e) {
            throw new PessoaException("Erro ao selecionar cliente por ID: " + e.getMessage());  // Trata exceções
        }
    }
}
