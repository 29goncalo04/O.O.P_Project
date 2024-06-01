import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Funcoes_ficheiro {
    
    public static void guardaUtilizador(List<Utilizador> utilizadores, String nome_ficheiro) throws FileNotFoundException, IOException {
        try (FileOutputStream fos = new FileOutputStream(nome_ficheiro); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Utilizador utilizador : utilizadores) {
                oos.writeObject(utilizador);
            }
        }
    }

    public static Utilizador findCodigo(String nome_ficheiro, int codigo) throws FileNotFoundException, IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(nome_ficheiro); ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Utilizador utilizador = (Utilizador) ois.readObject();
                    if(codigo == utilizador.getCodigo()) return utilizador.clone();
                } catch (EOFException e) {
                    break; // Chegou ao final do arquivo
                }
            }
        }
        return null;
    }

    public static List<Utilizador> leUtilizador(String nome_ficheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        List<Utilizador> utilizadores = new ArrayList<Utilizador>();
        try (FileInputStream fis = new FileInputStream(nome_ficheiro); ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Utilizador utilizador = (Utilizador) ois.readObject();
                    utilizadores.add(utilizador.clone());
                } catch (EOFException e) {
                    break; // Chegou ao final do arquivo
                }
            }
        }
        return utilizadores;
    }

    public static void appendUtilizador(Utilizador utilizador){
        File file = new File("Dataset");
        try {
            if(!file.exists() || file.length()==0){  // O ficheiro não existe ou não tem conteúdo
                List<Utilizador> utilizadores = new ArrayList<>();
                utilizadores.add(utilizador);
                Funcoes_ficheiro.guardaUtilizador(utilizadores, "Dataset");
            }
            else{
                List<Utilizador> utilizadores = Funcoes_ficheiro.leUtilizador("Dataset");
                utilizadores.add(utilizador);
                Funcoes_ficheiro.guardaUtilizador(utilizadores, "Dataset");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void preencheRecordes(Recordes recordes){
        if(recordes.getRecordes().isEmpty()){
            try {
                List<Utilizador> utilizadores = Funcoes_ficheiro.leUtilizador("Dataset");
                for(Utilizador utilizador : utilizadores) recordes.fillRecord(utilizador);;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}