import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		int count = Integer.parseInt(args[0]);
		while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
			q.enqueue(StdIn.readString());
		}
		while (count > 0) {
			StdOut.println(q.dequeue());
			count--;
		}
	}

}
