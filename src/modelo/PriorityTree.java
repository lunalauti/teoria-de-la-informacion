package modelo;

import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityTree {
    private Node root;

    public PriorityTree(){
        this.root = null;
    }

    public void loadTree(Iterator<Cadena> it){
        PriorityQueue<Node> queue = getPriorityQueue(it);
        this.root = getRoot(queue);
    }

    private PriorityQueue<Node> getPriorityQueue(Iterator<Cadena> it){
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        while(it.hasNext())
            queue.add(new Node(it.next()));
        return queue;
    }

    private Node getRoot(PriorityQueue<Node> queue){
        while(queue.size() > 1){
            mergeNodes(queue);
        }
        return queue.poll();
    }

    private void mergeNodes(PriorityQueue<Node> queue){
        Node left = queue.poll();
        Node right = queue.poll();
        if(left != null && right != null){
            float newValue = right.getValue() + left.getValue();
            Node newNode = new Node(new Cadena("", newValue));
            newNode.setLeft(left);
            newNode.setRight(right);
            left.appendPrefix("0");
            right.appendPrefix("1");
            queue.add(newNode);
        }

    }

    public String getPreorder(){
        return preorderRoute(this.root);

    }

    private String preorderRoute(Node node){
        if(node != null){
            return  " " + node + preorderRoute(node.getLeft()) + preorderRoute(node.getRight());
        }
        return "";
    }

}
