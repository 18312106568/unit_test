package com.creditcloud.jpa.unit_test.Tree;


import java.util.*;

/**
 * 2-3树
 */
@SuppressWarnings("unchecked")
public class TTTree extends AbstractSet implements SortedSet {

    Node root;
    int size = 0;

    public boolean add(Object value) {
        if (root == null)
            root = Node.newTwoNode(value);
        else {
            try {
                Node result = insert(value, root);
                if (result != null) {
                    root = result;
                }
            } catch (DuplicateException e) {
                return false;
            }
        }
        size ++;
        return true;
    }

    private Node insert(Object value, Node node) throws DuplicateException {
        Node returnValue = null;
        if (node.isTwoNode()) {
            int comp = ((Comparable) value).compareTo(node.val());

            if (node.isTerminal()) {
                if (comp == 0)
                    throw DUPLICATE;
                Node thnode = Node.newThreeNode(value, node.val());
                Node parent = node.parent();
                if (parent != null)
                    parent.replaceChild(node, thnode);
                else
                    root = thnode;
            } else {
                if (comp < 0) {
                    Node result = insert(value, node.leftChild());
                    if (result != null) {
                        Node threeNode = Node.newThreeNode(result.val(), node.val());
                        threeNode.setRightChild(node.rightChild());
                        threeNode.setMiddleChild(result.rightChild());
                        threeNode.setLeftChild(result.leftChild());
                        if (node.parent() != null) {
                            node.parent().replaceChild(node, threeNode);
                        } else {
                            root = threeNode;
                        }
                        unlinkNode(node);
                    }
                } else if (comp > 0) {
                    Node result = insert(value, node.rightChild());
                    if (result != null) {
                        Node threeNode = Node.newThreeNode(result.val(), node.val());
                        threeNode.setLeftChild(node.leftChild());
                        threeNode.setMiddleChild(result.leftChild());
                        threeNode.setRightChild(result.rightChild());
                        if (node.parent() != null) {
                            node.parent().replaceChild(node, threeNode);
                        } else {
                            root = threeNode;
                        }
                        unlinkNode(node);
                    }
                } else {
                    throw DUPLICATE;
                }
            }

        } else { // three node
            Node threeNode = node;

            int leftComp = ((Comparable) value).compareTo(threeNode.leftVal());
            int rightComp = ((Comparable) value).compareTo(threeNode.rightVal());
            if (leftComp == 0 || rightComp == 0) {
                throw DUPLICATE;
            }

            if (threeNode.isTerminal()) {

                returnValue = splitNode(threeNode, value);

            } else {
                if (leftComp < 0) {
                    Node result = insert(value, threeNode.leftChild());
                    if (result != null) {
                        returnValue = splitNode(threeNode, result.val());
                        returnValue.leftChild().setLeftChild(result.leftChild());
                        returnValue.leftChild().setRightChild(result.rightChild());
                        returnValue.rightChild().setLeftChild(threeNode.middleChild());
                        returnValue.rightChild().setRightChild((threeNode.rightChild()));
                        unlinkNode(threeNode);
                    }
                } else if (rightComp < 0) {
                    Node result = insert(value, threeNode.middleChild());
                    if (result != null) {
                        returnValue = splitNode(threeNode, result.val());
                        returnValue.leftChild().setLeftChild(threeNode.leftChild());
                        returnValue.leftChild().setRightChild(result.leftChild());
                        returnValue.rightChild().setLeftChild(result.rightChild());
                        returnValue.rightChild().setRightChild(threeNode.rightChild());
                        unlinkNode(threeNode);
                    }
                } else  {
                    Node result = insert(value, threeNode.rightChild());
                    if (result != null) {
                        returnValue = splitNode(threeNode, result.val());
                        returnValue.leftChild().setLeftChild(threeNode.leftChild());
                        returnValue.leftChild().setRightChild(threeNode.middleChild());
                        returnValue.rightChild().setLeftChild(result.leftChild());
                        returnValue.rightChild().setRightChild(result.rightChild());
                        unlinkNode(threeNode);
                    }
                }
            }
        }
        return returnValue;
    }

    private void unlinkNode(Node node) {
        node.removeChildren();
        node.setParent(null);
    }

    private Node splitNode(Node threeNode, Object value) {
        Object min;
        Object max;
        Object middle;
        if (((Comparable)value).compareTo(threeNode.leftVal()) < 0) {
            min = value;
            middle = threeNode.leftVal();
            max = threeNode.rightVal();
        } else if (((Comparable)value).compareTo(threeNode.rightVal()) < 0) {
            min = threeNode.leftVal();
            middle = value;
            max = threeNode.rightVal();
        } else {
            min = threeNode.leftVal();
            max = value;
            middle = threeNode.rightVal();
        }

        Node parent = Node.newTwoNode(middle);
        parent.setLeftChild(Node.newTwoNode(min));
        parent.setRightChild(Node.newTwoNode(max));
        return parent;
    }


    @Override
    public Iterator iterator() {
        return new Iterator() {
            Node nextNode;

            // Stack to keep three nodes
            Deque threeNodes = new ArrayDeque();
            Object next;
            {
                Node node = root;
                while(node != null && node.leftChild() != null) {
                    node = node.leftChild();
                }
                nextNode = node;
            }
            public boolean hasNext() {
                return next != null || nextNode != null;
            }

            public Object next() {
                Object prev;
                if (next != null) {
                    prev = next;
                    next = null;
                    nextNode = successor(nextNode, prev);
                    return prev;
                }
                if (nextNode.isThreeNode()) {
                    if (nextNode.isTerminal()) {
                        next = nextNode.rightVal();
                        prev = nextNode.leftVal();
                    } else {
                        if (threeNodes.peekFirst() == nextNode) {
                            threeNodes.pollFirst();
                            prev = nextNode.rightVal();
                            nextNode = successor(nextNode, prev);
                        } else {
                            prev = nextNode.leftVal();
                            threeNodes.addFirst(nextNode);
                            nextNode = successor(nextNode, prev);
                        }
                    }
                } else {
                    prev = nextNode.val();
                    nextNode = successor(nextNode, prev);
                }
                return prev;
            }


            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private Node successor(Node node, Object value) {
        if (node == null)
            return null;

        if (!node.isTerminal()) {
            Node p;
            if (node.isThreeNode() && node.leftVal().equals(value)) {
                p = node.middleChild();
            } else {
                p = node.rightChild();
            }
            while (p.leftChild() != null) {
                p = p.leftChild();
            }
            return p;
        } else {
            Node p = node.parent();
            if (p == null) return null;

            Node ch = node;
            while (p != null && ch == p.rightChild()) {
                ch = p;
                p = p.parent();
            }
            return p != null ? p : null;
        }
    }

    private Node predecessor(Node node, Object value) {
        if (node == null)
            return null;

        Node p;
        if (!node.isTerminal()) {
            if (node.isThreeNode() && node.rightVal().equals(value)) {
                p = node.middleChild();
            } else {
                p = node.leftChild();
            }

            while (p.rightChild() != null) {
                p = p.rightChild();
            }
            return p;
        } else {
            throw new UnsupportedOperationException("Implement predecessor parent is not terminal node");
        }

    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public Comparator comparator() {
        return null;
    }

    @Override
    public SortedSet subSet(Object fromElement, Object toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet headSet(Object toElement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public SortedSet tailSet(Object fromElement) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Object first() {
        return null;
    }

    @Override
    public Object last() {
        return null;
    }

    private static final class DuplicateException extends RuntimeException {};
    private static final DuplicateException DUPLICATE = new DuplicateException();

    static class Node<T> {
        private Node parent;
        private Node leftChild;
        private Node rightChild;
        private Node middleChild;

        // When node is 2-node, leftVal is the values, and rightVal is null.
        private T leftVal;
        private T rightVal;

        private boolean twoNode;


        protected Node() {

        }

        public static <T>Node newTwoNode(T value) {
            Node node = new Node();
            node.leftVal = value;
            node.twoNode = true;
            return node;
        }


        public static <T>Node newThreeNode(T leftVal, T rightVal) {
            Node node = new Node();
            if (((Comparable)leftVal).compareTo(rightVal) > 0) {
                node.rightVal = leftVal;
                node.leftVal = rightVal;
            } else {
                node.leftVal = leftVal;
                node.rightVal = rightVal;
            }
            node.twoNode = false;
            return node;
        }


        public HoleNode newHole() {
            return new HoleNode();
        }


        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
            if (leftChild != null)
                leftChild.setParent(this);
        }

        public void removeChildren() {
            this.leftChild = null;
            this.rightChild = null;
        }


        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
            if (rightChild != null)
                rightChild.setParent(this);
        }

        public void setMiddleChild(Node middleChild) {
            assert isThreeNode();
            this.middleChild = middleChild;
            if (middleChild != null) {
                middleChild.setParent(this);
            }
        }


        public final Node parent() {
            return parent;
        }

        public final void setParent(Node parent) {
            this.parent = parent;
        }


        public boolean isTerminal() {
            return leftChild == null && rightChild == null;
        }


        public T val() {
            assert isTwoNode();
            return leftVal;
        }


        public T leftVal() {
            assert isThreeNode();
            return leftVal;
        }

        public void setVal(T val) {
            assert isTwoNode();
            leftVal = val;
        }


        public T rightVal() {
            assert isThreeNode();
            return rightVal;
        }

        public void setLeftVal(T leftVal) {
            assert isThreeNode();
            this.leftVal = leftVal;
        }

        public void setRightVal(T rightVal) {
            assert isThreeNode();
            this.rightVal = rightVal;
        }

        public boolean isTwoNode() {
            return twoNode;
        }

        public boolean isThreeNode() {
            return !isTwoNode();
        }

        public Node leftChild() {
            return leftChild;
        }

        public Node rightChild() {
            return rightChild;
        }

        public Node middleChild() {
            assert isThreeNode();
            return middleChild;
        }

        @SuppressWarnings("unchecked")
        public void replaceChild(Node currentChild, Node newChild) {
            if (currentChild == leftChild) {
                leftChild = newChild;
            } else if (currentChild == rightChild) {
                rightChild = newChild;
            } else {
                assert middleChild == currentChild;
                middleChild = newChild;
            }
            newChild.setParent(this);
            currentChild.setParent(null);
        }
    }

    static class HoleNode extends Node {
        private Node child;

        HoleNode() {
            super();
        }

        public boolean isTwoNode() {
            return false;
        }

        public Node sibling() {
            if (parent() != null) {
                return parent().leftChild() == this ? parent().rightChild() : parent().leftChild();
            }
            return null;
        }

        @Override
        public void setLeftChild(Node leftChild) {
        }

        @Override
        public void removeChildren() {
            child = null;
        }


        @Override
        public void setRightChild(Node rightChild) {
        }

        public Node child() {
            return child;
        }

        public void setChild(Node child) {
            this.child = child;
        }


    }
}
