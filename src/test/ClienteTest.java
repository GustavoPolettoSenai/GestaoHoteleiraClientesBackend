package test;

import exception.PessoaException;
import model.Cliente;
import service.ClienteService;

import java.time.LocalDate;

public class ClienteTest implements Test {

    // Atributo que guarda o serviço de cliente
    private ClienteService clienteService;

    // Construtor que recebe o serviço de cliente
    public ClienteTest(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Método para testar a listagem de clientes
    public String listar() throws PessoaException {
        // Retorna a lista de clientes como string
        return clienteService.listarClientes().toString();
    }

    // Método para testar o cadastro de um novo cliente
    public String cadastrar() throws PessoaException {
        // Cria um novo cliente e tenta cadastrar
        return clienteService.adicionarCliente(
                new Cliente(
                        "Ana Rodrigues Souza",               // Nome do cliente
                        LocalDate.of(1977, 2, 14),           // Data de nascimento
                        "481",                               // Documento
                        "Brasil",                            // País
                        "SP",                                // Estado
                        "São Paulo",                         // Cidade
                        true,                                // Fidelidade
                        "Novo cliente com fidelidade"        // Observações
                )
        ) ? "Cliente cadastrado com sucesso!" : "Falha ao cadastrar cliente!";
    }

    // Método para testar a alteração de um cliente
    public String alterar() throws PessoaException {
        // Cria um cliente com ID existente e tenta atualizar os dados
        return clienteService.atualizarCliente(
                new Cliente(
                        1L,                                  // ID do cliente a ser alterado
                        "Xablau da Silva",                   // Nome atualizado
                        LocalDate.of(1990, 8, 25),           // Nova data de nascimento
                        "11122233344",                       // Novo documento
                        "Brasil",                            // País
                        "RJ",                                // Estado
                        "Rio de Janeiro",                    // Cidade
                        null,                                // Campo opcional (nulo)
                        true,                                // Fidelidade
                        "Cliente alterado para fidelidade"   // Observações
                )
        ) ? "Cliente alterado com sucesso!" : "Falha ao alterar cliente!";
    }

    // Método para testar a exclusão de um cliente
    public String excluir() throws PessoaException {
        // Tenta excluir o cliente com ID 1
        return clienteService.deletarCliente(1L) ? "Cliente excluído com sucesso!" : "Falha ao excluir cliente!";
    }
}
