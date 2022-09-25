package negocio;

import modelo.Matriz;
import java.io.*;
import java.util.HashMap;

import static java.lang.Math.abs;

public class Fuente {

    private static final int cantSimbolos = 10000;
    private static Fuente _instancia;
    private Matriz matriz = new Matriz();
    private HashMap<Character,Float> contador = new HashMap<Character,Float>();

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

    public HashMap<Character, Float> getContador(){
        return this.contador;
    }

    public Matriz getPCondicional(String nom_arch) {
        File arch = new File(nom_arch);

        try {
            /*
             * Arma la matriz leyendo todos los simbolos del archivo */
            Reader reader = new BufferedReader(new FileReader(arch));
            char antSimbol = (char) reader.read(); //simbolo anterior
            char primera = antSimbol;
            int nextSimbol = reader.read();
            while (nextSimbol != -1) { //si el proximo simbolo es -1(ascii), fin de archivo
                char actSimbol = (char) nextSimbol;

                //aumenta el caso en que ocurra actSimbol(i) si antSimbol(j)
                this.matriz.incrementaM(Util.simbolToIndex(actSimbol), Util.simbolToIndex(antSimbol));

                if (this.contador.containsKey((char)actSimbol)){
                    this.contador.replace((char)actSimbol, this.contador.get((char)actSimbol)+1);
                }
                else{
                    this.contador.put((char) actSimbol, 1F);
                }

                antSimbol = actSimbol;
                nextSimbol = reader.read();
            }
            this.matriz.calculaCondicional(contador);
            //agrega el primer caracter al hash que no habia sido tomado en cuenta para la matriz
            this.contador.replace((char)primera, this.contador.get((char)primera)+1);
            //Pongo en el contador la probabilidad independiente de cada signo
            this.calculaProbabilidadesInd();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.matriz;
    }

    public void calculaProbabilidadesInd(){
        HashMap<Character, Float> probInd = new HashMap<Character, Float>();
        for(HashMap.Entry<Character, Float> aux : this.contador.entrySet()){
            this.contador.replace(aux.getKey(), aux.getValue()/cantSimbolos);
        }
    }

    //Chequea que las probabilidades de la matriz y de el hash de probabilidades independientes coincidan
    //Para esto multiplico las prob independientes de los simbolos de la fuente y comparo con el valor de la matriz en esa filaxcolumna
    //Si la diferencia entre matriz[i][j] y probIndependiente <= valor aboluto 0.01 es de memoria nula
    public boolean isMemoriaNula(){
        int i = 0;
        boolean aux = true;
        while ((i < this.matriz.getCant() && (aux == true))){
            int j = i;
            while ((j < this.matriz.getCant() && (aux == true))){
                if (i == j){
                    float valor = this.getContador().get((char)(i+65));
                    if (this.matriz.getValor(i,i) - valor*valor <= abs(0.01)){
                        j++;
                    } else {
                        aux = false;
                    }
                //No estan en la diagonal
                } else {
                    float valor1 = this.getContador().get((char)(i+65));
                    float valor2 = this.getContador().get((char)(j+65));
                    if ((this.matriz.getValor(i,j) - valor1*valor2 <= abs(0.01)) && (this.matriz.getValor(j,i) - valor1*valor2 <= abs(0.01)) ){
                        j++;
                    } else {
                        aux = false;
                    }
                }
            }
            i++;
        }
        return aux;
    }

    /**
     * Otra alternativa para retornar si la fuente es de memoria nula, comparando los valores
     * de la matriz, con el valor equiprobable 1/n, con una tolerancia de 0.01
     * @return true si la memoria es nula, y false si la memoria es no nula
     */
    public boolean isNulaMemoria(){
        boolean response = true;
        int i = 0;
        double tolerance = 0.01;
        while( i < this.matriz.getCant() && response){
            int j = 0;
            while(j < this.matriz.getCant() && response){
                double absoluteDifference = Math.abs(this.matriz.getValor(i,j) - (1/this.matriz.getCant()));
                if(absoluteDifference > tolerance)
                    response = false;
            }
        }
        return response;
    }

    //Calcula la potencia de la matriz de transicion
    private double[][] potenciaMat(double[][] matriz){
        double[][] pot = new double[this.matriz.getCant()][this.matriz.getCant()];

        for(int i = 0; i < this.matriz.getCant(); i++){
            for (int j = 0; j < this.matriz.getCant(); j++){
                for (int k = 0; k < this.matriz.getCant(); k++){
                    pot[i][j] += matriz[i][k] * matriz[k][j];
                }
            }
        }
        return pot;
    }

    //Revisa que todos los elementos de la matriz son mayores a 0
    private boolean mayoresACero(double[][] matriz){
        boolean aux = true;
        int i = 0;
        while (i < this.matriz.getCant() && aux == true){
            int j = 0;
            while (j < this.matriz.getCant() && aux == true){
                if (matriz[i][j] <= 0){
                    aux = false;
                } else {
                    j++;
                }
            }
            i++;
        }
        return aux;
    }

    //Se fija si la fuente es ergodica sacando la potencia de la matriz y fijandose que todos sus elementos sean mayores a 0
    //Revisa 5 potencias de la matriz y si ninguna es entonces no es ergodica
    public boolean isErgodica(){
        boolean aux = true;
        double[][] potencia = matriz.getM();
        int i = 0;
        while (aux == true && i < 5){
            potencia = potenciaMat(potencia);
            if (!mayoresACero(potencia)){
                aux = false;
            } else{
                i++;
            }
        }
        return aux;
    }

    /* Metodo para obtener el vector estacionario  */
    public double[] getVectorEstacionario(int n){
        int i,j,k;
        double[][] mat= this.matriz.getM();
        double[][] aux= new double[n+1][n+2];
        double l;
        double V[]= new double[n];

        /* Copia la matriz */
        for(i=0;i<n;i++)
            for (j = 0; j < n; j++)
                aux[i][j] = (i == n-1) ? 1 : mat[i][j]; //reemplaza la ultima ecuacion por 1's

        /* Resta la matriz identidad */
        for(i=0;i<n-1;i++)
            aux[i][i] -= 1; // a la ultima fila no le resta la id

        /*Extiende la matriz*/
        for(i=0;i<n;i++)
                aux[i][n]=(i==n-1)?1:0;

        /* Metodo de Gauss Jordan */
        for (k=0;k<n;k++){
            for (i=0;i<n;i++){
                l= aux[i][k];
                for(j=0;j<=n;j++){
                    if (i!=k)
                        aux[i][j]= (aux[k][k]*aux[i][j]) - (l* aux[k][j]);
                }
            }
        }
        for ( i = 0;i < n;i++ )
        {
            //System.out.format("%.3f   ", aux[i][n]/aux[i][i]);
            V[i]= aux[i][n]/aux[i][i];
        }
        return V;
    }

    public double calculaEntropia(double[] V, int n){
        int i,j;
        double[][] mat= this.matriz.getM();
        double H=0,aux;
        for(i=0;i<n;i++){
            aux=0;
            for(j=0;j<n;j++){
                aux+= mat[i][j]*(Math.log(1/mat[i][j]) / Math.log(2));
            }
            H+= V[i]*aux;
        }

        System.out.format("%.3f   ", H);
        return H;
    }
}
