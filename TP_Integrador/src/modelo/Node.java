package modelo;

public class Node implements Comparable{
    private Node left;
    private Node right;
    private float value;

    public Node(float value){
        this.value = value;
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public int compareTo(Object o) {
        Node other = (Node) o;
        return Float.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.format("%3.2f", this.value);
    }


}
