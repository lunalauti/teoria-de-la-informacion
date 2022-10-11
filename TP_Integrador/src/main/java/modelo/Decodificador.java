package main.java.modelo;

import main.java.fileSystem.ReaderFile;

import java.io.IOException;
import java.util.HashMap;

public class Decodificador {
    private HashMap<String, String> table;
    private String message;

    public Decodificador(){
        this.table = new HashMap<>();
    }

    public void readFile(String fileName) throws IOException {
        ReaderFile reader = new ReaderFile(fileName);
        readTable(reader);
        System.out.println(table);
    }

    private void readTable(ReaderFile reader) throws IOException {
        while(!reader.isFinish() && reader.getCurrentSymbol() != '\n'){
            String cadena = "";
            String code = "";
            while(!reader.isFinish() && reader.getCurrentSymbol() != ':'){
                cadena += reader.getCurrentSymbol();
                reader.readNext();
            }
            if(!reader.isFinish())
                reader.readNext();
            while(!reader.isFinish() && reader.getCurrentSymbol() != ';' ){
                code += reader.getCurrentSymbol();
                reader.readNext();
            }
            if(!reader.isFinish())
                reader.readNext();
            table.put(cadena,code);
        }
        if(!reader.isFinish())
            reader.readNext();
    }
}
