public class AtividadeDistancia extends Atividade{
    
    private int distancia;

    public int getDistancia(){
        return this.distancia;
    }

    public void setDistancia(int distancia){
        this.distancia = distancia;
    }

    // Construtor por omissão
    public AtividadeDistancia(){
        super(); // Chamar os dados da classe Atividade
        this.distancia = -1;
    }

    // Construtor parametrizado
    public AtividadeDistancia(String descricao, int tempo_dispendido, int caloriasGastas, int distancia){
        super(descricao, tempo_dispendido, caloriasGastas); // vai chamar os dados da classe Atividade
        this.distancia = distancia;
        //(...)
    }

    // Construtor de cópia
    public AtividadeDistancia(AtividadeDistancia myAtividade){
        super(myAtividade); // Chamar os dados da classe Atividade
        this.distancia = myAtividade.getDistancia();
    }

    public boolean isHard(String descricao){
        if(descricao.equals("Natação")) return true;
        return false;
    }

    /*  O consumo de calorias vai depender dos dados do utilizador
        A fórmula usada será:    
            METs (da atividade) x Peso Corporal (Kg) x Duração (min/60)
    */
    public void ConsumoCalorias(Utilizador atleta){
        String descricao = this.getDescricao();
        double caloriasGastas_aux = 0; 
        switch (descricao) {
            case "Corrida":
                // 11 (MET)
                caloriasGastas_aux = 11 * atleta.getPeso() * (float)getTempoDispendido()/60;
                break;
            
            case "Ciclismo":
                // 6 (MET)
                caloriasGastas_aux = 6 * atleta.getPeso() * (float)getTempoDispendido()/60;
                break;
        
            case "Natação":
                // 9 (MET) por ser atividade Hard
                caloriasGastas_aux = 9 * atleta.getPeso() * (float)getTempoDispendido()/60;
                break;
            
            case "Caminhada":
                // 5 (MET)
                caloriasGastas_aux = 5 * atleta.getPeso() * (float)getTempoDispendido()/60;
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
        AtividadeDistancia that = (AtividadeDistancia) o;
        return this.distancia==that.distancia;
    }

    public String toString(){
        return super.toString() + "\t\t\tDistância percorrida: " + this.getDistancia() + " km\n";
    }

    public AtividadeDistancia clone(){
        return new AtividadeDistancia(this);
    }  
}