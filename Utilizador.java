import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Utilizador implements Serializable {
    private int codigo;
    private String nome;
    private int idade;
    private int altura;
    private int peso;
    private String email;
    private String morada;
    private char genero;
    private int freq_cardiaca_media;
    private Map <LocalDateTime, Atividade> atividades;
    private PlanoTreino plano_treino;
    private Map <String, Map<String, Integer>> recordes; // <Tipo de atividade, <Parâmetro, valor do parâmetro>>


    // Construtor por omissão
    public Utilizador(){
        this.codigo = -1;
        this.nome = null;
        this.idade = -1;
        this.altura = -1;
        this.peso = -1;
        this.email = null;
        this.morada = null;
        this.genero = '\0';
        this.freq_cardiaca_media = -1;
        this.atividades = new TreeMap<LocalDateTime,Atividade>();
        this.plano_treino = new PlanoTreino();
        this.recordes = new TreeMap<String, Map<String, Integer>>();
    }

    // Construtor parametrizado
    public Utilizador (int codigo, String nome, int idade, int altura,int peso, String email, String morada,
                       char genero, int freq_cardiaca_media, Map<LocalDateTime,Atividade> atividades,
                       PlanoTreino plano_treino, Map <String, Map<String, Integer>> recordes){
        this.codigo = codigo;
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.email = email;
        this.morada = morada;
        this.genero = genero;
        this.freq_cardiaca_media = freq_cardiaca_media;
        this.setAtividades(atividades);
        this.setPlanoTreino(plano_treino);
        this.setRecordes(recordes);
    }

    // Construtor de cópia
    public Utilizador (Utilizador myUtilizador){
        this.codigo = myUtilizador.getCodigo();
        this.nome = myUtilizador.getNome();
        this.idade = myUtilizador.getIdade();
        this.altura = myUtilizador.getAltura();
        this.peso = myUtilizador.getPeso();
        this.email = myUtilizador.getEmail();
        this.morada = myUtilizador.getMorada();
        this.genero = myUtilizador.getGenero();
        this.freq_cardiaca_media = myUtilizador.getFreqCardiacaMedia();
        this.setAtividades(myUtilizador.getAtividades());
        this.setPlanoTreino(myUtilizador.getPlanoTreino());
        this.setRecordes(myUtilizador.getRecordes());
    }

    // Getters e setters
    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getAltura() {
        return this.altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso(){
        return this.peso;
    }

    public void setPeso(int peso){
        this.peso = peso;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return this.morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public char getGenero() {
        return this.genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public int getFreqCardiacaMedia() {
        return this.freq_cardiaca_media;
    }

    public void setFreqCardiacaMedia(int freq_cardiaca_media) {
        this.freq_cardiaca_media = freq_cardiaca_media;
    }


    public Map<LocalDateTime, Atividade> getAtividades() {
        TreeMap<LocalDateTime, Atividade> atividadesTreeMap = new TreeMap<>();
        this.atividades.forEach((key, value) -> atividadesTreeMap.put(key, value.clone()));
        return atividadesTreeMap;
    }
    
    public void setAtividades(Map<LocalDateTime, Atividade> atividades) {
        this.atividades = atividades.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone()));
    }    

    public PlanoTreino getPlanoTreino() {
        return this.plano_treino;
    }

    public void setPlanoTreino(PlanoTreino plano_treino) {
        this.plano_treino = plano_treino;
    }

    public Map<String, Map<String, Integer>> getRecordes(){
        Map<String, Map<String, Integer>> copy = new TreeMap<String, Map<String, Integer>>();
        for (Map.Entry<String, Map<String, Integer>> entry : recordes.entrySet()) {
            String key = entry.getKey();
            TreeMap<String, Integer> value = new TreeMap<String, Integer>(entry.getValue());
            copy.put(key, value);
        }
        return copy;
    }

    public void setRecordes(Map<String,Map<String,Integer>> newRecordes){
        recordes = new TreeMap<String, Map<String, Integer>>();
        for (Map.Entry<String, Map<String, Integer>> entry : newRecordes.entrySet()) {
            String key = entry.getKey();
            Map<String, Integer> valueCopy = new HashMap<String, Integer>(entry.getValue());
            recordes.put(key, valueCopy);
        }
    }
    
    
    
    public double fatorMultiplicativo(int idade, int altura, char genero, int freq_cardiaca_media){
        double fator_multiplicativo = 1;

        // idade
        if(0<idade && idade<=24) fator_multiplicativo *= 1;
        else if(25<=idade && idade<=34) fator_multiplicativo *= 0.95;
        else if(35<=idade && idade<=44) fator_multiplicativo *= 0.90;
        else if(45<=idade && idade<=54) fator_multiplicativo *= 0.85;
        else fator_multiplicativo *= 0.80;

        // altura
        if(0<altura && altura<=160) fator_multiplicativo *= 1.05;
        else if(161<=altura && altura <=170) fator_multiplicativo *= 1.00;
        else fator_multiplicativo *= 0.95;

        // genero
        if(genero == 'M') fator_multiplicativo *= 1.00;        
        else fator_multiplicativo *= 0.90;

        // freq_cardiaca_media
        if(0<freq_cardiaca_media && freq_cardiaca_media<=108) fator_multiplicativo *= 0.50;
        else if(109<=freq_cardiaca_media && freq_cardiaca_media<=143) fator_multiplicativo *= 0.75;
        else if(144<=freq_cardiaca_media && freq_cardiaca_media<=180) fator_multiplicativo *= 1.00;
        else fator_multiplicativo *= 1.25;

        return fator_multiplicativo;
    }


    public void fillRecordUser(){
        TreeMap<String, Map<String, Integer>> recordes = new TreeMap<String, Map<String, Integer>>();
        // Percorrer as atividades do histórico
        TreeMap<String, Integer> recordes_AtividadeDistancia = new TreeMap<String, Integer>();
        int distancia_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistancia.class))
                                .mapToInt(e -> ((AtividadeDistancia) e.getValue()).getDistancia()).max().orElse(0);
        recordes_AtividadeDistancia.put("Distância (km)", distancia_max);

        int caloriasDistancia_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistancia.class))
                                .mapToInt(e -> ((AtividadeDistancia) e.getValue()).getCaloriasGastas()).max().orElse(0);
        recordes_AtividadeDistancia.put("Calorias (kcal)", caloriasDistancia_max);
        recordes.put("Atividade de Distância", recordes_AtividadeDistancia);  // distância - AtividadeDistância
        TreeMap<String, Integer> recordes_AtividadeDistanciaAltimetria = new TreeMap<String, Integer>();
        
        
        int distanciaAltimetria_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistanciaAltimetria.class))
                                .mapToInt(e -> ((AtividadeDistanciaAltimetria) e.getValue()).getDistancia()).max().orElse(0);
        recordes_AtividadeDistanciaAltimetria.put("Distância (km)", distanciaAltimetria_max);  // distância - AtividadeDistânciaAltimetria
        
        int altimetria_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistanciaAltimetria.class))
                                .mapToInt(e -> ((AtividadeDistanciaAltimetria) e.getValue()).getAltimetria()).max().orElse(0);
        recordes_AtividadeDistanciaAltimetria.put("Altimetria (m)", altimetria_max);

        int caloriasAltimetria_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeDistanciaAltimetria.class))
                                .mapToInt(e -> ((AtividadeDistanciaAltimetria) e.getValue()).getCaloriasGastas()).max().orElse(0);
        recordes_AtividadeDistanciaAltimetria.put("Calorias (kcal)", caloriasAltimetria_max);
        recordes.put("Atividade de Distância e Altimetria", recordes_AtividadeDistanciaAltimetria);  // altimetria - AtividadeDistânciaAltimetria
        TreeMap<String, Integer> recordes_AtividadeRepeticoes = new TreeMap<String, Integer>();
        
        
        int series_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoes.class))
                                .mapToInt(e -> ((AtividadeRepeticoes) e.getValue()).getSeries()).max().orElse(0);
        recordes_AtividadeRepeticoes.put("Séries", series_max);    // séries - AtividadeRepeticoes
        
        int repeticoes_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoes.class))
                                .mapToInt(e -> ((AtividadeRepeticoes) e.getValue()).getRepeticoes()).max().orElse(0);
        recordes_AtividadeRepeticoes.put("Repetições", repeticoes_max);

        int caloriasRepeticoes_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoes.class))
                                .mapToInt(e -> ((AtividadeRepeticoes) e.getValue()).getCaloriasGastas()).max().orElse(0);
        recordes_AtividadeRepeticoes.put("Calorias (kcal)", caloriasRepeticoes_max);
        recordes.put("Atividade de Repetições", recordes_AtividadeRepeticoes);  // repetições - AtividadeRepeticoes
        TreeMap<String, Integer> recordes_AtividadeRepeticoesPesos = new TreeMap<String, Integer>();
        

        int seriesPesos_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoesPesos.class))
                                .mapToInt(e -> ((AtividadeRepeticoesPesos) e.getValue()).getSeries()).max().orElse(0);
        recordes_AtividadeRepeticoesPesos.put("Séries", seriesPesos_max);  // séries - AtividadeRepeticoesPesos
        
        int repeticoesPesos_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoesPesos.class))
                                .mapToInt(e -> ((AtividadeRepeticoesPesos) e.getValue()).getRepeticoes()).max().orElse(0);
        recordes_AtividadeRepeticoesPesos.put("Repetições", repeticoesPesos_max);    // repetições - AtividadeRepeticoesPesos
        
        int pesos_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoesPesos.class))
                                .mapToInt(e -> ((AtividadeRepeticoesPesos) e.getValue()).getPesos()).max().orElse(0);
        recordes_AtividadeRepeticoesPesos.put("Pesos (kg)", pesos_max);

        int caloriasPesos_max = this.getAtividades().entrySet()
                                .stream().filter(e -> e.getValue().getClass().equals(AtividadeRepeticoesPesos.class))
                                .mapToInt(e -> ((AtividadeRepeticoesPesos) e.getValue()).getCaloriasGastas()).max().orElse(0);
        recordes_AtividadeRepeticoesPesos.put("Calorias (kcal)", caloriasPesos_max);
        recordes.put("Atividade de Repetições com Pesos", recordes_AtividadeRepeticoesPesos);  // pesos - AtividadeRepeticoesPesos
        this.setRecordes(recordes);
    }
    
    
    
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || (o.getClass()!=this.getClass())) return false;
        Utilizador that = (Utilizador) o;
        return this.codigo == that.codigo; 
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nUtilizador\n");
        sb.append("\tCódigo: " + this.getCodigo() + "\n");
        sb.append("\tNome: " + this.getNome() + "\n");
        sb.append("\tIdade: " + this.getIdade() + "\n");
        sb.append("\tAltura: " + this.getAltura() + " cm\n");
        sb.append("\tPeso: " + this.getPeso() + " kg\n");
        sb.append("\tEmail: " + this.getEmail() + "\n");
        sb.append("\tMorada: " + this.getMorada() + "\n");
        sb.append("\tGénero: " + this.getGenero() + "\n");
        sb.append("\tFrequência Cardíaca Média: " + this.getFreqCardiacaMedia() + " bpm\n");
        sb.append("\tAtividades\n");
        for (Map.Entry<LocalDateTime, Atividade> entry : this.getAtividades().entrySet()) {
            sb.append("\t\t- Data e hora " + entry.getKey() + "\n" + entry.getValue().clone().toString());
        }
        sb.append("\tPlano de Treino\n");
        sb.append(this.getPlanoTreino().toString());
        sb.append("\tRecordes\n");

        for (Map.Entry<String, Map<String, Integer>> entry : this.getRecordes().entrySet()) {
            String tipoAtividade = entry.getKey();
            Map<String, Integer> parametros = entry.getValue();
            sb.append("\t\tTipo de Atividade: " + tipoAtividade + "\n");
            for (Map.Entry<String, Integer> parametroEntry : parametros.entrySet()) {
                String parametro = parametroEntry.getKey();
                int valor = parametroEntry.getValue();
                sb.append("\t\t\t" + parametro + ": " + valor + "\n");
            }
        }

        return sb.toString();
    }

    public abstract Utilizador clone();
}