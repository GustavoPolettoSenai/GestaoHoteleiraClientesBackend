package service;

import dao.ClienteDAO;
import exception.PessoaException;
import model.Cliente;

import java.util.ArrayList;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = new ClienteDAO();
    }

    public ArrayList<Cliente> listarClientes() throws PessoaException {
        try {
            return clienteDAO.selecionar();
        } catch (PessoaException e) {
            e.printStackTrace();
            throw new PessoaException("Erro ao listar clientes: " + e.getMessage());
        }
    }

    public Cliente buscarClientePorId(Long id) throws PessoaException {
        if (id == null || id <= 0) {
            throw new PessoaException("ID inválido para busca.");
        }
        Cliente cliente = clienteDAO.selecionarPorId(id);
        if (cliente == null) {
            throw new PessoaException("Cliente com ID " + id + " não encontrado.");
        }
        return cliente;
    }

    public Boolean adicionarCliente(Cliente cliente) throws PessoaException {
        validarCliente(cliente);
        return clienteDAO.inserir(cliente);
    }

    public Boolean atualizarCliente(Cliente cliente) throws PessoaException {
        if (cliente.getId() == null || cliente.getId() <= 0) {
            throw new PessoaException("ID do cliente inválido para atualização.");
        }

        Cliente clienteEscolhido = clienteDAO.selecionarPorId(cliente.getId());
        if(clienteEscolhido == null) throw new PessoaException("ID do cliente não encontrado.");

        cliente.setIdPessoa(clienteEscolhido.getIdPessoa());

        validarCliente(cliente);
        return clienteDAO.atualizar(cliente);
    }

    public Boolean deletarCliente(Long id) throws PessoaException {
        if (id == null || id <= 0) {
            throw new PessoaException("ID inválido para exclusão.");
        }
        return clienteDAO.deletar(id);
    }

    private void validarCliente(Cliente cliente) throws PessoaException {
        if (cliente.getNomeCompleto() == null || cliente.getNomeCompleto().isEmpty()) {
            throw new PessoaException("Nome completo é obrigatório.");
        }
        if (cliente.getDataNascimento() == null) {
            throw new PessoaException("Data de nascimento é obrigatória.");
        }
        if (cliente.getDocumento() == null || cliente.getDocumento().isEmpty()) {
            throw new PessoaException("Documento é obrigatório.");
        }
        if (cliente.getPais() == null || cliente.getPais().isEmpty()) {
            throw new PessoaException("País é obrigatório.");
        }
        if (cliente.getEstado() == null || cliente.getEstado().isEmpty()) {
            throw new PessoaException("Estado é obrigatório.");
        }
        if (cliente.getCidade() == null || cliente.getCidade().isEmpty()) {
            throw new PessoaException("Cidade é obrigatória.");
        }
        if (cliente.getFidelidade() == null) {
            throw new PessoaException("O campo fidelidade é obrigatório.");
        }
    }
}
