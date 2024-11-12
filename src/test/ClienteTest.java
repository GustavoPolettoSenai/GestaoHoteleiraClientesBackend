package test;

import exception.PessoaException;
import model.Cliente;
import service.ClienteService;

import java.time.LocalDate;

public class ClienteTest implements Test {

    // Atributos
    private ClienteService clienteService;

    // Construtor
    public ClienteTest(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Métodos de testes

    public String listar() throws PessoaException {
        return clienteService.listarClientes().toString();
    }

    public String cadastrar() throws PessoaException {
        return clienteService.adicionarCliente(
                new Cliente(
                        "João da Silva",
                        LocalDate.of(1995, 5, 10),
                        "123456789",
                        "Brasil",
                        "SP",
                        "São Paulo",
                        true,
                        "Novo cliente com fidelidade"
                )
        ) ? "Cliente cadastrado com sucesso!" : "Falha ao cadastrar cliente!";
    }

    public String alterar() throws PessoaException {
        return clienteService.atualizarCliente(
                new Cliente(
                        2L, // ID do cliente a ser alterado
                        "Maria Oliveira",
                        LocalDate.of(1990, 8, 25),
                        "987654321",
                        "Brasil",
                        "RJ",
                        "Rio de Janeiro",
                        null,
                        true,
                        "Cliente alterado para fidelidade"
                )
        ) ? "Cliente alterado com sucesso!" : "Falha ao alterar cliente!";
    }

    public String excluir() throws PessoaException {
        return clienteService.deletarCliente(1L) ? "Cliente excluído com sucesso!" : "Falha ao excluir cliente!";
    }
}
