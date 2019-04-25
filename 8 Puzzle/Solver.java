import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

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
	private Stack<Board> solution;
	private boolean solvable;
	private int steps;

	private class ManhattanPriority implements Comparator<Node> {

		@Override
		public int compare(Node x, Node y) {

			int x_man = x.board.manhattan();
			int y_man = y.board.manhattan();

			int x_dist = x.steps + x_man;
			int y_dist = y.steps + y_man;

			if (x_dist == y_dist) {
				if (x_man < y_man)
					return -1;
				else if (x_man > y_man)
					return 1;
				return 0;
			} else if (x_dist < y_dist)
				return -1;
			else
				return 1;
		}

	}

	private class HammingPriority implements Comparator<Node> {

		@Override
		public int compare(Node x, Node y) {
			int x_ham = x.board.hamming();
			int y_ham = y.board.hamming();
			int x_dist = x.steps + x_ham;
			int y_dist = y.steps + y_ham;

			if (x_dist == y_dist) {
				if (x_ham < y_ham)
					return -1;
				else if (x_ham > y_ham)
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
		if (initial == null)
			throw new IllegalArgumentException();

		Node node = new Node(initial, 0, null);
		Node node_twin = new Node(initial.twin(), 0, null);
		heap = new MinPQ<Node>(new ManhattanPriority());
		heap_twin = new MinPQ<Node>(new ManhattanPriority());
		heap.insert(node);
		heap_twin.insert(node_twin);

		while (!node.board.isGoal() && !node_twin.board.isGoal()) {
			Iterable<Board> neighbors = node.board.neighbors();
			Iterable<Board> neighbors_twin = node_twin.board.neighbors();

			for (Board b : neighbors) {
				if (node.parent == null || !b.equals(node.parent.board)) {
					heap.insert(new Node(b, node.steps + 1, node));
				}
			}

			node = heap.delMin();

			for (Board b : neighbors_twin) {
				if (node_twin.parent == null || !b.equals(node_twin.parent.board)) {
					heap_twin.insert(new Node(b, node_twin.steps + 1, node_twin));
				}
			}
			node_twin = heap_twin.delMin();
		}
		if (node_twin.board.isGoal()) {
			this.solvable = false;
			this.solution = null;
			this.steps = -1;
		}

		if (node.board.isGoal()) {
			this.solvable = true;
			this.solution = new Stack<Board>();
			generateStack(node);
			this.steps = solution.size() - 1;
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
		return this.steps;
	}

	public Iterable<Board> solution() {
		return solution;
	}

	// Test Client
	public static void main(String[] args) {
		String file;
		if (args.length > 0)
			file = args[0];
		else
			file = "D:\\Data Structures And Algorithms\\workspace\\8Puzzle\\SampleIO\\puzzle27.txt";
		// create initial board from file
		In in = new In(file);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
