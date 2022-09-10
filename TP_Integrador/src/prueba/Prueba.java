package prueba;

import negocio.Fuente;
import modelo.Matriz;

public class Prueba {

    public static void main(String[] args) {
        String nom_arch= "tp1_grupo3.txt"; //archivo ubicado en la carpeta del proyecto
        Matriz matPCondicional = Fuente.getInstance().getPCondicional(nom_arch);
        matPCondicional.muestraM();
        System.out.println(Fuente.getInstance().getContador());
    }
}
