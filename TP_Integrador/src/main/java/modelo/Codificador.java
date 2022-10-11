package main.java.modelo;

import main.java.fileSystem.ReaderFile;
import main.java.fileSystem.WriterFile;

import java.io.IOException;
import java.util.HashMap;

public class Codificador {
    private final int wordlength;
    private int wordCount;
    private HashMap<String, Cadena> cadenas;
    private final char[] alfabetoCodigo;
    private float entrophy;
    private float kraft;
    private float longMedia;

    public Codificador(int wordlength, char[] alfabetoCodigo){
        this.wordlength = wordlength;
        this.alfabetoCodigo = alfabetoCodigo;
        this.wordCount = 0;
        this.cadenas = new HashMap<String, Cadena>();
    }

    public void readFile(String fileName) throws IOException {
        ReaderFile file = new ReaderFile(fileName);

        while(!file.isFinish()){
            String word = file.readWord(wordlength);
            if(word != ""){
                wordCount++;
                if(!this.cadenas.containsKey(word))
                    this.cadenas.put(word, new Cadena(word));
                else
                    this.cadenas.get(word).increaseCount();
            }
        }
        file.closeFile();
        determinateProbabilities(wordCount);
        this.entrophy = getCodeEntrophy();
        this.kraft = getKraft();
        this.longMedia = getLongitudMedia();
        codingHuffman();
    }

    private void determinateProbabilities(int count){
        for (Cadena cadena : this.cadenas.values()) {
            cadena.setProbability((float) cadena.getCount() / count);
        }
    }

    public float getCodeEntrophy(){
        float entrophy = 0;
        for (Cadena cadena : this.cadenas.values()) {
            entrophy += (float) (cadena.getProbability() * -Math.log(cadena.getProbability()) / Math.log(3));
        }
        return entrophy;
    }

    public float getKraft(){
        float kraft = 0;
        for (Cadena cadena : this.cadenas.values()) {
            kraft += (float) (Math.pow(this.alfabetoCodigo.length, -cadena.getCadena().length()));
        }
        return kraft;
    }

    public float getLongitudMedia(){
        float longitud = 0;
        for (Cadena cadena : this.cadenas.values()) {
            longitud += cadena.getProbability() * cadena.getCadena().length();
        }
        return longitud;
    }

    public void codingHuffman(){
        PriorityTree tree = new PriorityTree();
        tree.loadTree(this.cadenas.values().iterator());
    }

    public void codingFile(String inputName, String outputName) throws IOException {
        ReaderFile inputFile = new ReaderFile(inputName);
        WriterFile outputFile = new WriterFile(outputName);
        outputFile.writeString(getFormatTable());
        while(!inputFile.isFinish()){
            String word = inputFile.readWord(wordlength);
            if(word != ""){
                outputFile.writeCode(cadenas.get(word).getCode());
            }
        }
        inputFile.closeFile();
        outputFile.closeFile();
    }

    private String getFormatTable(){
        String table = "";
        for (Cadena cadena : this.cadenas.values()) {
            table += String.format("%s:%s;", cadena.getCadena(), cadena.getCode());
        }
        table +="\n";
        return table;
    }

    public String toString(){
        String response = "";
        response += String.format("%-7s %-5s %-5s %-13s\n", "Cadena", "Prob", "Info", "Codigo");
        for (Cadena cadena : this.cadenas.values()) {
            response += cadena;
        }
        response += String.format("La entropia es: %4.2f\n", this.entrophy);
        response += String.format("La inecuacion de Kraft es: %4.2f <= 1\n", this.kraft);
        response += String.format("La longitud media del codigo es: %4.2f\n", this.longMedia);
        response += String.format("Rendimiento: %4.2f  Redudancia: %4.2f", this.entrophy/this.longMedia, 1 - this.entrophy/ this.longMedia);

        return response;
    }

    public String getStats(){
        String response = "";
        response += String.format("La entropia es: %4.2f\n", this.entrophy);
        response += String.format("La inecuacion de Kraft es: %4.2f <= 1\n", this.kraft);
        response += String.format("La longitud media del codigo es: %4.2f\n", this.longMedia);
        response += String.format("Rendimiento: %4.2f  Redudancia: %4.2f", this.entrophy/this.longMedia, 1 - this.entrophy/ this.longMedia);

        return response;
    }

}
