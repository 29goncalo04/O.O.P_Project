import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Estatisticas {
    
    public static String mostCaloriesEver(List<Utilizador> utilizadores){
        int maxCalorias = 0, res = -1;
        for(Utilizador user : utilizadores){
            int calorias = user.getAtividades().entrySet().stream()
                            .mapToInt(e -> e.getValue().getCaloriasGastas()).sum();
            if(calorias>maxCalorias){
                maxCalorias = calorias;
                res = user.getCodigo();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("O utilizador que dispendeu mais calorias desde sempre tem o código " + res + ", e gastou " + maxCalorias + " kcal" + "\n");
        return sb.toString();
    }

    public static String mostCaloriesDate(List<Utilizador> utilizadores, LocalDate beginDate, LocalDate endDate){
        int maxCalorias = 0, res = -1;
        for(Utilizador user : utilizadores){
            int calorias = user.getAtividades().entrySet().stream()
                            .filter(e -> (!e.getKey().toLocalDate().isBefore(beginDate)) && (!e.getKey().toLocalDate().isAfter(endDate)))
                            .mapToInt(e -> e.getValue().getCaloriasGastas()).sum();
            if(calorias>maxCalorias){
                maxCalorias = calorias;
                res = user.getCodigo();
            }
        }
        StringBuilder sb = new StringBuilder();
        if(res == -1) sb.append("Não existem utilizadores com atividades efetuadas entre essas datas\n");
        else sb.append("O utilizador que dispendeu mais calorias nesse período de tempo tem o código " + res + ", e gastou " + maxCalorias + " kcal" + "\n");
        return sb.toString();
    }



    public static String mostActivitiesEver(List<Utilizador> utilizadores){
        int codigo = -1, maxAtividades = -1;
        for(Utilizador user : utilizadores){
            int contador = user.getAtividades().size();
            if(contador>maxAtividades){
                maxAtividades = contador;
                codigo = user.getCodigo();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("O utilizador que realizou mais atividades desde sempre tem o código " + codigo + ", e efetuou " + maxAtividades + " atividades" + "\n");
        return sb.toString();
    }

    public static String mostActivitiesDate(List<Utilizador> utilizadores, LocalDate beginDate, LocalDate endDate){
        int codigo = -1;
        long maxAtividades = 0;
        for(Utilizador user : utilizadores){
            long contador = user.getAtividades().entrySet().stream()
                                .filter(e -> (!e.getKey().toLocalDate().isBefore(beginDate)) && (!e.getKey().toLocalDate().isAfter(endDate)))
                                .count();
            if(contador>maxAtividades){
                maxAtividades = contador;
                codigo = user.getCodigo();
            }
        }
        StringBuilder sb = new StringBuilder();
        if(codigo == -1) sb.append("Não existem utilizadores com atividades efetuadas entre essas datas\n");
        else sb.append("O utilizador que realizou mais atividades nesse período de tempo tem o código " + codigo + ", e efetuou " + maxAtividades + " atividades" + "\n");
        return sb.toString();
    }

    public static String mostPerformedActivityType(List<Utilizador> utilizadores){
        ArrayList<Integer> activityType = new ArrayList<Integer>();      // 0 -> AtividadeDistância  |  1 -> AtividadeDistânciaAltimetria  |  2 -> AtividadeRepetições  |  3 -> AtividadeRepetiçõesPesos
        activityType.add(0);
        activityType.add(0);
        activityType.add(0);
        activityType.add(0);
        String mostPerformedActivityType = null;
        int n = -1;
        for(Utilizador user : utilizadores){
            user.getAtividades().entrySet().stream().map(e -> e.getValue())
                .forEach(e -> {
                    if(e.getClass().equals(AtividadeDistancia.class)) activityType.set(0, activityType.get(0) + 1);
                    else if(e.getClass().equals(AtividadeDistanciaAltimetria.class)) activityType.set(1, activityType.get(1) + 1);
                    else if(e.getClass().equals(AtividadeRepeticoes.class)) activityType.set(2, activityType.get(2) + 1);
                    else if(e.getClass().equals(AtividadeRepeticoesPesos.class)) activityType.set(3, activityType.get(3) + 1);
                });
        }
        n = Collections.max(activityType);
        int position = activityType.indexOf(n);
        if(position == 0) mostPerformedActivityType = "Atividade de Distância";
        else if(position == 1) mostPerformedActivityType = "Atividade de Distância e Altimetria";
        else if(position == 2) mostPerformedActivityType = "Atividade de Séries e Repetições";
        else if(position == 3) mostPerformedActivityType = "Atividade de Séries e Repetições com Pesos";
        StringBuilder sb = new StringBuilder();
        sb.append("A atividade mais realizada foi " + mostPerformedActivityType + ", e foi efetuada " + n + " vezes" + "\n");
        return sb.toString();
    }

    public static String mostDistanceEver(Scanner input){
        System.out.print("Insira o código do utilizador que quer ver: ");
        int codigo = input.nextInt();
        try {
            Utilizador utilizador = Funcoes_ficheiro.findCodigo("Dataset", codigo);
            if(utilizador != null){
                int maxDistance = utilizador.getAtividades().entrySet().stream()
                                    .map(e -> e.getValue())
                                    .filter(e -> e instanceof AtividadeDistancia)
                                    .mapToInt(e -> ((AtividadeDistancia) e).getDistancia()).sum();
                StringBuilder sb = new StringBuilder();
                if(maxDistance == 0) sb.append("O utilizador indicado nunca realizou atividades que envolvam distância\n");
                else sb.append("O utilizador indicado percorreu " + maxDistance + " km desde sempre\n");
                return sb.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Esse utilizador não existe\n";
    }

    public static String mostDistanceDate(Scanner input, LocalDate beginDate, LocalDate endDate){
        System.out.print("Insira o código do utilizador que quer ver: ");
        int codigo = input.nextInt();
        try {
            Utilizador utilizador = Funcoes_ficheiro.findCodigo("Dataset", codigo);
            if(utilizador != null){
                int maxDistance = utilizador.getAtividades().entrySet().stream()
                                    .filter(e -> e.getValue() instanceof AtividadeDistancia && 
                                                (!e.getKey().toLocalDate().isBefore(beginDate)) && (!e.getKey().toLocalDate().isAfter(endDate)))
                                    .mapToInt(e -> ((AtividadeDistancia) e.getValue()).getDistancia()).sum();
                StringBuilder sb = new StringBuilder();
                if(maxDistance == 0) sb.append("O utilizador indicado não realizou atividades que envolvam distância nesse período de tempo\n");
                else sb.append("O utilizador indicado percorreu " + maxDistance + " km nesse período de tempo\n");
                return sb.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Esse utilizador não existe\n";
    }

    public static String mostAltimetryEver(Scanner input){
        System.out.print("Insira o código do utilizador que quer ver: ");
        int codigo = input.nextInt();
        try {
            Utilizador utilizador = Funcoes_ficheiro.findCodigo("Dataset", codigo);
            if(utilizador != null){
                int maxAltimetry = utilizador.getAtividades().entrySet().stream()
                                    .map(e -> e.getValue())
                                    .filter(e -> e.getClass().equals(AtividadeDistanciaAltimetria.class))
                                    .mapToInt(e -> ((AtividadeDistanciaAltimetria) e).getAltimetria()).sum();
                StringBuilder sb = new StringBuilder();
                if(maxAltimetry == 0) sb.append("O utilizador indicado nunca realizou atividades que envolvam altimetria\n");
                else sb.append("O utilizador indicado totalizou " + maxAltimetry + " m de altimetria desde sempre\n");
                return sb.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Esse utilizador não existe\n";
    }

    public static String mostAltimetryDate(Scanner input, LocalDate beginDate, LocalDate endDate){
        System.out.print("Insira o código do utilizador que quer ver: ");
        int codigo = input.nextInt();
        try {
            Utilizador utilizador = Funcoes_ficheiro.findCodigo("Dataset", codigo);
            if(utilizador != null){
                int maxAltimetry = utilizador.getAtividades().entrySet().stream()
                                    .filter(e -> e.getValue().getClass().equals(AtividadeDistanciaAltimetria.class) && 
                                                (!e.getKey().toLocalDate().isBefore(beginDate)) && (!e.getKey().toLocalDate().isAfter(endDate)))
                                    .mapToInt(e -> ((AtividadeDistanciaAltimetria) e.getValue()).getAltimetria()).sum();
                StringBuilder sb = new StringBuilder();
                if(maxAltimetry == 0) sb.append("O utilizador indicado não realizou atividades que envolvam altimetria nesse período de tempo\n");
                else sb.append("O utilizador indicado totalizou " + maxAltimetry + " m de altimetria nesse período de tempo\n");
                return sb.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Esse utilizador não existe\n";
    }

    public static String mostIntensiveTrainingPlan(List<Utilizador> utilizadores){
        PlanoTreino planoTreino = new PlanoTreino();
        int maxCalorias = -1;
        for(Utilizador user : utilizadores){
            int calorias = user.getPlanoTreino().getPlanoTreino().entrySet().stream()
                                    .map(e -> e.getValue()).flatMap(e -> e.stream())
                                    .mapToInt(e -> e.getCaloriasGastas()).sum();
            if(calorias>maxCalorias){
                maxCalorias = calorias;
                planoTreino = user.getPlanoTreino().clone();
            }
        }
        StringBuilder sb = new StringBuilder();
        if(planoTreino.getPlanoTreino().isEmpty()) sb.append("Não existem planos de treino para nenhum utilizador\n");
        else{
            sb.append("O Plano de Treino mais exigente em função das calorias gastas é o seguinte:\n");
            sb.append(planoTreino.toString());
        }
        return sb.toString();
    }

    public static String acivitiesUser(Scanner input){
        System.out.print("Insira o código do utilizador que quer ver: ");
        int codigo = input.nextInt();
        try {
            Utilizador utilizador = Funcoes_ficheiro.findCodigo("Dataset", codigo);
            if(utilizador != null){
                StringBuilder sb = new StringBuilder();
                sb.append("As atividades realizadas por esse utilizador são as seguintes:\n");
                for (Map.Entry<LocalDateTime, Atividade> entry : utilizador.getAtividades().entrySet()) {
                    sb.append("\t\t- Data e hora " + entry.getKey() + "\n" + entry.getValue().clone().toString());
                }
                return sb.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Esse utilizador não existe\n";
    }

}