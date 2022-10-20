package modelo;

public class Matrix implements Cloneable{
    double [][] matrix;

    public Matrix(int dimension){
        this.matrix = new double[dimension][dimension];
        initMatrix();
    }
    public Matrix(int rows, int columns){
        this.matrix = new double[rows][columns];
        initMatrix();
    }

    public Matrix(double[][] matrix){
        this.matrix = new double[matrix.length][matrix[0].length];
        for(int i = 0 ; i < matrix.length ; i++){
            for(int j = 0 ; j < matrix[0].length ; j++)
                this.matrix[i][j] = matrix[i][j];
        }
    }

    private void initMatrix(){
        for(int i = 0 ; i < this.matrix.length ; i++){
            for(int j = 0 ; j < this.matrix[0].length ; j++)
                this.matrix[i][j] = 0;
        }
    }

    public void counterPlus(int row, int colum){
        this.matrix[row][colum]++;
    }
    
    public double getValue(int row, int colum){ return matrix[row][colum]; }

    public double[][] getMatrix() { return matrix; }

    public String toString(){
        String res = "";
        for(int i = 0 ; i < this.matrix.length ; i++){
            for (int j = 0 ; j < this.matrix[0].length ; j++)
                res += String.format("%20.15f ",this.matrix[i][j]);
            res += "\n";
        }
        return res;
    }

    private double getSummationColum(int colum){
        double sum = 0;
        for(int i = 0 ; i < this.matrix.length ; i++)
            sum += this.matrix[i][colum];
        return sum;
    }

    public void splitColumByColumSummation(){
        for(int colum = 0 ; colum < this.matrix.length ; colum++){
            double summationColum = getSummationColum(colum);
            for(int i = 0 ; i < this.matrix.length ; i++)
                matrix[i][colum] /= summationColum;
        }
    }

    public Matrix getCloneMatrix(){
        int dim = this.matrix.length;
        double[][] matrix = new double[dim][dim];
        for(int i = 0 ; i < dim ; i++){
            for(int j = 0 ; j < dim; j++)
                matrix[i][j] = this.matrix[i][j];
        }
        return new Matrix(matrix);
    }

    public void changeRow(int row , double[] values){
        for(int i = 0 ; i < this.matrix.length ; i++)
            matrix[row][i] = values[i];
    }

    public void lessIdentityMatrix(double[][] matrix){
        for(int i = 0 ; i < matrix.length ; i++)
            matrix[i][i] -= 1;
    }

    public String gaussJordan(double[] results){
        int dim = matrix.length;
        for(int k = 0 ; k < dim ; k++){
            for(int i = k + 1 ; i < dim ; i++){
                double value = matrix[i][k];
                for(int j = 0 ; j < dim ; j++){
                    matrix[i][j] -= matrix[k][j]/matrix[k][k] * value;
                }
                results[i] -= results[k]/matrix[k][k] * value;

            }
            for(int i = 0 ; i < k ; i++){
                double value = matrix[i][k];
                for(int j = i + 1 ; j < dim ; j++){
                    matrix[i][j] -= matrix[k][j]/matrix[k][k] * value;
                }
                results[i] -= results[k]/matrix[k][k] * value;
            }
        }
        for(int i = 0; i < dim ; i++){
            results[i] /= matrix[i][i];
            matrix[i][i] /= matrix[i][i];
        }

        return new Matrix(matrix).toString();
    }

}
