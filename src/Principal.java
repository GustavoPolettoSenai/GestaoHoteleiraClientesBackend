import controller.ClienteController;
import dao.ClienteDAO;
import enumeration.Funcionalidade;
import exception.PessoaException;
import service.ClienteService;
import test.ClienteTest;

public class Principal {

    public static void main(String[] args) throws PessoaException {
        // Cria um objeto para acessar o banco de dados
        ClienteDAO clienteDAO = new ClienteDAO();

        // Cria um objeto de serviço que usa o acesso ao banco de dados
        ClienteService clienteService = new ClienteService(clienteDAO);

        // Cria um objeto para testar as funcionalidades do serviço
        ClienteTest clienteTest = new ClienteTest(clienteService);

        // Cria o controlador que vai gerenciar os testes
        ClienteController clienteController = new ClienteController(clienteTest);

        // Escolhe a funcionalidade que será testada
        Funcionalidade funcionalidade = Funcionalidade.LISTAR;
        System.out.println("CLIENTE: " + funcionalidade);
        System.out.println("RESULTADO DO TESTE:");

        // Executa o teste para a funcionalidade escolhida
        try {
            // Mostra o resultado do teste
            System.out.println(clienteController.testar(funcionalidade));
        } catch (PessoaException excecao) {
            // Se houver um erro, mostra a mensagem de erro
            System.err.println(excecao.getMessage());
        }
    }
}
