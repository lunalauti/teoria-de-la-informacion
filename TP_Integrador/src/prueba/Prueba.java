package prueba;

import negocio.Fuente;
import modelo.Matriz;

public class Prueba {

    public static void main(String[] args) {
        String nom_arch= "tp1_grupo3.txt"; //archivo ubicado en la carpeta del proyecto
        Matriz matPCondicional = Fuente.getInstance().getPCondicional(nom_arch);
        matPCondicional.muestraM();
        //System.out.println(Fuente.getInstance().getContador());
        System.out.println("La fuente es de memoria nula? -> "+Fuente.getInstance().isMemoriaNula());
        //System.out.println("La fuente es de memoria nula? -> "+Fuente.getInstance().isNulaMemoria());
       // System.out.println("La fuente es ergodica? -> "+Fuente.getInstance().isErgodica());
        double[] VE= Fuente.getInstance().getVectorEstacionario(3);
        Fuente.getInstance().getEntropia(VE,3);
        Fuente.getInstance().setCadenas(nom_arch);
        Fuente.getInstance().getFrecuenciasCadenas();
        Fuente.getInstance().getEntropiaCadenas();
        Fuente.getInstance().getInecuaciones();
    }
}
