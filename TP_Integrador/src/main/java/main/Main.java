package main.java.main;

import main.java.modelo.Codificador;
import main.java.modelo.Decodificador;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        char simbolos[] = {'a','b','c'};
//        Fuente fuente = new Fuente(3, simbolos);
//        fuente.loadSourceFromFile("./tp1_grupo3.txt");
//        System.out.println(fuente);

        Codificador codificador = new Codificador(3, simbolos);
        codificador.readFile("./tp1_grupo3.txt");
        codificador.codingFile("./tp1_grupo3.txt", "./cadenas3.dat");

        codificador = new Codificador(5, simbolos);
        codificador.readFile("./tp1_grupo3.txt");
        codificador.codingFile("./tp1_grupo3.txt", "./cadenas5.dat");

        codificador = new Codificador(7, simbolos);
        codificador.readFile("./tp1_grupo3.txt");
        codificador.codingFile("./tp1_grupo3.txt", "./cadenas7.dat");

        Decodificador dec = new Decodificador();
        dec.readFile("./cadenas3.dat");

    }

}
