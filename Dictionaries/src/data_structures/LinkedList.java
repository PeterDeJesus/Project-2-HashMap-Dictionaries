package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.LinkedList.IteratorHelper;
import data_structures.LinkedList.Node;

/**
 * 
 * @author Peter De Jesus
 * 		   CS310 - Data Structures
 * 		   Assignment 2
 *
 * This code is the complete version of the Linked List data structure
 * that will be used by the hash class
 * Method includes: add, remove, contains, isEmpty.
 */

public class LinkedList<E> implements Iterable<E> {

	/**
	 * Creates the node for the linked list
	 * @param <E> generic type E
	 */
	@SuppressWarnings("hiding")
	class Node<E> {
		E data;
		@SuppressWarnings("rawtypes")
		Node next;
		public Node(E obj) {
			data = obj;
			next = null;
		}
	}
	
	private Node<E> head;
	private Node<E> tail;
	int currentSize;
	
	/**
	 * constructor for node
	 */
	public LinkedList() {
		head = tail = null;
		currentSize = 0;
	}
	
	/**
	 * Iterator class that goes thru each item
	 * in the linked list.
	 */
	class IteratorHelper implements Iterator<E> {
		Node<E> index;
		
		public IteratorHelper() {
			index = head;
		}
		
		public boolean hasNext() {
			return index != null;
		}
		
		@SuppressWarnings("unchecked")
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			E tmp = index.data;
			index = index.next;
			return tmp;
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Not Enabled");
		}
	}
	
	/** 
	 * Add an object to the list (specifically the front)
	 * @param obj the object to be added to the list
	 */
	public void add(E obj) {
		Node<E> newNode = new Node<E>(obj);
		newNode.next = head;
		head = newNode;
		if (currentSize==0)
			tail = newNode;
		currentSize++;
	}
	
	/**
	 * This method will go through the list and remove the
	 * desired object.
	 * @param obj the object that is to be remove
	 * @return true if remove is successful else false
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(E obj) {
		Node<E> curr = head;
		Node<E> prev = null;
		
		while (curr!=null && ((Comparable<E>)obj).compareTo(curr.data)!=0) {
			prev = curr;
			curr = curr.next;
		}
		if (curr==null)
			return false;
		if (curr==head)
			head = curr.next;
		else if (curr==tail) {
			tail = prev;
			prev.next = null;
		}
		else
			prev.next = curr.next;
		return true;
	}
	
	/**
	 * This method will go through the list and get
	 * the desired data.
	 * return data if found, else return null
	 * @param obj is the object that is to check
	 * @return the object that is returned
	 */
	@SuppressWarnings("unchecked")
	public E get(E obj) {
		Node<E> tmp = head;
		
		while (tmp!=null) {
			if(((Comparable<E>)obj).compareTo(tmp.data)==0) {
				return tmp.data;
			}
			tmp = tmp.next;
		}
		return null;
	}
	
	/** 
	 * Removes the first Object in the list and returns it.
	 * Returns null if the list is empty.
	 * @return the object removed.
	 */
	@SuppressWarnings("unchecked")
	public E removeFirst() {
		if (head==null)
			return null;
		E tmp = head.data;
		head = head.next;
		currentSize--;
		return tmp;
	}

	/** 
	 * return true if list is empty, else return false
	 * @return status of list (empty or not)
	 */
	public boolean isEmpty() {
		if (currentSize==0)
			return true;
		return false;
	}
	
	/**
	 * Returns the number of Objects in the list.
	 * @return the number of Objects currently in the list.
	 */
	public int size() {
		return currentSize;
	}
	
	/**
	 * Returns an Iterator of the values in the list, presented in
	 * the same order as the list.
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

}
