package Model;

public class BinarySearchTree {
    
    private Node root;
    
    public void Insert(int value){
        this.root = InsertRecursion(root, value);
        System.out.println("Insert sukses");
    }
    
    public Node InsertRecursion(Node root, int value){
        
        if (root == null){
            return new Node(value);
        }
        
        if (value < root.getValue()){
            root.setLeft(InsertRecursion(root.getLeft(), value));
        } else if (value > root.getValue()){
            root.setRight(InsertRecursion(root.getRight(), value));
        }
        
        return root;
    }
    
    public void Remove(int value){
        this.root = RemoveRecursion(root, value);
        System.out.println("penghapusan sukses");
    }
    
    private Node RemoveRecursion(Node root, int value){
        
        if (root == null){
            return null;
        }
        
        if (value < root.getValue()){
            root.setLeft(InsertRecursion(root.getLeft(), value));
        } else if (value > root.getValue()){
            root.setRight(InsertRecursion(root.getRight(), value));
        } else {
            if (root.getLeft() == null){
                return root.getRight();
            } else if (root.getRight() == null){
                return root.getLeft();
            }
            
            root.setValue(minValue(root.getRight()));
            root.setRight(RemoveRecursion(root.getRight(), root.getValue()));
        }
        
        return root;
        
    }
    
    private int minValue(Node root){
        int min = root.getValue();
        while(root.getLeft() != null){
            root = root.getLeft();
            min = root.getValue();
        }
        return min;
    }
    
    public boolean Contain(int value){
        return ContainRecursion(root, value);
    }
    
    public boolean ContainRecursion(Node root, int value){
        if(root == null){
            return false;
        }
        
        if(value == root.getValue()){
            return true;
        }
        
        return value < root.getValue() ? ContainRecursion(root.getLeft(), value) : ContainRecursion(root.getRight(), value);
    }
    
    public void PrintTree(){
        PrintTreeRecursion(root, "", true);
    }
    
    private void PrintTreeRecursion(Node root, String prefix, boolean isLeft){
        if(root != null){
            System.out.println(prefix + (isLeft ? "|--" : "|--") + root.getValue());
            PrintTreeRecursion(root.getLeft(), prefix + (isLeft ? "|   " : "    "), true);
            PrintTreeRecursion(root.getRight(), prefix + (isLeft ? "|   " : "    "), false);
        }
    }
}
