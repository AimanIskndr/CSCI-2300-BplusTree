import java.util.LinkedList;
import java.util.Queue;

public class BplusTree {
    
    private int degree;
    private Node root;

    BplusTree(int degree){
        this.degree = degree;
    }

    public Node getRoot() {
        return root;
    }

    void insert(int num) {

		if (root == null) {
			root = new Node(degree, true);
			root.elements.add(num);
		} 
        
        else {
			Node current = root;

			while (!current.isLeaf()) {
				int j;
                for(j = 0; j < current.size && num >= current.elements.get(j); j++) {}
                current = current.child.get(j);
			}

			current.insert(num);

            if(current.elements.size() == degree){

				Node newLeaf = new Node(degree, current.leaf);

				int midIndex = current.size / 2;
                int newLeafSize = current.size - midIndex;

                newLeaf.elements.addAll(current.elements.subList(midIndex, midIndex + newLeafSize));
                current.elements.subList(midIndex, current.size).clear();

                current.right = newLeaf;
                newLeaf.left = current;

				if (current == root) {
					Node newRoot = new Node(degree, false);
                    newRoot.child.add(current);
                    newRoot.child.add(newLeaf);
                    newRoot.elements.add(newLeaf.elements.get(0));
                    root = newRoot;
                    root.size = 1;
                    newLeaf.size = newLeafSize;
                    current.size = current.elements.size();
                    newLeaf.parent = newRoot;
                    current.parent = newRoot;
                }

			    else {
					//shiftLevel(newLeaf.elements.get(0), current.findParent(), newLeaf);
				}
			}
		}
	}

    void remove(int num) {
        
        Node pos = find(num);
        
        if(pos == null) return;
        pos.remove(num);

        if(pos.elements.size() < degree / 2){
            if(pos.right != null && pos.right.elements.size() > degree / 2){
                System.out.println("Borrow Right");
                pos.elements.add(pos.right.elements.get(0));
                pos.right.elements.remove(0);
                pos.right.size = pos.right.elements.size();
                pos.parent.elements.set(pos.parent.findKeyIndex(num), pos.right.elements.get(0));
            }

            else if(pos.left != null && pos.left.elements.size() > degree / 2){
                System.out.println("Borrow Left");
                pos.elements.add(pos.left.elements.get(pos.size - 1));
                pos.left.elements.remove(pos.size - 1);
                pos.left.size = pos.left.elements.size();
                pos.parent.elements.set(pos.parent.findKeyIndex(num), pos.elements.get(0));
            }

            else if(pos.right != null){
                System.out.println("Merge");
                pos.elements.addAll(pos.right.elements);
                pos.right = pos.right.right;
                pos.size = pos.elements.size();
                root = pos;
            }
        }
    }

    Node find(int num) {

        if (root == null)
            return null;

        else {
            Node current = root;
            while (!current.isLeaf()) {
                int j;
                for (j = 0; j < current.size && num >= current.elements.get(j); j++) {}
                current = current.child.get(j);
            }
 
            for (int i = 0; i < current.size; i++) {
                if (current.elements.get(i) == num) {
                    return current;
                }
            }
 
            return null;
        }
    }
    
    public void displayTree() {
        if (root != null) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
    
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
    
                for (int i = 0; i < levelSize; i++) {
                    Node currentNode = queue.poll();
    
                    if (currentNode != null) {
                        for (int j = 0; j < currentNode.elements.size(); j++) {
                            if (currentNode.elements != null) {
                                System.out.print(currentNode.elements.get(j) + " ");
                            }
                        }
    
                        if (currentNode.child != null) {
                            for (int j = 0; j < currentNode.size + 1; j++) {
                                if (currentNode.child.size() > j && currentNode.child.get(j) != null) {
                                    queue.add(currentNode.child.get(j));
                                }
                            }
                        }
    
                        System.out.print("\t");
                    }
                }
                System.out.println();
            }
        }
    }
}
