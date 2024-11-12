import controller.ClienteController;
import dao.ClienteDAO;
import enumeration.Funcionalidade;
import exception.PessoaException;
import service.ClienteService;
import test.ClienteTest;

public class Principal {

    public static void main(String[] args) throws PessoaException {
        // Inicialização de objetos
        ClienteDAO clienteDAO = new ClienteDAO();
        ClienteService clienteService = new ClienteService(clienteDAO);
        ClienteTest clienteTest = new ClienteTest(clienteService);
        ClienteController clienteController = new ClienteController(clienteTest);

        // Informações do teste
        Funcionalidade funcionalidade = Funcionalidade.ALTERAR;
        System.out.println("CLIENTE: " + funcionalidade);
        System.out.println("RESULTADO DO TESTE:");

        // Realização do teste
        try {
            System.out.println(clienteController.testar(funcionalidade));
        } catch (PessoaException excecao) {
            System.err.println(excecao.getMessage());
        }
    }
}

