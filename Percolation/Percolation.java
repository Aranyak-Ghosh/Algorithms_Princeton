
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] canvas;
	private int size;
	private int openSite;
	private int top, bottom;
	private WeightedQuickUnionUF uf, uf_b;

	public Percolation(int n) // create n-by-n grid, with all sites blocked
	{
		super();

		if (n > 0) {
			this.openSite = 0;
			this.size = n;
			top = 0;
			bottom = (n * n) + 1;
			uf = new WeightedQuickUnionUF((n * n) + 2);
			uf_b = new WeightedQuickUnionUF((n * n) + 1);
			this.canvas = new boolean[n][n];
		} else
			throw new java.lang.IllegalArgumentException();
	}

	private void validate(int row, int col) {
		if (row <= 0 || col <= 0 || row > size || col > size)
			throw new java.lang.IllegalArgumentException(row + " or " + col + " out of bounds");
	}

	public void open(int row, int col) // open site (row, col) if it is not open already
	{
		validate(row, col);
		if (!this.canvas[row - 1][col - 1]) {
			this.canvas[row - 1][col - 1] = true;
			int pos = (row - 1) * this.size + col;
			int up = (row - 2) * this.size + col;
			int down = (row) * this.size + col;
			int left = (row - 1) * this.size + col - 1;
			int right = (row - 1) * this.size + col + 1;
			this.openSite++;
			if (row == 1) {
				this.uf.union(pos, top);
				this.uf_b.union(pos, top);
			}
			if (row == size)
				this.uf.union(pos, bottom);
			if (row > 1 && isOpen(row - 1, col)) {
				this.uf.union(pos, up);
				this.uf_b.union(pos, up);
			}
			if (row < size && isOpen(row + 1, col)) {
				this.uf.union(pos, down);
				this.uf_b.union(pos, down);
			}
			if (col > 1 && isOpen(row, col - 1)) {
				this.uf.union(pos, left);
				this.uf_b.union(pos, left);
			}
			if (col < size && isOpen(row, col + 1)) {
				this.uf.union(pos, right);
				this.uf_b.union(pos, right);
			}
		}
	}

	public boolean isOpen(int row, int col) // is site (row, col) open?
	{
		validate(row, col);
		return this.canvas[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) // is site (row, col) full?
	{
		validate(row, col);
		int pos = (row - 1) * this.size + col;
		return this.uf_b.connected(top, pos);
	}

	public int numberOfOpenSites() // number of open sites
	{
		return this.openSite;
	}

	public boolean percolates() // does the system percolate?
	{
		return this.uf.connected(top, bottom);
	}
}
