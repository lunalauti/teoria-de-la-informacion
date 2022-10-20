package modelo;

import fileSystem.ReaderFile;
import fileSystem.WriterFile;
import helpers.Binary;


import java.io.IOException;
import java.util.HashMap;

public class Decodificador {
    private HashMap<String, Cadena> table;
    private String message;

    public Decodificador(){
        this.table = new HashMap<>();
        this.message = "";
    }

    public String getMessage(){
        return this.message;
    }

    public void readFile(String fileName) throws IOException {
        ReaderFile reader = new ReaderFile(fileName);
        readTable(reader);
        int length = readLength(reader);
        readContain(reader, length);
    }

    public void saveMessage(String fileName) throws IOException {
        WriterFile writer = new WriterFile(fileName);
        writer.writeString(message);
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
            table.put(code,new Cadena(cadena, code));
        }
        if(!reader.isFinish())
            reader.readNext();
    }
    private int readLength(ReaderFile reader) throws IOException {
        return reader.readInteger();
    }
    private void readContain(ReaderFile reader, int length) throws IOException {
        String contain = getContain(reader);
        reader.closeFile();
        this.message = translateContain(contain, length);
    }

    private String getContain(ReaderFile reader) throws IOException {
        StringBuilder buffer = new StringBuilder("");
        while(!reader.isFinish()){
            buffer.append(Binary.getbinaryByInt(reader.getCurrentSymbol()));
            reader.readNext();
        }
        return buffer.toString();
    }

    private String translateContain(String contain, int length){
        StringBuilder message = new StringBuilder();
        String chunk = contain.substring(0, length);
        String code = "";
        while(chunk.length() > 0){
            code += chunk.charAt(0);
            chunk = chunk.substring(1);
            if(table.containsKey(code)){
                message.append(table.get(code).getCadena());
                code = "";
            }
        }
        return message.toString();
    }
}
