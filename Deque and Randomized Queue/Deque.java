import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private class Node {
		Node next;
		Item item;
	}

	private Node top, bottom;
	private int size;

	public Deque() {
		top = null;
		bottom = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void addFirst(Item item) {
		if (item != null) {
			size++;
			if (top == null) {
				top = new Node();
				top.item = item;
				top.next = null;
				bottom = top;
			} else {
				Node temp = new Node();
				temp.item = item;
				temp.next = top;
				top.next = temp;
				top = temp;
			}
		} else
			throw new java.lang.IllegalArgumentException();
	}

	public void addLast(Item item) {
		if (item != null) {
			size++;
			if (bottom == null) {
				bottom = new Node();
				bottom.item = item;
				bottom.next = null;
				top = bottom;
			} else {
				Node temp = new Node();
				temp.item = item;
				temp.next = bottom;
				bottom = temp;
			}
		} else
			throw new java.lang.IllegalArgumentException();
	}

	public Item removeFirst() {
		Item res;
		if (!isEmpty()) {
			res = top.item;
			if (size == 1) {
				top = null;
				bottom = null;
			} else
				top = top.next;
			size--;
			return res;
		} else
			throw new NoSuchElementException();
	}

	public Item removeLast() {
		Item res;
		if (!isEmpty()) {

			res = bottom.item;
			if (size == 1) {
				top = null;
				bottom = null;
			} else
				bottom = bottom.next;
			size--;
			return res;
		} else
			throw new NoSuchElementException();
	}

	private class DequeIterator implements Iterator<Item> {

		@Override
		public boolean hasNext() {
			return !isEmpty();
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			else {
				return removeFirst();
			}
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new DequeIterator();
	}

}
