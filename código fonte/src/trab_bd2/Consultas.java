package trab_bd2;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class Consultas {
    Scanner reader = new Scanner(System.in);
    //consultas e menu principal
    public String lockEscrita(){
        System.out.print("Insira o nome da tabela: ");
        String nomeTabela = reader.nextLine();
        nomeTabela = nomeTabela.toLowerCase();
        
        if(nomeTabela.intern() != "paciente" && nomeTabela.intern() != "requisicao" && nomeTabela.intern() != "resultadoexame"){
            System.out.println("Tabela inexistente!");
            return lockEscrita();
        }else{
            return nomeTabela;
        }
    }
    
    public String lockLeitura(){
        System.out.print("Insira o nome da tabela: ");
        String nomeTabela = reader.nextLine();
        nomeTabela = nomeTabela.toLowerCase();
        
        if(nomeTabela.intern() != "paciente" && nomeTabela.intern() != "requisicao" && nomeTabela.intern() != "resultadoexame"){
            System.out.println("Tabela inexistente!");
            return lockLeitura();
        }else{
            return nomeTabela;
        }
    }
    
    public DadosTabela consultarDado(){
        DadosTabela dados = new DadosTabela();
        
        System.out.print("Insira o nome da tabela...: ");
        dados.nomeTabela = reader.nextLine();
        
        //caso usuário digite nome da tabela invalido, o programa reexecuta o procedimento
        if(dados.nomeTabela.intern() != "paciente" && dados.nomeTabela.intern() != "requisicao" && dados.nomeTabela.intern() != "resultadoexame"){
            System.out.println("Tabela inexistente!");
            return consultarDado();
        }
        
        System.out.print("Insira o nome da coluna...: ");
        dados.nomeColuna = reader.nextLine();
        System.out.print("Insira a condicao do where: ");
        dados.condicaoWhere = reader.nextLine();
        
        if("".equals(dados.condicaoWhere.trim()) && "paciente".equals(dados.nomeTabela)){
            dados.condicaoWhere = "numpaciente > 0";
        }
        
        if("".equals(dados.condicaoWhere.trim()) && "requisicao".equals(dados.nomeTabela)){
            dados.condicaoWhere = "numprotocolo > 0";
        }
        
        if("".equals(dados.condicaoWhere.trim()) && "resultadoexame".equals(dados.nomeTabela)){
            dados.condicaoWhere = "numitem > 0";
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return dados;
    }
    
    public DadosTabela alterarDado(){
        DadosTabela dados = new DadosTabela();
        
        System.out.print("Insira o nome da tabela...: ");
        dados.nomeTabela = reader.nextLine();
        
        //caso usuário digite nome da tabela invalido, o programa reexecuta o procedimento
        if(dados.nomeTabela.intern() != "paciente" && dados.nomeTabela.intern() != "requisicao" && dados.nomeTabela.intern() != "resultadoexame"){
            System.out.println("Tabela inexistente!");
            return alterarDado();
        }
        System.out.print("Insira o nome da coluna...: ");
        dados.nomeColuna = reader.nextLine();
        System.out.print("Valor a ser setado........: ");
        dados.valorASetar = reader.nextLine();
        System.out.print("Insira a condicao do where: ");
        dados.condicaoWhere = reader.nextLine();
        
        if("".equals(dados.condicaoWhere.trim()) && "paciente".equals(dados.nomeTabela)){
            dados.condicaoWhere = "numpaciente > 0";
        }
        
        if("".equals(dados.condicaoWhere.trim()) && "requisicao".equals(dados.nomeTabela)){
            dados.condicaoWhere = "numprotocolo > 0";
        }
        
        if("".equals(dados.condicaoWhere.trim()) && "resultadoexame".equals(dados.nomeTabela)){
            dados.condicaoWhere = "numitem > 0";
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return dados;
    }
    
    public String total_SEMOTM(){
        return ("SELECT \n" +
                "    COUNT(p.numpaciente) AS Quantidade\n" +
                "FROM\n" +
                "    resultadoexame re\n" +
                "        JOIN\n" +
                "    requisicao r ON re.numprotocolo = r.numprotocolo\n" +
                "        JOIN\n" +
                "    paciente p ON r.numpaciente = p.numpaciente\n" +
                "WHERE\n" +
                "    codexame LIKE 'hemog'\n" +
                "        AND p.codgrupo LIKE 'liber';");
    }
    
    public String total_COMOTM(){
        return ("SELECT \n" +
                "    COUNT(p.numpaciente) AS Quantidade\n" +
                "FROM\n" +
                "    (SELECT \n" +
                "        codexame, numprotocolo\n" +
                "    FROM\n" +
                "        resultadoexame\n" +
                "    WHERE\n" +
                "        codexame LIKE 'hemog') AS re\n" +
                "        LEFT JOIN\n" +
                "    (SELECT \n" +
                "        numprotocolo, numpaciente\n" +
                "    FROM\n" +
                "        requisicao) AS r ON re.numprotocolo = r.numprotocolo\n" +
                "        LEFT JOIN\n" +
                "    (SELECT \n" +
                "        numpaciente\n" +
                "    FROM\n" +
                "        paciente\n" +
                "    WHERE\n" +
                "        codgrupo LIKE 'liber') AS p ON r.numpaciente = p.numpaciente;");
    }
    
    public String totalComTipo_SEMOTM(){
        return ("SELECT \n" +
                "    re.tipo AS Tipo, COUNT(*) AS Quantidade\n" +
                "FROM\n" +
                "    resultadoexame re\n" +
                "        LEFT JOIN\n" +
                "    requisicao r ON re.numprotocolo = r.numprotocolo\n" +
                "        LEFT JOIN\n" +
                "    paciente p ON r.numpaciente = p.numpaciente\n" +
                "WHERE\n" +
                "    re.codexame LIKE 'hemog' AND \n" +
                "    p.codgrupo LIKE 'liber'\n" +
                "GROUP BY re.tipo;");
        
    }
    
    public String totalComTipo_COMOTM(){
        return ("SELECT \n" +
                "    re.tipo AS Tipo, COUNT(*) AS Quantidade\n" +
                "FROM\n" +
                "    (SELECT \n" +
                "        numprotocolo, tipo\n" +
                "    FROM\n" +
                "        resultadoexame\n" +
                "    WHERE\n" +
                "        codexame LIKE 'hemog') AS re\n" +
                "        JOIN\n" +
                "    requisicao r ON re.numprotocolo = r.numprotocolo\n" +
                "        JOIN\n" +
                "    (SELECT \n" +
                "        numpaciente, codgrupo\n" +
                "    FROM\n" +
                "        paciente\n" +
                "    WHERE\n" +
                "        codgrupo LIKE 'liber') AS p ON r.numpaciente = p.numpaciente\n" +
                "GROUP BY re.tipo;");
        
    }
    
    public String exameMulheres_SEMOTM(){
        return ("SELECT \n" +
                "    pac.nomepaciente,\n" +
                "    pac.codgrupo,\n" +
                "    pac.codseguradora,\n" +
                "    req.dataaquisicao,\n" +
                "    result.codexame\n" +
                "FROM\n" +
                "    resultadoexame result\n" +
                "        LEFT JOIN\n" +
                "    requisicao req ON result.numprotocolo = req.numprotocolo\n" +
                "        LEFT JOIN\n" +
                "    paciente pac ON req.numpaciente = pac.numpaciente\n" +
                "WHERE\n" +
                "    pac.sexo = 2\n" +
                "LIMIT 99999;/*trará no max 99.999 registros*/");    
    }
    
    public String exameMulheres_COMOTM(){
        return ("SELECT \n" +
                "    pac.nomepaciente,\n" +
                "    pac.codgrupo,\n" +
                "    pac.codseguradora,\n" +
                "    req.dataaquisicao,\n" +
                "    result.codexame\n" +
                "FROM\n" +
                "    (SELECT \n" +
                "        codexame, numprotocolo\n" +
                "    FROM\n" +
                "        resultadoexame) AS result\n" +
                "        JOIN\n" +
                "    (SELECT \n" +
                "        dataaquisicao, numprotocolo, numpaciente\n" +
                "    FROM\n" +
                "        requisicao) AS req ON result.numprotocolo = req.numprotocolo\n" +
                "        JOIN\n" +
                "    (SELECT \n" +
                "        nomepaciente, codgrupo, codseguradora, numpaciente\n" +
                "    FROM\n" +
                "        paciente\n" +
                "    WHERE\n" +
                "        sexo = 2) pac ON req.numpaciente = pac.numpaciente\n" +
                "LIMIT 99999;/*trará no max 99.999 registros*/");    
    }
    
    public String qtdePacientes(){
        return "SELECT \n" +
               "    COUNT(numpaciente) AS QuantidadePacientes\n" +
               "FROM\n" +
               "    paciente;";
    }
    
    public String qtdeRequisicao(){
        return "SELECT \n" +
               "    COUNT(numprotocolo) AS QuantidadeRequisicoes\n" +
               "FROM\n" +
               "    requisicao;";
    }
    
    public String qtdeResultadoexame(){
        return "SELECT \n" +
               "    COUNT(numitem) AS QuantidadeResultados\n" +
               "FROM\n" +
               "    resultadoexame;";
    }
    
    public int showMenu(){
        Scanner reader = new Scanner(System.in);    
        System.out.println("--------------------------------------------------------------------------- MENU ---------------------------------------------------------------------------");
        System.out.print(""
                +"Parte 1 - TESTE DE CONCORRENCIA\n"
                +"    1) Realizar Lock de Escrita.\n"
                +"    2) Realizar Lock de Leitura.\n"
                +"    3) Realizar UNLOCK em todas tabelas.\n"
                +"    4) Trazer dados de uma tabela(SELECT).\n"
                +"    5) Realizar alteracao de dados em uma tabela(UPDATE).\n"
                +"    6) Realizar COMMIT das alterações.\n"
                +"    7) Realizar ROLLBACK das alterações.\n\n"
                +"Parte 2 - OTIMIZAÇÃO DE CONSULTAS\n"
                +"    8) Consulta o TOTAL de pessoas que fizeram exame do tipo 'hemog' e são do grupo 'liber'.\n"
                +"    9) Consulta os TIPOS e QUANTIDADE DE CADA TIPO das pessoas que fizeram exame do tipo 'hemog' e são do grupo 'liber'.\n"
                +"   10) Consulta os nomes dos pacientes, código do grupo, código da seguradora, data das requisições e exames das pessoas que são do sexo '2'(FEMININO).\n\n"
                +"Parte 3 - INFORMAÇÕES SCHEMA 'Trab_BD2'\n"
                +"   11) Quantidade de registros na tabela PACIENTE.\n"
                +"   12) Quantidade de registros na tabela REQUISICAO.\n"
                +"   13) Quantidade de registros na tabela RESULTADOEXAME.\n\n"
                + "0) SAIR.\n"
                + "------------------------------------------------------------------------------------------------------------------------------------------------------------"
                + "\nOpcao: ");
        int retorno = reader.nextInt();
        return retorno;
    }
    
    public void limpaConsole() throws AWTException{
        //comandos para limpar tela
        Robot robot = new Robot();
        robot.setAutoDelay(10);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_L);robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_L);
    }
}
