package main.java.main;

import main.java.fileSystem.ReaderFile;
import main.java.modelo.Codificador;
import main.java.modelo.Decodificador;
import main.java.modelo.Fuente;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        char simbolos[] = {'a','b','c'};
        Fuente fuente = new Fuente(3, simbolos);
        fuente.loadSourceFromFile("./tp1_grupo3.txt");
        System.out.println(fuente);

        codificacion3();
        codificacion5();
        codificacion7();

        decodificacion3();
        decodificacion5();
        decodificacion7();


//        compareFiles("./tp1_grupo3.txt", "./reconstruccion3.txt");
//        compareFiles("./tp1_grupo3.txt", "./reconstruccion5.txt");
//        compareFiles("./tp1_grupo3.txt", "./reconstruccion7.txt");

    }



    public static void binaryToInt() throws IOException {
        InputStream inputStream = new FileInputStream("./cadenas3.dat");
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        dataInputStream.readByte();
    }

    public static void codificacion3() throws IOException {
        char simbolos[] = {'a','b','c'};
        Codificador codificador = new Codificador(3, simbolos);
        codificador.readFile("./tp1_grupo3.txt");
        codificador.codingFile("./tp1_grupo3.txt", "./cadenas3.dat");
        System.out.println("Codificador de 3\n" + codificador.getStats());
    }

    public static void codificacion5() throws IOException {
        char simbolos[] = {'a','b','c'};
        Codificador codificador = new Codificador(5, simbolos);
        codificador.readFile("./tp1_grupo3.txt");
        codificador.codingFile("./tp1_grupo3.txt", "./cadenas5.dat");
        System.out.println("Codificador de 5\n" + codificador.getStats());
    }

    public static void codificacion7() throws IOException {
        char simbolos[] = {'a','b','c'};
        Codificador codificador = new Codificador(7, simbolos);
        codificador.readFile("./tp1_grupo3.txt");
        codificador.codingFile("./tp1_grupo3.txt", "./cadenas7.dat");
        System.out.println("Codificador de 7\n" + codificador.getStats());
    }

    public static void decodificacion3() throws  IOException {
        Decodificador dec = new Decodificador();
        dec.readFile("./cadenas3.dat");
        dec.saveMessage("./reconstruccion3.txt");
    }

    public static void decodificacion5() throws  IOException {
        Decodificador dec = new Decodificador();
        dec.readFile("./cadenas5.dat");
        dec.saveMessage("./reconstruccion5.txt");
    }

    public static void decodificacion7() throws  IOException {
        Decodificador dec = new Decodificador();
        dec.readFile("./cadenas7.dat");
        dec.saveMessage("./reconstruccion7.txt");
    }

    public static void compareFiles(String name1, String name2) throws IOException {
        ReaderFile reader1 = new ReaderFile(name1);
        ReaderFile reader2 = new ReaderFile(name2);
        boolean equals = true;
        while(!reader1.isFinish() && !reader2.isFinish() && equals){
            equals = reader1.getCurrentSymbol() == reader2.getCurrentSymbol();
            reader1.readNext();
            reader2.readNext();
        }
        System.out.println(equals ? "Son iguales" : "Son distintos");
    }

}
