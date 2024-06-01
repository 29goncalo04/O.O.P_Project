import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Controller_estatisticas {

    public static void menu_estatisticas(Scanner input, Recordes recordes, List<Utilizador> utilizadores){ 
        System.out.println("Insira o número correspondente à opção que deseja");
        System.out.println("Ver recordes para cada tipo de atividade (1)," 
                            + " qual o utilizador que mais calorias gastou (2),"
                            + " qual o utilizador que mais atividades efetuou (3),"
                            + " qual o tipo de atividade mais realizada (4),"
                            + " quantos kms de distância um utilizador realizou (5)," 
                            + " quantos m de altimetria um utilizador totalizou (6),"
                            + " qual o plano de treino mais exigente em função do gasto de calorias (7) ou"
                            + " ver as atividades que um utilizador fez (8):");  //completar 
        int opcao_estatisticas = input.nextInt();
        switch (opcao_estatisticas) {
        case 1:
            //mostrar recordes de cada tipo de atividade
            System.out.println(recordes.toString());
            break;
        case 2:
            System.out.print("Deseja ver o utilizador com mais calorias gastas desde sempre (1) ou num período de tempo (2): ");
            int opcao_topCalorias = input.nextInt();
            if(opcao_topCalorias == 1){
                System.out.print(Estatisticas.mostCaloriesEver(utilizadores));
            }
            else if(opcao_topCalorias == 2){
                input.nextLine();
                LocalDate[] dates = Funcoes_auxiliares.writeDates(input);
                System.out.print(Estatisticas.mostCaloriesDate(utilizadores, dates[0], dates[1]));
            }
            break;
        case 3:
            System.out.print("Deseja ver o utilizador com mais atividades efetuadas desde sempre (1) ou num período de tempo (2): ");
            int opcao_topAtividades = input.nextInt();
            if(opcao_topAtividades == 1){
                System.out.print(Estatisticas.mostActivitiesEver(utilizadores));
            }
            else if(opcao_topAtividades == 2){
                input.nextLine();
                LocalDate[] dates = Funcoes_auxiliares.writeDates(input);
                System.out.print(Estatisticas.mostActivitiesDate(utilizadores, dates[0], dates[1]));
            }
            break;
        case 4:
            System.out.print(Estatisticas.mostPerformedActivityType(utilizadores));
            break;
        case 5:
            System.out.print("Deseja ver um utilizador com mais kms percorridos desde sempre (1) ou num período de tempo (2): ");
            int opcao_distanceUser = input.nextInt();
            if(opcao_distanceUser == 1){
                System.out.print(Estatisticas.mostDistanceEver(input));
            }
            else if(opcao_distanceUser == 2){
                input.nextLine();
                LocalDate[] dates = Funcoes_auxiliares.writeDates(input);
                System.out.print(Estatisticas.mostDistanceDate(input, dates[0], dates[1]));
            }
            break;
        case 6:
            System.out.print("Deseja ver um utilizador com mais m de altimetria totalizados desde sempre (1) ou num período de tempo (2): ");
            int opcao_altimetryUser = input.nextInt();
            if(opcao_altimetryUser == 1){
                System.out.print(Estatisticas.mostAltimetryEver(input));
            }
            else if(opcao_altimetryUser == 2){
                input.nextLine();
                LocalDate[] dates = Funcoes_auxiliares.writeDates(input);
                System.out.print(Estatisticas.mostAltimetryDate(input, dates[0], dates[1]));
            }
            break;
        case 7:
            System.out.println(Estatisticas.mostIntensiveTrainingPlan(utilizadores));
            break;
        case 8:
            System.out.print(Estatisticas.acivitiesUser(input));
            break;
        default:
            break;
        }
    }

}