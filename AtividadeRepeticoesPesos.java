public class AtividadeRepeticoesPesos extends AtividadeRepeticoes{
    
    private int pesos;

    public int getPesos(){
        return this.pesos;
    }

    public void setPesos(int pesos){
        this.pesos = pesos;
    }

    // Construtor por omissão
    public AtividadeRepeticoesPesos(){
        super(); // vai chamar os dados da classe AtividadeRepeticoes
        this.pesos = -1;
    }

    // Construtor parametrizado
    public AtividadeRepeticoesPesos(String descricao, int tempo_dispendido, int caloriasGastas, int series, int repeticoes, int pesos){
        super(descricao, tempo_dispendido, caloriasGastas, series, repeticoes); // Chamar os dados da classe AtividadeRepeticoes
        this.pesos = pesos;
    }

    // Construtor de cópia
    public AtividadeRepeticoesPesos(AtividadeRepeticoesPesos myAtividade){
        super(myAtividade); // Chamar os dados da classe AtividadeRepeticoes
        this.pesos = myAtividade.getPesos();
    }

    public boolean isHard(String descricao){
        if(descricao.equals("Extensão de pernas")) return true;
        return false;
    }

    // O consumo de calorias vai depender dos dados do utilizador
    public void ConsumoCalorias(Utilizador atleta){        
        String descricao = this.getDescricao();
        double caloriasGastas_aux = 0; 
        switch (descricao) {
            case "Extensão de pernas":
                // peso_levantado * reps * 0.0003238315 ∗ 0.707     ->> fórmula de estimativa (tal como as outras)
                // atividade Hard
                caloriasGastas_aux = this.getPesos() * this.getRepeticoes() * this.getSeries() * 0.003238315 * 0.707;
                break;
            
            case "Supino reto":
                // peso_levantado/150∗5∗repetições 
                caloriasGastas_aux = (float)this.getPesos()/150 * 5 * this.getRepeticoes() * this.getSeries();
                break;
        
            case "Levantamento terra":
                caloriasGastas_aux = this.getPesos() * (1.0 + (0.2 * (float)this.getPesos() / atleta.getPeso()));
                break;
            
            case "Extensão de tríceps":
                caloriasGastas_aux = this.getPesos() * this.getRepeticoes() * this.getSeries() * 0.003238315 * 0.5;
                break;
            default:
                break;
        }

        caloriasGastas_aux *= atleta.fatorMultiplicativo(atleta.getIdade(), atleta.getAltura(), atleta.getGenero(), atleta.getFreqCardiacaMedia());
        
        int caloriasGastas = (int) caloriasGastas_aux;

        this.setCaloriasGastas(caloriasGastas);
    }


    public boolean equals(Object o){
        if(this==o) return true;
        if(!(super.equals(o))) return false;
        AtividadeRepeticoesPesos that = (AtividadeRepeticoesPesos) o;
        return this.pesos==that.pesos;
    }

    public String toString(){
        return super.toString() + "\t\t\tQuantidade de pesos: " + this.getPesos() + " kg\n";
    }

    public AtividadeRepeticoesPesos clone(){
        return new AtividadeRepeticoesPesos(this);
    }
}