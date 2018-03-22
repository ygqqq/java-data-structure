package com.zondy.Tree;

// 使用递归方式再次实现二叉树的一些操作
public class BinaryTreeRec {

    private Node root;

    class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return "node.data = " + this.data;
        }
    }

    public Node Insert(Node node, int key) {
        if (node == null)
            return new Node(key);
        if (key < node.data)
            node.left = Insert(node.left, key);
        else if (key > node.data)
            node.right = Insert(node.right, key);
        else
            System.out.println("重复插入了数据");
        return node;
    }

    public Node Find(Node node, int key) {
        if (node == null)
            return null;
        if (key < node.data)
            node = Find(node.left, key);
        else if (key > node.data)
            node = Find(node.right, key);
        else
            return node;
        return node;
    }

    private Node Delete(Node delNode, int key) {

        return delNode;
    }

    public void Delete(int key) {
        Node delNode;
        if ((delNode = Find(this.root, key)) != null) {
            this.root = Delete(delNode, key);
        }
    }

    public void MiddleOrderPrintTree(Node tree) {
        if (tree == null) {
            return;
        }
        MiddleOrderPrintTree(tree.left);
        System.out.print(tree.data + "\t");
        MiddleOrderPrintTree(tree.right);
    }

    public static void main(String args[]) {
        BinaryTreeRec tree = new BinaryTreeRec();
        int[] arr = {30, 5, 7, 9, 15, 4, 13, 78, 22, 54, 31};
        for (int x : arr) {
            tree.root = tree.Insert(tree.root, x);
        }
        tree.MiddleOrderPrintTree(tree.root);

        System.out.println(tree.Find(tree.root, 9));
    }
}
