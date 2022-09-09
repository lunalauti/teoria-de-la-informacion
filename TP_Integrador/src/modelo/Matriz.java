package modelo;

public class Matriz {
    public static final int CANT= 3;
    double [][] m= new double[CANT][CANT];

    public Matriz(){
        iniciaM();
    }

    public void incrementaM(int i, int j){
        m[i][j]++;
    }
    private void iniciaM(){
        int i,j;
        for(i=0;i<CANT;i++)
            for(j=0;j<CANT;j++)
                m[i][j]=0;
    }
    public void muestraM(){
        int i,j;
        for(i=0;i<CANT;i++) {
            for (j = 0; j < CANT; j++)
                System.out.print(m[i][j]+"  ");
            System.out.println();
        }
    }

    /*
    * Calcula las probabilidades condicionales de cada simbolo (por columna)*/
    public void calculaCondicional(){
        int i,j,acum;
        for(j=0;j<CANT;j++) {
            acum=0;
            for (i= 0; i < CANT; i++)
                acum+=m[i][j];
            for (i= 0; i < CANT; i++)
                m[i][j]/=acum;
        }
    }
}
