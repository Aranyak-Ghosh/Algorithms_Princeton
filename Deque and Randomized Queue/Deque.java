import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private class Node {
		Node next;
		Node prev;
		Item item;
	}

	private Node last, first;
	private int size;

	public Deque() {
		last = null;
		first = null;
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
			Node temp = first;
			first = new Node();
			first.item = item;
			first.next = temp;
			if (last != null)
				first.next.prev = first;
			else
				last = first;
		} else
			throw new java.lang.IllegalArgumentException();
	}

	public void addLast(Item item) {
		if (item != null) {
			size++;
			Node temp = last;
			last = new Node();
			last.item = item;
			last.prev = temp;
			if (first != null)
				last.prev.next = last;
			else
				first = last;
		} else
			throw new java.lang.IllegalArgumentException();
	}

	public Item removeFirst() {
		Item res;
		if (!isEmpty()) {
			res = first.item;
			if (size == 1) {
				last = null;
				first = null;
			} else {
				first = first.next;
				first.prev = null;
			}
			size--;
			return res;
		} else
			throw new NoSuchElementException();
	}

	public Item removeLast() {
		Item res;
		if (!isEmpty()) {

			res = last.item;
			if (size == 1) {
				last = null;
				first = null;
			} else {
				last = last.prev;
				last.next = null;

			}
			size--;
			return res;
		} else
			throw new NoSuchElementException();
	}

	private class DequeIterator implements Iterator<Item> {

		private Node current;

		public DequeIterator() {
			current = first;
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
