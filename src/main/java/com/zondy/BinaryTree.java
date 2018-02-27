package com.zondy;

import org.junit.Test;

public class BinaryTree<T extends Comparable<T>> {
    public TreeNode<T> root = null;

    /**
     * @param key 　要查找的关键字
     * @Description: 查找二叉树节点
     * @return: TreeNode 找到返回对应节点，未找到返回null
     * @Author: ygqqq
     * @Date: 18-2-26
     */
    public TreeNode<T> Search(T key) {
        //空树的情况
        if (this.root == null)
            return null;
        TreeNode<T> current = this.root;
        while (current.data.compareTo(key) != 0) {
            if (key.compareTo(current.data) > 0)
                current = current.right;
            else
                current = current.left;
            if (current == null)
                return null;
        }
        //能跳出while循环运行到这里说明找到了
        return current;
    }

    /**
     * @param key
     * @Description: 向二叉树插入数据
     * @return: boolean
     * @Author: ygqqq
     * @Date: 18-2-26
     */
    public boolean Insert(T key) {
        if (this.root == null) {
            this.root = new TreeNode<T>(key);
            return true;
        }
        TreeNode<T> current = this.root;    //指向当前遍历到的节点
        TreeNode<T> parent = this.root;     //指向当前遍历到的节点的父节点，也就是挂载点
        boolean isInsertLeft = true;        //标识挂载到左节点还是右节点
        while (current != null) {
            if (key.compareTo(current.data) > 0) {// key > 当前节点
                parent = current;
                current = current.right;
                isInsertLeft = false;
            } else if (key.compareTo(current.data) < 0) {// key < 当前节点
                parent = current;
                current = current.left;
                isInsertLeft = true;
            } else {
                System.out.println("不要重复添加节点");
                return false;
            }
        }
        //能运行到这里说明找到了挂载点，parent中记录的就是挂载点
        if (isInsertLeft)
            parent.left = new TreeNode<T>(key);
        else
            parent.right = new TreeNode<T>(key);
        return true;
    }

    /*
     * 删除节点，这个是所有操作里最复杂的，如果脑海中不好想象的话，最好画个图
     */
    public boolean Delete(T key) {
        //相删除节点的话，依然需要先找到节点，并且要记录下来其父节点等节点，用于节点之间关系的变换
        if (this.root == null) return false;

        TreeNode<T> current = this.root;    //指向当前遍历到的节点
        TreeNode<T> parent = this.root;     //指向当前遍历到的节点的父节点，也是待删除节点的父亲
        boolean isDelLeft = true;           //标识要删除的节点是左节点还是右节点
        while (current.data.compareTo(key) != 0) {
            parent = current;
            if (key.compareTo(current.data) > 0) {
                current = current.right;
                isDelLeft = false;
            } else {
                current = current.left;
                isDelLeft = true;
            }
            //没找到该节点
            if (current == null) return false;
        }
        // 能跳出while循环运行到这里说明已经找到了要删除的节点，并保存到了current中，其父节点保存到了parent中
        // 接下来需要解决的就是删除的问题，删除分为几种情况:
        // 1. 要删除的是叶节点，这种情况比较简单，可以直接删除
        // 2. 要删除的节点只有１个子节点，这种情况稍微麻烦一些，最好画图理清思路
        // 3. 要删除的节点有左右２个子节点，这种情况最复杂
        // 接下来分别处理这几种情况
        if (current.left == null && current.right == null) {//删除叶子节点
            if (current == this.root) this.root = null; //如果删除的是根，并且整棵树只有根这一个节点
            else if (isDelLeft) parent.left = null;
            else parent.right = null;
        } else if (current.left == null) {// 要删除的节点只有右子树
            if (current == this.root) this.root = current.right;
            else if (isDelLeft) parent.left = current.right;
            else parent.right = current.right;
        } else if (current.right == null) {// 要删除的节点只有左子树
            if (current == this.root) this.root = current.left;
            else if (isDelLeft) parent.left = current.left;
            else parent.right = current.left;
        } else {//要删除的节点有左右2个子节点,此时需要找到待删除节点的直接后继，并处理相关节点之间的变换关系
            TreeNode<T> subNode = GetSubsequenceNode(current);
            if (current == this.root) this.root = subNode;
            else if (isDelLeft) parent.left = subNode;
            else parent.right = subNode;
        }
        return true;
    }

    //找到待删除节点的直接后继，并处理相关节点之间的变换关系
    public TreeNode<T> GetSubsequenceNode(TreeNode<T> delNode) {
        TreeNode<T> subNode = delNode;  //待返回的直接后继节点
        TreeNode<T> subNodeParent = delNode;//待返回的直接后继节点的父节点
        TreeNode<T> current = delNode.right; //当前遍历的节点，从待删除节点的右节点开始遍历
        //先不管节点之间的关系，先找到后继节点以及其父亲节点
        while (current != null) {
            subNodeParent = subNode;
            subNode = current;
            current = current.left;
        }
        //跳出循环后，就找到了后继节点，但有可能后继节点就是待删除节点的右节点
        //如果后继节点不是待删除节点的右节点，说明是顺着右节点的左侧一路找下来的，此时需要处理一些关系，最好画图梳理
        if (subNode != delNode.right) {
            subNodeParent.left = subNode.right;
            //下面被注释的这句话，不能在这里写，必须在外面方法写。
            //因为对于subNode.right的赋值，只在subNode != delNode.right情况需要赋值
            //subNode.left=delNode.left;
            subNode.right = delNode.right;
        }
        subNode.left = delNode.left;
        return subNode;
    }

    // 中序遍历
    public void MiddleOrderPrintTree(TreeNode<Integer> tree) {
        if (tree == null) {
            return;
        }
        MiddleOrderPrintTree(tree.left);
        System.out.print(tree.data + "\t");
        MiddleOrderPrintTree(tree.right);
    }

    @Test
    public void test() {
        int[] arr = {30,5, 7, 9, 15, 4, 13, 78, 22, 54, 31};
        BinaryTree<Integer> demo = new BinaryTree<Integer>();
        for (int x : arr) {
            demo.Insert(x);
        }
        demo.MiddleOrderPrintTree(demo.root);
//        TreeNode<Integer> node = demo.Search(16);
//        if (node == null)
//            System.out.println("没找到");
//        else
//            System.out.println("找到了,data:" + node.data);
        demo.Delete(30);
        System.out.println("");
        demo.MiddleOrderPrintTree(demo.root);

        demo.Delete(15);
        System.out.println("");
        demo.MiddleOrderPrintTree(demo.root);

        demo.Delete(22);
        System.out.println("");
        demo.MiddleOrderPrintTree(demo.root);

        demo.Delete(78);
        System.out.println("");
        demo.MiddleOrderPrintTree(demo.root);

        demo.Delete(4);
        System.out.println("");
        demo.MiddleOrderPrintTree(demo.root);

        demo.Delete(7);
        System.out.println("");
        demo.MiddleOrderPrintTree(demo.root);
    }


}
