package negocio;

import modelo.Matriz;

import java.io.*;

public class Probabilidad {

    public static Matriz getPCondicional(String nom_arch){
        File arch=new File(nom_arch);
        Matriz matriz= new Matriz();
        try {
            /*
            * Arma la matriz leyendo todos los simbolos del archivo */
            Reader reader = new BufferedReader(new FileReader(arch));
            char antSimbol= (char)reader.read(); //simbolo anterior
            int nextSimbol =reader.read();
            while(nextSimbol!=-1) { //si el proximo simbolo es -1(ascii), fin de archivo
                char actSimbol = (char) nextSimbol;

                //aumenta el caso en que ocurra actSimbol(i) si antSimbol(j)
                matriz.incrementaM(Util.simbolToIndex(actSimbol), Util.simbolToIndex(antSimbol));

                antSimbol=actSimbol;
                nextSimbol = reader.read();
            }
            matriz.calculaCondicional();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matriz;
    }

}
