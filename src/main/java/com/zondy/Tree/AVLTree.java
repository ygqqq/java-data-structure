package com.zondy.Tree;

// 偷下懒直接用int了，没使用泛型
public class AVLTree {

    private AVLTreeNode root;

    class AVLTreeNode {
        int data;
        AVLTreeNode left;
        AVLTreeNode right;
        int height;

        public AVLTreeNode() {
        }

        public AVLTreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 0;
        }

        @Override
        public String toString() {
            return "node.data = " + this.data;
        }
    }

    /*
        获取一个树的高度
     */
    private int height(AVLTreeNode tree) {
        if (tree == null)
            return -1;
        else
            return tree.height;
    }

    /*
        LL情况下的树旋转(右旋一次):
            1. 保存原根节点的左节点(也即新的根节点)，记为n1
            2. 将n1的右节点赋值给原根节点的左节点
            3. 将原根节点挂载到n1的右节点
            4. 更新原根节点和n1节点的高度，并返回新的根节点，也即n1节点
     */
    private AVLTreeNode LL(AVLTreeNode tree) {
        //调整节点关系
        AVLTreeNode n1 = tree.left;
        tree.left = n1.right;
        n1.right = tree;
        //更新高度
        tree.height = Math.max(height(tree.left), height(tree.right)) + 1;
        n1.height = Math.max(height(n1.left), height(n1.right)) + 1;
        return n1;
    }

    /*
        RR情况下的树旋转(左旋一次):
     */
    private AVLTreeNode RR(AVLTreeNode tree) {
        //调整节点关系
        AVLTreeNode n1 = tree.right;
        tree.right = n1.left;
        n1.left = tree;
        //更新高度
        tree.height = Math.max(height(tree.left), height(tree.right)) + 1;
        n1.height = Math.max(height(n1.left), height(n1.right)) + 1;
        return n1;
    }

    /*
        LR情况下的旋转：
            1. 先左旋一次
            2. 再右一次
     */
    private AVLTreeNode LR(AVLTreeNode tree) {
        tree.left = RR(tree.left);
        return LL(tree);
    }

    /*
        RL情况下的旋转：
            1. 先右旋一次
            2. 再左一次
     */
    private AVLTreeNode RL(AVLTreeNode tree) {
        tree.right = LL(tree.right);
        return RR(tree);
    }

    private AVLTreeNode Insert(AVLTreeNode tree, int key) {
        // 空树直接插入
        if (tree == null)
            return new AVLTreeNode(key);
        if (key < tree.data) {
            tree.left = Insert(tree.left, key);
            if (height(tree.left) - height(tree.right) == 2) {
                if (key < tree.left.data)
                    tree = LL(tree);
                else
                    tree = LR(tree);
            }
        } else if (key > tree.data) {
            tree.right = Insert(tree.right, key);
            if (height(tree.right) - height(tree.left) == 2) {
                if (key < tree.right.data)
                    tree = RL(tree);
                else
                    tree = RR(tree);
            }
        } else {
            System.out.println("插入了重复节点");
        }
        tree.height = Math.max(height(tree.left), height(tree.right)) + 1;
        return tree;
    }

    private AVLTreeNode Search(AVLTreeNode tree, int key) {
        if (tree == null)
            return null;
        if (key < tree.data)
            return Search(tree.left, key);
        else if (key > tree.data)
            return Search(tree.right, key);
        else
            return tree;
    }

    private AVLTreeNode Delete(AVLTreeNode tree, AVLTreeNode delNode) {
        if (tree == null || delNode == null)
            return null;
        if (delNode.data < tree.data) {
            tree.left = Delete(tree.left, delNode);
            if (height(tree.right) - height(tree.left) == 2) {
                AVLTreeNode node = tree.right;
                if (height(node.left) > height(node.right))
                    tree = RL(tree);
                else
                    tree = RR(tree);
            }
        } else if (delNode.data > tree.data) {
            tree.right = Delete(tree.right, delNode);
            if (height(tree.left) - height(tree.right) == 2) {
                AVLTreeNode node = tree.left;
                if (height(node.right) > height(node.left))
                    tree = LR(tree);
                else
                    tree = LL(tree);
            }
        } else {
            if ((tree.left != null) && (tree.right != null)) {
                if (height(tree.left) > height(tree.right)) {
                    AVLTreeNode max = maximum(tree.left);
                    tree.data = max.data;
                    tree.left = Delete(tree.left, max);
                } else {
                    AVLTreeNode min = minimum(tree.right);
                    tree.data = min.data;
                    tree.right = Delete(tree.right, min);
                }
            } else {
                AVLTreeNode tmp = tree;
                tree = (tree.left != null) ? tree.left : tree.right;
                tmp = null;
            }
        }
        if(tree != null) tree.height = Math.max(height(tree.left), height(tree.right)) + 1;
        return tree;
    }

    public void Delete(int key) {
        AVLTreeNode delNode;
        if ((delNode = Search(this.root, key)) != null) {
            Delete(this.root, delNode);
        }
    }

    private AVLTreeNode maximum(AVLTreeNode tree) {
        if (tree == null)
            return null;

        while (tree.right != null)
            tree = tree.right;
        return tree;
    }

    private AVLTreeNode minimum(AVLTreeNode tree) {
        if (tree == null)
            return null;
        while (tree.left != null)
            tree = tree.left;
        return tree;
    }

    public void MiddleOrderPrintTree(AVLTreeNode tree) {
        if (tree == null) {
            return;
        }
        MiddleOrderPrintTree(tree.left);
        System.out.print(tree.data + "\t");
        MiddleOrderPrintTree(tree.right);
    }


    public static void main(String args[]) {
        AVLTree tree = new AVLTree();
        int[] arr = {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
        for (int x : arr) {
            tree.root = tree.Insert(tree.root, x);
        }
        tree.MiddleOrderPrintTree(tree.root);
        System.out.println("");
        tree.Delete(13);
        tree.MiddleOrderPrintTree(tree.root);

        System.out.println("");
        tree.Delete(tree.root.data);
        tree.MiddleOrderPrintTree(tree.root);

        System.out.println("");
        tree.Delete(54);
        tree.MiddleOrderPrintTree(tree.root);

        System.out.println("");
        tree.Delete(7);
        tree.MiddleOrderPrintTree(tree.root);

        //System.out.println(tree.Search(tree.root,10));
    }


}
