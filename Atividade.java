import java.io.Serializable;

public abstract class Atividade implements Serializable{
    
    private String descricao;
    private int tempo_dispendido;
    private int caloriasGastas;
    private boolean isHard;
    

    // Construtor por omissão
    public Atividade(){
        this.descricao = null;
        this.tempo_dispendido = -1;
        this.caloriasGastas = -1;
        this.isHard = false;
    }
    
    // Construtor parametrizado
    public Atividade(String descricao, int tempo_dispendido, int caloriasGastas){
        this.descricao = descricao;
        this.tempo_dispendido = tempo_dispendido;
        this.caloriasGastas = caloriasGastas;
        this.isHard = isHard(descricao);
    }
    
    // Construtor de cópia
    public Atividade(Atividade myAtividade){
        this.descricao = myAtividade.getDescricao();
        this.tempo_dispendido = myAtividade.getTempoDispendido();
        this.caloriasGastas = myAtividade.getCaloriasGastas();
        this.isHard = isHard(myAtividade.getDescricao());
    }

    public abstract boolean isHard(String descricao);
    

    // Getters e setters

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTempoDispendido() {
        return this.tempo_dispendido;
    }

    public void setTempoDispendido(int tempo_dispendido) {
        this.tempo_dispendido = tempo_dispendido;
    }

    public int getCaloriasGastas() {
        return this.caloriasGastas;
    }

    public void setCaloriasGastas(int caloriasGastas) {
        this.caloriasGastas = caloriasGastas;
    }

    public boolean getIsHard() {
        return this.isHard;
    }

    public void setIsHard(boolean isHard) {
        this.isHard = isHard;
    }

    // Profissional(x3), Amador(x2), Praticante Ocasional(x1)
    public abstract void ConsumoCalorias(Utilizador atleta);
    

    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || (o.getClass()!=this.getClass())) return false;
        Atividade that = (Atividade) o;
        return this.descricao==that.descricao && this.tempo_dispendido==that.tempo_dispendido && this.caloriasGastas==that.caloriasGastas && this.isHard==that.isHard; 
    }
    
    public abstract Atividade clone();

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t\tDescrição da atividade: " + this.getDescricao() + "\n");
        sb.append("\t\t\tTempo dispendido: " + this.getTempoDispendido() + " minutos\n");
        sb.append("\t\t\tCalorias gastas: " + this.getCaloriasGastas() + "\n");
        sb.append("\t\t\tÉ uma atividade 'Hard'? " + this.getIsHard() + "\n");
        return sb.toString();
    }
}