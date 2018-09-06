/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Tree;

/**
 *
 * @author MRB
 */
public class RBTree<T extends Comparable<T>> {

    public static void main(String[] args) {

        ///*
        int[] testNum = new int[]{15, 1, 3, 6, 8, 20, 22, 43, 67};
        RBTree<Integer> fuck = new RBTree<Integer>();
        for (int i = 0; i < testNum.length; i++) {
          fuck.insertNode(testNum[i]);
        }
        inorder(fuck.root,0);
       // System.out.println(fuck.root.data);
        //*/
        /*
        RBTree<Character> fuck = new RBTree<Character>();
        fuck.insertNode('F');
        fuck.insertNode('G');
        fuck.insertNode('D');
        fuck.insertNode('B');
        fuck.insertNode('C');
        System.out.println(fuck.root.data);
        */
    }
    
    public static void inorder( TreeNode treeNode,int i){
        if(treeNode == null){
            return;
        }
        i++;
        inorder(treeNode.left,i);
        System.out.print(i+"-");
        System.out.print(treeNode.data);
        System.out.print(treeNode.color?"-Red":"-Black");
        System.out.println();
        inorder(treeNode.right, i);
    }

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private TreeNode<T> root;

    /**
     * 插入一个新的节点，插入完毕后符合红黑树性质
     *
     * @param data  需要插入的数据
     */
    public void insertNode(T data) {
        TreeNode<T> curr;
        TreeNode<T> parent = toTargetParent(data);
        if (parent == null) {
            curr = root = new TreeNode<T>(data);
        } else {
            if (data.compareTo(parent.data) < 0) {
                curr = parent.left = new TreeNode<T>(data);
                curr.parent = parent;
            } else {
                curr = parent.right = new TreeNode<T>(data);
                curr.parent = parent;
            }
        }
        fixupTree(curr);
    }

    /**
     * 修复红黑树的不平衡状态
     *
     * @param node  新增节点
     */
    private void fixupTree(TreeNode node) {
        TreeNode parent = null, grandParent = null;
        boolean parentColor = false, uncleColor = false;
        if (node != root) {
            parent = node.parent;
            grandParent = parent.parent;
            parentColor = parent.color;
            uncleColor = getUncleColor(node);
        }
        //父节点为空表示其为根节点
        if (parent == null && node.color == RED) {
            node.color = BLACK;
        } else if (parentColor == RED && uncleColor == RED) {
            changeColor(grandParent);
            //再次判断根节点是否满足要求
            fixupTree(grandParent);
        } else if (parentColor == RED && uncleColor == BLACK) {
            dispatchRotation(grandParent, parent, node);
        }
    }

    /**
     * 判断属于哪种四种情况，LL、LR、RR、RL，使用正确的旋转
     *
     * @param grandParent   祖父节点
     * @param parent    父节点
     * @param child     新增节点
     */
    private void dispatchRotation(TreeNode grandParent, TreeNode parent, TreeNode child) {
        if (grandParent.left == parent) {
            if (parent.left == child) {
                rightRotation(grandParent);
            } else {
                leftRotation(parent);
                rightRotation(grandParent);
            }
        } else {
            if (parent.left == child) {
                rightRotation(parent);
                leftRotation(grandParent);
            } else {
                leftRotation(grandParent);
            }
        }
    }


    /**
     *  改变祖父节点以及两个子节点的颜色
     *
     * @param grandParent   传入新增节点的祖父
     */
    private void changeColor(TreeNode grandParent) {
        grandParent.color = RED;
        if (grandParent.left != null) {
            grandParent.left.color = BLACK;
        }
        if (grandParent.right != null) {
            grandParent.right.color = BLACK;
        }
    }

    /**
     * 返回叔叔节点的颜色
     *
     * @param node 一个节点
     * @return 其叔叔节点的颜色
     */
    private boolean getUncleColor(TreeNode node) {
        TreeNode parent = node.parent;
        //如果当前结点的祖父是空的，说明是其父节点是根节点
        return getBrotherColor(parent.parent == null ? node : parent);
    }

    /**
     * 返回兄弟节点的颜色
     *
     * @param child 传入节点
     * @return 返回兄弟节点的颜色
     */
    private boolean getBrotherColor(TreeNode child) {
        TreeNode parent = child.parent;
        if (parent.left == child && parent.right != null) {
            return parent.right.color;
        } else if (parent.right == child && parent.left != null) {
            return parent.left.color;
        } else {
            return BLACK;
        }
    }

    /**
     *  将传入的节点的右子节点提升为新的父节点，传入节点降为其右子节点
     *  注意颜色、父节点需要处理，务必要清除传入节点的右子节点，因为其已经被提升了父节点了。
     * @param curr  一个节点
     */
    private void leftRotation(TreeNode curr) {
        TreeNode tParent = curr.right;
        tParent.parent = curr.parent;
        tParent.color = BLACK;
        //新父节点的左子节点,放在传入节点的右边
        curr.right = tParent.left;
        if (tParent.left != null) {
            tParent.left.parent = curr;
        }
        //降为子节点前的数据整理
        curr.color = RED;
        curr.parent = tParent;
        tParent.left = curr;
        setChild(curr, tParent);
    }

    /**
     *  将传入的节点的左子节点提升为新的父节点，传入节点降为其右子节点
     *  注意颜色、父节点需要处理，务必要清除传入节点的左子节点，因为其已经被提升了父节点了。
     * @param curr  一个节点
     */
    private void rightRotation(TreeNode curr) {
        //新的父节点
        TreeNode tParent = curr.left;
        tParent.parent = curr.parent;
        tParent.color = BLACK;
        //新父节点的右子节点,放在传入节点的左边
        curr.left = tParent.right;
        if (tParent.right != null) {
            tParent.right.parent = curr;
        }
        //传入节点降为子节点前的数据整理
        curr.color = RED;
        curr.parent = tParent;
        tParent.right = curr;
        //父节点的指向改变
        setChild(curr, tParent);
    }

    /**
     * 使旋转在树中生效
     *
     * @param roNode    被旋转的节点
     * @param newParent 被旋转之后的父节点
     */
    private void setChild(TreeNode roNode, TreeNode newParent) {
        TreeNode roNodeParent = newParent.parent;
        if (roNodeParent == null) {
            root = newParent;
        } else if (roNodeParent.left == roNode) {
            roNodeParent.left = newParent;
        } else {
            roNodeParent.right = newParent;
        }
    }

    /**
     *  调到data存放位置的父节点处
     * @param data  用于对比的数据
     * @return  data可存放处的父节点
     */
    private TreeNode<T> toTargetParent(T data) {
        TreeNode<T> curr = root;
        TreeNode<T> parent = root;
        while (curr != null) {
            parent = curr;
            if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return parent;
    }

    /**
     * 内部节点
     */
    static class TreeNode<T extends Comparable<T>> {
        T data;
        boolean color;
        //伪删除
        boolean isDeleted;
        TreeNode<T> left;
        TreeNode<T> right;
        TreeNode<T> parent;

        TreeNode(T data, boolean color) {
            this.data = data;
            this.color = color;
        }

        TreeNode(T data) {
            this.data = data;
            this.color = RED;
        }
    }
}
