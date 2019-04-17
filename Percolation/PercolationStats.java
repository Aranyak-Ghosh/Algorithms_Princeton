import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

	private double[] minOpen;
	private int trials;
	private double mean, stddev;

	public PercolationStats(int n, int trials) // perform trials independent experiments on an n-by-n grid
	{
		Percolation test;
		if (n <= 0)
			throw new java.lang.IllegalArgumentException(n + " must be greater than 0");
		else if (trials <= 0)
			throw new java.lang.IllegalArgumentException(trials + " must be greater than 0");
		else {
			minOpen = new double[trials];
			this.trials = trials;
			for (int i = 0; i < trials; i++) {
				test = new Percolation(n);
				while (!test.percolates()) {
					int row = StdRandom.uniform(1, n + 1);
					int col = StdRandom.uniform(1, n + 1);
					if (!test.isOpen(row, col))
						test.open(row, col);
				}

				this.minOpen[i] = ((double) test.numberOfOpenSites() / (n * n));
			}
			mean = StdStats.mean(this.minOpen);
			stddev = StdStats.stddev(this.minOpen);
		}
	}

	public double mean() // sample mean of percolation threshold
	{
		return mean;
	}

	public double stddev() // sample standard deviation of percolation threshold
	{
		return stddev;
	}

	public double confidenceLo() // low endpoint of 95% confidence interval
	{
		double lowConf = mean - (1.96 * stddev / Math.sqrt(this.trials));
		return lowConf;
	}

	public double confidenceHi() // high endpoint of 95% confidence interval
	{
		double highConf = mean + (1.96 * stddev / Math.sqrt(this.trials));
		return highConf;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length >= 2) {
			PercolationStats x = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			StdOut.print();
			StdOut.println("mean                    = " + x.mean());
			StdOut.println("stddev                  = " + x.stddev());
			StdOut.println("95% confidence interval = [" + x.confidenceLo() + ", " + x.confidenceHi() + "]");
		}

	}

}
