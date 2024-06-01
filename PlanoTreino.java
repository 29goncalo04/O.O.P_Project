import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.stream.Collectors;

public class PlanoTreino implements Serializable{
    Map<DayOfWeek,Set<Atividade>> plano_treino;


    // Construtor por omissão
    public PlanoTreino(){
        this.plano_treino = new TreeMap<DayOfWeek,Set <Atividade>>();
    }
    
    // Construtor parametrizado
    public PlanoTreino(Map<DayOfWeek, HashSet<Atividade>> plano_treino){
        this.setPlanoTreino(plano_treino);
    }

    // Construtor de cópia
    public PlanoTreino(PlanoTreino myPlanoTreino){
        this.setPlanoTreino(myPlanoTreino.getPlanoTreino());
    }


    // Getter e setter
    public Map<DayOfWeek, HashSet<Atividade>> getPlanoTreino(){
        TreeMap<DayOfWeek, HashSet<Atividade>> planoTreinoTreeMap = new TreeMap<>();
        this.plano_treino.forEach((key, value) -> planoTreinoTreeMap.put(key, new HashSet<>(value)));
        return planoTreinoTreeMap;
    }

    public void setPlanoTreino(Map<DayOfWeek, HashSet<Atividade>> plano_treino){
        this.plano_treino = plano_treino.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> new HashSet<>(e.getValue())));
    }



    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || (o.getClass()!=this.getClass())) return false;
        PlanoTreino that = (PlanoTreino) o;
        return this.plano_treino.equals(that.plano_treino); 
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<DayOfWeek,HashSet<Atividade>> plano_treino: this.getPlanoTreino().entrySet()){
            sb.append("\t\t- Dia da semana: " + plano_treino.getKey().name() + "\n");
            for(Atividade a : plano_treino.getValue()){
                sb.append(a.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    public PlanoTreino clone(){
        return new PlanoTreino(this);
    }
}