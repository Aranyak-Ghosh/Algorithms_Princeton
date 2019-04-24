import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private class Node {
		Board board;
		int steps;
		Node parent;

		public Node(Board board, int steps, Node parent) {
			super();
			this.board = board;
			this.steps = steps;
			this.parent = parent;
		}

	}

	private final MinPQ<Node> heap;
	private final MinPQ<Node> twin_heap;
	private final Stack<Board> solution;
	private boolean solvable;

	private class Priority implements Comparator<Node> {

		@Override
		public int compare(Node x, Node y) {
			int x_dist = x.steps + x.board.manhattan();
			int y_dist = y.steps + y.board.manhattan();

			if (x_dist == y_dist) {
				if (x.board.manhattan() < y.board.manhattan())
					return -1;
				else if (x.board.manhattan() > y.board.manhattan())
					return 1;
				return 0;
			} else if (x_dist < y_dist)
				return -1;
			else
				return 1;
		}

	}

	public Solver(Board initial) {
		// TODO Auto-generated constructor stub
		solvable = true;
		solution = new Stack();
		if (initial == null)
			throw new IllegalArgumentException();

		Node node = new Node(initial, 0, null);
		Node node_twin = new Node(initial.twin(), 0, null);
		heap = new MinPQ<Node>(new Priority());
		twin_heap = new MinPQ<Node>(new Priority());
		heap.insert(node);
		twin_heap.insert(node_twin);

		while (!heap.isEmpty()) {
			node_twin = twin_heap.delMin();
			if (node_twin.board.isGoal()) {
				solvable = false;
				break;
			}
			
			node=heap.delMin();
			if(node.board.isGoal())
				generateStack(node);
			else {
				Node child,twin_child;
				
			}

		}
	}

	private void generateStack(Node node) {
		while (node != null) {
			solution.push(node.board);
			node = node.parent;
		}
	}

	public boolean isSolvable() {
		return solvable;
	}

	public int moves() {
		if (solvable)
			return solution.size() - 1;
		else
			return -1;
	}

	public Iterable<Board> solution() {
		if (solvable)
			return solution;
		else
			return null;
	}

}
