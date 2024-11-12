package controller;

import enumeration.Funcionalidade;
import exception.PessoaException;
import test.ClienteTest;

public class ClienteController {

    // Atributos
    private ClienteTest clienteTest;

    // Construtor
    public ClienteController(ClienteTest clienteTest) {
        this.clienteTest = clienteTest;
    }

    // Gerenciador de testes
    public String testar(Funcionalidade funcionalidade) throws PessoaException {
        switch (funcionalidade) {
            case LISTAR:
                return clienteTest.listar();
            case CADASTRAR:
                return clienteTest.cadastrar();
            case ALTERAR:
                return clienteTest.alterar();
            case EXCLUIR:
                return clienteTest.excluir();
            default:
                return null;
        }
    }
}
