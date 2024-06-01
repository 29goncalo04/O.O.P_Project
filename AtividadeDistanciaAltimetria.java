public class AtividadeDistanciaAltimetria extends AtividadeDistancia{
    
    private int altimetria;

    public int getAltimetria(){
        return this.altimetria;
    }

    public void setAltimetria(int altimetria){
        this.altimetria = altimetria;
    }

    // Construtor por omissão
    public AtividadeDistanciaAltimetria(){
        super(); // Chamar os dados da classe AtividadeDistancia
        this.altimetria = -1;
    }

    // Construtor parametrizado
    public AtividadeDistanciaAltimetria(String descricao, int tempo_dispendido, int caloriasGastas, int distancia, int altimetria){
        super(descricao, tempo_dispendido, caloriasGastas, distancia); // vai chamar os dados da classe AtividadeDistancia
        this.altimetria = altimetria;
    }

    // Construtor de cópia 
    public AtividadeDistanciaAltimetria(AtividadeDistanciaAltimetria myAtividade){
        super(myAtividade); // Chamar os dados da classe AtividadeDistancia
        this.altimetria = myAtividade.getAltimetria();
    }

    public boolean isHard(String descricao){
        if(descricao.equals("Bicicleta de montanha")) return true;
        return false;
    }

    // O consumo de calorias vai depender dos dados do utilizador
    public void ConsumoCalorias(Utilizador atleta){
        String descricao = this.getDescricao();
        double caloriasGastas_aux = 0; 
        // fatorSubida = (Ganho de altitude em metros / Distância total em km) * 0.035    -> o 0.035 é uma estimativa geral
        double fatorSubida = (this.altimetria/this.getDistancia()) * 0.035 ;
        switch (descricao) {
            case "Bicicleta de montanha":
                // 8 (MET) por ser Hard
                caloriasGastas_aux = 8 * atleta.getPeso() * (float)getTempoDispendido()/60 * (1+fatorSubida);
                break;
            
            case "Trail":
                // 7 (MET)
                caloriasGastas_aux = 7 * atleta.getPeso() * (float)getTempoDispendido()/60 * (1+fatorSubida);
                break;
        
            case "Caminhada de montanha":
                // 6 (MET)
                caloriasGastas_aux = 6 * atleta.getPeso() * (float)getTempoDispendido()/60 * (1+fatorSubida);
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
        AtividadeDistanciaAltimetria that = (AtividadeDistanciaAltimetria) o;
        return this.altimetria==that.altimetria;
    }

    public String toString(){
        return super.toString() + "\t\t\tAltimetria total: " + this.getAltimetria() + " m\n";
    }

    public AtividadeDistanciaAltimetria clone(){
        return new AtividadeDistanciaAltimetria(this);
    }  
}