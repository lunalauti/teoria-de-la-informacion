package negocio;

public class Util {

    /*
    * convierte un simbolo en el indice correspondiente */
    public static int simbolToIndex(char simbol){
        char caracter=Character.toUpperCase(simbol);
        return caracter=='A'?0:caracter=='B'?1:2;
    }

}
