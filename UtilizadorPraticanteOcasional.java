import java.util.Map;
import java.time.LocalDateTime;

public class UtilizadorPraticanteOcasional extends Utilizador{

    // Construtor por omissão
    public UtilizadorPraticanteOcasional(){
        super();
    }

    // Construtor parametrizado
    public UtilizadorPraticanteOcasional(int codigo, String nome, int idade, int altura, int peso, String email, String morada,
                       char genero, int freq_cardiaca_media, Map<LocalDateTime,Atividade> atividades,
                       PlanoTreino plano_treino, Map <String, Map<String, Integer>> recordes){
        super(codigo, nome, idade, altura, peso, email, morada, genero, freq_cardiaca_media, atividades, plano_treino, recordes);
    }

    // Construtor de cópia
    public UtilizadorPraticanteOcasional(UtilizadorPraticanteOcasional myUtilizador){
        super(myUtilizador);
    }


    
    public double fatorMultiplicativo(int idade, int altura, char genero, int freq_cardiaca_media){
        // Bónus do tipo de utilizador
        return super.fatorMultiplicativo(idade, altura, genero, freq_cardiaca_media) * 0.85;
    }

    public UtilizadorPraticanteOcasional clone(){
        return new UtilizadorPraticanteOcasional(this);
    }
}