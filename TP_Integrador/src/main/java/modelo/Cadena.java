package main.java.modelo;

public class Cadena implements Comparable {
    private String cadena;
    private float probability;

    private float information;
    private String code;
    private int count;

    public Cadena(String cadena){
        this.cadena = cadena;
        this.probability = 0;
        this.information = 0;
        this.code = "";
        this.count = 1;
    }

    public Cadena(String cadena, float probability){
        this.cadena = cadena;
        this.probability = probability;
        this.information = 0;
        this.code = "";
        this.count = 1;
    }

    public String getCadena() {
        return cadena;
    }

    public float getProbability() {
        return probability;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) { this.code = code; }

    public void setProbability(float probability){
        this.probability = probability;
        this.information = (float) (-Math.log(probability) / Math.log(3));
    }

    public void appendPrefix(String prefix){
        this.code = prefix + this.code;
    }

    public int getCount() { return this.count; }

    public void increaseCount() { this.count++; }

    public void setCount(int count) { this.count = count; }

    public float getInformation() { return information; }


    @Override
    public int compareTo(Object o) {
        Cadena other = (Cadena) o;
        return (this.cadena == other.cadena) ? 0 : Float.compare(this.probability, other.probability);
    }

    public String toString(){
        return String.format("%-7s %4.3f %4.3f %-13s\n", cadena, probability, information, code);
    }
}
