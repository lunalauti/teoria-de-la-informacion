package prueba;

import negocio.Fuente;
import modelo.Matriz;

public class Prueba {

    public static void main(String[] args) {
        //1° PARTE
        String nom_arch= "tp1_grupo3.txt";
        Matriz matPCondicional = Fuente.getInstance().getPCondicional(nom_arch);
        matPCondicional.muestraM();

        double[] VE= Fuente.getInstance().getVectorEstacionario(3);
        Fuente.getInstance().getEntropia(VE,3);

        //2° PARTE
        Fuente.getInstance().setCadenas(nom_arch);
        Fuente.getInstance().getFrecuenciasCadenas();
        Fuente.getInstance().getEntropiaCadenas();
        Fuente.getInstance().incisoC();
    }
}
