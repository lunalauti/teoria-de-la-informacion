package main.java.fileSystem;

import main.java.helpers.Binary;

import java.io.*;

public class ReaderFile {
    InputStream file = null;
    DataInputStream reader = null;
    int currentSymbol;

    public ReaderFile(String fileName) throws IOException {
        file = new FileInputStream(fileName);
        reader = new DataInputStream(file);
        currentSymbol = reader.read();
    }

    public int readNext() throws IOException {
        assert file != null && reader != null : "No se ha inicializado correctamente el archivo";
        if(currentSymbol == -1)
            throw new IOException("El archivo ya se encuentra terminado");
        currentSymbol =  reader.read();
        return currentSymbol;
    }

    public boolean isFinish(){
        return currentSymbol == -1;
    }

    public char getCurrentSymbol() {
        return (char) currentSymbol;
    }

    public void closeFile() throws IOException {
        this.reader.close();
        this.file = null;
        this.reader = null;
    }

    public String readWord(int length) throws IOException {
        String response = "";
        while(response.length() < length && currentSymbol != -1){
            response += String.format("%c", currentSymbol);
            readNext();
        }
        return response.length() == length ? response : "";
    }

    public int readInteger() throws IOException {
        StringBuilder chunk = new StringBuilder();
        for(int i = 0 ; i < 4 ; i++){
            chunk.append(Binary.getbinaryByInt(currentSymbol));
            readNext();
        }
        return Binary.getIntByBinary(chunk.toString());
    }

}
