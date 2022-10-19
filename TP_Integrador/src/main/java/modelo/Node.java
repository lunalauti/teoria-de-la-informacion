package main.java.modelo;

public class Node implements Comparable{
    private Node left;
    private Node right;
    private Cadena cadena;

    public Node(Cadena cadena){
        this.cadena = cadena;
        this.left = null;
        this.right = null;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public float getValue(){
        return this.cadena.getProbability();
    }

    public void setCadena(Cadena cadena) {
        this.cadena = cadena;
    }

    public void appendPrefix(String prefix){
        cadena.appendPrefix(prefix);
        if(right != null)
            right.appendPrefix(prefix);
        if(left != null)
            left.appendPrefix(prefix);

    }

    @Override
    public int compareTo(Object o) {
        Node other = (Node) o;
        return cadena.compareTo(other.cadena);
    }

    @Override
    public String toString() {
        return cadena.toString();
    }


}
