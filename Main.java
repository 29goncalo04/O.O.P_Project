import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main implements Serializable{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int opcao_user = 0;
        Recordes recordes = new Recordes();
        Funcoes_ficheiro.preencheRecordes(recordes);
        while (opcao_user != 5) {
            System.out.print("Deseja adicionar um novo utilizador (1), usar um existente (2), ver todos os utilizadores guardados (3), obter estatísticas (4) ou terminar o programa (5) (insira o número correspondente): ");
            opcao_user = input.nextInt();
            if (opcao_user == 1) {  // Novo utilizador
                int codigo = -1;
                while (codigo == -1) {
                    System.out.print("Insira o seu código de utilizador: ");
                    codigo = input.nextInt();
                    //procura no ficheiro se o código inserido existe
                    try {
                        Utilizador utilizador = Funcoes_ficheiro.findCodigo("Dataset", codigo);
                        if(utilizador!=null){
                            System.out.println("Esse código de utilizador já existe");
                            break;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input.nextLine(); // Consumir o \n
                    System.out.print("Insira o seu nome: ");
                    String nome = input.nextLine();
                    System.out.print("Insira a sua idade: ");
                    int idade = input.nextInt();
                    System.out.print("Insira a sua altura: ");
                    int altura = input.nextInt();
                    System.out.print("Insira o seu peso: ");
                    int peso = input.nextInt();
                    input.nextLine(); // Consumir o \n
                    System.out.print("Insira o seu email: ");
                    String email = input.nextLine();
                    System.out.print("Insira a sua morada: ");
                    String morada = input.nextLine();
                    System.out.print("Insira o género (M ou F): ");
                    char genero = input.next().charAt(0);
                    System.out.print("Insira a sua frequência cardíaca média: ");
                    int freq_cardiaca_media = input.nextInt();
                    System.out.print("Você é um atleta profissional (1), amador (2), ou praticante ocasional (3)? ");
                    int tipo_utilizador = input.nextInt();
                    TreeMap <LocalDateTime, Atividade> atividades = new TreeMap <LocalDateTime, Atividade>();
                    Utilizador utilizador;
                    PlanoTreino plano_treino = new PlanoTreino();
                    Map <String, Map<String, Integer>> recordes_aux = new TreeMap <String, Map<String, Integer>>();
                    if(tipo_utilizador == 1) utilizador = new UtilizadorProfissional(codigo, nome, idade, altura, peso, email, morada, genero, freq_cardiaca_media, atividades, plano_treino, recordes_aux); 
                    else if(tipo_utilizador == 2) utilizador = new UtilizadorAmador(codigo, nome, idade, altura, peso, email, morada, genero, freq_cardiaca_media, atividades, plano_treino, recordes_aux); 
                    else if(tipo_utilizador == 3) utilizador = new UtilizadorPraticanteOcasional(codigo, nome, idade, altura, peso, email, morada, genero, freq_cardiaca_media, atividades, plano_treino, recordes_aux); 
                    else {
                        System.out.println("Tipo de utilizador inválido");
                        return;
                    }

                    while (true){
                        input.nextLine(); // Consumir o \n
                        System.out.print("Insira a data da atividade (no formato 'yyyy-MM-dd HH:mm'): ");
                        String dataString = input.nextLine();
                        // Formatar a data da atividade
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime data = LocalDateTime.parse(dataString, formatter);
                        Funcoes_auxiliares.writeAtividade(atividades, input, utilizador, data);
                        System.out.print("\nDeseja inserir mais atividades (1 se sim, 0 se não)? ");
                        int opcao_continue = input.nextInt();
                        if(opcao_continue == 0) break;                            
                    }
                    input.nextLine();  // Consumir o \n
                    System.out.print("\nDeseja inserir um plano de treino? (1 se sim, 0 se não) ");
                    int opcao_plano = input.nextInt();
                    if(opcao_plano == 1){  // Cria um plano de treino
                        TreeMap<DayOfWeek, HashSet<Atividade>> plano_treino_aux = new TreeMap<DayOfWeek, HashSet<Atividade>> ();
                        while(true){
                            System.out.println("Vai ter que inserir o número correspondente ao dia da semana: MONDAY (1) | TUESDAY (2) | WEDNESDAY (3) | THURSDAY (4) | FRIDAY (5) | SATURDAY (6) | SUNDAY (7)");
                            System.out.print("Deseja inserir um novo dia da semana? (1 se sim, 0 se não) ");
                            int more_dates = input.nextInt();
                            input.nextLine();
                            if(more_dates == 1){
                                System.out.print("Insira o número correspondente ao dia da semana: ");
                                int data = input.nextInt();
                                DayOfWeek dia_semana = null;
                                switch(data){
                                    case 1:
                                        dia_semana = DayOfWeek.MONDAY;
                                        break;
                                    case 2:
                                        dia_semana = DayOfWeek.TUESDAY;
                                        break;
                                    case 3:
                                        dia_semana = DayOfWeek.WEDNESDAY;
                                        break;
                                    case 4:
                                        dia_semana = DayOfWeek.THURSDAY;
                                        break;
                                    case 5:
                                        dia_semana = DayOfWeek.FRIDAY;
                                        break;
                                    case 6:
                                        dia_semana = DayOfWeek.SATURDAY;
                                        break;
                                    case 7:
                                        dia_semana = DayOfWeek.SUNDAY;
                                        break;
                                    default:
                                        System.out.println("Inseriu um valor errado");
                                        return;
                                }
                                HashSet<Atividade> set_atividades = new HashSet<Atividade>();
                                int more_atividades = 1;
                                while(true){
                                    if(more_atividades == 1){
                                        Funcoes_auxiliares.writeSetAtividade(set_atividades, input, utilizador, dia_semana);
                                    }
                                    else{
                                        System.out.println();
                                        break;
                                    }
                                    System.out.print("Deseja inserir uma nova atividade? (1 se sim, 0 se não) ");
                                    more_atividades = input.nextInt();
                                }
                                plano_treino_aux.put(dia_semana, set_atividades);
                            }
                            else break;
                        }
                        // Cria o plano de treino
                        plano_treino = new PlanoTreino(plano_treino_aux); 
                    }
                    else plano_treino = new PlanoTreino();
                    utilizador.setAtividades(atividades);
                    utilizador.setPlanoTreino(plano_treino);
                    utilizador.fillRecordUser();
                    recordes.fillRecord(utilizador);
                    Funcoes_ficheiro.appendUtilizador(utilizador);
                }
                System.out.println("\n\n\n\n");
            }
            else if (opcao_user == 2) {     //faz alterações num utilizador existente
                    System.out.print("Insira o seu código de utilizador: ");
                    int codigo = input.nextInt();
                    try {
                        Utilizador utilizador = Funcoes_ficheiro.findCodigo("Dataset", codigo);
                        if(utilizador!=null){
                            Funcoes_auxiliares.changeUser("Dataset", codigo, input, recordes);
                        }
                        else{
                            System.out.println("Esse utilizador não existe");
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } 
                    System.out.println("\n\n\n\n");
            }
            else if(opcao_user == 3){   //apresenta todo o ficheiro no terminal
                    try {
                        List<Utilizador> utilizadores = Funcoes_ficheiro.leUtilizador("Dataset");
                        for(Utilizador utilizador : utilizadores) System.out.println(utilizador.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("\n\n\n\n");
            }
            else if(opcao_user == 4){ // Obter estatísticas
                List<Utilizador> utilizadores = new ArrayList<Utilizador>();
                try {
                    utilizadores = Funcoes_ficheiro.leUtilizador("Dataset");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int more_statistics = 1;
                while(more_statistics == 1){
                    Controller_estatisticas.menu_estatisticas(input, recordes, utilizadores);
                    System.out.print("\nDeseja ver mais estatísticas (1 para sim, 0 para não)? ");
                    more_statistics = input.nextInt();
                }
                System.out.println("\n\n\n\n");
            } else {
                if(opcao_user == 5) break;  //terminar programa
                else System.out.println("Opção inválida");
            }
        }
        input.close();
    }

}