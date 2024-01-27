import java.util.ArrayList;
import java.util.Collections;

public class Node {

    private Node parent = null;
    Node right = null;
    Node left = null;
    public ArrayList<Integer> elements;
    ArrayList<Node> child;
    int degree;
    int size;
    boolean leaf;

    Node() {}

    Node(int degree, boolean leaf){
        size = 0;
        this.degree = degree;
        this.leaf = leaf;
        elements = new ArrayList<Integer>(degree);
        child = new ArrayList<Node>(degree+1);
    }

    boolean isLeaf() {
        return leaf;
    }

    protected void setLeaf(boolean flag){
        leaf = flag;
    }

    int findKeyIndex(int key) {
        int j;
        for(j = 0; j < size; j++)
            if(key < elements.get(j))
                break;
        return j;
    }

    Node findParent(){
        return parent;
    }

    void insert(int key){
        elements.add(key);
        size = elements.size();
        Collections.sort(elements);
    }

    void remove(int key) {
        elements.remove(key);
        size = elements.size();
    }
}
