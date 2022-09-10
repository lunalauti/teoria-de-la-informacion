package negocio;

import modelo.Matriz;
import java.io.*;
import java.util.HashMap;

public class Fuente {

    private static Fuente _instancia;
    private Matriz matriz = new Matriz();
    private HashMap<Character,Integer> contador = new HashMap<Character,Integer>();

    private Fuente(){ }

    public static Fuente getInstance(){
        if (_instancia == null){
            _instancia = new Fuente();
        }
        return _instancia;
    }

    public Matriz getMatriz(){
        return this.matriz;
    }

    public HashMap<Character, Integer> getContador(){
        return this.contador;
    }

    public Matriz getPCondicional(String nom_arch) {
        File arch = new File(nom_arch);

        try {
            /*
             * Arma la matriz leyendo todos los simbolos del archivo */
            Reader reader = new BufferedReader(new FileReader(arch));
            char antSimbol = (char) reader.read(); //simbolo anterior
            int nextSimbol = reader.read();
            while (nextSimbol != -1) { //si el proximo simbolo es -1(ascii), fin de archivo
                char actSimbol = (char) nextSimbol;

                //aumenta el caso en que ocurra actSimbol(i) si antSimbol(j)
                this.matriz.incrementaM(Util.simbolToIndex(actSimbol), Util.simbolToIndex(antSimbol));

                if (this.contador.containsKey((char)actSimbol)){
                    this.contador.replace((char)actSimbol, this.contador.get((char)actSimbol)+1);
                }
                else{
                    this.contador.put((char) actSimbol,1);
                }

                antSimbol = actSimbol;
                nextSimbol = reader.read();
            }
            this.matriz.calculaCondicional(contador);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.matriz;
    }

}
