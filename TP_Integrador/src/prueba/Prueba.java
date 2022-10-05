package prueba;

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
        ArrayList<Float> list = new ArrayList<Float>();
        list.add((float) 0.2);
        list.add((float) 0.1);
        list.add((float) 0.05);
        list.add((float) 0.25);
        list.add((float) 0.07);
        list.add((float) 0.33);
        tree.loadTree(list.iterator());
        System.out.println(tree.getPreorder());

    }
}
