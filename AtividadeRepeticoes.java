public class AtividadeRepeticoes extends Atividade{
    
    private int series;
    private int repeticoes;

    public int getSeries(){
        return this.series;
    }

    public void setSeries(int series){
        this.series = series;
    }

    public int getRepeticoes(){
        return this.repeticoes;
    }

    public void setRepeticoes(int repeticoes){
        this.repeticoes = repeticoes;
    }

    // Construtor por omissão
    public AtividadeRepeticoes(){
        super(); // Chamar os dados da classe Atividade
        this.series = -1;
        this.repeticoes = -1;
    }

    // Construtor parametrizado
    public AtividadeRepeticoes(String descricao, int tempo_dispendido, int caloriasGastas, int series, int repeticoes){
        super(descricao, tempo_dispendido, caloriasGastas); // Chamar os dados da classe Atividade
        this.series = series;
        this.repeticoes = repeticoes;
    }

    // Construtor de cópia
    public AtividadeRepeticoes(AtividadeRepeticoes myAtividade){
        super(myAtividade); // Chamar os dados da classe Atividade
        this.series = myAtividade.getSeries();
        this.repeticoes = myAtividade.getRepeticoes();
    }

    public boolean isHard(String descricao){
        if(descricao.equals("Elevações")) return true;
        return false;
    }

    // O consumo de calorias vai depender dos dados do utilizador
    public void ConsumoCalorias(Utilizador atleta){        
        String descricao = this.getDescricao();
        int caloriasGastas = 0; 
        switch (descricao) {
            case "Elevações":
                // 5 (MET) por ser Hard
                caloriasGastas = 5 * this.getRepeticoes() * this.getSeries();
                break;
            
            case "Flexões":
                // 3 (MET)
                caloriasGastas = 3 * this.getRepeticoes() * this.getSeries();
                break;
        
            case "Abdominais":
                // 2 (MET)
                caloriasGastas = 2 * this.getRepeticoes() * this.getSeries();
                break;
            
            case "Agachamentos":
                // 4 (MET)
                caloriasGastas = 4 * this.getRepeticoes() * this.getSeries();
                break;
            default:
                break;
        }

        caloriasGastas *= atleta.fatorMultiplicativo(atleta.getIdade(), atleta.getAltura(), atleta.getGenero(), atleta.getFreqCardiacaMedia());
        
        this.setCaloriasGastas(caloriasGastas);
    }

    public boolean equals(Object o){
        if(this==o) return true;
        if(!(super.equals(o))) return false;
        AtividadeRepeticoes that = (AtividadeRepeticoes) o;
        return this.series==that.series && this.repeticoes==that.repeticoes;
    }

    public String toString(){
        return super.toString() + "\t\t\tNúmero de séries: " + this.getSeries() + "\n\t\t\tNúmero de repetições: " + this.getRepeticoes() + "\n";
    }

    public AtividadeRepeticoes clone(){
        return new AtividadeRepeticoes(this);
    }
}