package prueba;

import negocio.Probabilidad;
import modelo.Matriz;

public class Prueba {

    public static void main(String[] args) {
        String nom_arch= "tp1_grupo3.txt"; //archivo ubicado en la carpeta del proyecto
        Matriz matPCondicional=Probabilidad.getPCondicional(nom_arch);
        matPCondicional.muestraM();
    }
}
