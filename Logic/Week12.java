package Logic;

import Model.BinarySearchTree;

public class Week12 {
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        
        tree.Insert(10);
        tree.Insert(30);
        tree.Insert(5);
        
        tree.PrintTree();
        
        System.out.println(tree.Contain(5));
        System.out.println(tree.Contain(10));
        
        tree.Remove(10);
        tree.PrintTree();
    }
    
}
