package main.java.fileSystem;

import main.java.helpers.Binary;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriterFile {
    String buffer = "";
    FileOutputStream outputStream;
    DataOutputStream dataOutputStream;


    public WriterFile(String fileName) throws FileNotFoundException {
        this.outputStream = new FileOutputStream(fileName);
        this.dataOutputStream = new DataOutputStream(outputStream);
    }

    private void write(int content) throws IOException {
        dataOutputStream.writeByte(content);
    }

    public void writeCode(String chunk) throws IOException {
        while(chunk.length() > 0){
            buffer += (char) chunk.charAt(0);
            chunk = chunk.substring(1);
            if(buffer.length() == 8){
                write(Binary.getIntByBinary(buffer));
                buffer = "";
            }
        }
    }

    public void closeFile() throws IOException {
        if(buffer.length() != 0)
            write(Binary.getIntByBinary(Binary.completeWithCeros(buffer)));
        this.dataOutputStream.close();
        this.outputStream.close();
    }



    public void writeString(String string) throws IOException {
        dataOutputStream.writeBytes(string);
    }

    public void writeInteger(int number) throws IOException {
        dataOutputStream.writeInt(number);
    }



}
