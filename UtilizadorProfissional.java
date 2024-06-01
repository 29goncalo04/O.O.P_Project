import java.util.Map;
import java.time.LocalDateTime;

public class UtilizadorProfissional extends Utilizador{

    // Construtor por omissão
    public UtilizadorProfissional(){
        super();
    }
    // Construtor parametrizado
    public UtilizadorProfissional(int codigo, String nome, int idade, int altura, int peso, String email, String morada,
                       char genero, int freq_cardiaca_media, Map<LocalDateTime,Atividade> atividades,
                       PlanoTreino plano_treino, Map <String, Map<String, Integer>> recordes){
        super(codigo, nome, idade, altura, peso, email, morada, genero, freq_cardiaca_media, atividades, plano_treino, recordes);
    }
    // Construtor de cópia
    public UtilizadorProfissional(UtilizadorProfissional myUtilizador){
        super(myUtilizador);
    }

    public double fatorMultiplicativo(int idade, int altura, char genero, int freq_cardiaca_media){
        // Bónus do tipo de utilizador
        return super.fatorMultiplicativo(idade, altura, genero, freq_cardiaca_media) * 1.15;
    }

    public UtilizadorProfissional clone (){
        return new UtilizadorProfissional(this);
    }
}