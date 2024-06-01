import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Funcoes_auxiliares{
    public static void changeUser(String nome_ficheiro, int codigo, Scanner input, Recordes recordes) throws FileNotFoundException, IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(nome_ficheiro); ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<Utilizador> utilizadores = new ArrayList<>();
            while (true) {
                try {
                    Utilizador utilizador = (Utilizador) ois.readObject();
                    if(codigo == utilizador.getCodigo()){  //faz as alterações necessárias
                        System.out.print("Deseja acrescentar uma nova atividade (1), substituir o plano de treino (2) ou avançar no tempo (3)? ");
                        int opcao_change_user = input.nextInt();
                        if(opcao_change_user == 1){
                            while (true){
                                input.nextLine(); // Consumir o \n
                                System.out.print("Insira a data da atividade (no formato 'yyyy-MM-dd HH:mm'): ");
                                String dataString = input.nextLine();
                                // Formatar a data da atividade
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                LocalDateTime data = LocalDateTime.parse(dataString, formatter);
                                TreeMap <LocalDateTime, Atividade> atividades = new TreeMap <LocalDateTime, Atividade>(utilizador.getAtividades());
                                writeAtividade(atividades, input, utilizador, data);
                                utilizador.setAtividades(atividades);
                                utilizador.fillRecordUser();
                                recordes.fillRecord(utilizador);
                                System.out.print("\nDeseja inserir mais atividades (1 se sim, 0 se não)? ");
                                int opcao_continue = input.nextInt();
                                if(opcao_continue == 0) break;                            
                            }
                            input.nextLine();  // Consumir o \n
                        }
                        else if(opcao_change_user == 2){
                                System.out.println("\nVai ter que inserir um dia da semana e as atividades para esse dia");
                                TreeMap<DayOfWeek, HashSet<Atividade>> plano_treino_aux = new TreeMap<DayOfWeek, HashSet<Atividade>> ();
                                while(true){
                                    System.out.print("Deseja inserir um novo dia da semana? (1 se sim, 0 se não) ");
                                    int more_dates = input.nextInt();
                                    input.nextLine();
                                    DayOfWeek dia_semana = null;
                                    if(more_dates == 1){
                                        System.out.print("Insira o número correspondente ao dia da semana que pretende: MONDAY (1) | TUESDAY (2) | WEDNESDAY (3) | THURSDAY (4) | FRIDAY (5) | SATURDAY (6) | SUNDAY (7) ");
                                        int data = input.nextInt();
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
                                                writeSetAtividade(set_atividades, input, utilizador, dia_semana);
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
                                PlanoTreino plano_treino = new PlanoTreino(plano_treino_aux); 
                                utilizador.setPlanoTreino(plano_treino);
                        }
                        else if (opcao_change_user == 3){  // Avança no tempo
                            System.out.print("Quantos dias deseja avançar? ");
                            int dias_avanco = input.nextInt();
                            System.out.printf("Avançou até ao dia %s\n", LocalDateTime.now().plusDays(dias_avanco).toLocalDate());
                            TreeMap <LocalDateTime, Atividade> atividades = new TreeMap <LocalDateTime, Atividade>(utilizador.getAtividades());
                            for(int i = 0; i<=dias_avanco; i++){
                                DayOfWeek dia_semana = LocalDateTime.now().plusDays(i).getDayOfWeek();
                                HashSet<Atividade> atividades_plano = utilizador.getPlanoTreino().getPlanoTreino()
                                    .entrySet().stream().filter(e->e.getKey().equals(dia_semana))
                                    .map(e -> e.getValue()).findFirst().orElse(new HashSet<>());
                                final int daysToAdd = i;
                                if(!(atividades_plano.isEmpty())){
                                    for(Atividade a : atividades_plano){
                                        boolean existe_data = utilizador.getAtividades().entrySet().stream()
                                            .map(e -> e.getKey().toLocalDate())
                                            .anyMatch(date -> date.isEqual(LocalDateTime.now().plusDays(daysToAdd).toLocalDate()));
                                        if(!existe_data){
                                            atividades.put(LocalDateTime.now().plusDays(i), a);
                                        }
                                        else{
                                            // Verificar se já foi feito um salto no tempo
                                            boolean existe_dataEAtividade = utilizador.getAtividades().entrySet().stream()
                                                .anyMatch(e -> e.getKey().toLocalDate().isEqual(LocalDateTime.now().plusDays(daysToAdd).toLocalDate()) && e.getValue().equals(a));
                                            if(!existe_dataEAtividade){
                                               atividades.put(LocalDateTime.now().plusDays(i), a);
                                            }
                                        }
                                    }
                                }
                            }
                            utilizador.setAtividades(atividades);
                            utilizador.fillRecordUser();
                            recordes.fillRecord(utilizador);
                        } else {
                            //if(opcao_change_user == 4) break;   
                            /*else*/ System.out.println("Opção inválida");  
                        }
                        utilizadores.add(utilizador);
                        break;
                    } else utilizadores.add(utilizador);
                } catch (EOFException e) {
                    break; // Chegou ao final do arquivo
                }
            }
            Funcoes_ficheiro.guardaUtilizador(utilizadores, "Dataset");
        }
    }

    public static void writeAtividade(TreeMap <LocalDateTime, Atividade> atividades, Scanner input, Utilizador utilizador, LocalDateTime data){
        System.out.print("A sua atividade é: de distância e altimetria (1), de distância (2), de série de repetições (3) ou de série de repetições com pesos (4)? ");
        int tipo_atividade = input.nextInt();
        System.out.print("Insira o tempo da realização da atividade (em minutos)? ");
        int tempo_dispendido = input.nextInt();
        input.nextLine(); // Consumir o \n
        System.out.println("Insira o número correspondente à sua atividade"); 
        
        // Atividade de distância e altimetria
        if(tipo_atividade == 1){
            System.out.println("\t- Bicicleta de montanha (1)");
            System.out.println("\t- Trail (2)");
            System.out.println("\t- Caminhada de montanha (3)");
            int cod_descricao_atividade = input.nextInt();
            String descricao_atividade;
            switch (cod_descricao_atividade) {
                case 1:
                    descricao_atividade = "Bicicleta de montanha";
                    break;
                case 2:
                    descricao_atividade = "Trail";
                    break;
                case 3:
                    descricao_atividade = "Caminhada de montanha";
                    break;
                default:
                    System.out.println("Número inválido");
                    return;
            }
            Atividade atividade;
            System.out.print("Qual a distância da atividade (em km)? ");
            int distancia = input.nextInt();
            input.nextLine(); // Consumir o \n
            System.out.print("Qual a altimetria da atividade (em m)? ");
            int altimetria = input.nextInt();
            atividade = new AtividadeDistanciaAltimetria(descricao_atividade, tempo_dispendido, 0, distancia, altimetria);
            atividade.ConsumoCalorias(utilizador);
            atividades.put(data, atividade); 
        }
        // Atividade de distância
        if(tipo_atividade == 2){
            System.out.println("\t- Corrida (1)");
            System.out.println("\t- Ciclismo (2)");
            System.out.println("\t- Natação (3)");
            System.out.println("\t- Caminhada (4)");
            int cod_descricao_atividade = input.nextInt();
            String descricao_atividade;
            switch (cod_descricao_atividade) {
                case 1:
                    descricao_atividade = "Corrida";
                    break;
                case 2:
                    descricao_atividade = "Ciclismo";
                    break;
                case 3:
                    descricao_atividade = "Natação";
                    break;
                case 4:
                    descricao_atividade = "Caminhada";
                    break;
                default:
                    System.out.println("Número inválido");
                    return;
            }
            Atividade atividade;
            System.out.print("Que distância percorreu (em km)? ");
            int distancia = input.nextInt();
            atividade = new AtividadeDistancia(descricao_atividade, tempo_dispendido, 0, distancia);
            atividade.ConsumoCalorias(utilizador);
            atividades.put(data, atividade);
        }
        // Atividade de séries de repetições
        if(tipo_atividade == 3){
            System.out.println("\t- Elevações (1)");
            System.out.println("\t- Flexões (2)");
            System.out.println("\t- Abdominais (3)");
            System.out.println("\t- Agachamentos (4)");
            int cod_descricao_atividade = input.nextInt();
            String descricao_atividade;
            switch (cod_descricao_atividade) {
                case 1:
                    descricao_atividade = "Elevações";
                    break;
                case 2:
                    descricao_atividade = "Flexões";
                    break;
                case 3:
                    descricao_atividade = "Abdominais";
                    break;
                case 4:
                    descricao_atividade = "Agachamentos";
                    break;
                default:
                    System.out.println("Número inválido");
                    return;
            }
            Atividade atividade;
            System.out.print("Qual o nº de séries? ");
            int series = input.nextInt();
            System.out.print("Qual o nº de repetições? ");
            int repeticoes = input.nextInt();
            atividade = new AtividadeRepeticoes(descricao_atividade, tempo_dispendido, 0, series, repeticoes);
            atividade.ConsumoCalorias(utilizador);
            atividades.put(data, atividade);
        }
        // Atividade de séries de repetições com pesos       
        if(tipo_atividade == 4){
            System.out.println("\t- Extensão de pernas (1)");
            System.out.println("\t- Supino reto (2)");
            System.out.println("\t- Levantamento terra (3)");
            System.out.println("\t- Extensão de tríceps (4)");
            int cod_descricao_atividade = input.nextInt();
            String descricao_atividade;
            switch (cod_descricao_atividade) {
                case 1:
                    descricao_atividade = "Extensão de pernas";
                    break;
                case 2:
                    descricao_atividade = "Supino reto";
                    break;
                case 3:
                    descricao_atividade = "Levantamento terra";
                    break;
                case 4:
                    descricao_atividade = "Extensão de tríceps";
                    break;
                default:
                    System.out.println("Número inválido");
                    return;
            }
            Atividade atividade;
            System.out.print("Qual o nº de séries? ");
            int series = input.nextInt();
            System.out.print("Qual o nº de repetições? ");
            int repeticoes = input.nextInt();
            System.out.print("Qual a quantidade de peso (em kg)? ");
            int pesos = input.nextInt();
            atividade = new AtividadeRepeticoesPesos(descricao_atividade, tempo_dispendido, 0, series, repeticoes, pesos);
            atividade.ConsumoCalorias(utilizador);
            atividades.put(data, atividade);
        }
    }

    public static void writeSetAtividade(HashSet <Atividade> atividades, Scanner input, Utilizador utilizador, DayOfWeek data){
        System.out.print("A sua atividade é: de distância e altimetria (1), de distância (2), de série de repetições (3) ou de série de repetições com pesos (4)? ");
        int tipo_atividade = input.nextInt();
        System.out.print("Insira o tempo da realização da atividade (em minutos)? ");
        int tempo_dispendido = input.nextInt();
        input.nextLine(); // Consumir o \n
        System.out.println("Insira o número correspondente à sua atividade"); 
        
        // Atividade de distância e altimetria
        if(tipo_atividade == 1){
            System.out.println("\t- Bicicleta de montanha (1)");
            System.out.println("\t- Trail (2)");
            System.out.println("\t- Caminhada de montanha (3)");
            int cod_descricao_atividade = input.nextInt();
            String descricao_atividade;
            switch (cod_descricao_atividade) {
                case 1:
                    descricao_atividade = "Bicicleta de montanha";
                    break;
                case 2:
                    descricao_atividade = "Trail";
                    break;
                case 3:
                    descricao_atividade = "Caminhada de montanha";
                    break;
                default:
                    System.out.println("Número inválido");
                    return;
            }
            Atividade atividade;
            System.out.print("Qual a distância da atividade (em km)? ");
            int distancia = input.nextInt();
            input.nextLine(); // Consumir o \n
            System.out.print("Qual a altimetria da atividade (em m)? ");
            int altimetria = input.nextInt();
            atividade = new AtividadeDistanciaAltimetria(descricao_atividade, tempo_dispendido, 0, distancia, altimetria);
            atividade.ConsumoCalorias(utilizador);
            atividades.add(atividade);
        }
        // Atividade de distância
        if(tipo_atividade == 2){
            System.out.println("\t- Corrida (1)");
            System.out.println("\t- Ciclismo (2)");
            System.out.println("\t- Natação (3)");
            System.out.println("\t- Caminhada (4)");
            int cod_descricao_atividade = input.nextInt();
            String descricao_atividade;
            switch (cod_descricao_atividade) {
                case 1:
                    descricao_atividade = "Corrida";
                    break;
                case 2:
                    descricao_atividade = "Ciclismo";
                    break;
                case 3:
                    descricao_atividade = "Natação";
                    break;
                case 4:
                    descricao_atividade = "Caminhada";
                    break;
                default:
                    System.out.println("Número inválido");
                    return;
            }
            Atividade atividade;
            System.out.print("Que distância percorreu (em km)? ");
            int distancia = input.nextInt();
            atividade = new AtividadeDistancia(descricao_atividade, tempo_dispendido, 0, distancia);
            atividade.ConsumoCalorias(utilizador);
            atividades.add(atividade);
        }
        // Atividade de séries e repetições
        if(tipo_atividade == 3){
            System.out.println("\t- Elevações (1)");
            System.out.println("\t- Flexões (2)");
            System.out.println("\t- Abdominais (3)");
            System.out.println("\t- Agachamentos (4)");
            int cod_descricao_atividade = input.nextInt();
            String descricao_atividade;
            switch (cod_descricao_atividade) {
                case 1:
                    descricao_atividade = "Elevações";
                    break;
                case 2:
                    descricao_atividade = "Flexões";
                    break;
                case 3:
                    descricao_atividade = "Abdominais";
                    break;
                case 4:
                    descricao_atividade = "Agachamentos";
                    break;
                default:
                    System.out.println("Número inválido");
                    return;
            }
            Atividade atividade;
            System.out.print("Qual o nº de séries? ");
            int series = input.nextInt();
            System.out.print("Qual o nº de repetições? ");
            int repeticoes = input.nextInt();
            atividade = new AtividadeRepeticoes(descricao_atividade, tempo_dispendido, 0, series, repeticoes);
            atividade.ConsumoCalorias(utilizador);
            atividades.add(atividade);
        }
        // Atividade de séries de repetições com pesos       
        if(tipo_atividade == 4){
            System.out.println("\t- Extensão de pernas (1)");
            System.out.println("\t- Supino reto (2)");
            System.out.println("\t- Levantamento terra (3)");
            System.out.println("\t- Extensão de tríceps (4)");
            int cod_descricao_atividade = input.nextInt();
            String descricao_atividade;
            switch (cod_descricao_atividade) {
                case 1:
                    descricao_atividade = "Extensão de pernas";
                    break;
                case 2:
                    descricao_atividade = "Supino reto";
                    break;
                case 3:
                    descricao_atividade = "Levantamento terra";
                    break;
                case 4:
                    descricao_atividade = "Extensão de tríceps";
                    break;
                default:
                    System.out.println("Número inválido");
                    return;
            }
            Atividade atividade;
            System.out.print("Qual o nº de séries? ");
            int series = input.nextInt();
            System.out.print("Qual o nº de repetições? ");
            int repeticoes = input.nextInt();
            System.out.print("Qual a quantidade de peso (em kg)? ");
            int pesos = input.nextInt();
            atividade = new AtividadeRepeticoesPesos(descricao_atividade, tempo_dispendido, 0, series, repeticoes, pesos);
            atividade.ConsumoCalorias(utilizador);
            atividades.add(atividade);
        }
    }

    public static LocalDate[] writeDates(Scanner input){
        LocalDate[] dates = new LocalDate[2];
        System.out.print("Insira a data de início (no formato 'yyyy-MM-dd'): ");
        String dataString = input.nextLine();
        // Formatar a data da atividade
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dates[0] = LocalDate.parse(dataString, formatter);
        System.out.print("Insira a data de fim (no formato 'yyyy-MM-dd'): ");
        dataString = input.nextLine();
        dates[1] = LocalDate.parse(dataString, formatter);
        return dates;
    }
}