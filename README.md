# Projeto Java - Controle de Impressora Elgin via DLL

Este projeto Java permite a comunicação com impressoras Elgin utilizando a biblioteca E1_Impressora01.dll por meio da interface JNA. Ele fornece um menu interativo via terminal que permite executar diversas funções, como impressão de texto, QR Code, código de barras, XML SAT, abertura de gaveta e emissão de sinal sonoro.

## 🚀 Funcionalidades

- Configuração e abertura de conexão com impressoras Elgin
- Impressão de:
    - Texto
    - QR Code
    - Código de barras
    - XML SAT
    - XML de Cancelamento SAT
- Abertura de gaveta (Elgin e genérica)
- Emissão de sinal sonoro
- Interface simples via terminal
- Autenticação por login simples

## 📂 Estrutura do Projeto

- Main.java: Classe principal contendo toda a lógica do sistema e chamada das funções da DLL.
- Interface ImpressoraDLL: Mapeia os métodos da DLL utilizando JNA.

## 📌 Requisitos

- Java 8 ou superior
- Biblioteca JNA (jna.jar)
- DLL da impressora Elgin (E1_Impressora01.dll)
- Arquivos XML (XMLSAT.xml, CANC_SAT.xml) para testes

## 🔧 Configuração da Conexão

Antes de imprimir, o usuário deve configurar a conexão informando:
- Tipo de conexão (ex: 1 = USB)
- Modelo da impressora
- Porta de conexão (ex: USB, COM1)
- Parâmetro adicional (ex: 0 para padrão)

## 🔄 Fluxo de Execução

1. O usuário realiza login (usuário: admin, senha: 1234)
2. É exibido um menu com as opções de controle da impressora
3. As operações podem ser executadas conforme necessário
4. Ao sair, a conexão é encerrada

## 🧩 Principais Funções

### Conexão

- configurarConexao(): Captura parâmetros de conexão via terminal
- abrirConexao(): Abre a conexão com a impressora utilizando os parâmetros fornecidos
- fecharConexao(): Encerra a conexão ativa

### Impressões

- imprimirTexto(): Imprime um texto simples com corte
- imprimirQRCode(): Imprime um QR Code com tamanho e correção fixos
- imprimirCodigoBarras(): Imprime um código de barras no formato Code128
- imprimirXMLSAT(): Lê e imprime um arquivo XML SAT a partir do disco
- imprimirXMLCancelamentoSAT(): Lê e imprime um XML de cancelamento SAT com assinatura de QR Code

### Dispositivos

- abrirGavetaElgin(): Aciona a gaveta da impressora Elgin
- abrirGaveta(): Aciona uma gaveta genérica com parâmetros fixos
- emitirSinalSonoro(): Emite um som da impressora (ex: alerta)

### Utilitários

- capturarEntrada(String mensagem): Lê entradas do usuário via terminal
- exibirMenuLogin(): Menu de login
- exibirMenu(): Menu principal do sistema

## 🗂️ Caminhos de Arquivos Importantes

- DLL:  
  C:/Users/leandro_vinicius/Downloads/Material de apoio/Exemplo Java/Bibliotecas E1 Impressora/x64/E1_Impressora01.dll

- XMLs:
    - SAT: C:/Users/leandro_vinicius/Downloads/Material de apoio/Exemplo Java/XMLSAT.xml
    - Cancelamento: C:/Users/leandro_vinicius/Downloads/Material de apoio/Exemplo Java/CANC_SAT.xml

> ⚠️ *Importante:* Certifique-se de que os caminhos dos arquivos estejam corretos e que os arquivos estejam acessíveis no momento da execução.

## 📥 Execução

Compile e execute o projeto com:

bash
javac Main.java
java Main


## 👨‍💻 Autores

Ailton Santos
Vinicius Leandro
João Pedro
Lucas Andrade
Lucas Oliveira  
Projeto acadêmico/demonstrativo de integração Java + Impressora Elgin