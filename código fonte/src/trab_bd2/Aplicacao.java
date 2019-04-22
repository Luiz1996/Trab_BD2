package trab_bd2;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] Args) throws SQLException, IOException, AWTException{
        Scanner reader = new Scanner(System.in);
        Conexao con = new Conexao(); 
        try{
            con.conexao.setAutoCommit(false);
            try{
                Statement st = con.conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                ResultSet rs;
                Consultas consultas = new Consultas();
                Consultas auxiliar = new Consultas();
                DadosTabela dados = new DadosTabela();
                String nomeTabela;
                long tempoInicio, tempoDuracao1, tempoDuracao2;

                //manutenção do menu principal
                
                int opcao = auxiliar.showMenu();
                System.out.println("");
                while (opcao != 0) {
                    auxiliar.limpaConsole();
                    System.out.println("------------------------------------------------------------------------- RESULTADO -------------------------------------------------------------------------");
                    switch (opcao) {
                        case 1:
                            nomeTabela = consultas.lockEscrita();
                            System.out.println("Lock de ESCRITA na tabela '" + nomeTabela + "' realizado com sucesso!");
                            st.executeQuery("lock table trab_bd2." + nomeTabela + " write;");
                            break;
                        case 2:
                            nomeTabela = consultas.lockLeitura();
                            System.out.println("Lock de LEITURA na tabela '" + nomeTabela + "' realizado com sucesso!");
                            st.executeQuery("lock tables trab_bd2." + nomeTabela + " as t_alias read;");
                            break;
                        case 3:
                            st.executeQuery("unlock tables;");
                            System.out.println("O UNLOCK foi realizado com sucesso!");
                            break;
                        case 4:
                            dados = auxiliar.consultarDado();
                            st.executeQuery("select " + dados.nomeColuna + " from trab_bd2." + dados.nomeTabela + " where " + dados.condicaoWhere + ";");
                            rs = st.getResultSet();
                            while(rs.next())
                                System.out.println(dados.nomeColuna.toUpperCase() + " : " + rs.getString(dados.nomeColuna));
                            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println("Comando Executado: select ".toUpperCase() + dados.nomeColuna + " from trab_bd2.".toUpperCase() + dados.nomeTabela + " where ".toUpperCase() + dados.condicaoWhere + ";\n----------------\nSELECT realizado com sucesso!");
                            break;
                        case 5:
                            dados = auxiliar.alterarDado();
                            st.executeQuery("start transaction;");
                            st.executeUpdate("update trab_bd2." + dados.nomeTabela + " set " + dados.nomeColuna + " = '" + dados.valorASetar + "' where " + dados.condicaoWhere + ";");
                            System.out.println("Comando Executado: update trab_bd2.".toUpperCase() + dados.nomeTabela + " set ".toUpperCase() + dados.nomeColuna + " = '".toUpperCase() + dados.valorASetar + "' where ".toUpperCase() + dados.condicaoWhere + ";\n----------------\nUPDATE realizado com sucesso!");
                            break;
                        case 6:
                            st.executeQuery("commit;");
                            st.executeQuery("savepoint transaction;");
                            System.out.println("COMMIT realizado com sucesso!");
                            break;
                        case 7:
                            st.executeQuery("rollback;");
                            st.executeQuery("savepoint transaction;");
                            System.out.println("ROLLBACK realizado com sucesso!");
                            break;
                        case 8:
                            tempoInicio = System.currentTimeMillis();
                            st.executeQuery(consultas.total_SEMOTM());
                            rs = st.getResultSet();
                            tempoDuracao1 = (System.currentTimeMillis()-tempoInicio);
                            System.out.print("|***Duração SEM OTIMIZAÇÃO: " + tempoDuracao1 + " milissegundos.***|");
                            tempoInicio = System.currentTimeMillis();
                            st.executeQuery(consultas.total_COMOTM());
                            rs = st.getResultSet();
                            tempoDuracao2 = (System.currentTimeMillis()-tempoInicio);
                            System.out.println("-|***Duração COM OTIMIZAÇÃO: " + tempoDuracao2 + " milissegundos.***|-|Ganhou-se " + (tempoDuracao1-tempoDuracao2) + " milissegundos|\n----------------------------------------------------------------------------------------------------------------------------------");
                            while(rs.next())
                                System.out.println("Quantidade: " + rs.getString("quantidade"));
                            break;
                        case 9:
                            tempoInicio = System.currentTimeMillis();
                            st.executeQuery(consultas.totalComTipo_SEMOTM());
                            rs = st.getResultSet();
                            tempoDuracao1 = (System.currentTimeMillis()-tempoInicio);
                            System.out.print("|***Duração SEM OTIMIZAÇÃO: " + (System.currentTimeMillis()-tempoInicio) + " milissegundos.***|");
                            tempoInicio = System.currentTimeMillis();
                            st.executeQuery(consultas.totalComTipo_COMOTM());
                            rs = st.getResultSet();
                            tempoDuracao2 = (System.currentTimeMillis()-tempoInicio);
                            System.out.println("-|***Duração COM OTIMIZAÇÃO: " + tempoDuracao2 + " milissegundos.***|-|Ganhou-se " + (tempoDuracao1-tempoDuracao2) + " milissegundos|\n----------------------------------------------------------------------------------------------------------------------------------");
                            while(rs.next())
                                System.out.println("Tipo: " + rs.getString("tipo") + " | Quantidade: " + rs.getString("quantidade"));
                            break;
                        case 10:
                            int validaImprimir = 0;
                            tempoInicio = System.currentTimeMillis();
                            st.executeQuery(consultas.exameMulheres_SEMOTM());
                            rs = st.getResultSet();
                            tempoDuracao1 = (System.currentTimeMillis()-tempoInicio);
                            System.out.print("|***Duração SEM OTIMIZAÇÃO: " + (System.currentTimeMillis()-tempoInicio) + " milissegundos.***|");
                            tempoInicio = System.currentTimeMillis();
                            st.executeQuery(consultas.exameMulheres_COMOTM());
                            rs = st.getResultSet();
                            tempoDuracao2 = (System.currentTimeMillis()-tempoInicio);
                            System.out.println("-|***Duração COM OTIMIZAÇÃO: " + tempoDuracao2 + " milissegundos.***|-|Ganhou-se " + (tempoDuracao1-tempoDuracao2) + " milissegundos|\n----------------------------------------------------------------------------------------------------------------------------------");

                            //varificao para impressão do retorno do select
                            System.out.print("Deseja IMPRIMIR [0 = NÃO / 1 = SIM]\nR: ");
                            validaImprimir = reader.nextInt();
                            if(validaImprimir == 1){
                                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                while(rs.next())
                                    System.out.println("Nome: " + rs.getString("nomepaciente") + " | Cod.Grupo: " + rs.getString("codgrupo") + " | Cod.Seguradora: " + rs.getString("codseguradora") + " | Data Requisicao: " + rs.getString("dataaquisicao") + " | Exame: " + rs.getString("codexame"));
                            }else{
                                if(validaImprimir == 0){
                                    System.out.println("Impressão Abortada!");
                                }else{
                                    System.out.println("Opção Inválida, programa abortado!");
                                    System.exit(opcao);
                                } 
                            }
                            break;
                        case 11:
                            st.executeQuery(auxiliar.qtdePacientes());
                            rs = st.getResultSet();
                            while(rs.next())
                                System.out.println("Qtde. Pacientes: " + rs.getString("QuantidadePacientes"));
                            break;
                        case 12:
                            st.executeQuery(auxiliar.qtdeRequisicao());
                            rs = st.getResultSet();
                            while(rs.next())
                                System.out.println("Qtde. Requisições: " + rs.getString("QuantidadeRequisicoes"));
                            break;
                        case 13:
                            st.executeQuery(auxiliar.qtdeResultadoexame());
                            rs = st.getResultSet();
                            while(rs.next())
                                System.out.println("Qtde. Resultados de Exames: " + rs.getString("QuantidadeResultados"));
                            break;
                        default:
                            System.out.println("Opção "+ opcao + " é INVALIDA!");
                            break;
                    }
                    opcao = auxiliar.showMenu();
                }
            }catch(SQLException e){
                con.conexao.rollback();
                e.printStackTrace();
            }    
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

    
