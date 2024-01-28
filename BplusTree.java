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
                }

			    else {
					//shiftLevel(newLeaf.elements.get(0), current.findParent(), newLeaf);
				}
			}
		}
	}
    /* 
	// Method to shift a level in the B-tree during insertion
	void shiftLevel(int num, Node current, Node child) {
		if (current.size < degree) {
			int i = 0;

			while (num > current.elements.get(i) && i < current.size)
				i++;

			for (int j = current.size; j > i; j--)
				current.elements[j] = current.elements[j - 1];

			for (int j = current.size + 1; j > i + 1; j--)
				current.child[j] = current.child[j - 1];

			current.elements.get(i) = num;
			current.size++;
			current.child[i + 1] = child;
		} else {
			Node newInternal = new Node();
			int[] tempelements = new int[degree + 1];
			Node[] tempchild = new Node[degree + 2];

			for (int i = 0; i < degree; i++)
				tempelements[i] = current.elements.get(i);

			for (int i = 0; i < degree + 1; i++)
				tempchild[i] = current.child[i];

			int i = 0, j;
			while (num > tempelements[i] && i < degree)
				i++;

			for (j = degree + 1; j > i; j--)
				tempelements[j] = tempelements[j - 1];

			tempelements[i] = num;
			for (j = degree + 2; j > i + 1; j--)
				tempchild[j] = tempchild[j - 1];

			tempchild[i + 1] = child;
			newInternal.setLeaf(false);
			current.size = (degree + 1) / 2;

			newInternal.size = degree - (degree + 1) / 2;

			for (i = 0, j = current.size + 1; i < newInternal.size; i++, j++)
				newInternal.elements[i] = tempelements[j];

			for (i = 0, j = current.size + 1; i < newInternal.size + 1; i++, j++)
				newInternal.child[i] = tempchild[j];

			if (current == root) {
				Node newRoot = new Node();
				newRoot.elements[0] = current.elements[current.size];
				newRoot.child[0] = current;
				newRoot.child[1] = newInternal;
				newRoot.setLeaf(false);
				newRoot.size = 1;
				root = newRoot;
			} else
				shiftLevel(current.elements[current.size], current.findParent(), newInternal);
		}
	}*/

    void remove(int num) {
        
        Node pos = find(num);
        
        if(pos == null) return;
        pos.remove(num);
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
