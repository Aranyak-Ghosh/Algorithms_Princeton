import edu.princeton.cs.algs4.Queue;

public class Board {
	private int[][] board;
	private int length;

	public Board(int[][] board) {
		this.length = board.length;
		this.board = new int[length][length];
		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				this.board[i][j] = board[i][j];
	}

	public int dimension() {
		return this.length;
	}

	public int hamming() {
		int distance = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++)
				if (!(i == length - 1 && j == length - 1))
					if (board[i][j] != (length * i + j + 1))
						distance++;
		}
		return distance;
	}

	public int manhattan() {
		int distance = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (board[i][j] != 0) {
					distance += Math.abs(i - (board[i][j] - 1) / length);
					distance += Math.abs(j - (board[i][j] - 1) % length);
				}
			}
		}
		return distance;
	}

	public Board twin() {
		int[][] newBlocks = new int[length][length];
		duplicate(newBlocks);
		int row = 0;
		for (int i = 0; i < length; ++i) {
			if (board[0][i] == 0) {
				row = 1;
				break;
			}
		}
		newBlocks[row][0] = board[row][1];
		newBlocks[row][1] = board[row][0];
		return new Board(newBlocks);
	}

	private void duplicate(int[][] copy) {
		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				copy[i][j] = this.board[i][j];
	}

	public boolean isGoal() {
		return hamming() == 0;
	}

	public boolean equals(Object y) {
		if (y == null)
			return false;
		if (y.hashCode() == this.hashCode())
			return true;
		if (y instanceof Board) {
			Board temp = (Board) y;
			if (temp.length != this.length)
				return false;
			for (int i = 0; i < length; i++)
				for (int j = 0; j < length; j++)
					if (this.board[i][j] != temp.board[i][j])
						return false;
			return true;
		}
		return false;
	}

	public Iterable<Board> neighbors() {
		Queue<Board> q = new Queue<Board>();
		int row = -1, col = -1;
		boolean found = false;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (board[i][j] == 0) {
					found = true;
					row = i;
					col = j;
					break;
				}
			}
			if (found)
				break;
		}

		int[][] copy = new int[length][length];
		duplicate(copy);
		if (row > 0) {
			swap(copy, row, col, row - 1, col);
			q.enqueue(new Board(copy));
			swap(copy, row, col, row - 1, col);
		}
		if (row < length - 1) {
			swap(copy, row, col, row + 1, col);
			q.enqueue(new Board(copy));
			swap(copy, row, col, row + 1, col);
		}
		if (col > 0) {
			swap(copy, row, col, row, col - 1);
			q.enqueue(new Board(copy));
			swap(copy, row, col, row, col - 1);
		}
		if (col < length - 1) {
			swap(copy, row, col, row, col + 1);
			q.enqueue(new Board(copy));
			swap(copy, row, col, row, col + 1);
		}
		return q;
	}

	private void swap(int[][] copy, int row, int col, int newRow, int newCol) {
		int tmp = copy[row][col];
		copy[row][col] = copy[newRow][newCol];
		copy[newRow][newCol] = tmp;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(length + "\n");
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++)
				sb.append(board[i][j] + " ");
			sb.append("\n");
		}
		return sb.toString();
	}
}
