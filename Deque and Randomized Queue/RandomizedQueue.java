import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] arr;
	private int size;

	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		size = 0;
		arr = (Item[]) new Object[1];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void enqueue(Item item) {
		if (item == null)
			throw new java.lang.IllegalArgumentException();
		else if (size == arr.length)
			resize(size * 2);
		arr[size++] = item;
	}

	public Item dequeue() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		else {
			Item res;
			int index = StdRandom.uniform(0, size);
			res = arr[index];
			for (int i = index; i < size - 1; i++)
				arr[i] = arr[i + 1];
			size--;
			if (size > 0 && size <= arr.length / 4)
				resize(arr.length / 2);
			return res;
		}
	}

	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++)
			temp[i] = arr[i];
		arr = temp;
	}

	public Item sample() {
		Item res;

		if (isEmpty())
			throw new java.util.NoSuchElementException();
		else {
			int index = StdRandom.uniform(0, size);
			res = arr[index];
		}

		return res;
	}

	private class RandomizedQueueIterator implements Iterator<Item> {

		public RandomizedQueueIterator() {

		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return size > 0;
		}

		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			return dequeue();
		}

	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new RandomizedQueueIterator();
	}

}
