import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Recordes{
    private Map <String, Map<String, Integer>> recordes;

    // Construtor por omissão
    public Recordes(){
        this.recordes = new TreeMap<String, Map<String, Integer>>();
    }

    // Construtor parametrizado
    public Recordes(Map <String, Map<String, Integer>> recordes){
        this.setRecordes(recordes);
    }

    // Construtor de cópia
    public Recordes(Recordes myRecordes){
        this.setRecordes(myRecordes.getRecordes());
    }

    // Getter e setter
    public Map <String, Map<String, Integer>> getRecordes(){
        Map<String, Map<String, Integer>> copy = new TreeMap<String, Map<String, Integer>>();
        for (Map.Entry<String, Map<String, Integer>> entry : this.recordes.entrySet()) {
            String key = entry.getKey();
            Map<String, Integer> value = new LinkedHashMap<String, Integer>(entry.getValue());
            copy.put(key, value);
        }
        return copy;
    }

    public void setRecordes(Map<String,Map<String,Integer>> newRecordes){
        recordes.clear();
        for (Map.Entry<String, Map<String, Integer>> entry : newRecordes.entrySet()) {
            String key = entry.getKey();
            Map<String, Integer> valueCopy = new LinkedHashMap<String, Integer>(entry.getValue());
            recordes.put(key, valueCopy);
        }
    }


    public void fillRecord(Utilizador utilizador){
        TreeMap<String, Map<String, Integer>> recordes = new TreeMap<String, Map<String, Integer>>(this.getRecordes());
        // Percorrer as atividades do histórico
        if(recordes.isEmpty()){
            LinkedHashMap<String, Integer> recordes_AtividadeDistancia = new LinkedHashMap<String, Integer>();
            int distancia_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistancia.class))
                                    .mapToInt(e -> ((AtividadeDistancia) e.getValue()).getDistancia()).max().orElse(0);
            recordes_AtividadeDistancia.put("Distância (km)", distancia_max);
            recordes_AtividadeDistancia.put("Código de utilizador (recorde de distância)", utilizador.getCodigo());

            int caloriasDistancia_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistancia.class))
                                    .mapToInt(e -> ((AtividadeDistancia) e.getValue()).getCaloriasGastas()).max().orElse(0);
            recordes_AtividadeDistancia.put("Calorias (kcal)", caloriasDistancia_max);
            recordes_AtividadeDistancia.put("Código de utilizador (recorde de calorias)", utilizador.getCodigo());
            recordes.put("Atividade de Distância", recordes_AtividadeDistancia);  // distância - AtividadeDistância
            LinkedHashMap<String, Integer> recordes_AtividadeDistanciaAltimetria = new LinkedHashMap<String, Integer>();
            
            
            int distanciaAltimetria_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistanciaAltimetria.class))
                                    .mapToInt(e -> ((AtividadeDistanciaAltimetria) e.getValue()).getDistancia()).max().orElse(0);
            recordes_AtividadeDistanciaAltimetria.put("Distância (km)", distanciaAltimetria_max);  // distância - AtividadeDistânciaAltimetria
            recordes_AtividadeDistanciaAltimetria.put("Código de utilizador (recorde de distância)", utilizador.getCodigo());
            
            int altimetria_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistanciaAltimetria.class))
                                    .mapToInt(e -> ((AtividadeDistanciaAltimetria) e.getValue()).getAltimetria()).max().orElse(0);
            recordes_AtividadeDistanciaAltimetria.put("Altimetria (m)", altimetria_max);
            recordes_AtividadeDistanciaAltimetria.put("Código de utilizador (recorde de altimetria)", utilizador.getCodigo());

            int caloriasAltimetria_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistanciaAltimetria.class))
                                    .mapToInt(e -> ((AtividadeDistanciaAltimetria) e.getValue()).getCaloriasGastas()).max().orElse(0);
            recordes_AtividadeDistanciaAltimetria.put("Calorias (kcal)", caloriasAltimetria_max);
            recordes_AtividadeDistanciaAltimetria.put("Código de utilizador (recorde de calorias)", utilizador.getCodigo());
            recordes.put("Atividade de Distância e Altimetria", recordes_AtividadeDistanciaAltimetria);  // altimetria - AtividadeDistânciaAltimetria
            LinkedHashMap<String, Integer> recordes_AtividadeRepeticoes = new LinkedHashMap<String, Integer>();
            
            
            int series_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoes.class))
                                    .mapToInt(e -> ((AtividadeRepeticoes) e.getValue()).getSeries()).max().orElse(0);
            recordes_AtividadeRepeticoes.put("Séries", series_max);    // séries - AtividadeRepeticoes
            recordes_AtividadeRepeticoes.put("Código de utilizador (recorde de séries)", utilizador.getCodigo());
            
            int repeticoes_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoes.class))
                                    .mapToInt(e -> ((AtividadeRepeticoes) e.getValue()).getRepeticoes()).max().orElse(0);
            recordes_AtividadeRepeticoes.put("Repetições", repeticoes_max);
            recordes_AtividadeRepeticoes.put("Código de utilizador (recorde de repetições)", utilizador.getCodigo());

            int caloriasRepeticoes_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoes.class))
                                    .mapToInt(e -> ((AtividadeRepeticoes) e.getValue()).getCaloriasGastas()).max().orElse(0);
            recordes_AtividadeRepeticoes.put("Calorias (kcal)", caloriasRepeticoes_max);
            recordes_AtividadeRepeticoes.put("Código de utilizador (recorde de calorias)", utilizador.getCodigo());
            recordes.put("Atividade de Repetições", recordes_AtividadeRepeticoes);  // repetições - AtividadeRepeticoes
            LinkedHashMap<String, Integer> recordes_AtividadeRepeticoesPesos = new LinkedHashMap<String, Integer>();
            
            
            int seriesPesos_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoesPesos.class))
                                    .mapToInt(e -> ((AtividadeRepeticoesPesos) e.getValue()).getSeries()).max().orElse(0);
            recordes_AtividadeRepeticoesPesos.put("Séries", seriesPesos_max);  // séries - AtividadeRepeticoesPesos
            recordes_AtividadeRepeticoesPesos.put("Código de utilizador (recorde de séries)", utilizador.getCodigo());
            
            int repeticoesPesos_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoesPesos.class))
                                    .mapToInt(e -> ((AtividadeRepeticoesPesos) e.getValue()).getRepeticoes()).max().orElse(0);
            recordes_AtividadeRepeticoesPesos.put("Repetições", repeticoesPesos_max);    // repetições - AtividadeRepeticoesPesos
            recordes_AtividadeRepeticoesPesos.put("Código de utilizador (recorde de repetições)", utilizador.getCodigo());
            
            int pesos_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoesPesos.class))
                                    .mapToInt(e -> ((AtividadeRepeticoesPesos) e.getValue()).getPesos()).max().orElse(0);
            recordes_AtividadeRepeticoesPesos.put("Pesos (kg)", pesos_max);
            recordes_AtividadeRepeticoesPesos.put("Código de utilizador (recorde de pesos)", utilizador.getCodigo());

            int caloriasPesos_max = utilizador.getAtividades().entrySet()
                                    .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoesPesos.class))
                                    .mapToInt(e -> ((AtividadeRepeticoesPesos) e.getValue()).getCaloriasGastas()).max().orElse(0);
            recordes_AtividadeRepeticoesPesos.put("Calorias (kcal)", caloriasPesos_max);
            recordes_AtividadeRepeticoesPesos.put("Código de utilizador (recorde de calorias)", utilizador.getCodigo());
            recordes.put("Atividade de Repetições com Pesos", recordes_AtividadeRepeticoesPesos);  // pesos - AtividadeRepeticoesPesos
        }
        // Se já existirem recordes, atualiza-os
        else{
            int distancia_max = utilizador.getRecordes().entrySet()
                                    .stream().filter(e -> e.getKey().equals("Atividade de Distância"))
                                    .mapToInt(e -> e.getValue().get("Distância (km)")).findFirst()
                                    .orElse(0);
            if(distancia_max > this.getRecordes().get("Atividade de Distância").get("Distância (km)")){
                recordes.get("Atividade de Distância").put("Distância (km)", distancia_max);
                recordes.get("Atividade de Distância").put("Código de utilizador (recorde de distância)", utilizador.getCodigo());
            }

            int caloriasDistancia_max = utilizador.getRecordes().entrySet()
                                    .stream().filter(e -> e.getKey().equals("Atividade de Distância"))
                                    .mapToInt(e -> e.getValue().get("Calorias (kcal)")).findFirst()
                                    .orElse(0);
            if(caloriasDistancia_max > this.getRecordes().get("Atividade de Distância").get("Calorias (kcal)")){
                recordes.get("Atividade de Distância").put("Calorias (kcal)", caloriasDistancia_max);
                recordes.get("Atividade de Distância").put("Código de utilizador (recorde de calorias)", utilizador.getCodigo());
            }


            int distanciaAltimetria_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Distância e Altimetria"))
                                            .mapToInt(e -> e.getValue().get("Distância (km)")).findFirst()
                                            .orElse(0);
            if(distanciaAltimetria_max > this.getRecordes().get("Atividade de Distância e Altimetria").get("Distância (km)")){
                recordes.get("Atividade de Distância e Altimetria").put("Distância (km)", distanciaAltimetria_max);
                recordes.get("Atividade de Distância e Altimetria").put("Código de utilizador (recorde de distância)", utilizador.getCodigo());
            }

            int altimetria_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Distância e Altimetria"))
                                            .mapToInt(e -> e.getValue().get("Altimetria (m)")).findFirst()
                                            .orElse(0);
            if(altimetria_max > this.getRecordes().get("Atividade de Distância e Altimetria").get("Altimetria (m)")){
                recordes.get("Atividade de Distância e Altimetria").put("Altimetria (m)", altimetria_max);
                recordes.get("Atividade de Distância e Altimetria").put("Código de utilizador (recorde de altimetria)", utilizador.getCodigo());
            }

            int caloriasAltimetria_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Distância e Altimetria"))
                                            .mapToInt(e -> e.getValue().get("Calorias (kcal)")).findFirst()
                                            .orElse(0);
            if(caloriasAltimetria_max > this.getRecordes().get("Atividade de Distância e Altimetria").get("Calorias (kcal)")){
                recordes.get("Atividade de Distância e Altimetria").put("Calorias (kcal)", caloriasAltimetria_max);
                recordes.get("Atividade de Distância e Altimetria").put("Código de utilizador (recorde de calorias)", utilizador.getCodigo());
            }


            int series_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Repetições"))
                                            .mapToInt(e -> e.getValue().get("Séries")).findFirst()
                                            .orElse(0);
            if(series_max > this.getRecordes().get("Atividade de Repetições").get("Séries")){
                recordes.get("Atividade de Repetições").put("Séries", series_max);
                recordes.get("Atividade de Repetições").put("Código de utilizador (recorde de séries)", utilizador.getCodigo());
            }

            int repeticoes_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Repetições"))
                                            .mapToInt(e -> e.getValue().get("Repetições")).findFirst()
                                            .orElse(0);
            if(repeticoes_max > this.getRecordes().get("Atividade de Repetições").get("Repetições")){
                recordes.get("Atividade de Repetições").put("Repetições", repeticoes_max);
                recordes.get("Atividade de Repetições").put("Código de utilizador (recorde de repetições)", utilizador.getCodigo());
            }

            int caloriasRepeticoes_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Repetições"))
                                            .mapToInt(e -> e.getValue().get("Calorias (kcal)")).findFirst()
                                            .orElse(0);
            if(caloriasRepeticoes_max > this.getRecordes().get("Atividade de Repetições").get("Calorias (kcal)")){
                recordes.get("Atividade de Repetições").put("Calorias (kcal)", caloriasRepeticoes_max);
                recordes.get("Atividade de Repetições").put("Código de utilizador (recorde de calorias)", utilizador.getCodigo());
            }
            

            int seriesPesos_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Repetições com Pesos"))
                                            .mapToInt(e -> e.getValue().get("Séries")).findFirst()
                                            .orElse(0);
            if(seriesPesos_max > this.getRecordes().get("Atividade de Repetições com Pesos").get("Séries")){
                recordes.get("Atividade de Repetições com Pesos").put("Séries", seriesPesos_max);
                recordes.get("Atividade de Repetições com Pesos").put("Código de utilizador (recorde de séries)", utilizador.getCodigo());
            }

            int repeticoesPesos_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Repetições com Pesos"))
                                            .mapToInt(e -> e.getValue().get("Repetições")).findFirst()
                                            .orElse(0);
            if(repeticoesPesos_max > this.getRecordes().get("Atividade de Repetições com Pesos").get("Repetições")){
                recordes.get("Atividade de Repetições com Pesos").put("Repetições", repeticoesPesos_max);
                recordes.get("Atividade de Repetições com Pesos").put("Código de utilizador (recorde de repetições)", utilizador.getCodigo());
            }

            int pesos_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Repetições com Pesos"))
                                            .mapToInt(e -> e.getValue().get("Pesos (kg)")).findFirst()
                                            .orElse(0);
            if(pesos_max > this.getRecordes().get("Atividade de Repetições com Pesos").get("Pesos (kg)")){
                recordes.get("Atividade de Repetições com Pesos").put("Pesos (kg)", pesos_max);
                recordes.get("Atividade de Repetições com Pesos").put("Código de utilizador (recorde de pesos)", utilizador.getCodigo());
            }
            int caloriasPesos_max = utilizador.getRecordes().entrySet()
                                            .stream().filter(e -> e.getKey().equals("Atividade de Repetições com Pesos"))
                                            .mapToInt(e -> e.getValue().get("Calorias (kcal)")).findFirst()
                                            .orElse(0);
            if(caloriasPesos_max > this.getRecordes().get("Atividade de Repetições com Pesos").get("Calorias (kcal)")){
                recordes.get("Atividade de Repetições com Pesos").put("Calorias (kcal)", caloriasPesos_max);
                recordes.get("Atividade de Repetições com Pesos").put("Código de utilizador (recorde de calorias)", utilizador.getCodigo());
            }
        }
        this.setRecordes(recordes);
    }



    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || (o.getClass()!=this.getClass())) return false;
        Recordes that = (Recordes) o;
        return this.recordes.equals(that.recordes); 
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\tRecordes\n");

        for (Map.Entry<String, Map<String, Integer>> entry : this.getRecordes().entrySet()) {
            String tipoAtividade = entry.getKey();
            Map<String, Integer> parametros = entry.getValue();
            sb.append("\t\tTipo de Atividade: " + tipoAtividade + "\n");
            for (Map.Entry<String, Integer> parametroEntry : parametros.entrySet()) {
                String parametro = parametroEntry.getKey();
                int valor = parametroEntry.getValue();
                if(parametro.startsWith("Código de utilizador")) sb.append("\t\t\t\t" + parametro + ": " + valor + "\n");
                else sb.append("\t\t\t" + parametro + ": " + valor + "\n");
            }
        }

        return sb.toString();
    }


    public Recordes clone(){
        return new Recordes(this);
    }
}