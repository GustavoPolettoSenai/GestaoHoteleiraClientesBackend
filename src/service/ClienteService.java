package service;

import dao.ClienteDAO;
import exception.PessoaException;
import model.Cliente;

import java.util.ArrayList;

public class ClienteService {

    // Atributo que armazena o DAO para acesso aos dados dos clientes
    private ClienteDAO clienteDAO;

    // Construtor que inicializa o DAO
    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = new ClienteDAO();
    }

    // Método para listar todos os clientes
    public ArrayList<Cliente> listarClientes() throws PessoaException {
        try {
            // Chama o DAO para selecionar todos os clientes
            return clienteDAO.selecionar();
        } catch (PessoaException e) {
            e.printStackTrace();
            throw new PessoaException("Erro ao listar clientes: " + e.getMessage());
        }
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscarClientePorId(Long id) throws PessoaException {
        // Valida se o ID é nulo ou inválido
        if (id == null || id <= 0) {
            throw new PessoaException("ID inválido para busca.");
        }
        // Busca o cliente pelo ID no DAO
        Cliente cliente = clienteDAO.selecionarPorId(id);
        // Lança uma exceção se o cliente não for encontrado
        if (cliente == null) {
            throw new PessoaException("Cliente com ID " + id + " não encontrado.");
        }
        return cliente;
    }

    // Método para adicionar um novo cliente
    public Boolean adicionarCliente(Cliente cliente) throws PessoaException {
        // Valida os dados do cliente
        validarCliente(cliente);
        // Chama o DAO para inserir o cliente
        return clienteDAO.inserir(cliente);
    }

    // Método para atualizar os dados de um cliente
    public Boolean atualizarCliente(Cliente cliente) throws PessoaException {
        // Valida se o ID do cliente é nulo ou inválido
        if (cliente.getId() == null || cliente.getId() <= 0) {
            throw new PessoaException("ID do cliente inválido para atualização.");
        }

        // Busca o cliente existente no DAO pelo ID
        Cliente clienteEscolhido = clienteDAO.selecionarPorId(cliente.getId());
        // Lança uma exceção se o cliente não for encontrado
        if (clienteEscolhido == null) {
            throw new PessoaException("ID do cliente não encontrado.");
        }

        // Atualiza o ID da pessoa no objeto cliente
        cliente.setIdPessoa(clienteEscolhido.getIdPessoa());

        // Valida os dados do cliente antes de atualizar
        validarCliente(cliente);
        // Chama o DAO para atualizar o cliente
        return clienteDAO.atualizar(cliente);
    }

    // Método para deletar um cliente pelo ID
    public Boolean deletarCliente(Long id) throws PessoaException {
        // Valida se o ID é nulo ou inválido
        if (id == null || id <= 0) {
            throw new PessoaException("ID inválido para exclusão.");
        }
        // Chama o DAO para deletar o cliente
        return clienteDAO.deletar(id);
    }

    // Método privado para validar os dados do cliente
    private void validarCliente(Cliente cliente) throws PessoaException {
        // Verifica se o nome completo é nulo ou vazio
        if (cliente.getNomeCompleto() == null || cliente.getNomeCompleto().isEmpty()) {
            throw new PessoaException("Nome completo é obrigatório.");
        }
        // Verifica se a data de nascimento é nula
        if (cliente.getDataNascimento() == null) {
            throw new PessoaException("Data de nascimento é obrigatória.");
        }
        // Verifica se o documento é nulo ou vazio
        if (cliente.getDocumento() == null || cliente.getDocumento().isEmpty()) {
            throw new PessoaException("Documento é obrigatório.");
        }
        // Verifica se o país é nulo ou vazio
        if (cliente.getPais() == null || cliente.getPais().isEmpty()) {
            throw new PessoaException("País é obrigatório.");
        }
        // Verifica se o estado é nulo ou vazio
        if (cliente.getEstado() == null || cliente.getEstado().isEmpty()) {
            throw new PessoaException("Estado é obrigatório.");
        }
        // Verifica se a cidade é nula ou vazia
        if (cliente.getCidade() == null || cliente.getCidade().isEmpty()) {
            throw new PessoaException("Cidade é obrigatória.");
        }
        // Verifica se o campo fidelidade é nulo
        if (cliente.getFidelidade() == null) {
            throw new PessoaException("O campo fidelidade é obrigatório.");
        }
    }
}
