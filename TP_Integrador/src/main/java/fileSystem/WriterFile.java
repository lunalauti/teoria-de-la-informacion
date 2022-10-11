package main.java.fileSystem;

import java.io.*;

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
                write(getIntByBinary(buffer));
                buffer = "";
            }
        }
    }

    public void closeFile() throws IOException {
        if(buffer.length() != 0)
            write(getIntByBinary(buffer));
        this.dataOutputStream.close();
        this.outputStream.close();
    }

    private int getIntByBinary(String buffer){
        int res = 0;
        char chars[] = buffer.toCharArray();
        for(int i = 0 ; i < chars.length ; i++){
            int pos = chars.length - i - 1;
            res += (chars[i] == '1') ? Math.pow(2, pos) : 0;
        }
        return res;
    }



}
