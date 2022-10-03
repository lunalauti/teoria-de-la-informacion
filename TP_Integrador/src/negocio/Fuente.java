package negocio;

import modelo.Matriz;

import java.io.*;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class Fuente {

    private static final int cantSimbolos = 10000;
    private static Fuente _instancia;
    private Matriz matriz = new Matriz();
    private HashMap<Character, Float> contador = new HashMap<Character, Float>();

    private HashMap<String, Float> cadena3 = new HashMap<String, Float>();

    private HashMap<String, Float> cadena5 = new HashMap<String, Float>();

    private HashMap<String, Float> cadena7 = new HashMap<String, Float>();


    private Fuente() {
    }

    public static Fuente getInstance() {
        if (_instancia == null) {
            _instancia = new Fuente();
        }
        return _instancia;
    }

    public Matriz getMatriz() {
        return this.matriz;
    }

    public HashMap<Character, Float> getContador() {
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

                if (this.contador.containsKey((char) actSimbol)) {
                    this.contador.replace((char) actSimbol, this.contador.get((char) actSimbol) + 1);
                } else {
                    this.contador.put((char) actSimbol, 1F);
                }

                antSimbol = actSimbol;
                nextSimbol = reader.read();
            }
            this.matriz.calculaCondicional(contador);
            //agrega el primer caracter al hash que no habia sido tomado en cuenta para la matriz
            this.contador.replace((char) primera, this.contador.get((char) primera) + 1);
            //Pongo en el contador la probabilidad independiente de cada signo
            this.calculaProbabilidadesInd();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.matriz;
    }

    public void calculaProbabilidadesInd() {
        HashMap<Character, Float> probInd = new HashMap<Character, Float>();
        for (HashMap.Entry<Character, Float> aux : this.contador.entrySet()) {
            this.contador.replace(aux.getKey(), aux.getValue() / cantSimbolos);
        }
    }

    //Chequea que las probabilidades de la matriz y de el hash de probabilidades independientes coincidan
    //Para esto multiplico las prob independientes de los simbolos de la fuente y comparo con el valor de la matriz en esa filaxcolumna
    //Si la diferencia entre matriz[i][j] y probIndependiente <= valor aboluto 0.01 es de memoria nula
    public boolean isMemoriaNula() {
        int i = 0;
        boolean aux = true;
        while ((i < this.matriz.getCant() && (aux == true))) {
            int j = i;
            while ((j < this.matriz.getCant() && (aux == true))) {
                if (i == j) {
                    float valor = this.getContador().get((char) (i + 65));
                    if (this.matriz.getValor(i, i) - valor * valor <= abs(0.01)) {
                        j++;
                    } else {
                        aux = false;
                    }
                    //No estan en la diagonal
                } else {
                    float valor1 = this.getContador().get((char) (i + 65));
                    float valor2 = this.getContador().get((char) (j + 65));
                    if ((this.matriz.getValor(i, j) - valor1 * valor2 <= abs(0.01)) && (this.matriz.getValor(j, i) - valor1 * valor2 <= abs(0.01))) {
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
     *
     * @return true si la memoria es nula, y false si la memoria es no nula
     */
    public boolean isNulaMemoria() {
        boolean response = true;
        int i = 0;
        double tolerance = 0.01;
        while (i < this.matriz.getCant() && response) {
            int j = 0;
            while (j < this.matriz.getCant() && response) {
                double absoluteDifference = Math.abs(this.matriz.getValor(i, j) - (1 / this.matriz.getCant()));
                if (absoluteDifference > tolerance)
                    response = false;
            }
        }
        return response;
    }

    //Calcula la potencia de la matriz de transicion
    private double[][] potenciaMat(double[][] matriz) {
        double[][] pot = new double[this.matriz.getCant()][this.matriz.getCant()];

        for (int i = 0; i < this.matriz.getCant(); i++) {
            for (int j = 0; j < this.matriz.getCant(); j++) {
                for (int k = 0; k < this.matriz.getCant(); k++) {
                    pot[i][j] += matriz[i][k] * matriz[k][j];
                }
            }
        }
        return pot;
    }

    //Revisa que todos los elementos de la matriz son mayores a 0
    private boolean mayoresACero(double[][] matriz) {
        boolean aux = true;
        int i = 0;
        while (i < this.matriz.getCant() && aux == true) {
            int j = 0;
            while (j < this.matriz.getCant() && aux == true) {
                if (matriz[i][j] <= 0) {
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
    public boolean isErgodica() {
        boolean aux = true;
        double[][] potencia = matriz.getM();
        int i = 0;
        while (aux == true && i < 5) {
            potencia = potenciaMat(potencia);
            if (!mayoresACero(potencia)) {
                aux = false;
            } else {
                i++;
            }
        }
        return aux;
    }

    /* Metodo para obtener el vector estacionario  */
    public double[] getVectorEstacionario(int n) {
        int i, j, k;
        double[][] mat = this.matriz.getM();
        double[][] aux = new double[n + 1][n + 2];
        double l;
        double V[] = new double[n];

        /* Copia la matriz */
        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                aux[i][j] = (i == n - 1) ? 1 : mat[i][j]; //reemplaza la ultima ecuacion por 1's

        /* Resta la matriz identidad */
        for (i = 0; i < n - 1; i++)
            aux[i][i] -= 1; // a la ultima fila no le resta la id

        /*Extiende la matriz*/
        for (i = 0; i < n; i++)
            aux[i][n] = (i == n - 1) ? 1 : 0;

        /* Metodo de Gauss Jordan */
        for (k = 0; k < n; k++) {
            for (i = 0; i < n; i++) {
                l = aux[i][k];
                for (j = 0; j <= n; j++) {
                    if (i != k)
                        aux[i][j] = (aux[k][k] * aux[i][j]) - (l * aux[k][j]);
                }
            }
        }
        for (i = 0; i < n; i++) {
            //System.out.format("%.3f   ", aux[i][n]/aux[i][i]);
            V[i] = aux[i][n] / aux[i][i];
        }
        return V;
    }

    public void getEntropia(double[] V, int n) {
        int i, j;
        double[][] mat = this.matriz.getM();
        double H = 0, aux;
        for (i = 0; i < n; i++) {
            aux = 0;
            for (j = 0; j < n; j++) {
                aux += mat[i][j] * (Math.log(1 / mat[i][j]) / Math.log(3));
            }
            H += V[i] * aux;
        }
        System.out.format("Entropia de la fuente: %.2f \n", H);
    }

    public void setCadenas(String nom_arch) {
        File arch = new File(nom_arch);
        try {
            Reader reader = new BufferedReader(new FileReader(arch));
            StringBuilder s3 = new StringBuilder(3);
            StringBuilder s5 = new StringBuilder(5);
            StringBuilder s7 = new StringBuilder(7);
            int nextSimbol = reader.read();
            int cont3 = 0, cont5 = 0, cont7 = 0;
            while (nextSimbol != -1) {
                char actSimbol = (char) nextSimbol;
                cont3++;
                cont5++;
                cont7++;
                s3.append(actSimbol);
                s5.append(actSimbol);
                s7.append(actSimbol);

                if (cont3 == 3) {
                    if (cadena3.containsKey(s3.toString())) {
                        cadena3.replace(s3.toString(), (cadena3.get(s3.toString()) + 1));
                    } else {
                        cadena3.put(s3.toString(), 1F);
                    }
                    cont3 = 0;
                    s3.delete(0, s3.length()); //reinicia la cadena
                }

                if (cont5 == 5) {
                    if (cadena5.containsKey(s5.toString())) {
                        cadena5.replace(s5.toString(), (cadena5.get(s5.toString()) + 1));
                    } else {
                        cadena5.put(s5.toString(), 1F);
                    }
                    cont5 = 0;
                    s5.delete(0, s5.length()); //reinicia la cadena
                }

                if (cont7 == 7) {
                    if (cadena7.containsKey(s7.toString())) {
                        cadena7.replace(s7.toString(), (cadena7.get(s7.toString()) + 1));
                    } else {
                        cadena7.put(s7.toString(), 1F);
                    }
                    cont7 = 0;
                    s7.delete(0, s7.length()); //reinicia la cadena
                }
                nextSimbol = reader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getFrecuenciasCadenas() {

        int cant3 = cantSimbolos / 3;
        for (Map.Entry<String, Float> entry3 : cadena3.entrySet()) {
            String clave = entry3.getKey();
            cadena3.replace(clave, cadena3.get(clave) / cant3);
        }

        int cant5 = cantSimbolos / 5;
        for (Map.Entry<String, Float> entry5 : cadena5.entrySet()) {
            String clave = entry5.getKey();
            cadena5.replace(clave, cadena5.get(clave) / cant5);
        }

        int cant7 = cantSimbolos / 7;
        for (Map.Entry<String, Float> entry7 : cadena7.entrySet()) {
            String clave = entry7.getKey();
            cadena7.replace(clave, cadena7.get(clave) / cant7);
        }
    }

    public void getEntropiaCadenas() {
        float H3 = 0, H5 = 0, H7 = 0;
        System.out.println("Cantidad de informacion de cadenas de 3");
        for (Map.Entry<String, Float> entry3 : cadena3.entrySet()) {
            String clave = entry3.getKey();
            System.out.printf("%s: \t %.2f \n", clave, Math.log(1 / cadena3.get(clave)) / Math.log(3));
            H3 += cadena3.get(clave) * (Math.log(1 / cadena3.get(clave)) / Math.log(3));
        }
        System.out.printf("Entropia de cadenas de 3: %.2f \n", H3);

        int cant5 = cantSimbolos / 5;
        System.out.println("Cantidad de informacion de cadenas de 5");
        for (Map.Entry<String, Float> entry5 : cadena5.entrySet()) {
            String clave = entry5.getKey();
            System.out.printf("%s: \t %.2f \n", clave, Math.log(1 / cadena5.get(clave)) / Math.log(3));
            H5 += cadena5.get(clave) * (Math.log(1 / cadena5.get(clave)) / Math.log(3));
        }
        System.out.printf("Entropia de cadenas de 5: %.2f \n", H5);

        int cant7 = cantSimbolos / 7;
        System.out.println("Cantidad de informacion de cadenas de 7");
        for (Map.Entry<String, Float> entry7 : cadena7.entrySet()) {
            String clave = entry7.getKey();
            System.out.printf("%s: \t %.2f \n", clave, Math.log(1 / cadena7.get(clave)) / Math.log(3));
            H7 += cadena7.get(clave) * (Math.log(1 / cadena7.get(clave)) / Math.log(3));
        }
        System.out.printf("Entropia de cadenas de 7: %.2f \n", H7);
    }

    // TODO: 3/10/2022 inecuacion de Kraft, MacMillan, Longitud Media
    public void incisoC() { //no se que nombre ponerle
        int r = 3;

        //calcula Kraft
        getKraft(r, this.cadena3);
        getKraft(r, this.cadena5);
        getKraft(r, this.cadena7);

        //calcula longitud media
        getLongitudMedia(this.cadena3);
        getLongitudMedia(this.cadena5);
        getLongitudMedia(this.cadena7);
    }

    public void getKraft(int r, HashMap<String, Float> cadena) {
        int q, i;
        float suma = 0;
        for (Map.Entry<String, Float> entry : cadena.entrySet()) {
            String clave = entry.getKey();
            suma += Math.pow(r, -clave.length());
        }
        System.out.printf("KRAFT:  %.2f \n", suma);
    }

    public void getLongitudMedia(HashMap<String, Float> cadena) {
        float longitud = 0;
        for (Map.Entry<String, Float> entry : cadena.entrySet()) {
            String clave = entry.getKey();
            longitud += cadena.get(clave) * clave.length();
        }
        System.out.printf("LONGITUD MEDIA: %.2f \n", longitud);
    }

}
