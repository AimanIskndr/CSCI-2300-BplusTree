import java.util.*;

public class Main {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of degree: ");
        int degree = sc.nextInt();

        BplusTree tree = new BplusTree(degree);
        test(tree);

        sc.close();
    }

    public static void test(BplusTree tree){

        int[] tc = {83, 27, 20, 84, 29, 15, 58, 64, 11, 18};

        for(int i = 0; i < tc.length; i++){
            System.out.printf("Insert(%d): %n", tc[i]);
            tree.insert(tc[i]);
            tree.displayTree();
            System.out.println();
        }
    }
}
