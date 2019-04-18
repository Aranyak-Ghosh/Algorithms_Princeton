import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

	public static void main(String[] args) {
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		int count = Integer.parseInt(args[0]);
		while (!StdIn.isEmpty() && count > 0) {
			q.enqueue(StdIn.readString());
			count--;
		}
		while (!q.isEmpty()) {
			StdOut.println(q.dequeue());
			count--;
		}
	}

}
