package prueba;

import modelo.Cadena;
import modelo.PriorityTree;
import negocio.Fuente;
import modelo.Matriz;

import java.util.ArrayList;

public class Prueba {

    public static void main(String[] args) {
        //1° PARTE
        String nom_arch = "tp1_grupo3.txt";
        Matriz matPCondicional = Fuente.getInstance().getPCondicional(nom_arch);
        matPCondicional.muestraM();

        double[] VE = Fuente.getInstance().getVectorEstacionario(3);
        Fuente.getInstance().getEntropia(VE, 3);

        //2° PARTE
        Fuente.getInstance().setCadenas(nom_arch);
        Fuente.getInstance().getFrecuenciasCadenas();
        Fuente.getInstance().getEntropiaCadenas();
        Fuente.getInstance().incisoC();

        //TREE
        PriorityTree tree = new PriorityTree();
        ArrayList<Cadena> list = new ArrayList<Cadena>();
        list.add(new Cadena("s1", (float) 0.33));
        list.add(new Cadena("s2", (float) 0.25));
        list.add(new Cadena("s3", (float) 0.2));
        list.add(new Cadena("s4", (float) 0.1));
        list.add(new Cadena("s5", (float) 0.07));
        list.add(new Cadena("s6", (float) 0.05));



//        list.add((float) 0.1);
//        list.add((float) 0.05);
//        list.add((float) 0.25);
//        list.add((float) 0.07);
//        list.add((float) 0.33);
        tree.loadTree(list.iterator());
        for(int i = 0 ; i < list.size() ; i++)
            System.out.println(list.get(i));
//        System.out.println(tree.getPreorder());

    }
}
