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
	private final MinPQ<Node> heap_twin;
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
		solution = new Stack<Board>();
		if (initial == null)
			throw new IllegalArgumentException();

		Node node = new Node(initial, 0, null);
		Node node_twin = new Node(initial.twin(), 0, null);
		heap = new MinPQ<Node>(new Priority());
		heap_twin = new MinPQ<Node>(new Priority());
		heap.insert(node);
		heap_twin.insert(node_twin);

		while (!heap.isEmpty()) {
			node_twin = heap_twin.delMin();
			if (node_twin.board.isGoal()) {
				solvable = false;
				break;
			}

			node = heap.delMin();
			if (node.board.isGoal())
				generateStack(node);
			else {
				Node child, child_twin;
				Iterable<Board> neighbors = node.board.neighbors();
				Iterable<Board> neighbors_twin = node_twin.board.neighbors();
				boolean addChild, addChild_twin;
				addChild = true;
				addChild_twin = true;
				for (Board b : neighbors) {
					if (addChild) {
						if (node.parent != null && node.board.equals(b)) {
							addChild = false;
							continue;
						} else {
							child = new Node(b, node.steps + 1, node);
							heap.insert(child);
						}
					} else {
						child = new Node(b, node.steps + 1, node);
						heap.insert(child);
					}
				}
				
				for (Board b : neighbors_twin) {
					if (addChild_twin) {
						if (node_twin.parent != null && node_twin.board.equals(b)) {
							addChild_twin = false;
							continue;
						} else {
							child_twin = new Node(b, node_twin.steps + 1, node_twin);
							heap_twin.insert(child_twin);
						}
					} else {
						child_twin = new Node(b, node_twin.steps + 1, node_twin);
						heap_twin.insert(child_twin);
					}
				}
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
