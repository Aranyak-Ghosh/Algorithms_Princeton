import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] arr;
	private int size;

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
		swapItem();
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

	private void swapItem() {
		int i = StdRandom.uniform(size);
		Item temp = arr[i];
		arr[i] = arr[size - 1];
		arr[size - 1] = temp;
	}

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

		private int i;

		public RandomizedQueueIterator() {
			i = 0;
		}

		@Override
		public boolean hasNext() {
			return i < size;
		}

		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		@Override
		public Item next() {
			if (!isEmpty()) {
				return arr[i++];
			} else
				throw new java.util.NoSuchElementException();
		}

	}

	@Override
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

}
