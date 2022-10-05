package main;

public class Main {

    public static void main(String[] args){




    }

    //Metodo en prueba, para ver como podemos conseguir el vector estacionario
    public static double[][] getMatrizTriangulada(double[][] mat){
        double[][] matTriang = mat;
        for(int i = 0 ; i < 4 ; i++){
            for(int j = 0 ; j < 4 ; j++){
                if(i != j){
                    double value = matTriang[i][j] / matTriang[j][j];
                    for(int k = 0 ; k < 4 ; k++){
                        matTriang[i][k] = matTriang[i][k] - value * matTriang[j][k];
                    }
                }

            }
        }
        return matTriang;
    }

    public static double[][] getMatrizAmpliada(double[][] matriz){
        int dim = matriz.length;
        double[][] matrizAmpliada = new double[dim][dim + 1];
        for(int i = 0; i < dim ; i++){
            for(int j = 0 ; j < dim ; j++){
                matrizAmpliada[i][j] = matriz[i][j];
            }
            matrizAmpliada[i][dim] = 0;
        }

        return matrizAmpliada;
    }

    public static void gauss(double matriz[][]){
        //k = orden .-. i = fila .-. j = colunma
        int dim = matriz.length;
        for(int k = 0 ; k < dim ; k++){
            for(int i = k + 1 ; i < dim ; i++){
                double value = matriz[i][k];
                for(int j = 0 ; j < dim + 1; j++){
                    matriz[i][j] -= matriz[k][j]/matriz[k][k] * value;
                }
            }
        }
    }

    public static void restaIdentidad(double matriz[][]){
        int dim = matriz.length;
        for (int i = 0; i < dim ; i++){
            matriz[i][i] -= 1;
        }
    }

    public static void gaussJordan(double matriz[][]){
        //k = orden .-. i = fila .-. j = colunma
        int dim = matriz.length;
        for(int k = 0 ; k < dim ; k++){
            for(int i = k + 1 ; i < dim ; i++){
                double value = matriz[i][k];
                for(int j = 0 ; j < dim + 1; j++){
                    matriz[i][j] -= matriz[k][j]/matriz[k][k] * value;
                }
            }
            for(int i = 0 ; i < k ; i++){
                double value = matriz[i][k];
                for(int j = i + 1 ; j < dim + 1 ; j++){
                    matriz[i][j] -= matriz[k][j]/matriz[k][k]*value;
                }
            }
        }
        for(int i = 0; i < dim ; i++){
            matriz[i][dim] /= matriz[i][i];
            matriz[i][i] /= matriz[i][i];
        }

    }
}
