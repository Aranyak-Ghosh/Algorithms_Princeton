import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private class Node {
		Node next;
		Node prev;
		Item item;
	}

	private Node end, start;
	private int size;

	public Deque() {
		end = null;
		start = null;
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
			if (end == null) {
				end = new Node();
				end.item = item;
				start = end;
			} else {
				Node temp = new Node();
				temp.item = item;
				temp.next = start;
				start.prev = temp;
				start = temp;
			}
		} else
			throw new java.lang.IllegalArgumentException();
	}

	public void addLast(Item item) {
		if (item != null) {
			size++;
			if (start == null) {
				start = new Node();
				start.item = item;
				end = start;
			} else {
				Node temp = new Node();
				temp.item = item;
				temp.prev = end;
				end.next = temp;
				end = temp;
			}
		} else
			throw new java.lang.IllegalArgumentException();
	}

	public Item removeFirst() {
		Item res;
		if (!isEmpty()) {
			res = start.item;
			if (size == 1) {
				end = null;
				start = null;
			} else {
				start = start.next;
				start.prev = null;
			}
			size--;
			return res;
		} else
			throw new NoSuchElementException();
	}

	public Item removeLast() {
		Item res;
		if (!isEmpty()) {

			res = end.item;
			if (size == 1) {
				end = null;
				start = null;
			} else {
				end = end.prev;
				end.next = null;

			}
			size--;
			return res;
		} else
			throw new NoSuchElementException();
	}

	private class DequeIterator implements Iterator<Item> {

		private Node current;

		public DequeIterator() {
			current = start;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				Item item = current.item;
				current = current.next;
				return item;
			}
		}

	}

	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

}
