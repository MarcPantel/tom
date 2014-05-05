package examples.adt;

import examples.adt.tree.types.*;

public class TreeDemo {
	%include{ tree/Tree.tom }

	public static boolean isEmpty(Tree tree) {
		return tree.isempty();
	}

	public static Tree createTree(Node rootItem, Tree left, Tree right) {
		return `tree(rootItem, left, right);
	}

	public static Node getRootNode(Tree tree) throws EmptyQueueException {
		%match(tree) {
			tree(x, _, _) -> { return `x; }
		}
		throw new EmptyQueueException();
	}

	public static Tree attachLeftTree(Tree root, Tree left) throws EmptyQueueException {
		%match(root) {
			tree(x, _, r) -> { return `tree(x, left, r); }
		}
		throw new EmptyQueueException();
	}

	public static Tree attachRightTree(Tree root, Tree right) throws EmptyQueueException {
		%match(root) {
			tree(x, l, _) -> { return `tree(x, l, right); }
		}
		throw new EmptyQueueException();
	}

	public static Tree attactLeft(Tree root, Node node) throws EmptyQueueException {
		%match(root) {
			tree(x, _, r) -> { return `tree(x, tree(node, empty(), empty()), r); }
		}
		throw new EmptyQueueException();
	}

	public static Tree attactRight(Tree root, Node node) throws EmptyQueueException {
		%match(root) {
			tree(x, l, _) -> { return `tree(x, l, tree(node, empty(), empty())); }
		}
		throw new EmptyQueueException();
	}

	/**
	* Wrong implementation
	**/
	public static Tree detachLeftSubtree(Tree tree) throws EmptyQueueException {
		%match(tree) {
			//tree(_, l, _) -> { return `l; }
			tree(_, l, r) -> { return `r; }
		}
		throw new EmptyQueueException();
	}
	
	public static Tree detachRightSubtree(Tree tree) throws EmptyQueueException {
		%match(tree) {
			tree(_, _, r) -> { return `r; }
		}
		throw new EmptyQueueException();
	}

	public static class EmptyQueueException extends Exception {
		public EmptyQueueException() {
			super("Queue is empty.");
		}

		public EmptyQueueException(String message) {
			super(message);
		}
	}
} 
