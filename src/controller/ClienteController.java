package controller;

import enumeration.Funcionalidade;
import exception.PessoaException;
import test.ClienteTest;

public class ClienteController {

    // Atributo que guarda o objeto de teste
    private ClienteTest clienteTest;

    // Construtor que recebe um objeto de teste
    public ClienteController(ClienteTest clienteTest) {
        this.clienteTest = clienteTest;
    }

    // Método que gerencia qual teste será executado
    public String testar(Funcionalidade funcionalidade) throws PessoaException {
        // Verifica qual funcionalidade foi escolhida
        switch (funcionalidade) {
            case LISTAR:
                // Executa o teste de listar clientes
                return clienteTest.listar();
            case CADASTRAR:
                // Executa o teste de cadastrar cliente
                return clienteTest.cadastrar();
            case ALTERAR:
                // Executa o teste de alterar cliente
                return clienteTest.alterar();
            case EXCLUIR:
                // Executa o teste de excluir cliente
                return clienteTest.excluir();
            default:
                // Retorna nulo se a funcionalidade não for reconhecida
                return null;
        }
    }
}
