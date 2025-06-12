import com.sun.jna.Library;
import com.sun.jna.Native;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    // Interface que representa a DLL usando JNA
    public interface ImpressoraDLL extends Library {
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:/Users/leandro_vinicius/Downloads/Material de apoio/Exemplo Java/Bibliotecas E1 Impressora/x64/E1_Impressora01.dll", ImpressoraDLL.class); // Substitua pelo caminho correto da DLL

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);

        int FechaConexaoImpressora();

        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);

        int Corte(int avanco);

        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);

        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);

        int AbreGavetaElgin();

        int AbreGaveta(int pino, int ti, int tf);

        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);

        int ImprimeXMLSAT(String dados, int param);

        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
    private static final Scanner scanner = new Scanner(System.in); // Scanner estático e final

    // Função para capturar entradas do usuário
    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    // Função para configurar conexão
    public static void configurarConexao() {
        if (!conexaoAberta) {
            System.out.println("Digite o tipo de conexao (1 para USB, 2 para Serial, etc.): ");
            tipo = scanner.nextInt(); // Lê o tipo de conexão como inteiro
            scanner.nextLine(); // Consumir quebra de linha restante

            System.out.println("Digite o modelo da impressora: ");
            modelo = scanner.nextLine(); // Lê o modelo da impressora

            System.out.println("Digite a porta de conexão (ex: USB): ");
            conexao = scanner.nextLine(); // Lê a porta de conexão

            System.out.println("Digite o parâmetro adicional (ex: 0 para padrão): ");
            parametro = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha restante

            System.out.println("Parâmetros de conexão configurados com sucesso.");
        } else {
            System.out.println("Conexão já configurada. Pronta para uso.");
        }
    }

    public static void abrirConexao() {
        // Função para abrir a conexão com a impressora
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }

    public static void fecharConexao() {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            conexaoAberta = false;
            System.out.println("Conexão fechada.");
        } else {
            System.out.println("Nenhuma conexão aberta para fechar.");
        }
    }

    public static void imprimirQRCode() {
        // Função para imprimir QRCode na impressora

        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de impressao", 6, 4);
            ImpressoraDLL.INSTANCE.Corte(5);
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }

    }

    public static void imprimirCodigoBarras() {
        // Função para imprimir código de barras na impressora

        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);
            ImpressoraDLL.INSTANCE.Corte(5);
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }

    public static void imprimirXMLSAT() throws java.io.IOException{
        // Função para imprimir XML SAT

        if (conexaoAberta){
            try{

                // Nesta variavel será gerada uma nova String apartir do Bytes do arquivo lido
                String dadosXML = new String(Files.readAllBytes(Paths.get("C:/Users/leandro_vinicius/Downloads/Material de apoio/Exemplo Java/XMLSAT.xml")));

                // Fução que irá imprimir o cupom fiscal
                int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dadosXML,0);
                ImpressoraDLL.INSTANCE.Corte(5);

                //Verificação se a impressão foi feita com sucesso
                if (retorno==0){
                    System.out.println("XML SAT impresso com sucesso");
                }
                else {
                    System.out.println("Erro ao imprimir XML SAT Código: "+ retorno);
                }

            }catch(Exception e){
                System.out.println("Erro ao ler ou imprimir o arquivo XML: "+e.getMessage());
            }
        }
        else{
            System.out.println("Erro: Conexão não está aberta");
        }
    }

    public static void imprimirXMLCancelamentoSAT() {
        if (conexaoAberta) {
            try {
                // Ler o conteúdo do arquivo XML de Cancelamento SAT diretamente do caminho
                String dadosXML = new String(Files.readAllBytes(Paths.get("C:/Users/leandro_vinicius/Downloads/Material de apoio/Exemplo Java/CANC_SAT.xml")));


                // Assinatura do QR Code
                String assQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";


                // Enviar o XML para a impressora
                int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(dadosXML, assQRCode, 0);
                ImpressoraDLL.INSTANCE.Corte(5);
                if (retorno == 0) {
                    System.out.println("XML de Cancelamento SAT impresso com sucesso!");
                } else {
                    System.out.println("Erro ao imprimir XML de Cancelamento SAT. Código: " + retorno);
                }

            } catch (Exception e) {
                System.out.println("Erro ao ler ou imprimir o arquivo XML: " + e.getMessage());
            }
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }

    public static void abrirGavetaElgin() {
        // Função para abrir gaveta Elgin
        if (conexaoAberta) {
            int ret = ImpressoraDLL.INSTANCE.AbreGavetaElgin();
            System.out.print(ret + "erro");
        }
        else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }

    public static void abrirGaveta() {
        // Função para abrir gaveta genérica
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);
        }
        else{
            System.out.println("Erro: Conexão não está aberta.");
        }

    }

    public static void emitirSinalSonoro() {
        // Função para emitir sinal sonoro
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.SinalSonoro(4,50,5);
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }


    public static void exibirMenuLogin() {
        System.out.println("*****************************************");
        System.out.println("********** MENU DE LOGIN ***************");
        System.out.println("*****************************************");

        String usuarioCorreto = "admin";
        String senhaCorreta = "1234";

        while (true) {
            String usuario = capturarEntrada("Usuário: ");
            String senha = capturarEntrada("Senha: ");

            if (usuario.equals(usuarioCorreto) && senha.equals(senhaCorreta)) {
                System.out.println("Login bem-sucedido!\n");
                break;
            } else {
                System.out.println("Usuário ou senha inválidos. Tente novamente.\n");
            }
        }
    }

    public static void exibirMenu() {
        System.out.println("\n*************************************************");
        System.out.println("**************** MENU IMPRESSORA *******************");
        System.out.println("*************************************************\n");

        System.out.println("1  - Configurar Conexao");
        System.out.println("2  - Abrir Conexao");
        System.out.println("3  - Impressao Texto");
        System.out.println("4  - Impressao QRCode");
        System.out.println("5  - Impressao Cod Barras");
        System.out.println("6  - Impressao XML SAT");
        System.out.println("7  - Impressao XML Canc SAT");
        System.out.println("8  - Abrir Gaveta Elgin");
        System.out.println("9  - Abrir Gaveta");
        System.out.println("10 - Sinal Sonoro");
        System.out.println("0  - Fechar Conexao e Sair");
        System.out.println("--------------------------------------");
    }

    public static void imprimirTexto() {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);
            ImpressoraDLL.INSTANCE.Corte(5);
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
    }


    public static void main(String[] args) throws java.io.IOException {

        exibirMenuLogin();
        
        while (true) {
            exibirMenu();
            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            if (escolha.equals("0")) {
                fecharConexao();
                System.out.println("Programa encerrado.");
                break;
            } else if (escolha.equals("1")) {
                configurarConexao();
            } else if (escolha.equals("2")) {
                abrirConexao();
            } else if (escolha.equals("3")) {
                imprimirTexto();
            } else if (escolha.equals("4")) {
                imprimirQRCode();
            } else if (escolha.equals("5")){
                imprimirCodigoBarras();
            } else if (escolha.equals("6")){
                imprimirXMLSAT();
            } else if (escolha.equals("7")){
                imprimirXMLCancelamentoSAT();
            } else if (escolha.equals("8")){
                abrirGavetaElgin();
            } else if (escolha.equals("9")){
                abrirGaveta();
            } else if (escolha.equals("10")){
                emitirSinalSonoro();
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
