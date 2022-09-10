package modelo;

import java.util.HashMap;

public class Matriz {
    public static final int CANT= 3;
    double [][] m = new double[CANT][CANT];

    public Matriz(){

    }

    public void incrementaM(int i, int j){
        m[i][j]++;
    }

    public void muestraM(){
        int i,j;
        for(i=0;i<CANT;i++) {
            for (j = 0; j < CANT; j++)
                System.out.format("%.3f   ",m[i][j]);
            System.out.println();
        }
    }

    /*
    * Calcula las probabilidades condicionales de cada simbolo (por columna)*/
    public void calculaCondicional(HashMap<Character, Integer> mapa){
        for (int i = 0;i < CANT; i++) {
            int cantidad = mapa.get((char)(i+65));
            for (int j = 0; j < CANT; j++) {
                this.m[i][j] /= cantidad;
            }
        }
    }
}
