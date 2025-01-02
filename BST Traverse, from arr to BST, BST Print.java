package ru.dan.aisd;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinTree {
	static class Node {
	    private int value;
		private Node left, right, parent = null;
	}
	private Node root;
	
	public BinTree(int rootVal) {
		root = new Node();
		root.value = rootVal;
		root.parent = root;
	}
	private BinTree(Node root) {
		this.root = root;
		this.root.parent = root;
	}
	public BinTree() {
		root = null;
	}
	public void add(int addVal) {
		if (root == null) {
			root = new Node();
			root.value = addVal;
			root.parent = root;
			return;
		}
		Node addNode = new Node();
		Node projection = root;
		addNode.value = addVal;
		while (true) {
			if (addVal >= projection.value) {
				if (projection.right == null) {
					addNode.parent = projection;
					projection.right = addNode;
					break;
				}
				projection = projection.right;
			}
			else {
				if (projection.left == null) {
					addNode.parent = projection;
					projection.left = addNode;
					break;
				}
				projection = projection.left;
			}
		}
	}
	public Node getRoot() {
		return root;
	}
	public void printByLevel(Node root, int level) {
		if (root  == null) {
			return;
		}
		if (level == 1) {
			System.out.println("val is " + root.value + " and parent is " + root.parent.value);
			return;
		}
		printByLevel(root.left, level - 1);
		printByLevel(root.right, level - 1);
	}
	private static Node arrayRoot(int[] array, int begin, int end) {
		if (array.length == 0 || begin > end) {
			return null;
		}
		int mid = (begin + end) / 2;
		Node node = new Node();
		node.value = array[mid];
		node.left = arrayRoot(array, begin, mid - 1);
		if (node.left != null) {
	        node.left.parent = node;  
	    }
		node.right = arrayRoot(array, mid + 1, end);
		if (node.right != null) {
	        node.right.parent = node;  
	    }
		return node;
	}
	public static BinTree arrayTree(int[] array, int begin, int end) {
		Node root = arrayRoot(array, begin, end);
		return new BinTree(root);
	}

	public static void BFS(BinTree bt) {
		Queue<Node> q = new LinkedList<Node>();
		q.add(bt.root);
		while (!q.isEmpty()) {
			Node visiting = q.poll();
			System.out.println(visiting.value);
			if (visiting.left != null) {
				q.add(visiting.left);
			}
			if (visiting.right != null) {
				q.add(visiting.right);
			}
		}
	}

	public static void DFSSymm(BinTree bt) {
		if (bt.root == null) return;
		Stack<Node> stack = new Stack<Node>();
		Queue<Node> visited = new LinkedList<Node>();
		stack.push(bt.root);
		while (!stack.empty()) {
			Node visiting = stack.pop();
			if (visiting.right != null) {
				stack.push(visiting.right);
			}
			if (visiting.left != null && !visited.contains(visiting.left)) {
				stack.push(visiting);
				stack.push(visiting.left);
			} else {
				if (!visited.contains(visiting)) {
					visited.add(visiting);
					System.out.println(visiting.value);
				}
			}
		}
	}
	public static void DFSStraight(BinTree bt) {
		if (bt.root == null) return;
		Stack<Node> stack = new Stack<Node>();
		stack.push(bt.root);
		while (!stack.empty()) {
			Node visiting = stack.pop();
			System.out.println(visiting.value);
			if (visiting.right != null) {
				stack.push(visiting.right);
			}
			if (visiting.left != null) {
				stack.push(visiting.left);
			}
		}
	}
	public static void DFSReverse(BinTree bt) {
		if (bt.root == null) return;
		Stack<Node> stack = new Stack<Node>();
		Queue<Node> visited = new LinkedList<Node>();
		stack.push(bt.root);
		while(!stack.empty()) {
			boolean ifPushed = false;
			Node visiting = stack.peek();
			if (visiting.right != null && !visited.contains(visiting.right)) {
				stack.push(visiting.right);
				ifPushed = true;
			}
			if (visiting.left != null && !visited.contains(visiting.left)) {
				stack.push(visiting.left);
				ifPushed = true;
			}
			if (!ifPushed) {
				System.out.println(visiting.value);
				visited.add(visiting);
				stack.pop();
			}
		}
	}
}