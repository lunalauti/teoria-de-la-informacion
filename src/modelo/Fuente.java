package modelo;


import fileSystem.ReaderFile;

import java.io.IOException;
import java.util.ArrayList;

public class Fuente {
    private Matrix matrizDeTransicion;
    private double[] stationaryArray;
    private int dimension;
    private ArrayList<Symbol> simbolos = new ArrayList<Symbol>();


    public Fuente(int dimension){
        this.dimension = dimension;
        this.matrizDeTransicion = new Matrix(dimension);
    }

    public Fuente(int dimension, char[] simbolos){
        assert dimension == simbolos.length : "La dimension tiene que ser igual a la cantidad de simbolos";

        this.dimension = dimension;
        this.matrizDeTransicion = new Matrix(dimension);
        for(int i = 0 ; i < dimension ; i++)
            this.simbolos.add(new Symbol(simbolos[i]));
    }

    public void addSimbol(char simbol){
        this.simbolos.add(new Symbol(simbol));
    }

    public Matrix getMatrizDeTransicion(){
        return matrizDeTransicion;
    }

    public double[] getStationaryArray(){ return this.stationaryArray; }

    public String toString(){
        String str = this.simbolos.toString() + "\n" + this.matrizDeTransicion.toString();
        str += "\nV:[ ";
        for(int i = 0 ; i < this.stationaryArray.length ; i++)
            str += String.format("%5.3f ", stationaryArray[i]);
        str += "]\n";
        str += String.format("La entropia de la fuente es: %4.3f", this.getSourceEntrophy()) + "\n";
        return str;
    }

    public void loadSourceFromFile(String fileName) throws IOException {
        readFile(fileName);
        getConditionalProbabilities();
        if(!isMemoriaNula())
            calculateStationaryArray();
        else
            stationaryArray = new double[3];
    }

    private void readFile(String fileName) throws IOException {
        ReaderFile file = new ReaderFile(fileName);
        int prev = (!file.isFinish()) ? simbolos.indexOf(new Symbol(file.getCurrentSymbol())) : -1;
        simbolos.get(prev).counterPlus();
        file.readNext();
        while (!file.isFinish()){
            int current = simbolos.indexOf(new Symbol(file.getCurrentSymbol()));
            if(prev != -1 && current != -1){
                simbolos.get(current).counterPlus();
                this.matrizDeTransicion.counterPlus(current,prev);
            }
            file.readNext();
            prev = current;
        }
        file.closeFile();
    }

    private void getConditionalProbabilities(){
        this.matrizDeTransicion.splitColumByColumSummation();
    }

    private void calculateStationaryArray(){
        Matrix matrixAuxiliar = matrizDeTransicion.getCloneMatrix();
        double[] equality = {1,1,1};
        double[] results = {0, 0, 0};
        int row = 2;

        matrixAuxiliar.lessIdentityMatrix(matrixAuxiliar.getMatrix());
        matrixAuxiliar.changeRow(row, equality);
        results[row] = 1;

        matrixAuxiliar.gaussJordan(results);

        this.stationaryArray = results;
    }

    public boolean isMemoriaNula(){
        boolean response = true;
        int i = 0;
        double tolerance = 0.01;
        double[][] matrix = matrizDeTransicion.getMatrix();
        while( i < matrix.length && response){
            int j = 0;
            while(j < matrix.length && response){
                double absoluteDifference = Math.abs(matrix[i][j] - matrix[i][i]);
                if(absoluteDifference > tolerance)
                    response = false;
                j++;
            }
            i++;
        }
        return response;
    }

    public double getSourceEntrophy(){
        double[][] matrix = this.matrizDeTransicion.getMatrix();
        double acum = 0;
        for(int i = 0 ; i < matrix.length ; i++){
            for(int j = 0 ; j < matrix[0].length ; j++){
                acum += matrix[i][j] * this.stationaryArray[j] * (matrix[i][j] == 0 ? 0 : (- Math.log(matrix[i][j])/Math.log(3)));
            }
        }
        return acum;
    }

}
