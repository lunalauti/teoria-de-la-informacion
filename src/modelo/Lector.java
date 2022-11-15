package modelo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Lector { //hay que cambiarle el nombre, no sabiamos cual ponerle
    HashMap<String,Float> probabilities= new HashMap<String, Float>();
    public void readFile(String fileName) throws IOException {
        int wordCount=0;
        FileReader fileReader;
        int valor;

        File arch= new File(fileName);
        Reader reader = new BufferedReader(new FileReader(arch));
        StringBuilder word = new StringBuilder();
        int nextSimbol = reader.read();
        while (nextSimbol != -1) {
            char actSimbol = (char) nextSimbol;

            while(nextSimbol != -1 && actSimbol!=' '){
                word.append(actSimbol);
                nextSimbol=reader.read();
                actSimbol = (char) nextSimbol;
            }
            if (probabilities.containsKey(word.toString())){
                probabilities.replace(word.toString(),(probabilities.get(word.toString())+1F));
            } else{
                probabilities.put(word.toString(),1F);
            }
            wordCount++;
            word.delete(0,word.length()); //reinicia la cadena
            nextSimbol=reader.read();
        }

        for (Map.Entry<String,Float> entry : probabilities.entrySet()){
            String key= entry.getKey();

            System.out.println(key);
            System.out.println(probabilities.get(key));

            probabilities.replace(key,probabilities.get(key)/wordCount);
           System.out.println(probabilities.get(key));
        }




        }

}


